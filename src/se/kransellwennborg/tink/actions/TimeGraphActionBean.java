package se.kransellwennborg.tink.actions;

import java.util.ArrayList;

import net.sourceforge.stripes.action.DefaultHandler;
import net.sourceforge.stripes.action.ForwardResolution;
import net.sourceforge.stripes.action.Resolution;
import se.kransellwennborg.tink.Logger;
import se.kransellwennborg.tink.beans.TimeEntry;
import se.kransellwennborg.tink.dao.TimeEntryDao;

/**
 * Login action.
 */
public class TimeGraphActionBean extends BaseActionBean {


	private ArrayList<TimeEntry> entries;

	private TimeEntryDao timeEntryDao;
	
	public ArrayList<TimeEntry> getEntries() {
		return entries;
	}

	public void setEntries(ArrayList<TimeEntry> entries) {
		this.entries = entries;
	}


	@DefaultHandler
	public Resolution execute() {
		Logger.debug(getClass(), "in execute()");
		return new ForwardResolution("/timeGraph.jsp");
	}
	

	public TimeEntryDao getTimeEntryDao() {
		return timeEntryDao;
	}

	public void setTimeEntryDao(TimeEntryDao timeEntryDao) {
		this.timeEntryDao = timeEntryDao;
	}
}