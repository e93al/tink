package se.kransellwennborg.tink.beans;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import se.kransellwennborg.tink.Utilities;
import se.kransellwennborg.tink.beans.ProgressTableSorter.SortCol;
import se.kransellwennborg.tink.beans.ProgressTableSorter.SortDirection;
import se.kransellwennborg.tink.dao.TimeEntryDao;

public class TimeEntries {

	private String dateString;
	private String sortOrder;
	

	public String getDateString() {
		return dateString;
	}

	public void setDateString(String dateString) {
		this.dateString = dateString;
	}

	private String staffAlias;

	public String getstaffAlias() {
		return staffAlias;
	}

	public void setstaffAlias(String staffAlias) {
		this.staffAlias = staffAlias;
	}

	private double totalDuration;

	private int totalRevenue;
	
	
	public int getTotalRevenue() {
		return totalRevenue;
	}

	public void setTotalRevenue(int totalRevenue) {
		this.totalRevenue = totalRevenue;
	}

	public String getStaffAlias() {
		return staffAlias;
	}

	public void setStaffAlias(String staffAlias) {
		this.staffAlias = staffAlias;
	}

	public double getTotalDuration() {
		return Utilities.roundOneDec(totalDuration);
	}

	public void setTotalDuration(double totalDuration) {
		this.totalDuration = totalDuration;
	}

	private ArrayList<TimeEntry> timeEntries;
	private ArrayList<TimeEntry> uninvoicedEntries;

	public ArrayList<TimeEntry> getUninvoicedEntries() {

		TimeEntryDao teDao = new TimeEntryDao();
		uninvoicedEntries = teDao.getUninvoicedCases(staffAlias, this);
		
		return uninvoicedEntries;
	}

	public void setUninvoicedEntries(ArrayList<TimeEntry> uninvoicedEntries) {
		this.uninvoicedEntries = uninvoicedEntries;
	}

	public ArrayList<TimeEntry> getTimeEntries() {

		Date date = stringToDate(dateString);
		if (dateString.equals("")) {
			date = Calendar.getInstance().getTime();
		}
		TimeEntryDao teDao = new TimeEntryDao();
		timeEntries = teDao.getTimeEntries(staffAlias, date, this);
		
		return timeEntries;
	}

	public void setTimeEntries(ArrayList<TimeEntry> timeEntries) {
		this.timeEntries = timeEntries;
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

	private ProgressTableSorter progressTableSorter;
	public ProgressTableSorter getProgressTableSorter() {
		if (progressTableSorter == null) {
			progressTableSorter = new ProgressTableSorter();
			progressTableSorter.sortDirection = SortDirection.ASC;
			progressTableSorter.sortCol = SortCol.CLIENT;
		}
		return progressTableSorter;
	}

	public void setProgressTableSorter(ProgressTableSorter progressTableSorter) {
		this.progressTableSorter = progressTableSorter;
	}

	public String getSortOrder() {
		return sortOrder;
	}

	public void setSortOrder(String sortOrder) {
		this.sortOrder = sortOrder;
	}


}
