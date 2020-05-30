package se.kransellwennborg.tink.dao;


public class TimeBalanceDao {

	/**
	 * reads a day balance entry from the DB and returns a populated object
	 * 
	 * @param id
	 * @return TimeEntry
//	 */
//	public TimeBalanceEntry read(int id) {
//		TimeBalanceEntry tbe = new TimeBalanceEntry();
//		try {
//
//			Connection con = MysqlConnectionProvider.getNewConnection();
//
//			Statement stmt = con.createStatement();
//
//			ResultSet result = stmt.executeQuery("SELECT * FROM time_balance " + " WHERE id = ('" + id + "')");
//
//			if (result.next()) {
//				tbe.setUserName(result.getString("staff_alias"));
//				tbe.setDate(result.getDate("date"));
//				tbe.setNormalTime(result.getDouble("normal_time"));
//				tbe.setActualTime(result.getDouble("actual_time"));
//				tbe.setDayBalance(result.getDouble("day_balance"));
//			}
//			stmt.close();
//			con.close();
//		} catch (Exception e) {
//			org.apache.log4j.Logger.getRootLogger().error("Error in populate time balance entry!", e);
//		}
//		return tbe;
//	}
//	
//	public TimeBalanceEntry read(String staffAlias, Date date) {
//		TimeBalanceEntry tbe = null;
//		try {
//
//			Connection con = MysqlConnectionProvider.getNewConnection();
//
//			Statement stmt = con.createStatement();
//
//			String dbString = "SELECT * FROM time_balance " + " WHERE staff_alias = '" + staffAlias +
//					"' AND date = '" + Utilities.formatDate(date) + "'";
//
//			Logger.debug(this.getClass(), "read() about to execute \n" + dbString);
//			ResultSet result = stmt.executeQuery(dbString);
//
//			if (result.next()) {
//				tbe = new TimeBalanceEntry();
//				tbe.setUserName(result.getString("staff_alias"));
//				tbe.setDate(result.getDate("date"));
//				tbe.setNormalTime(result.getDouble("normal_time"));
//				tbe.setActualTime(result.getDouble("actual_time"));
//				tbe.setDayBalance(result.getDouble("day_balance"));
//				tbe.setId(result.getInt("id"));
//			}
//			stmt.close();
//			con.close();
//		} catch (Exception e) {
//			org.apache.log4j.Logger.getRootLogger().error("Error in populate time balance entry!", e);
//		}
//		return tbe;
//	}
//	
//	public ArrayList<TimeBalanceEntry> getEntries(String staffAlias) {
//
//		ArrayList<TimeBalanceEntry> entries = new ArrayList<TimeBalanceEntry>();
//		try {
//			Connection con = MysqlConnectionProvider.getNewConnection();
//
//			Statement stmt = con.createStatement();
//			String dbString = "SELECT * FROM time_balance " + " WHERE staff_alias = '" + staffAlias +
//			"'";
//
//
//			ResultSet rs = stmt.executeQuery(dbString);
//			Logger.debug(this.getClass(), "getEntries: " + dbString);
//
//			while (rs.next()) {
//				TimeBalanceEntry tbe = new TimeBalanceEntry();
//
//				tbe.setDate(rs.getDate("date"));
//				tbe.setNormalTime(rs.getDouble("normal_time"));
//				tbe.setActualTime(rs.getDouble("actual_time"));
//				tbe.setDayBalance(rs.getDouble("day_balance"));
//				tbe.setAbsent(rs.getInt("absent") == 0 ? false : true);
//				tbe.setAbsentReason(rs.getString("absent_reason"));
//				entries.add(tbe);
//			}
//
//			rs.close();
//			stmt.close();
//			con.close();
//		} catch (Exception e) {
//			org.apache.log4j.Logger.getRootLogger().error("Error in get punch entries!", e);
//		}
//		return entries;
//	}
//
//	
//
//	public void post(TimeBalanceEntry tbe) {
//		Logger.debug(this.getClass(), "post()");
//		try {
//
//			Connection con = MysqlConnectionProvider.getNewConnection();
//
//			Statement stmt = con.createStatement();
//			String dbString = "";
//			
//			if (tbe.getId() > 0) {
//				// update
//				dbString = "UPDATE time_balance SET " 
//				+ "staff_alias = '" + tbe.getUserName()
//				+ "', date = '" + Utilities.formatDate(tbe.getDate())
//				+ "', normal_time = " + tbe.getNormalTime()
//				+ ", actual_time = " + tbe.getActualTime()
//				+ ", day_balance = " + tbe.getDayBalance()
//				+ " WHERE ID='" + tbe.getId() + "'";
//			}
//			else {
//				// insert
//				dbString = "INSERT INTO time_balance (staff_alias, date, normal_time, actual_time, day_balance) VALUES"
//					+ " ('" + tbe.getUserName()
//					+ "', '" + Utilities.formatDate(tbe.getDate())
//					+ "', " + tbe.getNormalTime()
//					+ ", " + tbe.getActualTime()
//					+ ", " + tbe.getDayBalance() + ")";
//			}
//
//			Logger.debug(this.getClass(), "post() to execute " + dbString);
//			stmt.execute(dbString);
//			stmt.close();
//			con.close();
//		} catch (Exception e) {
//			e.printStackTrace(System.out);
//			org.apache.log4j.Logger.getRootLogger().error("Error in post punch entry to database!", e);
//		}
//	}
}
