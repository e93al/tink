package se.kransellwennborg.tink.dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

import se.kransellwennborg.tink.Logger;
import se.kransellwennborg.tink.Utilities;
import se.kransellwennborg.tink.beans.User;

public class StaffDao {

	/**
	 * gets the rate for a particular staff member identified by the alias
	 * 
	 * @param alias
	 * @return int
	 */
	public int getRate(String alias) {
		int result = 0;
		try {

			Connection con = MysqlConnectionProvider.getNewConnection();

			Statement stmt = con.createStatement();

			ResultSet rs = stmt.executeQuery("SELECT * FROM staff " + " WHERE LOWER(alias) = LOWER('" + alias + "')");

			if (rs.next()) {
				result = rs.getInt("rate");
				Logger.debug(this.getClass(), " getRate() go");
			}
			stmt.close();
			con.close();
		} catch (Exception e) {
			org.apache.log4j.Logger.getRootLogger().error("Error in populate user bean!", e);
		}
		return result;
	}

	public User getUserData(String alias) {
		User result = null;
		int rctr;
		try{

			Connection con = MysqlConnectionProvider.getNewConnection();
	
			Statement stmt = con.createStatement();
	
			ResultSet rs = stmt.executeQuery("SELECT * FROM staff " 
					+ " WHERE lower(alias) = lower('" + alias + "')");
			
			if (rs.next()) {
				result = new User();
				result.setRate (rs.getInt("rate"));
				result.setFullName (rs.getString("full_name"));
				result.setUserName(alias);
				result.setWorkPercent(rs.getInt("work_percent"));
				result.setPunchedIn(rs.getInt("is_punchedin") == 0 ? false : true);
				result.setPunchInDate(rs.getDate("punch_in_date"));
				result.setPunchInTime(rs.getTime("punch_in_time"));
				result.setTimeBalance(rs.getDouble("time_balance"));
				result.setModifiedPunchedIn(rs.getInt("is_modified_punchedin") == 0 ? false : true);
				result.setAttorney(rs.getInt("is_attorney") == 1 ? true : false);
				result.setShowInStats(rs.getInt("show_in_stats") == 1 ? true : false);
				result.setAccountCode(rs.getInt("account_code"));
				rctr = rs.getInt("revenue_centre");
				if (rctr < 10) {
					result.setRevenueCentre("0" + Integer.toString(rctr));
				}
				else{
					result.setRevenueCentre(Integer.toString(rctr));					
				}

				org.apache.log4j.Logger.getLogger(this.getClass()).debug("user bean is populated !");
			}
		}
		catch (Exception e) {
			org.apache.log4j.Logger.getRootLogger().error("Error in populate user bean!", e);
		}
		return result;
	}

	public double getTimeBalance(String alias) {
	
		double result = 0.0;

		try{

			Connection con = MysqlConnectionProvider.getNewConnection();
	
			Statement stmt = con.createStatement();
	
			ResultSet rs = stmt.executeQuery("SELECT SUM(duration) from punch_times WHERE staff_alias = '" + alias + "'");
			
			if (rs.next()) {
				result = rs.getDouble(1);
			}
		}
		catch (Exception e) {
			org.apache.log4j.Logger.getRootLogger().error("Error in populate user bean!", e);
		}
		return result;
	}

	public void updateUserData(User user) {
		try{

			Connection con = MysqlConnectionProvider.getNewConnection();
	
			Statement stmt = con.createStatement();
	
			String dbString = "UPDATE staff SET"
				+ " rate=" + user.getRate()
				+ ", full_name='" + user.getFullName()
				+ "', work_percent=" + user.getWorkPercent()
				+ ", is_punchedin=" + (user.isPunchedIn() ? 1 : 0)
				+ ", punch_in_date='" + Utilities.formatDate(user.getPunchInDate())
				+ "', punch_in_time='" + Utilities.formatTime(user.getPunchInTime()) + "00"
				+ "', time_balance=" + user.getTimeBalance()
				+ ", is_modified_punchedin=" + (user.isModifiedPunchedIn() ? 1 : 0)
				+ " WHERE lower(alias) = lower('" + user.getUserName() + "')";
			
			Logger.debug(this.getClass(), "updateUserData() to execute " + dbString);
			stmt.execute(dbString);		
			stmt.close();
			con.close();
		}
		catch (Exception e) {
			org.apache.log4j.Logger.getRootLogger().error("Error in populate user bean!", e);
		}
	}

/*	public ArrayList<String> getAttorneys() {
		ArrayList<String> attorneys = new ArrayList<String>();
		
		try{

			Connection con = MysqlConnectionProvider.getNewConnection();
	
			Statement stmt = con.createStatement();
	
			ResultSet rs = stmt.executeQuery("SELECT alias FROM staff WHERE is_attorney = 1 ORDER BY alias");

			while (rs.next()) {				
				attorneys.add(rs.getString("alias"));
				Logger.debug(this.getClass(), " getAttorneys()");
			}
			stmt.close();
			con.close();
		} catch (Exception e) {
			org.apache.log4j.Logger.getRootLogger().error("Error in get attorneys!", e);
		}
		return attorneys;
	}
*/
	public ArrayList<String> getStatsStaff() {
		ArrayList<String> attorneys = new ArrayList<String>();
		
		try{

			Connection con = MysqlConnectionProvider.getNewConnection();
	
			Statement stmt = con.createStatement();
	
			ResultSet rs = stmt.executeQuery("SELECT alias FROM staff WHERE show_in_stats = 1 ORDER BY alias");

			while (rs.next()) {				
				attorneys.add(rs.getString("alias"));
				Logger.debug(this.getClass(), " getStaffList()");
			}
			stmt.close();
			con.close();
		} catch (Exception e) {
			org.apache.log4j.Logger.getRootLogger().error("Error in get staff list!", e);
		}
		return attorneys;
	}
}
