package se.kransellwennborg.tink.dao;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class MysqlConnectionProvider {
	public static String host = "";
	public static String user = "";
	public static String password = "";
	public static String database = "KW_time";
	public static String charset = "utf8";
//	public static String HOST = "localhost";
//	public static String USER = "root";
//	public static String PASSWORD = "pixelpoo";


	private static MysqlConnectionProvider INST;

	private MysqlConnectionProvider() throws ClassNotFoundException {
		Class.forName("com.mysql.jdbc.Driver");
	}

	public static Connection getNewConnection() throws SQLException,
			ClassNotFoundException {
		if (INST == null) {
			INST = new MysqlConnectionProvider();
		}
		Connection conn = INST.createConnection();

		return conn;
	}

	public Connection createConnection() throws SQLException {
		if (MysqlConnectionProvider.host.equals("")) {
			populateDbData();
		}			
		
		String url = new String("jdbc:mysql://" + host + "/" + database + "?characterEncoding=" + charset);
		return DriverManager.getConnection(url, user, password);
	}
	
	public void populateDbData(){
		MysqlConnectionProvider.host = "localhost";
		MysqlConnectionProvider.user = "root";
		MysqlConnectionProvider.password = "pixelpoo";
		
		String path = System.getProperty("catalina.base")
			+ File.separator
			+ "db.properties";

		FileInputStream fis;
		Properties props = new Properties();
		try {
			fis = new FileInputStream(path);
			props.load(fis);
			fis.close();
			MysqlConnectionProvider.host = props.getProperty("host");
			MysqlConnectionProvider.user = props.getProperty("user");
			MysqlConnectionProvider.password = props.getProperty("password");
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			org.apache.log4j.Logger.getRootLogger().warn(
					"---- MysqlConnectionProvider: Error reading DB properties file: " + path + ". Using local db.");
		}
		catch (IOException e) {
			// TODO Auto-generated catch block
			org.apache.log4j.Logger.getRootLogger().warn(
					"---- MysqlConnectionProvider: IO Error ");
		}
		
	}

}
