package se.kransellwennborg.tink.actions;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import net.sourceforge.stripes.action.Before;
import net.sourceforge.stripes.action.DefaultHandler;
import net.sourceforge.stripes.action.DontValidate;
import net.sourceforge.stripes.action.ForwardResolution;
import net.sourceforge.stripes.action.RedirectResolution;
import net.sourceforge.stripes.action.Resolution;
import net.sourceforge.stripes.controller.LifecycleStage;
import net.sourceforge.stripes.validation.Validate;
import se.kransellwennborg.tink.Logger;
import se.kransellwennborg.tink.TinkDate;
import se.kransellwennborg.tink.Utilities;
import se.kransellwennborg.tink.beans.PunchEntry;
import se.kransellwennborg.tink.beans.User;
import se.kransellwennborg.tink.converters.TinkDateConverter;
import se.kransellwennborg.tink.converters.TinkTimeConverter;
import se.kransellwennborg.tink.dao.PunchEntryDao;
import se.kransellwennborg.tink.dao.StaffDao;

/**
 * Login action.
 */
public class PunchActionBean extends BaseActionBean {

	
	// TODO: add more validation
	private PunchEntry punchEntry;
	private int punchEntryId;
	private PunchEntryDao punchEntryDao;

	@Validate(required = true, converter = TinkDateConverter.class)
	private Date formDate;

	@Validate(required = true, converter = TinkTimeConverter.class)
	private Date formTime;

	private String postType;
	private String postLabel;

	@Validate(required = true, converter = TinkTimeConverter.class)
	private Date originalTime;
	
	
	public PunchEntry getPunchEntry() {
		return punchEntry;
	}

	public void setPunchEntry(PunchEntry punchEntry) {
		this.punchEntry = punchEntry;
	}

	public int getPunchEntryId() {
		return punchEntryId;
	}

	public void setPunchEntryId(int punchEntryId) {
		this.punchEntryId = punchEntryId;
	}

	public PunchEntryDao getPunchEntryDao() {
		if (punchEntryDao == null) {
			punchEntryDao = new PunchEntryDao();
		}
		return punchEntryDao;
	}

	public void setPunchEntryDao(PunchEntryDao punchEntryDao) {
		this.punchEntryDao = punchEntryDao;
	}

	public Date getFormTime() {
		return formTime;
	}

	public void setFormTime(Date formTime) {
		this.formTime = formTime;
	}

	public String getPostType() {
		return postType;
	}

	public void setPostType(String postType) {
		this.postType = postType;
	}

	public String getPostLabel() {
		return postLabel;
	}

	public void setPostLabel(String postLabel) {
		this.postLabel = postLabel;
	}

	public Date getFormDate() {
		return formDate;
	}

	public void setFormDate(Date formDate) {
		this.formDate = formDate;
	}
	
	public Date getOriginalTime() {
		return originalTime;
	}

	public void setOriginalTime(Date originalTime) {
		this.originalTime = originalTime;
	}

	/*	private String[] holidays = {
	"090101","090106","090410","090412","090413","090501","090521","090531","090606","090620","091031","091225","091226",
	"100101","100106","100402","100404","100405","100501","100513","100523","100606","100626","101106","101225","101226",
	"110101","110106","110422","110424","110425","110501","110602","110606","110612","110625","111105","111225","111226",
	"120101","120106","120406","120408","120409","120501","120517","120606","120622","120623","121103","121224","121225","121226","121231",
	"130101","130106","130329","130331","130401","130501","130509","130606","120621","130622","131102","131224","121225","131226","131231"};
	 */	
	public ArrayList<String> getHolidays () {
		
		return this.getPunchEntryDao().getHolidays();		
	};


	/* action methods below */
	@Before(stages = LifecycleStage.ResolutionExecution)
	void prePopulate() {
		Logger.debug(this.getClass(), "in prePopulate() for punch entry");

		if (punchEntry == null) {
			punchEntry = new PunchEntry();
		}
		if (punchEntry.getId() > 0) {
			punchEntry = getPunchEntryDao().read(punchEntry.getId());
		} else {
			TinkDate now = new TinkDate(Calendar.getInstance().getTime());
			setFormDate(now);
			setFormTime(now);
			setOriginalTime(now);
			StaffDao sd = new StaffDao();
			boolean inService = sd.getUserData(getContext().getUser().getUserName()).isPunchedIn();
			setPostType(inService == true ? "punchOut" : "punchIn");
			setPostLabel(inService == true ? "Punch out" : "Punch in");
		}
	}

	public Resolution post() {
		Logger.debug(getClass(), "in post(), punchEntry:" + punchEntry);

		return new RedirectResolution(PunchActionBean.class);
	}

	public Resolution punchIn() {
		Logger.debug(this.getClass(), "normalTime: " + getNormalTime(new Date()));

		Logger.debug(getClass(), "in punchIn()");
		StaffDao sd = new StaffDao();
		User user = sd.getUserData(getContext().getUser().getUserName());
		user.setPunchedIn(true);
		user.setPunchInTime(formTime);
		user.setPunchInDate(formDate);

		if (originalTime.equals(formTime)) {
			user.setModifiedPunchedIn(false);
		}
		else {
			user.setModifiedPunchedIn(true);
		}

		sd.updateUserData(user);
		getContext().setUser(user);

		return new RedirectResolution(PunchActionBean.class);
	}

	public Resolution punchOut() {

		Logger.debug(this.getClass(), "normalTime: " + getNormalTime(new Date()));

		PunchEntry pe = new PunchEntry();
		StaffDao sd = new StaffDao();
		User user = sd.getUserData(getContext().getUser().getUserName());

//		 * add record to punch times
		if (originalTime.equals(formTime)) {
			pe.setModifiedOut(false);
		}
		else {
			pe.setModifiedOut(true);
		}

		pe.setModifiedIn(user.isModifiedPunchedIn());
		pe.setDate(user.getPunchInDate());
		pe.setPunchInTime(user.getPunchInTime());
		pe.setPunchOutTime(getFormTime());
		pe.setUserName(user.getUserName());
		pe.setDuration(Utilities.calcDuration(pe.getPunchInTime(), pe.getPunchOutTime()));
		getPunchEntryDao().post(pe);

		PunchEntry currentScheduledTimePe = getPunchEntryDao().getScheduledTimePe(pe.getUserName(), pe.getDate());
		
		double balanceAdjustment = 0.0;
		
		if (currentScheduledTimePe == null) {
			// no previous entry for user and date - create a new one
			PunchEntry spe = new PunchEntry();
			spe.setUserName(pe.getUserName());
			spe.setDate(pe.getDate());
			spe.setScheduledTime(true);
			double normalTime = getNormalTime(pe.getDate());
			spe.setDuration(-1 * normalTime);
			balanceAdjustment = pe.getDuration() - normalTime;
			getPunchEntryDao().post(spe);
		}
		else {
			// previous entry exists - adjust balance with new duration
			balanceAdjustment += pe.getDuration();
		}
		Logger.debug(getClass(), "in punchOut(), punchEntry:" + punchEntry);
		
		user.setPunchedIn(false);
		user.setModifiedPunchedIn(false);
		user.setTimeBalance(user.getTimeBalance() + balanceAdjustment);
		sd.updateUserData(user);
		getContext().setUser(user);

		return new RedirectResolution(PunchActionBean.class);
	}

	@DontValidate
	@DefaultHandler
	public Resolution standard() {
		Logger.debug(getClass(), "in standard()");

		return new ForwardResolution("/punch.jsp");
	}

	public double getNormalTime(Date date) {
		//todo: get better work times - holidays
		double res = 0.0;
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		int day = cal.get(Calendar.DAY_OF_WEEK);
		if (day >= Calendar.MONDAY && day <= Calendar.FRIDAY) {
			res = 7;
			// add holiday days
			if(isHoliday(cal)) {
				res = 0;
			}
			if (isDayBeforeHoliday(cal)) {
				res = 5;
			}
		}		
		double perc = (double) getContext().getUser().getWorkPercent();

		res = res * perc / 100;
		
		return res;
	}
	
/*	public void addMissingDates (PunchEntry pe) {
		PunchEntryDao ped = new PunchEntryDao();
		Date ctrDate = ped.getLastDate(pe.getUserName(), pe.getDate());

		double balanceAdjustment = 0.0;
		
		Calendar postCal = Calendar.getInstance();
		postCal.setTime(pe.getDate());
		
		if (ctrDate != null) {
			Calendar ctrCal = Calendar.getInstance();
			ctrCal.setTime(ctrDate);
			TimeBalanceDao tbd = new TimeBalanceDao();
			
			// loop through all dates between
			while (! ( 
					(ctrCal.get(Calendar.YEAR) == postCal.get(Calendar.YEAR)) && 
					(ctrCal.get(Calendar.MONTH) == postCal.get(Calendar.MONTH)) && 
					(ctrCal.get(Calendar.DAY_OF_MONTH) == postCal.get(Calendar.DAY_OF_MONTH)) )) {
				// increase date by one
				ctrCal.add(Calendar.DATE, 1); // increases month and year automatically if needed

				double normalTime = getNormalTime(ctrCal.getTime());
				
				if (normalTime > 0) {
					TimeBalanceEntry tbe = new TimeBalanceEntry();
					tbe.setDate(ctrCal.getTime());
					tbe.setUserName(pe.getUserName());
					tbe.setActualTime(0);
					tbe.setNormalTime(normalTime);
					tbe.setDayBalance(0 - normalTime);
					balanceAdjustment -= normalTime;
					tbd.post(tbe);
				}
			}
			StaffDao sd = new StaffDao();
			User user = sd.getUserData(getContext().getUser().getUserName());
			user.setTimeBalance(user.getTimeBalance() + balanceAdjustment);
		}
	}	*/

	boolean isHoliday (Calendar inCal) {
		boolean res = false;
		String dateString = Utilities.formatDate(inCal.getTime());
		ArrayList<String> hols = getHolidays();
		
		for (int i = 0; i < hols.size() ; i++) {
			if (dateString.equals(hols.get(i))) {
				res = true;
				break;
			}
		}
		return res;
	}

	boolean isDayBeforeHoliday (Calendar inCal) {
		boolean res = false;
		inCal.add(Calendar.DATE, 1);
		String dateString = Utilities.formatDate(inCal.getTime());
		
		ArrayList<String> hols = getHolidays();
		for (int i = 0; i < hols.size() ; i++) {
			if (dateString.equals(hols.get(i))) {
				res = true;
				break;
			}
		}
		return res;
	}
}
