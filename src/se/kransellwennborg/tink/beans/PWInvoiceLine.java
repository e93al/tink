package se.kransellwennborg.tink.beans;

import java.util.Date;

import se.kransellwennborg.tink.Utilities;

public class PWInvoiceLine {
	private String staffId;
	private String caseId;
	private Date date;
	private int amount;
	private String comment;
	private String accountCode; 
	private int timeInMinutes;
	private int rate;
	private String revenueCentre;
	public String getStaffId() {
		return staffId;
	}
	public void setStaffId(String staffId) {
		this.staffId = staffId;
	}
	public String getCaseId() {
		return caseId;
	}
	public void setCaseId(String caseId) {
		this.caseId = caseId;
	}
	public Date getDate() {
		return date;
	}
	public void setDate(Date date) {
		this.date = date;
	}
	public int getAmount() {
		return amount;
	}
	public void setAmount(int amount) {
		this.amount = amount;
	}
	public String getComment() {
		return comment;
	}
	public void setComment(String comment) {
		this.comment = comment;
	}
	public String getAccountCode() {
		return accountCode;
	}
	public void setAccountCode(String accountCode) {
		this.accountCode = accountCode;
	}
	public int getTimeInMinutes() {
		return timeInMinutes;
	}
	public void setTimeInMinutes(int timeInMinutes) {
		this.timeInMinutes = timeInMinutes;
	}
	public int getRate() {
		return rate;
	}
	public void setRate(int rate) {
		this.rate = rate;
	}
	public String getRevenueCentre() {
		return revenueCentre;
	}
	public void setRevenueCentre(String revenueCentre) {
		this.revenueCentre = revenueCentre;
	}

	public void esc() {
		comment = Utilities.esc(comment);
	}

}
