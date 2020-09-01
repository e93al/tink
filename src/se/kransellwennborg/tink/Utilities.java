package se.kransellwennborg.tink;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Abstract bean class for utility methods.
 */
public class Utilities {

	public static String formatTime(Date time) {
		String res = "";
		if (time != null) {
			SimpleDateFormat timeFormatter = new SimpleDateFormat("HHmm");
			res = timeFormatter.format(time);
		}
		return res;
	}

	public static String formatDate(Date inDate) {
		String res = "";
		if (inDate != null) {
			SimpleDateFormat dateFormatter = new SimpleDateFormat("yyMMdd");
			res = dateFormatter.format(inDate);
		}
		return res;
	}
	
	public static double calcDuration(Date startTime, Date endTime) {

		Calendar endCal = Calendar.getInstance();
		endCal.setTime(endTime);

		Calendar startCal = Calendar.getInstance();
		startCal.setTime(startTime);

		int endHour = endCal.get(Calendar.HOUR_OF_DAY);
		int endMinute = endCal.get(Calendar.MINUTE);
		int startHour = startCal.get(Calendar.HOUR_OF_DAY);
		int startMinute = startCal.get(Calendar.MINUTE);

		double duration = 0; // one decimal
		double durationTotMins = (endHour * 60 + endMinute) - (startHour * 60 + startMinute);

		duration = Math.ceil(durationTotMins / 6) * 6 / 60;
		
		return roundOneDec(duration); 
	}

	public static double roundOneDec(double inDouble) {
		double rounded = Math.round(inDouble * 10);
		return rounded / 10;		
	}

	public static double roundTwoDec(double inDouble) {
		double rounded = Math.round(inDouble * 100);
		return rounded / 100;		
	}

	public static int hrsToMins(double hrs) {
		int res = 0;
		res = (int) (hrs * 60.0);
		return res;
	}
	
	public static String decode (String input) {
		StringBuffer str = new StringBuffer(input);
		String[] in = {"Ã…", "Ã„", "Ã–", "Ã¥", "Ã¤", "Ã¶"};
		String[] out = {"Å", "Ä", "Ö", "å", "ä", "ö" };
		
		for (int i=0; i<in.length; i++) {
			int idx = 0;
			while (str.indexOf(in[i], idx) >= 0) {
				str.replace(idx, idx + in[i].length()-1, out[i]);				
			}
		}
		
		return str.toString();
	}
	
	public static String padNbr(int i) {
		String s = (new Integer(i)).toString();;
		if (i < 10 && i >=0) {
		}
			s = "0" + s;
		return s;
	}
	
	public static String esc(String s) {
		if (s!=null) {
			s = s.replaceAll("'", "''");
		}
		
		return s;
	}

}
