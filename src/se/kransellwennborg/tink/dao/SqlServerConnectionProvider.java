package se.kransellwennborg.tink.dao;

import java.sql.*;

public class SqlServerConnectionProvider {
/*	public static String HOST = "192.168.254.3";
	public static String DATABASE = "WENNBORG";
	public static String USER = "kw_java";
	public static String PASSWORD = "PixelP00";
	public static String HOST = "10.1.205.23";*/
	public static String HOST = "10.5.8.52";
	public static String DATABASE = "WENNBORG";
	public static String USER = "kw2";
	public static String PASSWORD = "kwtime";
	
	
	private static SqlServerConnectionProvider INST;

	private SqlServerConnectionProvider() throws ClassNotFoundException {
//		Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
		Class.forName("net.sourceforge.jtds.jdbc.Driver");
	}

	public static Connection getNewConnection(String host, String database,
			String user, String password) throws SQLException,
			ClassNotFoundException {
		if (INST == null) {
			INST = new SqlServerConnectionProvider();
		}
		return INST.createConnection(host, database, user, password);
	}

	public Connection createConnection(String host, String database,
			String user, String password) throws SQLException {
//		String url = new String("jdbc:sqlserver://" + host + "/" + database);

//		String url = "jdbc:sqlserver://" + host + ":1433;databaseName=" + database + ";user=" + user 
//				+ ";password=" + password + ";";
		String url = "jdbc:jtds:sqlserver://" + host + ":1433/" + database;
		return DriverManager.getConnection(url, user, password);

	}

}
