package se.kransellwennborg.tink.beans;

import java.util.Date;

import se.kransellwennborg.tink.Constants;
import se.kransellwennborg.tink.Utilities;
import se.kransellwennborg.tink.dao.StaffDao;

public class User {
	private String userName;
	private int rate;
	private String fullName;
	private int workPercent;
	private boolean isPunchedIn;
	private Date punchInDate;
	private Date punchInTime;
	private Double timeBalance;
	private boolean isModifiedPunchedIn;
	private Double hrsWeek;
	private Double avgWeek;
	private Double hrsMonth;
	private Double avgMonth;
	private Double hrsYear;
	private Double avgYear;
	
	private int accountCode;
	private String revenueCentre;

	private boolean isAttorney;
	private boolean showInStats;
	private String sortOrder;
	

	public Double getHrsWeek() {
		return hrsWeek;
	}
	public boolean isAttorney() {
		return isAttorney;
	}
	public void setAttorney(boolean isAttorney) {
		this.isAttorney = isAttorney;
	}
	public void setHrsWeek(Double hrsWeek) {
		this.hrsWeek = hrsWeek;
	}
	public Double getAvgWeek() {
		return avgWeek;
	}
	public void setAvgWeek(Double avgWeek) {
		this.avgWeek = avgWeek;
	}
	public Double getHrsMonth() {
		return hrsMonth;
	}
	public void setHrsMonth(Double hrsMonth) {
		this.hrsMonth = hrsMonth;
	}
	public Double getAvgMonth() {
		return avgMonth;
	}
	public void setAvgMonth(Double avgMonth) {
		this.avgMonth = avgMonth;
	}
	public Double getHrsYear() {
		return hrsYear;
	}
	public void setHrsYear(Double hrsYear) {
		this.hrsYear = hrsYear;
	}
	public Double getAvgYear() {
		return avgYear;
	}
	public void setAvgYear(Double avgYear) {
		this.avgYear = avgYear;
	}
	
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public int getRate() {
		return rate;
	}
	public void setRate(int rate) {
		this.rate = rate;
	}
	public String getFullName() {
		return fullName;
	}
	public void setFullName(String fullName) {
		this.fullName = fullName;
	}
	public int getWorkPercent() {
		return workPercent;
	}
	public void setWorkPercent(int workPercent) {
		this.workPercent = workPercent;
	}
	public boolean isPunchedIn() {
		return isPunchedIn;
	}
	public boolean getPunchedIn() {
		return isPunchedIn;
	}
	public void setPunchedIn(boolean isPunchedIn) {
		this.isPunchedIn = isPunchedIn;
	}
	public Date getPunchInDate() {
		return punchInDate;
	}
	public void setPunchInDate(Date punchInDate) {
		this.punchInDate = punchInDate;
	}
	public Date getPunchInTime() {
		return punchInTime;
	}
	public String getPunchInTimeString () {
		return Utilities.formatTime(punchInTime);
	}
	public void setPunchInTime(Date punchInTime) {
		this.punchInTime = punchInTime;
	}
	public Double getTimeBalance() {
		StaffDao sd = new StaffDao();
		timeBalance = Utilities.roundOneDec(sd.getTimeBalance(userName));
		return timeBalance;
	}
	public void setTimeBalance(Double timeBalance) {
		this.timeBalance = timeBalance;
	}
	
	public String getPunchStatus() {
		return (isPunchedIn ? "Punched in" : "Punched out");
	}

	public boolean isModifiedPunchedIn() {
		return isModifiedPunchedIn;
	}
	public void setModifiedPunchedIn(boolean isModifiedPunchedIn) {
		this.isModifiedPunchedIn = isModifiedPunchedIn;
	}

	public int getAccountCode() {
		return accountCode;
	}
	public void setAccountCode(int accountCode) {
		this.accountCode = accountCode;
	}
	public String getRevenueCentre() {
		return revenueCentre;
	}
	public void setRevenueCentre(String revenueCentre) {
		this.revenueCentre = revenueCentre;
	}
	public boolean isShowInStats() {
		return showInStats;
	}
	public void setShowInStats(boolean showInStats) {
		this.showInStats = showInStats;
	}
	
	public String getSortOrder() {
		if (sortOrder == null || sortOrder.isEmpty()) {
			sortOrder = Constants.DATE;
		}
		return sortOrder;
	}
	public void setSortOrder(String sortOrder) {
		this.sortOrder = sortOrder;
	}
	public void populate(String userName) {
		StaffDao staffDao = new StaffDao();
		User userData = staffDao.getUserData(userName);	
		setRate(userData.getRate());
		setFullName(userData.getFullName());
		setUserName(userName);
		setWorkPercent(userData.getWorkPercent());
		setPunchedIn(userData.isPunchedIn());
		setPunchInDate(userData.getPunchInDate());
		setPunchInTime(userData.getPunchInTime());
		setTimeBalance(userData.getTimeBalance());
		setAttorney(userData.isAttorney());
		setAccountCode(userData.getAccountCode());
		setRevenueCentre(userData.getRevenueCentre());
	}
}
