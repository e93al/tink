package se.kransellwennborg.tink.beans;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Calendar;
import java.util.Date;

import se.kransellwennborg.tink.GlobalConfig;
import se.kransellwennborg.tink.Logger;
import se.kransellwennborg.tink.Utilities;
import se.kransellwennborg.tink.dao.PatraWinDao;

public class TimeEntry {

	private int id;
	private String staffAlias;
	private String caseId;
	private String caseName;
	private String client;
	private Date date;
	private Date startTime;
	private Date endTime;
	private Integer rate;
	private int revenue;
	private double duration;
	private String comment;
	private boolean isInternal;
	private boolean isUpdate;
	private int invoiceId;
	private boolean include;
	private String clientRef;
	

	public String toString() {
		return "id:" + id + " staffAlias:" + staffAlias + " caseId:" + caseId + " date:" + date + " startTime:"
				+ startTime + " endTime:" + endTime + " duration:" + duration + " caseName:" + caseName + " client:"
				+ client + " rate:" + rate + " revenue:" + revenue + " comment:" + comment + " isInternal:"
				+ isInternal + " isUpdate:" + isUpdate;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getClient() {
		return (client == null ? "" : client);
	}

	public void setClient(String client) {
		this.client = client;
	}

	public boolean getIsInternal() {
		return isInternal;
	}

	public void setIsInternal(boolean isInternal) {
		this.isInternal = isInternal;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public Date getStartTime() {
		return startTime;
	}

	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}

	public Date getEndTime() {
		return endTime;
	}

	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}

	public String getCaseId() {
		return (caseId == null ? "" : caseId);
	}

	public void setCaseId(String caseId) {
		this.caseId = caseId;
	}

	public String getEncodedCaseId() {
//		String res = caseId;
//		return res;

		String res = "";
		try {
			res = URLEncoder.encode(caseId, "ISO-8859-1");
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Logger.debug(getClass(), "encoded to  " + res);
		return res;	
	}

	public String getCaseName() {
		return (caseName == null ? "" : caseName);
	}

	public void setCaseName(String caseName) {
		this.caseName = caseName;
	}

	public int getRevenue() {
		return revenue;
	}

	public void setRevenue(int revenue) {
		this.revenue = revenue;
	}

	public double getDuration() {
		return Utilities.roundOneDec(duration);
	}

	public void setDuration(double duration) {
		this.duration = duration;
	}

	public String getComment() {
		return (comment == null ? "" : comment);
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	public String getStaffAlias() {
		return staffAlias;
	}

	public void setStaffAlias(String staffAlias) {
		this.staffAlias = staffAlias;
	}

	public Integer getRate() {
		return rate;
	}

	public void setRate(Integer rate) {
		this.rate = rate;
	}

	public int getInvoiceId() {
		return invoiceId;
	}

	public void setInvoiceId(int invoiceId) {
		this.invoiceId = invoiceId;
	}

	public void setDateToday() {
		date = Calendar.getInstance().getTime();
	}

	public void setStartTimeNow() {
		startTime = Calendar.getInstance().getTime();
	}

	public boolean hasEmptyEndTime() {
		boolean result = false;
		try {
			endTime.equals(null);
		} catch (NullPointerException e) {
			result = true;
		}
		return result;
	}

	public boolean hasEmptyStartTime() {
		boolean result = false;
		try {
			startTime.equals(null);
		} catch (NullPointerException e) {
			result = true;
		}
		return result;
	}

	public boolean isUpdate() {
		return isUpdate;
	}

	public void setUpdate(boolean isUpdate) {
		this.isUpdate = isUpdate;
	}

	public String getStartTimeString() {
		if (startTime == null) {
			return "";
		} else {
			return Utilities.formatTime(startTime);
		}
	}

	public String getEndTimeString() {
		if (endTime == null) {
			return "";
		} else {
			return Utilities.formatTime(endTime);
		}
	}

	public String getDateString() {
		return Utilities.formatDate(date);
	}

	public String getShortCaseName() {
		return (caseName != null && caseName.length() > 22 ? caseName.substring(0, 20) + ".." : caseName);
	}

	public String getShortClient() {
		return (client != null && client.length() > 13 ? client.substring(0, 11) + ".." : client);
	}

	public String getShortShortClient() {
		return (client != null && client.length() > 10 ? client.substring(0, 8) + ".." : client);
	}

	public boolean isInclude() {
		return include;
	}

	public void setInclude(boolean include) {
		this.include = include;
	}
	
	public boolean empty() {
		if (duration < 0.1 && revenue < 1) {			
			return true;
		}
		else {
			return false;
		}
	}
	
	public void esc() {
		caseName = Utilities.esc(caseName);
		client = Utilities.esc(client);
		comment = Utilities.esc(comment);
	}

	public String getClientRef() {
		
		try {
			PatraWinDao.getCaseData(this);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return (clientRef != null && clientRef.length() > 15 ? clientRef.substring(0, 13) + ".." : clientRef);
	}

	public void setClientRef(String clientRef) {
		this.clientRef = clientRef;
	}
	
}