package se.kransellwennborg.tink.dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;

import se.kransellwennborg.tink.Logger;
import se.kransellwennborg.tink.Utilities;
import se.kransellwennborg.tink.beans.PunchEntries;
import se.kransellwennborg.tink.beans.PunchEntry;

public class PunchEntryDao {

	/**
	 * reads a time entry from the DB and returns a populated object
	 * 
	 * @param id
	 * @return TimeEntry
	 */
	public PunchEntry read(int id) {
		PunchEntry pe = new PunchEntry();
		try {

			Connection con = MysqlConnectionProvider.getNewConnection();

			Statement stmt = con.createStatement();

			ResultSet result = stmt.executeQuery("SELECT * FROM punch_times " + " WHERE id = ('" + id + "')");

			if (result.next()) {
				pe.setUserName(result.getString("staff_alias"));
				pe.setDate(result.getDate("date"));
				pe.setPunchInTime(result.getTime("punch_in_time"));
				pe.setPunchOutTime(result.getTime("punch_out_time"));
				pe.setModifiedIn(result.getInt("modified_in") == 0 ? false : true);
				pe.setModifiedOut(result.getInt("modified_out") == 0 ? false : true);
				pe.setScheduledTime(result.getInt("isScheduledTime") == 0 ? false : true);
				pe.setDuration(result.getDouble("duration"));
			}
			stmt.close();
			con.close();
		} catch (Exception e) {
			org.apache.log4j.Logger.getRootLogger().error("Error in populate punch entry!", e);
		}
		return pe;
	}

	public void post(PunchEntry pe) {
		Logger.debug(this.getClass(), "post()");
		try {

			Connection con = MysqlConnectionProvider.getNewConnection();

			Statement stmt = con.createStatement();
			String dbString = "";
			
			if (pe.getId() > 0) {
				// update
				dbString = "UPDATE punch_times SET " 
				+ "staff_alias = '" + pe.getUserName()
				+ "', date = '" + Utilities.formatDate(pe.getDate())
				+ "', punch_in_time = '" + Utilities.formatTime(pe.getPunchInTime()) + "00"
				+ "', punch_out_time = '" + Utilities.formatTime(pe.getPunchOutTime()) + "00"
				+ "', modified_in = " + (pe.getModifiedIn() ? "1" : "0")
				+ ", modified_out = " + (pe.getModifiedOut() ? "1" : "0")
				+ ", isScheduledTime = " + (pe.getScheduledTime() ? "1" : "0")
				+ ", duration = " + pe.getDuration()
				+ " WHERE ID='" + pe.getId() + "'";
			}
			else {
				// insert
				dbString = "INSERT INTO punch_times (staff_alias, date, punch_in_time, punch_out_time, modified_in, modified_out, duration, isScheduledTime) VALUES"
					+ " ('" + pe.getUserName()
					+ "', '" + Utilities.formatDate(pe.getDate())
					+ "', '" + Utilities.formatTime(pe.getPunchInTime()) + "00"
					+ "', '" + Utilities.formatTime(pe.getPunchOutTime()) + "00"
					+ "', " + (pe.getModifiedIn() ? "1" : "0") 
					+ ", " + (pe.getModifiedOut() ? "1" : "0") 
					+ ", " + pe.getDuration() 
					+ ", " + (pe.getScheduledTime() ? "1" : "0") 
					+ ")";
			}

			Logger.debug(this.getClass(), "post() to execute " + dbString);
			stmt.execute(dbString);
			stmt.close();
			con.close();
		} catch (Exception e) {
			e.printStackTrace(System.out);
			org.apache.log4j.Logger.getRootLogger().error("Error in post punch entry to database!", e);
		}
	}
	
	public ArrayList<PunchEntry> getEntries(String staffAlias, PunchEntries caller) {

		double totalDuration = 0;
		ArrayList<PunchEntry> entries = new ArrayList<PunchEntry>();
		try {
			Connection con = MysqlConnectionProvider.getNewConnection();

			Statement stmt = con.createStatement();
			String dbString = "SELECT * FROM punch_times "
				+ " WHERE staff_alias ='" + staffAlias + "' ORDER BY date DESC, isScheduledTime";

			Logger.debug(this.getClass(), "getEntries: about to execute " + dbString);
			ResultSet rs = stmt.executeQuery(dbString);
			Logger.debug(this.getClass(), "getEntries: executed: " + dbString);

			while (rs.next()) {
				PunchEntry pe = new PunchEntry();

				pe.setId(rs.getInt("id"));
				pe.setUserName(rs.getString("staff_alias"));
				pe.setDate(rs.getDate("date"));
				pe.setPunchInTime(rs.getTime("punch_in_time"));
				pe.setPunchOutTime(rs.getTime("punch_out_time"));
				pe.setModifiedIn(rs.getInt("modified_in") == 0 ? false : true);
				pe.setModifiedOut(rs.getInt("modified_out") == 0 ? false : true);
				double duration = Utilities.roundOneDec(rs.getDouble("duration"));
				pe.setDuration(duration);
				totalDuration += duration;
				pe.setScheduledTime(rs.getInt("isScheduledTime") == 0 ? false : true);
				entries.add(pe);
			}

			rs.close();
			stmt.close();
			con.close();
		} catch (Exception e) {
			org.apache.log4j.Logger.getRootLogger().error("Error in get punch entries!", e);
		}
		caller.setTotalDuration(Utilities.roundOneDec(totalDuration));
		return entries;
	}
	public Date getLastDate(String staffAlias, Date date) {

		Date result = null;
		try {
			Connection con = MysqlConnectionProvider.getNewConnection();

			Statement stmt = con.createStatement();
			String dbString = "SELECT * from tink.punch_times WHERE staff_alias = '" + staffAlias
				+ "' and date < '"+ Utilities.formatDate(date) + "' order by date desc";

			ResultSet rs = stmt.executeQuery(dbString);
			Logger.debug(this.getClass(), "getLastDate: " + dbString);

			while (rs.next()) {
				result = rs.getDate("date");
			}
			rs.close();
			stmt.close();
			con.close();
		} catch (Exception e) {
			org.apache.log4j.Logger.getRootLogger().error("Error in get punch entries!", e);
		}
		return result;		
	}

	public PunchEntry getScheduledTimePe(String staffAlias, Date date) {
		PunchEntry pe = new PunchEntry();
		try {

			Connection con = MysqlConnectionProvider.getNewConnection();

			Statement stmt = con.createStatement();

			String dbString = "SELECT * FROM punch_times " + " WHERE staff_alias = '" + staffAlias 
			+ "' AND date = '"+ Utilities.formatDate(date) 
			+ "' AND isScheduledTime > 0";
			
			Logger.debug(this.getClass(), "getScheduledTimePe about to execute \n: " + dbString);
			ResultSet result = stmt.executeQuery(dbString);

			if (result.next()) {
				pe.setUserName(result.getString("staff_alias"));
				pe.setDate(result.getDate("date"));
				pe.setScheduledTime(result.getInt("isScheduledTime") == 0 ? false : true);
				pe.setDuration(result.getDouble("duration"));
			}
			else {
				pe = null;
			}
			stmt.close();
			con.close();
		} catch (Exception e) {
			org.apache.log4j.Logger.getRootLogger().error("Error in populate punch entry!", e);
		}
		return pe;
	}
	public void postAdjustment(PunchEntry pe) {
		Logger.debug(this.getClass(), "post_adjustment()");
		try {

			Connection con = MysqlConnectionProvider.getNewConnection();

			Statement stmt = con.createStatement();
			String dbString = "";
			dbString = "INSERT INTO punch_times (staff_alias, date, duration) VALUES"
					+ " ('" + pe.getUserName()
					+ "', '" + Utilities.formatDate(pe.getDate())
					+ "', " + pe.getDuration() 
					+ ")";

			Logger.debug(this.getClass(), "post_adjust() to execute " + dbString);
			stmt.execute(dbString);
			stmt.close();
			con.close();
		} catch (Exception e) {
			e.printStackTrace(System.out);
			org.apache.log4j.Logger.getRootLogger().error("Error in post punch entry to database!", e);
		}
	}
	
	public ArrayList<String> getHolidays() {

		ArrayList<String> holidays = new ArrayList<String>();

		try {

			Connection con = MysqlConnectionProvider.getNewConnection();

			Statement stmt = con.createStatement();

			String dbString = "SELECT * FROM holidays ORDER BY holiday_date ASC";
			
			ResultSet result = stmt.executeQuery(dbString);
			while (result.next()) {
				holidays.add(result.getString("holiday_date"));
			}
			stmt.close();
			con.close();
		} catch (Exception e) {
			org.apache.log4j.Logger.getRootLogger().error("Error in populate punch entry!", e);
		}
		return holidays;
	}
	
}
