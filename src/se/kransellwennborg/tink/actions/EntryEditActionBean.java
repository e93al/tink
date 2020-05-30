package se.kransellwennborg.tink.actions;

import java.util.Calendar;
import java.util.Date;

import net.sourceforge.stripes.action.Before;
import net.sourceforge.stripes.action.DefaultHandler;
import net.sourceforge.stripes.action.DontValidate;
import net.sourceforge.stripes.action.ForwardResolution;
import net.sourceforge.stripes.action.RedirectResolution;
import net.sourceforge.stripes.action.Resolution;
import net.sourceforge.stripes.action.SimpleMessage;
import net.sourceforge.stripes.controller.LifecycleStage;
import net.sourceforge.stripes.validation.Validate;
import net.sourceforge.stripes.validation.ValidateNestedProperties;
import se.kransellwennborg.tink.Constants;
import se.kransellwennborg.tink.GlobalConfig;
import se.kransellwennborg.tink.Logger;
import se.kransellwennborg.tink.TinkDate;
import se.kransellwennborg.tink.Utilities;
import se.kransellwennborg.tink.beans.TimeEntry;
import se.kransellwennborg.tink.converters.TinkDateConverter;
import se.kransellwennborg.tink.converters.TinkTimeConverter;
import se.kransellwennborg.tink.dao.PatraWinDao;
import se.kransellwennborg.tink.dao.TimeEntryDao;

/**
 * Login action.
 */
public class EntryEditActionBean extends BaseActionBean {

	// TODO: add more validation
	@ValidateNestedProperties( { @Validate(field = "startTime", required = true, converter = TinkTimeConverter.class),
			@Validate(field = "endTime", required = true, converter = TinkTimeConverter.class),
			@Validate(field = "date", required = true, converter = TinkDateConverter.class),
			@Validate(field = "caseId", required = true) })
	TimeEntry timeEntry;

	private int timeEntryId;
	private String invoiceCase = "";
	private String caseId = "";
	private String caseName = "";
	private String client = "";
	private boolean fromWip = false;
	private String sortOrder = "";

	
	public String getClient() {
		return client;
	}

	public void setClient(String client) {
		this.client = client;
	}

	public String getCaseName() {
		return caseName;
	}

	public void setCaseName(String caseName) {
		this.caseName = caseName;
	}

	public String getCaseId() {
		return caseId;
	}

	public void setCaseId(String caseId) {
		this.caseId = caseId;
	}

	public String getInvoiceCase() {
		return invoiceCase;
	}

	public void setInvoiceCase(String invoiceCase) {
		this.invoiceCase = invoiceCase;
	}

	public TimeEntry getTimeEntry() {
		return timeEntry;
	}

	public void setTimeEntry(TimeEntry timeEntry) {
		this.timeEntry = timeEntry;
		if (timeEntry.getDate() != null) {
			getContext().setNavDate(new TinkDate(timeEntry.getDate()));
		}
	}

	public int getTimeEntryId() {
		return timeEntryId;
	}

	public void setTimeEntryId(int timeEntryId) {
		this.timeEntryId = timeEntryId;
	}
	
	public String getSortOrder() {
	
		try {
			sortOrder = this.getContext().getUser().getSortOrder();
			
		}
		catch (NullPointerException e) {
			sortOrder = Constants.DATE;
		}
		
		return sortOrder;
	}

	public void setSortOrder(String sortOrder) {
		if (sortOrder.equals("CASE_ID")) {
			this.sortOrder = Constants.CASE_ID;
		}
		if (sortOrder.equals("CLIENT_REF")) {
			this.sortOrder = Constants.CLIENT_REF;
		}
		if (sortOrder.equals("APPLICANT")) {
			this.sortOrder = Constants.APPLICANT;
		}
		if (sortOrder.equals("CASE_NAME")) {
			this.sortOrder = Constants.CASE_NAME;
		}
		if (sortOrder.equals("AMOUNT")) {
			this.sortOrder = Constants.AMOUNT;
		}
		if (sortOrder.equals("DATE")) {
			this.sortOrder = Constants.DATE;
		}
		
		this.getContext().getUser().setSortOrder(this.sortOrder);
	}

	@Before(stages = LifecycleStage.ResolutionExecution)
	void prePopulate() {
		Logger.debug(this.getClass(), "in prePopulate()");

		if (timeEntry == null) {
			timeEntry = new TimeEntry();
		}
		if (timeEntry.getId() > 0) {
			timeEntry = getTimeEntryDao().read(timeEntry.getId());
		} else {
				timeEntry.setDate(getContext().getNavDate());
				Date startTime = getTimeEntryDao().getLastEndTime(getContext().getUser().getUserName(), getContext().getNavDate());
				timeEntry.setStartTime(startTime);
			if (timeEntry.getRate() == null) {
				timeEntry.setRate(getContext().getUser().getRate());
			}
		}
		
		if (getContext().getUser().getHrsWeek() == null) {
			this.calculateStats();
		}
	}

	@DontValidate
	@DefaultHandler
	public Resolution standard() {
		if (fromWip == true) {
			TimeEntryDao ted = new TimeEntryDao();
			TimeEntry newTE = ted.read(timeEntry.getId());			
			
			getContext().setNavDate(new TinkDate(newTE.getDate()));
			return new ForwardResolution("/Main.jsp"); 			
		}		
		return new ForwardResolution("/Main.jsp");
	}

	@DontValidate
	public Resolution delete() {
		Logger.debug(this.getClass(), "in delete()");
		getTimeEntryDao().delete(timeEntry.getId());
		timeEntry.setId(0);
		return new RedirectResolution(EntryEditActionBean.class);
	}

	@DontValidate
	public Resolution gotoDate() {
		Logger.debug(this.getClass(), "in goto()");
		getContext().setNavDate(new TinkDate(timeEntry.getDate()));
		return new RedirectResolution(EntryEditActionBean.class);
	}

	@DontValidate
	public Resolution gotoToday() {
		Logger.debug(this.getClass(), "in gotoToday()");
		getContext().setNavDate(new TinkDate(Calendar.getInstance().getTime()));
		return new RedirectResolution(EntryEditActionBean.class);
	}

	@DontValidate
	public Resolution cont() {
		Logger.debug(this.getClass(), "in cont()");
		timeEntry = getTimeEntryDao().read(timeEntryId);
		timeEntry.setId(0);
		timeEntry.setEndTime(null);
		timeEntryId = 0;
		
		return new ForwardResolution("/Main.jsp");
	}
	
	@DontValidate
	public Resolution sort() {
		Logger.debug(this.getClass(), "in sort()");
		return new ForwardResolution("/Main.jsp");
	}
	
	@DontValidate
	public Resolution changeSortOrder() {
		Logger.debug(this.getClass(), "in changeSortOrder()");

		return new ForwardResolution("/Main.jsp");
	}
	
//	@SuppressWarnings("unused")
	public Resolution post() {
		Logger.debug(getClass(), "in post(), timeEntry:" + timeEntry);
		performPost();
		
	/*	getContext().setNavDate(new TinkDate(timeEntry.getDate()));

		if (timeEntry.getCaseName().equals("") 
				&& timeEntry.getClient().equals("") 
				&& GlobalConfig.isPwConnected()) {
			PatraWinDao.getCaseData(timeEntry);
		}
		calculateFields();

		timeEntry.setStaffAlias(getContext().getUser().getUserName());
		if (timeEntry.getId() > 0) {
			getTimeEntryDao().update(timeEntry);
		} else {
			getTimeEntryDao().add(timeEntry);
		}
	
		if (GlobalConfig.isPwConnected()) {
			String credit = PatraWinDao.getCreditData(timeEntry);
			if (!credit.equalsIgnoreCase("j")) {
				Logger.debug(this.getClass(), "credit warning: credit = " + credit);
				
				this.getContext().getMessages().add(new SimpleMessage("Warning! The client " + timeEntry.getClient() + " has a credit rating of " + credit));		
			}
		}
		if (invoiceCase != null && invoiceCase.length() > 2) {
//			return new ForwardResolution("/invoiceEdit.jsp?caseId=" + invoiceCase);
//			FlashScope fs = FlashScope.getCurrent(getContext().getRequest(), true);
//			fs.put("caseId", timeEntry.getCaseId());
			this.getContext().getRequest().getSession().setAttribute("caseId", timeEntry.getCaseId());
			Logger.debug(this.getClass(), "added case Id:" + timeEntry.getCaseId());
			return new RedirectResolution(InvoiceActionBean.class);
		}
		calculateStats(); */
		
		return new RedirectResolution(EntryEditActionBean.class);
	}

	public Resolution postAndInvoice() {
		performPost();
		
		return new RedirectResolution(InvoiceActionBean.class)
			    .addParameter("caseId", timeEntry.getCaseId())
			    .flash(this);
	}
	
	private void performPost() {
		Logger.debug(getClass(), "in performPost(), timeEntry:" + timeEntry);
		getContext().setNavDate(new TinkDate(timeEntry.getDate()));
		
		timeEntry.setCaseId(timeEntry.getCaseId().toUpperCase());

		if (timeEntry.getCaseName().equals("") 
				&& timeEntry.getClient().equals("") 
				&& GlobalConfig.isPwConnected()) {
			PatraWinDao.getCaseData(timeEntry);
		}
		calculateFields();

		timeEntry.setStaffAlias(getContext().getUser().getUserName());
		if (timeEntry.getId() > 0) {
			getTimeEntryDao().update(timeEntry);
		} else {
			getTimeEntryDao().add(timeEntry);
		}
	
		if (GlobalConfig.isPwConnected()) {
			String credit = PatraWinDao.getCreditData(timeEntry);
			if (!credit.equalsIgnoreCase("j")) {
				Logger.debug(this.getClass(), "credit warning: credit = " + credit);
				
				this.getContext().getMessages().add(new SimpleMessage("Warning! The client " + timeEntry.getClient() + " has a credit rating of " + credit));		
			}
		}
		if (invoiceCase != null && invoiceCase.length() > 2) {
//			return new ForwardResolution("/invoiceEdit.jsp?caseId=" + invoiceCase);
//			FlashScope fs = FlashScope.getCurrent(getContext().getRequest(), true);
//			fs.put("caseId", timeEntry.getCaseId());
			this.getContext().getRequest().getSession().setAttribute("caseId", timeEntry.getCaseId());
			Logger.debug(this.getClass(), "added case Id:" + timeEntry.getCaseId());
		}
		calculateStats();
	}

	
	
	void calculateFields() {

		// First we need to calculate duration with one decimal
		double duration = Utilities.calcDuration(timeEntry.getStartTime(), timeEntry.getEndTime());
		timeEntry.setDuration(duration);

		// and then it is time for revenue
		double rev = (duration * (double) timeEntry.getRate());
		timeEntry.setRevenue((int) Math.round(rev));	
	}
	
	void calculateStats() {
		String staffAlias = getContext().getUser().getUserName();
		
		// this week
		Calendar cal = Calendar.getInstance(); // today
		
		int dow = cal.get(Calendar.DAY_OF_WEEK);		
		if (dow == Calendar.SUNDAY) {
			dow = 7;
		}
		else{
			dow = dow - 1;
		}
			
		cal.add(Calendar.DATE, -dow);

		TimeEntry te = this.getTimeEntryDao().getMetricsAfter(staffAlias, cal.getTime());
		getContext().getUser().setHrsWeek(te.getDuration());
		getContext().getUser().setAvgWeek(
				Utilities.roundOneDec((te.getDuration() / ((double) Math.min(dow, 5)))));		

		// this month
		cal = Calendar.getInstance(); // today
		int daysInMonth = cal.get(Calendar.DAY_OF_MONTH);
		cal.add(Calendar.DATE, -daysInMonth);

		te = this.getTimeEntryDao().getMetricsAfter(staffAlias, cal.getTime());
		getContext().getUser().setHrsMonth(te.getDuration());

		double passedWorkDaysMonth = 22.0 * ((double) daysInMonth) / 30.0;

		getContext().getUser().setAvgMonth(
				Utilities.roundOneDec(te.getDuration()/passedWorkDaysMonth));

		// this year
		cal = Calendar.getInstance(); // today
		int daysInYear = cal.get(Calendar.DAY_OF_YEAR);
		cal.add(Calendar.DATE, -daysInYear);

		te = this.getTimeEntryDao().getMetricsAfter(staffAlias, cal.getTime());
		getContext().getUser().setHrsYear(te.getDuration());

		double passedWorkDaysYear = 220.0 * ((double) daysInYear) / 365.0;
		getContext().getUser().setAvgYear(
				Utilities.roundOneDec(te.getDuration()/passedWorkDaysYear));		
	}

	public boolean isFromWip() {
		return fromWip;
	}

	public void setFromWip(boolean fromWip) {
		this.fromWip = fromWip;
	}
}
