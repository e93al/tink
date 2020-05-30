package se.kransellwennborg.tink.actions;

import net.sourceforge.stripes.action.DefaultHandler;
import net.sourceforge.stripes.action.ForwardResolution;
import net.sourceforge.stripes.action.Resolution;
import se.kransellwennborg.tink.GlobalConfig;
import se.kransellwennborg.tink.Logger;
import se.kransellwennborg.tink.beans.TimeEntry;
import se.kransellwennborg.tink.dao.PatraWinDao;

public class CaseDataActionBean extends BaseActionBean {

	private TimeEntry timeEntry;
	
	public TimeEntry getTimeEntry() {
		return timeEntry;
	}

	public void setTimeEntry(TimeEntry timeEntry) {
		this.timeEntry = timeEntry;
	}

	@DefaultHandler
	public Resolution getCaseData() {
		Logger.debug(this.getClass(), "in getCaseData() for " + timeEntry.getCaseId());
		if (GlobalConfig.isPwConnected() && (timeEntry != null) ) {
			Logger.debug(this.getClass(), "PW connected. Getting case data..");
			PatraWinDao.getCaseData(timeEntry);			
			Logger.debug(this.getClass(), "Have case data. Case name: " + timeEntry.getCaseName());
		}
		return new ForwardResolution("/basicCaseData.jsp");
	}
}
