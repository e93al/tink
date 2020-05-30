package se.kransellwennborg.tink.actions;

import net.sourceforge.stripes.action.ActionBean;
import net.sourceforge.stripes.action.ActionBeanContext;
import se.kransellwennborg.tink.TinkActionBeanContext;
import se.kransellwennborg.tink.dao.StaffDao;
import se.kransellwennborg.tink.dao.TimeEntryDao;

/**
 * Login action.
 */

public class BaseActionBean implements ActionBean {
	public static String DEFAULT_BLANK_TODAY = "blank_today";

	private TinkActionBeanContext context;
	private TimeEntryDao timeEntryDao;
	private StaffDao staffDao;
	private String timeEntryDefault;

	public String getTimeEntryDefault() {
		return timeEntryDefault;
	}

	public void setTimeEntryDefault(String timeEntryDefault) {
		this.timeEntryDefault = timeEntryDefault;
	}

	public StaffDao getStaffDao() {
		if (staffDao == null) {
			staffDao = new StaffDao();
		}
		return staffDao;
	}

	public void setStaffDao(StaffDao staffDao) {
		this.staffDao = staffDao;
	}

	public TinkActionBeanContext getContext() {
		return context;
	}

	public void setContext(ActionBeanContext context) {
		this.context = (TinkActionBeanContext) context;
	}

	public TimeEntryDao getTimeEntryDao() {
		if (timeEntryDao == null) {
			timeEntryDao = new TimeEntryDao();
		}
		return timeEntryDao;
	}

	public void setTimeEntryDao(TimeEntryDao timeEntryDao) {
		this.timeEntryDao = timeEntryDao;
	}
}
