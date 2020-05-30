package se.kransellwennborg.tink.dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

import se.kransellwennborg.tink.Logger;
import se.kransellwennborg.tink.beans.TimeEntry;

public class PatraWinDao {

	public static String[] getCaseData(TimeEntry timeEntry) {

		/**
		 * gets case data from PatraWin database. Returns a string array of
		 * length two. The first item is the applicant name and the second item
		 * is the case name.
		 * */
		String[] result = new String[3];
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

/* old query based on applicant				String query = "SELECT  ARENDE_1.SLAGORD, SOKANDE_39.SOKANDESKORTNAMN FROM SOKANDE_39 INNER JOIN "
						+ "SOKANDE_ARENDE_78 ON SOKANDE_39.SOKANDENR = SOKANDE_ARENDE_78.SOKANDENR "
						+ "CROSS JOIN ARENDE_1 "
						+ "WHERE   (ARENDE_1.ARENDENR = '"
						+ timeEntry.getCaseId()
						+ "') "
						+ "AND (SOKANDE_ARENDE_78.ARENDENR = '"
						+ timeEntry.getCaseId() 
						+ "')"; */
				
				String query = "SELECT ARENDE_1.SLAGORD, KUND_24.Kundadress1 "
				+ "FROM ARENDE_1, KUND_24 "
				+ "INNER JOIN KUND_ARENDE_25 ON KUND_24.Kundnr = KUND_ARENDE_25.Kundnr "
				+ "WHERE KUND_ARENDE_25.Arendenr = '" 
				+ timeEntry.getCaseId() 
				+ "' AND KUND_ARENDE_25.Kundtyp = 2 AND ARENDE_1.Arendenr = KUND_ARENDE_25.ARENDENR";
				
				Logger.debug(PatraWinDao.class, "About to query: " + query);

				ResultSet resultSet = stmt
						.executeQuery(query);


				while (resultSet.next()) {
// old based on applicant					String client = resultSet.getString("SOKANDESKORTNAMN");
//					String caseName = resultSet.getString("SLAGORD");
					String client = resultSet.getString("Kundadress1");
					String caseName = resultSet.getString("SLAGORD");
					timeEntry.setClient(client);
					timeEntry.setCaseName(caseName);
					Logger.debug(PatraWinDao.class, "Added -" + client + "- and -" + caseName + "'");
				}

//				stmt.close();
				
				/* get client ref.*/
				ResultSet resultSet2 = stmt
						.executeQuery("SELECT Ref "
								+ "FROM KUND_ARENDE_25 "
								+ "where Arendenr='" + timeEntry.getCaseId() + "' "
								+ "and Kundtyp = 1");

				while (resultSet2.next()) {
					timeEntry.setClientRef(resultSet2.getString("Ref"));
				}

				stmt.close();

				
				con.close();
				resultSet.close();
				resultSet2.close();
			} catch (Exception e) {
				e.printStackTrace(System.out);
			}

		}
		return result;
	}
	
	public static String getCreditData(TimeEntry timeEntry) {

		/**
		 * gets case data from PatraWin database. Returns a string array of
		 * length two. The first item is the applicant name and the second item
		 * is the case name.
		 * */
		String result = new String();
		if (MysqlConnectionProvider.host.equals("localhost")) {
			// development - don't connect to PW. Do nothing.
		} else {
			try {

				Connection con = SqlServerConnectionProvider.getNewConnection(
						SqlServerConnectionProvider.HOST,
						SqlServerConnectionProvider.DATABASE,
						SqlServerConnectionProvider.USER,
						SqlServerConnectionProvider.PASSWORD);

				Statement stmt = con.createStatement();

				ResultSet resultSet = stmt
						.executeQuery("SELECT KUND_24.kreditjn "
								+ "FROM KUND_24 "
								+ "INNER JOIN KUND_ARENDE_25 ON KUND_24.Kundnr = KUND_ARENDE_25.Kundnr "
								+ "WHERE KUND_ARENDE_25.Arendenr = '"
								+ timeEntry.getCaseId()
								+ "' AND KUND_ARENDE_25.Kundtyp = 2");

				while (resultSet.next()) {
					result = resultSet.getString("kreditjn");
				}

				stmt.close();
				con.close();
				resultSet.close();
			} catch (Exception e) {
				e.printStackTrace(System.out);
			}
		}
		return result;
	}
	
	public static String getCaseResponsible(String caseId) {

		/**
		 * gets attorney responsible from PatraWin database. Returns a string of the 
		 * attorney which is responsible. 
		 * */
		String result = new String();
		if (MysqlConnectionProvider.host.equals("localhost")) {
			// development - don't connect to PW. Do nothing.
			result = "AL";
		} else {
			try {
				Connection con = SqlServerConnectionProvider.getNewConnection(
						SqlServerConnectionProvider.HOST,
						SqlServerConnectionProvider.DATABASE,
						SqlServerConnectionProvider.USER,
						SqlServerConnectionProvider.PASSWORD);

				Statement stmt = con.createStatement();

				/******************************************
				 * TODO	
				 * ADD CORRECT SELECT STATEMENT
				 * 
				 * 
				 * **/
				ResultSet resultSet = stmt
						.executeQuery("SELECT KUND_24.kreditjn "
								+ "FROM KUND_24 "
								+ "INNER JOIN KUND_ARENDE_25 ON KUND_24.Kundnr = KUND_ARENDE_25.Kundnr "
								+ "WHERE KUND_ARENDE_25.Arendenr = '"
								+ "' AND KUND_ARENDE_25.Kundtyp = 2");

				while (resultSet.next()) {
					result = resultSet.getString("");
				}

				stmt.close();
				con.close();
				resultSet.close();
			} catch (Exception e) {
				e.printStackTrace(System.out);
			}
		}
		return result;
	}
	
	public static String getClientRef(String caseId) {

		/**
		 * gets attorney responsible from PatraWin database. Returns a string of the 
		 * attorney which is responsible. 
		 * */
		String result = new String();
		if (MysqlConnectionProvider.host.equals("localhost")) {
			// development - don't connect to PW. Do nothing.
			result = "AL";
		} else {
			try {

				Connection con = SqlServerConnectionProvider.getNewConnection(
						SqlServerConnectionProvider.HOST,
						SqlServerConnectionProvider.DATABASE,
						SqlServerConnectionProvider.USER,
						SqlServerConnectionProvider.PASSWORD);

				Statement stmt = con.createStatement();

				ResultSet resultSet = stmt
						.executeQuery("SELECT Ref "
								+ "FROM KUND_ARENDE_25 "
								+ "where Arendenr='" + caseId + "' "
								+ "and Kundtyp = 1");

				while (resultSet.next()) {
					result = resultSet.getString("Ref");
				}

				stmt.close();
				con.close();
				resultSet.close();
			} catch (Exception e) {
				e.printStackTrace(System.out);
			}
		}
		return result;
	}
	
	
	
	
}
