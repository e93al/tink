package se.kransellwennborg.tink.beans;

public class MonthStatEntry {

	private int monthNbr;
	private double hrs;
	private double avgHrs;
	private int invoiced;
	private int workDays;
	@SuppressWarnings("unused")
	private String kInvoiced;
	
	public int getMonthNbr() {
		return monthNbr;
	}
	public void setMonthNbr(int monthNbr) {
		this.monthNbr = monthNbr;
	}
	public double getHrs() {
		return hrs;
	}
	public void setHrs(double hrs) {
		this.hrs = hrs;
	}
	public double getAvgHrs() {
		return avgHrs;
	}
	public void setAvgHrs(double avgHrs) {
		this.avgHrs = avgHrs;
	}
	public int getInvoiced() {
		return invoiced;
	}
	public void setInvoiced(int invoiced) {
		this.invoiced = invoiced;
	}
	public String getkInvoiced() {
		return new Integer ((int) (invoiced / 1000.0)).toString();
	}
	public void setkInvoiced(String kInvoiced) {
		this.kInvoiced = kInvoiced;
	}
	public int getWorkDays() {
		return workDays;
	}
	public void setWorkDays(int workDays) {
		this.workDays = workDays;
	}
}