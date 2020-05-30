package se.kransellwennborg.tink.beans;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import se.kransellwennborg.tink.dao.PunchEntryDao;

public class PunchEntries {

	private double totalDuration;
	private ArrayList<PunchEntry> punchEntries;
	private String dateString;	
	private String staffAlias;

	public String getDateString() {
		return dateString;
	}

	public void setDateString(String dateString) {
		this.dateString = dateString;
	}

	public double getTotalDuration() {
		return totalDuration;
	}

	public void setTotalDuration(double totalDuration) {
		this.totalDuration = totalDuration;
	}

	public String getStaffAlias() {
		return staffAlias;
	}

	public void setStaffAlias(String staffAlias) {
		this.staffAlias = staffAlias;
	}

	public void setPunchEntries(ArrayList<PunchEntry> punchEntries) {
		this.punchEntries = punchEntries;
	}
	
	public ArrayList<PunchEntry> getPunchEntries() {
		PunchEntryDao ped = new PunchEntryDao();
		//todo fix
		punchEntries = ped.getEntries(staffAlias, this);
		
		return punchEntries;
	}
	
	public Date stringToDate(String dateString) {
		SimpleDateFormat dateFormatter = new SimpleDateFormat("yyMMdd");
		Date res = null;
		if (!dateString.equals("")) {
			try {
				res = dateFormatter.parse(dateString);
			} catch (Exception e) {
				org.apache.log4j.Logger.getRootLogger().error(
						"Error parsing string " + dateString + " to Date in stringToDate()", e);
			}			
		}
		return res;
	}

}
