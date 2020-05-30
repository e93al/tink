package se.kransellwennborg.tink.beans;

import java.util.Date;

import se.kransellwennborg.tink.Utilities;

/**
 * Login action.
 */
public class PunchEntry {

	private int id;
	private String userName;
	private Date date;
	private Date punchInTime;
	private Date punchOutTime;
	private boolean isModifiedIn;
	private boolean isModifiedOut;
	private Double duration;
	private boolean isScheduledTime;
	

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public Date getPunchInTime() {
		return punchInTime;
	}

	public void setPunchInTime(Date punchInTime) {
		this.punchInTime = punchInTime;
	}

	public Date getPunchOutTime() {
		return punchOutTime;
	}

	public void setPunchOutTime(Date punchOutTime) {
		this.punchOutTime = punchOutTime;
	}

	public boolean getModifiedIn() {
		return isModifiedIn;
	}

	public void setModifiedIn(boolean isModifiedIn) {
		this.isModifiedIn = isModifiedIn;
	}

	public boolean getModifiedOut() {
		return isModifiedOut;
	}

	public void setModifiedOut(boolean isModifiedOut) {
		this.isModifiedOut = isModifiedOut;
	}

	public boolean getScheduledTime() {
		return isScheduledTime;
	}

	public void setScheduledTime(boolean isScheduledTime) {
		this.isScheduledTime = isScheduledTime;
	}

	public String getPunchInTimeString() {
		if (punchInTime == null) {
			return "";
		} else {
			return Utilities.formatTime(punchInTime);
		}
	}

	public String getPunchOutTimeString() {
		if (punchOutTime == null) {
			return "";
		} else {
			return Utilities.formatTime(punchOutTime);
		}
	}

	public String getDateString() {
		return Utilities.formatDate(date);
	}

	public Double getDuration() {
		return Utilities.roundOneDec(duration);
	}

	public void setDuration(Double duration) {
		this.duration = duration;
	}
}