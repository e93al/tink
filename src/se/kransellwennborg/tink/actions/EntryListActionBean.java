package se.kransellwennborg.tink.actions;

import java.util.ArrayList;

import net.sourceforge.stripes.action.DefaultHandler;
import net.sourceforge.stripes.action.ForwardResolution;
import net.sourceforge.stripes.action.Resolution;
import se.kransellwennborg.tink.TinkActionBeanContext;
import se.kransellwennborg.tink.beans.TimeEntry;
import se.kransellwennborg.tink.dao.TimeEntryDao;

public class EntryListActionBean extends BaseActionBean {

	private TinkActionBeanContext context;

	private ArrayList<TimeEntry> entries;

	private TimeEntryDao timeEntryDao;
	
	public TinkActionBeanContext getContext() {
		return context;
	}

	public void setContext(TinkActionBeanContext context) {
		this.context = context;
	}

	public ArrayList<TimeEntry> getEntries() {
		return entries;
	}

	public void setEntries(ArrayList<TimeEntry> entries) {
		this.entries = entries;
	}


	@DefaultHandler
	public Resolution execute() {
		return new ForwardResolution("/Main.jsp");
	}

	public TimeEntryDao getTimeEntryDao() {
		return timeEntryDao;
	}

	public void setTimeEntryDao(TimeEntryDao timeEntryDao) {
		this.timeEntryDao = timeEntryDao;
	}
}