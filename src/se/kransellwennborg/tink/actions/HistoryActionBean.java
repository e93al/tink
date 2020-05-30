package se.kransellwennborg.tink.actions;

import java.util.ArrayList;

import net.sourceforge.stripes.action.DefaultHandler;
import net.sourceforge.stripes.action.ForwardResolution;
import net.sourceforge.stripes.action.Resolution;
import se.kransellwennborg.tink.beans.TimeEntry;
import se.kransellwennborg.tink.dao.TimeEntryDao;

public class HistoryActionBean extends BaseActionBean {

	private TimeEntry filterEntry;
	
	public TimeEntry getFilterEntry() {
		return filterEntry;
	}

	public void setFilterEntry(TimeEntry filterEntry) {
		this.filterEntry = filterEntry;
	}

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
		return new ForwardResolution("/history.jsp");
	}

	public TimeEntryDao getTimeEntryDao() {
		return timeEntryDao;
	}

	public void setTimeEntryDao(TimeEntryDao timeEntryDao) {
		this.timeEntryDao = timeEntryDao;
	}
}