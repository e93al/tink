package se.kransellwennborg.tink;


public class Logger {	
	public static void debug(Class<? extends Object> theClass, String message) {
		org.apache.log4j.Logger.getLogger(theClass).debug(message);
	}	

	public static void info(Class<? extends Object> theClass, String message) {
		org.apache.log4j.Logger.getLogger(theClass).info(message);
	}	

	public static void warn(Class<? extends Object> theClass, String message) {
		org.apache.log4j.Logger.getLogger(theClass).warn(message);
	}	
/*	
	public static void error(String message, Throwable t) {
		org.apache.log4j.Logger.getLogger(TimeEntry.class).error(message, t);
	}	
	*/

	public static void error(Class<? extends Object> theClass, String message) {
		// TODO Auto-generated method stub
		org.apache.log4j.Logger.getLogger(theClass).error(message);
		
	}
}
