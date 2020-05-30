package se.kransellwennborg.tink.actions;

import java.util.ArrayList;
import java.util.Calendar;

import net.sourceforge.stripes.action.Before;
import net.sourceforge.stripes.action.DefaultHandler;
import net.sourceforge.stripes.action.DontValidate;
import net.sourceforge.stripes.action.ForwardResolution;
import net.sourceforge.stripes.action.RedirectResolution;
import net.sourceforge.stripes.action.Resolution;
import net.sourceforge.stripes.controller.LifecycleStage;
import se.kransellwennborg.tink.Logger;
import se.kransellwennborg.tink.beans.MonthStatEntries;
import se.kransellwennborg.tink.dao.StaffDao;

/**
 * Login action.
 */
public class StatisticsActionBean extends BaseActionBean {


	private String user = "";
	private int year = 0;

	// all rows
	private ArrayList<MonthStatEntries> rows;

	public ArrayList<MonthStatEntries> getRows() {
		Logger.debug(this.getClass(), "in getRows()");
		if (year == 0) {
			Calendar now = Calendar.getInstance();
			year = now.get(Calendar.YEAR);
		}	

		if (rows == null) {
			rows = new ArrayList<MonthStatEntries>();
			// get list of attorneys
			StaffDao sd = new StaffDao();
			ArrayList<String> statsStaff = sd.getStatsStaff();			
			// for each attorney
			for (int i = 0; i < statsStaff.size(); i++) {
				MonthStatEntries row = new MonthStatEntries();
				row.setStaffAlias(statsStaff.get(i));
				row.populate(year);
				// create row
				rows.add(row);				
			}			
		}
		return rows;
	}

	public void setRows(ArrayList<MonthStatEntries> entries) {
		this.rows = entries;
	}

	public String getUser() {
		return user;
	}

	public void setUser(String user) {
		this.user = user;
	}

	public int getYear() {
		return year;
	}

	public void setYear(int year) {
		this.year = year;
	}

	@Before(stages = LifecycleStage.ResolutionExecution)
	void prePopulate() {
		Logger.debug(this.getClass(), "in prePopulate()");
		if (year == 0) {
			Calendar now = Calendar.getInstance();
			year = now.get(Calendar.YEAR);
		}	
	}

	@DontValidate
	@DefaultHandler
	public Resolution standard() {
		return new ForwardResolution("/statistics.jsp");
	}
	
	public Resolution post() {
		year = 2016;
		return new RedirectResolution(StatisticsActionBean.class);
	}
}
