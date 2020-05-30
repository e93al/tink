package se.kransellwennborg.tink.beans;

import java.util.ArrayList;
import java.util.Calendar;

import se.kransellwennborg.tink.Utilities;
import se.kransellwennborg.tink.dao.TimeEntryDao;

public class MonthStatEntries {

	private String staffAlias;
	private double totalHrs;
	private int totalInvoiced;
	private ArrayList<MonthStatEntry> cells;
	private int workDaysPassed;
	private double avgHrs;
	@SuppressWarnings("unused")
	private String kTotalInvoiced;
	
	public String getStaffAlias() {
		return staffAlias;
	}
	public void setStaffAlias(String staffAlias) {
		this.staffAlias = staffAlias;
	}
	public double getTotalHrs() {
		return Utilities.roundOneDec(totalHrs);
	}
	public void setTotalHrs(double totalHrs) {
		this.totalHrs = totalHrs;
	}
	public int getTotalInvoiced() {
		return totalInvoiced;
	}
	public void setTotalInvoiced(int totalInvoiced) {
		this.totalInvoiced = totalInvoiced;
	}
	public ArrayList<MonthStatEntry> getCells() {
		return cells;
	}
	public void setCells(ArrayList<MonthStatEntry> cells) {
		this.cells = cells;
	}
	public void populate (int year){
		if (cells == null) {
			cells = new ArrayList<MonthStatEntry>();
			// loop through months
			totalHrs = 0;
			totalInvoiced = 0;
			workDaysPassed = 0;
			TimeEntryDao ted = new TimeEntryDao();
			for (int i = 1; i <= 12 ;i++) {
				MonthStatEntry cell = ted.getMonthStats(i, staffAlias, year);				
				cell.setMonthNbr(i);
				
				totalHrs += cell.getHrs();
				totalInvoiced += cell.getInvoiced();
				workDaysPassed += cell.getWorkDays();
				cells.add(cell);
			}
			// this year
			Calendar cal = Calendar.getInstance(); // today
			int daysInYear = cal.get(Calendar.DAY_OF_YEAR);
			cal.add(Calendar.DATE, -daysInYear);

			double passedWorkDaysYear = 220.0 * ((double) daysInYear) / 365.0;
			
			avgHrs = totalHrs/passedWorkDaysYear ;
		}
	}
	public int getWorkDaysPassed() {
		return workDaysPassed;
	}
	public void setWorkDaysPassed(int workDaysPassed) {
		this.workDaysPassed = workDaysPassed;
	}
	public double getAvgHrs() {
		return Utilities.roundOneDec(avgHrs);
	}
	public void setAvgHrs(double avgHrs) {
		this.avgHrs = avgHrs;
	}
	public String getkTotalInvoiced() {
		return new Integer ((int) (totalInvoiced / 1000.0)).toString();
	}
	public void setkTotalInvoiced(String kTotalInvoiced) {
		this.kTotalInvoiced = kTotalInvoiced;
	}
}