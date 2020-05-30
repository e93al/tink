package se.kransellwennborg.tink;

import java.text.SimpleDateFormat;
import java.util.Date;

public class TinkDate extends Date {
	private static final long serialVersionUID = 1L;

	public static final int MS_IN_DAY = 24*60*60*1000;
	
	public TinkDate(Date date) {
		super(date.getTime());
	}
	
	public TinkDate(long time) {
		super(time);
	}

	public String toString() {
		SimpleDateFormat dateFormatter = new SimpleDateFormat("yyMMdd");
		return dateFormatter.format(this);
	}
	
	public static Date string2Date (String dateString) {
		Date res = null;
		SimpleDateFormat dateFormatter = new SimpleDateFormat("yyMMdd");
		if (!dateString.equals("")) {
			try {
				res = dateFormatter.parse(dateString);
			} catch (Exception e) {
				org.apache.log4j.Logger.getRootLogger().error(
						"Error parsing string " + dateString + " in  string2Date()", e);
			}			
		}
		return res;
	}
	
	public String getDayBefore () {
		TinkDate dayBefore = new TinkDate(this.getTime() - MS_IN_DAY);		
		return dayBefore.toString();
	}

	public String getDayAfter () {
		TinkDate dayBefore = new TinkDate(this.getTime() + MS_IN_DAY);		
		return dayBefore.toString();
	}
}
