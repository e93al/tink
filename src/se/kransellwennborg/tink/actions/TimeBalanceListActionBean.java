package se.kransellwennborg.tink.actions;

import net.sourceforge.stripes.action.DefaultHandler;
import net.sourceforge.stripes.action.ForwardResolution;
import net.sourceforge.stripes.action.Resolution;
import se.kransellwennborg.tink.TinkActionBeanContext;

public class TimeBalanceListActionBean extends BaseActionBean {

	private TinkActionBeanContext context;

//	private ArrayList<TimeBalanceEntry> entries;

	private String staffAlias;

	public String getstaffAlias() {
		return staffAlias;
	}

	public void setstaffAlias(String staffAlias) {
		this.staffAlias = staffAlias;
	}

	
	public TinkActionBeanContext getContext() {
		return context;
	}

	public void setContext(TinkActionBeanContext context) {
		this.context = context;
	}

//	public ArrayList<TimeBalanceEntry> getEntries() {
//		TimeBalanceDao tbd = new TimeBalanceDao();
//		entries = tbd.getEntries(staffAlias);
//
//		return entries;
//	}
//
//	public void setEntries(ArrayList<TimeBalanceEntry> entries) {
//		this.entries = entries;
//	}


	@DefaultHandler
	public Resolution execute() {
		return new ForwardResolution("/Main.jsp");
	}

}