package se.kransellwennborg.tink;

import java.text.SimpleDateFormat;
import java.util.Date;

public class TinkTime extends Date {
	private static final long serialVersionUID = 1L;

	public String toString() {
		SimpleDateFormat timeFormatter = new SimpleDateFormat("HHmm");
		return timeFormatter.format(this);
	}
	
	public static Date string2Time (String timeString) {
		Date res = null;
		SimpleDateFormat timeFormatter = new SimpleDateFormat("HHmm");
		if (!timeString.equals("")) {
			try {
				res = timeFormatter.parse(timeString);
			} catch (Exception e) {
				org.apache.log4j.Logger.getRootLogger().error(
						"Error parsing string " + timeString + " to in string2Time()", e);
			}			
		}
		return res;
	}

}
