package se.kransellwennborg.tink.dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

import se.kransellwennborg.tink.Logger;
import se.kransellwennborg.tink.Utilities;
import se.kransellwennborg.tink.actions.InvoiceActionBean;
import se.kransellwennborg.tink.actions.InvoicesActionBean;
import se.kransellwennborg.tink.beans.PWInvoiceLine;
import se.kransellwennborg.tink.beans.TimeEntry;

public class InvoiceDao {

	public void addInvoice(InvoiceActionBean iab) {
		try {

			Connection con = MysqlConnectionProvider.getNewConnection();

			TimeEntry te = new TimeEntry();
			te.setDateToday();

			Statement stmt = con.createStatement();

			String dbString = "INSERT INTO invoices " + "(invoiced_by, case_id, date, amount, client, fixed_fee) VALUES"
					+ " ('" + iab.getContext().getUser().getUserName() + "','" + iab.getCaseId() + "','"
					+ te.getDateString() + "'," + iab.getAmount() + ", '" + iab.getClient() + "', 0)";

			Logger.debug(this.getClass(), "add() to execute " + dbString);
			stmt.execute(dbString, Statement.RETURN_GENERATED_KEYS);
			ResultSet rs = stmt.getGeneratedKeys();
			if (rs.next()) {
				iab.setId(rs.getInt(1));
			}
			if (rs!= null) {
				rs.close();
			}
			stmt.close();
			con.close();

		} catch (Exception e) {
			org.apache.log4j.Logger.getRootLogger().error("Error in add invoice !", e);
			e.printStackTrace(System.out);
		}
	}

	public ArrayList<InvoiceActionBean> getEntries(InvoicesActionBean iab) {

		ArrayList<InvoiceActionBean> entries = new ArrayList<InvoiceActionBean>();
		try {
			Connection con = MysqlConnectionProvider.getNewConnection();

			Statement stmt = con.createStatement();
			String dbString = "SELECT * FROM invoices "
				+ " WHERE invoiced_by ='" + iab.getInvoicedBy() + "' ORDER BY date DESC";

			ResultSet rs = stmt.executeQuery(dbString);
			Logger.debug(this.getClass(), "getEntries: " + dbString);

			while (rs.next()) {
				InvoiceActionBean invoice = new InvoiceActionBean();
				
				invoice.setId(rs.getInt("id"));
				invoice.setInvoicedBy(iab.getInvoicedBy());
				invoice.setCaseId(rs.getString("case_id"));
				invoice.setDate(rs.getDate("date"));
				invoice.setAmount(rs.getInt("amount"));	
				invoice.setClient(rs.getString("client"));

				entries.add(invoice);
			}
			if (rs!= null) {
				rs.close();
			}
			
			stmt.close();
			con.close();
		} catch (Exception e) {
			org.apache.log4j.Logger.getRootLogger().error("Error in getEntries!", e);
			e.printStackTrace(System.out);

		}
		return entries;
	}

	public void getInvoice(InvoiceActionBean iab) {

		try {
			Connection con = MysqlConnectionProvider.getNewConnection();

			Statement stmt = con.createStatement();
			String dbString = "SELECT * FROM invoices "
				+ " WHERE id ='" + iab.getId() + "'";

			ResultSet rs = stmt.executeQuery(dbString);
			Logger.debug(this.getClass(), "getInvoice: " + dbString);

			while (rs.next()) {
				iab.setId(rs.getInt("id"));
				iab.setInvoicedBy(iab.getInvoicedBy());
				iab.setCaseId(rs.getString("case_id"));
				iab.setDate(rs.getDate("date"));
				iab.setAmount(rs.getInt("amount"));	
			}
			if (rs!= null) {
				rs.close();
			}
			stmt.close();
			con.close();
		} catch (Exception e) {
			org.apache.log4j.Logger.getRootLogger().error("Error in getInvoice!", e);
			e.printStackTrace(System.out);
		}
	}
	public String postPWInvoiceLine (PWInvoiceLine pwil) {
		
		String result = new String();
		String dbString = new String();
		
		pwil.esc();
		
		dbString = "DECLARE @nr integer; DECLARE @kundnr integer; DECLARE @momsjn nvarchar(1); DECLARE @Userid int;" + 
				"SELECT @nr = Nrserie FROM dbo.NUMMERSERIER_57 WHERE Nrtyp ='1'; UPDATE dbo.NUMMERSERIER_57  " +
				"SET Nrserie = (@nr + 1) WHERE Nrtyp ='1';" +
				"SELECT @kundnr = Kundnr FROM dbo.KUND_ARENDE_25 WHERE Arendenr = '" + pwil.getCaseId()+ 
				"' and Kundtyp = 2;" + 
				"SELECT @momsjn = Momsjn from dbo.KUND_24 WHERE Kundnr = @kundnr;" +	
				"SELECT @Userid = Id FROM dbo.BEHORIG_50 WHERE Username = '" + pwil.getStaffId() + "'; " + 
				"INSERT INTO dbo.ATGARDER_120 (Id, Arendenr, Datum, Belopp, Atgardstext, Atgardsnr, Tid, " +
				"Timarvode, Intaktstalle, Kundnr, Regtid, Direktord, Debiterasjn, Valutakod, Transid, " +
				"Momsjn, Kostnadsbarare, Kontonr, Fastpris) " +
				"VALUES (@userid, '" + pwil.getCaseId()+ "', '" + Utilities.formatDate(pwil.getDate()) + 
				"', " + pwil.getAmount() + ",  '" + pwil.getComment() + "', " + pwil.getAccountCode() + 
				",  " + pwil.getTimeInMinutes() + ", " + pwil.getRate() + ", '" + pwil.getRevenueCentre()+ 
				"',  @kundnr,  getdate(), 0,  'J', 'SEK', "+  
				"@nr + 1,  @momsjn, 99, 3000, 1)";
		
		
		Logger.debug(this.getClass(), "in postPWInvoiceLine, dbstring = \n   " + dbString);
		
		if (MysqlConnectionProvider.host.equals("localhost")) {
			// development - don't connect to PW. Do nothing.
		} else {
			// production - connect to PW
			try {

				Connection con = SqlServerConnectionProvider.getNewConnection(
						SqlServerConnectionProvider.HOST,
						SqlServerConnectionProvider.DATABASE,
						SqlServerConnectionProvider.USER,
						SqlServerConnectionProvider.PASSWORD);

				Statement stmt = con.createStatement();

				stmt.execute(dbString);

				ResultSet rs = stmt.getResultSet();
				stmt.close();
				con.close();
				if (rs!= null) {
					rs.close();
				}
			} catch (Exception e) {
				Logger.error(this.getClass(), "SQL PW Error! : " + e);
				e.printStackTrace(System.out);
			}

		}
/*		try {
			Thread.sleep(20);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}*/
		
		return result;	
	}
}
