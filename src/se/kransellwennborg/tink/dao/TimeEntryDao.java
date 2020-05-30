package se.kransellwennborg.tink.dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import se.kransellwennborg.tink.Constants;
import se.kransellwennborg.tink.Logger;
import se.kransellwennborg.tink.Utilities;
import se.kransellwennborg.tink.actions.InvoiceActionBean;
import se.kransellwennborg.tink.beans.MonthStatEntry;
import se.kransellwennborg.tink.beans.TimeEntries;
import se.kransellwennborg.tink.beans.TimeEntry;

public class TimeEntryDao {

	/**
	 * reads a time entry from the DB and returns a populated object
	 * 
	 * @param id
	 * @return TimeEntry
	 */
	public TimeEntry read(int id) {
		TimeEntry te = new TimeEntry();
		try {

			Connection con = MysqlConnectionProvider.getNewConnection();

			Statement stmt = con.createStatement();

//			stmt.execute("SET NAMES '" + MysqlConnectionProvider.charset + "'");
			ResultSet result = stmt.executeQuery("SELECT * FROM time_items " + " WHERE id = ('" + id + "')");

			if (result.next()) {
				te.setCaseId(result.getString("case_id"));
				te.setCaseName(result.getString("case_name"));
				te.setClient(result.getString("client"));
				te.setComment(result.getString("comment"));
				te.setDate(result.getDate("the_date"));
				te.setDuration(result.getDouble("duration"));
				te.setStartTime(result.getTime("start_time"));
				te.setEndTime(result.getTime("end_time"));
				te.setIsInternal(result.getInt("is_internal") == 0 ? false : true);
				te.setRate(result.getInt("rate"));
				te.setRevenue(result.getInt("revenue"));
				te.setStaffAlias(result.getString("staff_alias"));
				te.setClientRef(result.getString("client_ref"));
				/* not fetched - is_update */
				Logger.debug(this.getClass(), "timeEntryBean is populated with id: " + te.getId());
			}
			stmt.close();
			con.close();
		} catch (Exception e) {
			org.apache.log4j.Logger.getRootLogger().error("Error in populate user bean!", e);
		}
		return te;
	}

	public void update(TimeEntry te) {
		Logger.debug(this.getClass(), "update()");
		try {

			Connection con = MysqlConnectionProvider.getNewConnection();

			Statement stmt = con.createStatement();

			te.esc();
			
//			stmt.execute("SET NAMES '" + MysqlConnectionProvider.charset + "'");
			String dbString = "UPDATE time_items SET " + "the_date='" + te.getDateString() + "', " + "start_time="
					+ te.getStartTimeString() + "00, " + "end_time=" + te.getEndTimeString() + "00, "
					+ "case_id=UPPER('" + te.getCaseId() + "'), " + "client='" + te.getClient() + "', " + "case_name='"
					+ te.getCaseName() + "', " + "rate='" + te.getRate() + "', " + "revenue='" + te.getRevenue()
					+ "', " + "duration='" + te.getDuration() + "', " + "comment='" + te.getComment() + "', "
					+ "staff_alias='" + te.getStaffAlias() + "', " + "is_internal=" + te.getIsInternal()+ " WHERE ID='" + te.getId() + "'";

			stmt.execute(dbString);
			Logger.debug(this.getClass(), "update() executed " + dbString);
			stmt.close();
			con.close();
		} catch (Exception e) {
			e.printStackTrace(System.out);
			org.apache.log4j.Logger.getRootLogger().error("Error in update to database!", e);
		}

	}

	public int add(TimeEntry te) {
		int id = 0;
		Logger.debug(this.getClass(), "add()");
		try {

			Connection con = MysqlConnectionProvider.getNewConnection();

			Statement stmt = con.createStatement();
//			stmt.execute("SET NAMES '" + MysqlConnectionProvider.charset + "'");

			te.esc();
			String dbString = "INSERT INTO time_items "
					+ "(the_date, start_time, end_time, case_id, client, case_name, rate, revenue, duration, comment,"
					+ " staff_alias, is_internal, is_update, invoice_id, client_ref) VALUES" + " ('"
					+ te.getDateString()
					+ "','"
					+ te.getStartTimeString()
					+ "00"
					+ "','"
					+ te.getEndTimeString()
					+ "00"
					+ "',UPPER('"
					+ te.getCaseId()
					+ "'),'"
					+ te.getClient()
					+ "','"
					+ te.getCaseName()
					+ "','"
					+ te.getRate()
					+ "','"
					+ te.getRevenue()
					+ "', '"
					+ te.getDuration()
					+ "', '"
					+ te.getComment()
					+ "', '" + te.getStaffAlias() + "', '" 
					+ (te.getIsInternal() ? "1" : "0") + "', '"
					+ (te.isUpdate() ? "1" : "0") + "', "
					+ te.getInvoiceId() + ", '"
					+ te.getClientRef() + "')";

			Logger.debug(this.getClass(), "add() about to execute " + dbString);
//			stmt.execute(dbString);
			stmt.execute(dbString, Statement.RETURN_GENERATED_KEYS);
			ResultSet rs = stmt.getGeneratedKeys();
			if (rs.next()) {
				id = rs.getInt(1);
			}

			stmt.close();
			con.close();
		} catch (Exception e) {
			e.printStackTrace(System.out);
			org.apache.log4j.Logger.getRootLogger().error("Error in add to database!", e);
		}
		return id;

	}

	/**
	 * deletes a time entry from the DB
	 * 
	 * @param id
	 */
	public void delete(int id) {
		try {

			Connection con = MysqlConnectionProvider.getNewConnection();

			Statement stmt = con.createStatement();
			stmt.execute("DELETE FROM time_items " + " WHERE id=" + id);
			stmt.close();
			con.close();
		} catch (Exception e) {
			org.apache.log4j.Logger.getRootLogger().error("Error in populate user bean!", e);
		}

	}

	/**
	 * Returns an ArrayList of uninvoiced cases, in the form of TimeEntry
	 * objects
	 * 
	 * @param staffAlias
	 * @return ArrayList<TimeEntry>
	 */
	public ArrayList<TimeEntry> getUninvoicedCases(String staffAlias, TimeEntries caller) {

		int totalRev = 0;
		
		String orderBy = "";
		
		if (caller.getSortOrder().equals (Constants.CASE_ID)) {
			orderBy = " ORDER BY case_id ASC";
		}

		if (caller.getSortOrder().equals (Constants.APPLICANT)) {
			orderBy = " ORDER BY client ASC";
		}

		if (caller.getSortOrder().equals (Constants.CASE_NAME)) {
			orderBy = " ORDER BY case_name ASC";
		}

		if (caller.getSortOrder().equals (Constants.AMOUNT)) {
			orderBy = " ORDER BY SUM(revenue) DESC";
		}

		if (caller.getSortOrder().equals (Constants.DATE)) {
			orderBy = " ORDER BY the_date DESC, end_time DESC";
		}

		ArrayList<TimeEntry> unInvoicedCases = new ArrayList<TimeEntry>();
		try {

			Connection con = MysqlConnectionProvider.getNewConnection();

			Statement stmt = con.createStatement();
//			stmt.execute("SET NAMES '" + MysqlConnectionProvider.charset + "'");
			String dbString = "SELECT case_id, SUM(revenue), client, case_name, the_date, end_time FROM time_items "
					+ " WHERE staff_alias ='" + staffAlias + "' AND (ISNULL(invoice_id) OR invoice_id = 0) "
					+ "GROUP BY case_id "
					+ orderBy;

			ResultSet result = stmt.executeQuery(dbString);
			Logger.debug(this.getClass(), "uninvoicedCases: " + dbString);

			while (result.next()) {
				TimeEntry unInvoicedCase = new TimeEntry();

				unInvoicedCase.setCaseId(result.getString("case_id"));

				if (result.getString("client") == null) {
					unInvoicedCase.setClient("");
				} else {
					unInvoicedCase.setClient(result.getString("client"));
				}
				if (result.getString("case_name") == null) {
					unInvoicedCase.setCaseName("");
				} else {
					unInvoicedCase.setCaseName(result.getString("case_name"));
				}
				unInvoicedCase.setRevenue(result.getInt("SUM(revenue)"));
				totalRev += result.getInt("SUM(revenue)");

				unInvoicedCases.add(unInvoicedCase);
			}
			result.close();
			stmt.close();
			con.close();
		} catch (Exception e) {
			org.apache.log4j.Logger.getRootLogger().error("Error in populate user bean!", e);
		}
		caller.setTotalRevenue(totalRev);
		return unInvoicedCases;
	}

	public ArrayList<TimeEntry> getTimeEntries(String staffAlias, Date date, TimeEntries entries) {
		double tmpTotal = 0.0;
		ArrayList<TimeEntry> timeEntries = new ArrayList<TimeEntry>();
		try {
			Connection con = MysqlConnectionProvider.getNewConnection();

			Statement stmt = con.createStatement();

			String sortString = "ORDER BY start_time";
			String dbString = "SELECT id, the_date, start_time, end_time, case_id, client, case_name, rate, revenue, duration,"
					+ " comment, staff_alias, rate, is_internal, invoice_id, client_ref "
					+ "FROM time_items WHERE staff_alias ='"
					+ staffAlias + "' AND the_date ='" + Utilities.formatDate(date) + "' " + sortString + " DESC";

//			stmt.execute("SET NAMES '" + MysqlConnectionProvider.charset + "'");
			ResultSet result = stmt.executeQuery(dbString);
			Logger.debug(getClass(), "getTimeEntries dbString: " + dbString);

			while (result.next()) {
				TimeEntry timeEntry = new TimeEntry();

				timeEntry.setId(result.getInt("id"));
				timeEntry.setStaffAlias(result.getString("staff_alias"));
				timeEntry.setCaseId(result.getString("case_id"));
				timeEntry.setClient(result.getString("client"));
				timeEntry.setCaseName(result.getString("case_name"));
				timeEntry.setDate(result.getDate("the_date"));
				timeEntry.setStartTime(result.getTime("start_time"));
				timeEntry.setEndTime(result.getTime("end_time"));
				timeEntry.setDuration(result.getDouble("duration"));
				timeEntry.setRate(result.getInt("rate"));
				timeEntry.setRevenue(result.getInt("revenue"));
				timeEntry.setComment(result.getString("comment"));
				timeEntry.setIsInternal(result.getInt("is_internal") == 0 ? false : true);
				timeEntry.setClientRef(result.getString("client_ref"));
				Logger.debug(this.getClass(), "timeEntry isInternal: " + timeEntry.getIsInternal());
				tmpTotal += result.getDouble("duration");

				Logger.debug(getClass(), "getting timeEntry with startTime: " + timeEntry.getStartTimeString());
				Logger.debug(getClass(), "data from DB: startTime: " + result.getTime("start_time"));

				timeEntries.add(timeEntry);
			}
			result.close();
			stmt.close();
			con.close();
		} catch (Exception e) {
			org.apache.log4j.Logger.getRootLogger().error("Error in populate user bean!", e);
		}

		Logger.debug(getClass(), "getTimeEntries  items: " + timeEntries.size() + " for theDate = " + date);
		double rounded = Math.round(tmpTotal * 10);
		tmpTotal = rounded / 10;
		entries.setTotalDuration(tmpTotal);
		return timeEntries;
	}

	public ArrayList<TimeEntry> getInvoiceTimeEntries(String caseId, InvoiceActionBean iab) {
		double durationTotal = 0.0;
		int	revenueTotal = 0;
		ArrayList<TimeEntry> timeEntries = new ArrayList<TimeEntry>();
		try {
			Connection con = MysqlConnectionProvider.getNewConnection();

			Statement stmt = con.createStatement();

//			stmt.execute("SET NAMES '" + MysqlConnectionProvider.charset + "'");
			String dbString = "SELECT * "
					+ "FROM time_items WHERE case_id ='"
					+ caseId + "' AND (ISNULL(invoice_id) OR invoice_id = 0) ORDER BY the_date";

			ResultSet result = stmt.executeQuery(dbString);
			Logger.debug(getClass(), "getInvoiceTimeEntries dbString: " + dbString);

			while (result.next()) {
				TimeEntry timeEntry = new TimeEntry();

				timeEntry.setId(result.getInt("id"));
				timeEntry.setStaffAlias(result.getString("staff_alias"));
				timeEntry.setCaseId(result.getString("case_id"));
				timeEntry.setClient(result.getString("client"));
				timeEntry.setCaseName(result.getString("case_name"));
				timeEntry.setDate(result.getDate("the_date"));
				timeEntry.setStartTime(result.getTime("start_time"));
				timeEntry.setEndTime(result.getTime("end_time"));
				timeEntry.setDuration(result.getDouble("duration"));
				timeEntry.setRate(result.getInt("rate"));
				timeEntry.setRevenue(result.getInt("revenue"));
				timeEntry.setComment(result.getString("comment"));
				timeEntry.setIsInternal(result.getInt("is_internal") == 0 ? false : true);
				timeEntry.setInclude(true);
				timeEntry.setClientRef(result.getString("client_ref"));
				durationTotal += result.getDouble("duration");
				revenueTotal += result.getInt("revenue");

				timeEntries.add(timeEntry);
			}
			result.close();
			stmt.close();
			con.close();
		} catch (Exception e) {
			org.apache.log4j.Logger.getRootLogger().error("Error in populate user bean!", e);
		}

		double rounded = Math.round(durationTotal * 10);
		durationTotal = rounded / 10;
		iab.setTotalDuration(durationTotal);
		iab.setAmount(revenueTotal);
		
		return timeEntries;
	}

	public Date getLastEndTime(String staffAlias, Date date) {
		Date res = Calendar.getInstance().getTime();

		try {

			Connection con = MysqlConnectionProvider.getNewConnection();

			Statement stmt = con.createStatement();

//			stmt.execute("SET NAMES '" + MysqlConnectionProvider.charset + "'");
			String dbString = "select end_time from time_items where staff_alias = '" + staffAlias
					+ "' and the_date = '" + Utilities.formatDate(date) + "' order by end_time desc";
			ResultSet result = stmt.executeQuery(dbString);
			Logger.debug(this.getClass(), "getLastEndTime() executed : " + dbString);

			if (result.next()) {
				res = result.getTime("end_time");
				Logger.debug(this.getClass(), "got last end time of: " + Utilities.formatTime(res));
			}
			stmt.close();
			con.close();
		} catch (Exception e) {
			org.apache.log4j.Logger.getRootLogger().error("Error in getting last end time!", e);
		}
		return res;
	}
	
	public void setInvoiceId (int entryId, int invId) {
		Logger.debug(this.getClass(), "setInvoiceId()");
		try {

			Connection con = MysqlConnectionProvider.getNewConnection();

			Statement stmt = con.createStatement();

			String dbString = "UPDATE time_items SET invoice_id = " + invId 
				+ " WHERE id=" + entryId;

			Logger.debug(this.getClass(), "setInvoiceId() to execute " + dbString);
			stmt.execute(dbString);
			stmt.close();
			con.close();
		} catch (Exception e) {
			e.printStackTrace(System.out);
			org.apache.log4j.Logger.getRootLogger().error("Error in update to database!", e);
		}		
	}
	
	public ArrayList<TimeEntry> getInvoiceEntries(int invId, InvoiceActionBean iab) {
		ArrayList<TimeEntry> timeEntries = new ArrayList<TimeEntry> () ;

		double durationTotal = 0.0;

		try {
			Connection con = MysqlConnectionProvider.getNewConnection();

			Statement stmt = con.createStatement();

			String dbString = "SELECT * "
					+ "FROM time_items WHERE invoice_id ='"
					+ invId + "' ORDER BY the_date";

			ResultSet result = stmt.executeQuery(dbString);
			Logger.debug(getClass(), "getInvoiceEntries dbString: " + dbString);

			while (result.next()) {
				TimeEntry timeEntry = new TimeEntry();

				timeEntry.setId(result.getInt("id"));
				timeEntry.setStaffAlias(result.getString("staff_alias"));
				timeEntry.setCaseId(result.getString("case_id"));
				timeEntry.setClient(result.getString("client"));
				iab.setClient(result.getString("client"));
				timeEntry.setCaseName(result.getString("case_name"));
				iab.setCaseName(result.getString("case_name"));
				timeEntry.setDate(result.getDate("the_date"));
				timeEntry.setStartTime(result.getTime("start_time"));
				timeEntry.setEndTime(result.getTime("end_time"));
				timeEntry.setDuration(result.getDouble("duration"));
				timeEntry.setRate(result.getInt("rate"));
				timeEntry.setRevenue(result.getInt("revenue"));
				timeEntry.setComment(result.getString("comment"));
				timeEntry.setIsInternal(result.getInt("is_internal") == 0 ? false : true);
				timeEntry.setInvoiceId(invId);
				timeEntry.setClientRef(result.getString("client_ref"));
				durationTotal += result.getDouble("duration");
				
				timeEntries.add(timeEntry);
			}
			result.close();
			stmt.close();
			con.close();
		} catch (Exception e) {
			org.apache.log4j.Logger.getRootLogger().error("Error in populate user bean!", e);
		}
		iab.setTotalDuration(durationTotal);

		return timeEntries;			
	}

	public TimeEntry getMetricsAfter (String staffAlias, Date date) {
		TimeEntry res = new TimeEntry();
		
		Logger.debug(this.getClass(), "getMetricsAfter()");
		try {

			Connection con = MysqlConnectionProvider.getNewConnection();

			Statement stmt = con.createStatement();

			String dbString = "select round(sum(duration),1) from time_items where staff_alias = '" +
				staffAlias + "' and the_date > '" + Utilities.formatDate(date)
				+ "' and is_internal = false;";

			Logger.debug(this.getClass(), "getHoursAfter() to execute " + dbString);
			ResultSet result = stmt.executeQuery(dbString);
			result.next();
			res.setDuration(result.getDouble(1));
			stmt.close();
			con.close();
			
		} catch (Exception e) {
			e.printStackTrace(System.out);
			org.apache.log4j.Logger.getRootLogger().error("Error in update to database!", e);
		}		
		return res;
	}
	
	public MonthStatEntry getMonthStats(int month, String staffAlias, int year) {
		MonthStatEntry result = new MonthStatEntry();
		Logger.debug(this.getClass(), "getMonthStats()");
		
		Calendar calNow = Calendar.getInstance();
			
		Calendar calStart = getCalendar(year, month - 1, 1);
		boolean priorYear = (calStart.get(Calendar.YEAR) > year ? true : false);

		int currentMonth = calNow.get(Calendar.MONTH);
		int currentDayInMonth = calNow.get(Calendar.DAY_OF_MONTH);
		
		Date dateStart = calStart.getTime();

		Calendar calEnd = getCalendar (year, month-1, calStart.getActualMaximum(Calendar.DAY_OF_MONTH)); //Calendar.getInstance();

		Logger.debug(this.getClass(), "input month: " + month + ", end of month for " + calEnd.get(Calendar.MONTH) + calStart.get(Calendar.MONTH)
			+ " gives " + calEnd.getActualMaximum(Calendar.DAY_OF_MONTH));
		
		
		Date dateEnd = calEnd.getTime();
		
		try {

			Connection con = MysqlConnectionProvider.getNewConnection();

			Statement stmt = con.createStatement();

			// hours first
			String dbString = "select round(sum(duration),1) from time_items where staff_alias = '" +
				staffAlias 
				+ "' and the_date >= '" + Utilities.formatDate(dateStart)
				+ "' and the_date <= '" + Utilities.formatDate(dateEnd)
				+ "' and is_internal = false;";

			Logger.debug(this.getClass(), "getMonthStats() to execute " + dbString);
			ResultSet rs = stmt.executeQuery(dbString);
			rs.next();
			result.setHrs(rs.getDouble(1));

			// average hours month
			double workDaysMonth = 0.0;
			if (month -1 < currentMonth || priorYear) {
				workDaysMonth = 22.0 * (((double) calEnd.get(Calendar.DAY_OF_MONTH)) / 30.0);			
			} else if (month - 1 == currentMonth) {
				workDaysMonth = ((double) currentDayInMonth * 22.0 / 30.0);							
			}
			
			result.setAvgHrs(Utilities.roundOneDec(result.getHrs()/workDaysMonth));
			result.setWorkDays((int)workDaysMonth);
			
			// now invoices
			dbString = "select sum(amount) from invoices where invoiced_by = '" +
				staffAlias 
				+ "' and date >= '" + Utilities.formatDate(dateStart)
				+ "' and date <= '" + Utilities.formatDate(dateEnd)
				+ "';";

			Logger.debug(this.getClass(), "getMonthStats() to execute " + dbString);
			rs = stmt.executeQuery(dbString);
			rs.next();
			result.setInvoiced(rs.getInt(1));
			
			stmt.close();
			con.close();
			
		} catch (Exception e) {
			e.printStackTrace(System.out);
			org.apache.log4j.Logger.getRootLogger().error("Error in update to database!", e);
		}		
		
		return result;
	}
	
	private Calendar getCalendar(int year, int month, int day) {
	    Calendar c = Calendar.getInstance();
	    c.set(Calendar.YEAR, year);
	    c.set(Calendar.MONTH, month);
	    c.set(Calendar.DAY_OF_MONTH, day);
	    return c;
	}
}
