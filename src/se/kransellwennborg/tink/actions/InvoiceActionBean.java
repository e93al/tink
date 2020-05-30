package se.kransellwennborg.tink.actions;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;

import net.sourceforge.stripes.action.DefaultHandler;
import net.sourceforge.stripes.action.ForwardResolution;
import net.sourceforge.stripes.action.RedirectResolution;
import net.sourceforge.stripes.action.Resolution;
import net.sourceforge.stripes.validation.Validate;
import net.sourceforge.stripes.validation.ValidateNestedProperties;
import se.kransellwennborg.tink.GlobalConfig;
import se.kransellwennborg.tink.Logger;
import se.kransellwennborg.tink.Utilities;
import se.kransellwennborg.tink.beans.PWInvoiceLine;
import se.kransellwennborg.tink.beans.TimeEntry;
import se.kransellwennborg.tink.beans.User;
import se.kransellwennborg.tink.converters.TinkDateConverter;
import se.kransellwennborg.tink.dao.InvoiceDao;
import se.kransellwennborg.tink.dao.PatraWinDao;
import se.kransellwennborg.tink.dao.StaffDao;
import se.kransellwennborg.tink.dao.TimeEntryDao;

public class InvoiceActionBean extends BaseActionBean {

	private ArrayList<TimeEntry> entries;

	private InvoiceDao invoiceDao;

//	private int adjustment;

	private int id;

	private Date date;

	private String invoicedBy;

	private String caseId;

	private double totalDuration;
	
//	private int totalRevenue;

	private String caseName;
	
	private String client;

	private int amount;
	
	private int blanketRate;

//	private int fixedFee;
	
	private int processEntryId;
	
	@ValidateNestedProperties( {@Validate(field = "date", required = false, converter = TinkDateConverter.class)})
	private TimeEntry newTimeEntry;
	
	private HashSet<String> posters;
	
	public ArrayList<TimeEntry> getEntries() {
		if (entries == null  && caseId != null) {
			TimeEntryDao ted = new TimeEntryDao();
			entries = ted.getInvoiceTimeEntries(caseId, this);
		}
		return entries;
	}

	public void setEntries(ArrayList<TimeEntry> entries) {
		this.entries = entries;
	}

	public InvoiceDao getInvoiceDao() {
		if (invoiceDao == null) {
			invoiceDao = new InvoiceDao();
		}
		return invoiceDao;
	}
/*
	public int getFixedFee() {
		return fixedFee;
	}

	public void setFixedFee(int fixedFee) {
		this.fixedFee = fixedFee;
	}
*/
	public void setInvoiceDao(InvoiceDao invoiceDao) {
		this.invoiceDao = invoiceDao;
	}
/*
	public int getAdjustment() {
		return adjustment;
	}

	public void setAdjustment(int adjustment) {
		this.adjustment = adjustment;
	}
*/
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public String getInvoicedBy() {
		return invoicedBy;
	}

	public void setInvoicedBy(String invoicedBy) {
		this.invoicedBy = invoicedBy;
	}

	public String getCaseId() {
		return caseId;
	}

	public void setCaseId(String caseId) {
		this.caseId = caseId;
	}

	public double getTotalDuration() {
		return Utilities.roundOneDec(totalDuration);
	}

	public void setTotalDuration(double totalDuration) {
		this.totalDuration = totalDuration;
	}

/*	public int getTotalRevenue() {
		return totalRevenue;
	}

	public void setTotalRevenue(int totalRevenue) {
		this.totalRevenue = totalRevenue;
	}
*/
	public String getCaseName() {
		if (caseName == null && caseId != null && GlobalConfig.isPwConnected()) {
			TimeEntry te = new TimeEntry();
			te.setCaseId(caseId);
			try {
				PatraWinDao.getCaseData(te);
			} catch (Exception e) {
				e.printStackTrace();
			}
			caseName = te.getCaseName();
		}
		
		return caseName;
	}

	public void setCaseName(String caseName) {
		this.caseName = caseName;
	}

	public String getClient() {
		if (client == null && caseId != null && GlobalConfig.isPwConnected()) {
			TimeEntry te = new TimeEntry();
			te.setCaseId(caseId);
			PatraWinDao.getCaseData(te);
			client = te.getClient();
		}
		return client;
	}

	public void setClient(String client) {
		this.client = client;
	}

	public int getAmount() {
		return amount;
	}

	public void setAmount(int amount) {
		this.amount = amount;
	}

	
	
	public int getProcessEntryId() {
		return processEntryId;
	}

	public void setProcessEntryId(int processEntryId) {
		this.processEntryId = processEntryId;
	}

	public TimeEntry getNewTimeEntry() {
		return newTimeEntry;
	}

	public void setNewTimeEntry(TimeEntry newTimeEntry) {
		this.newTimeEntry = newTimeEntry;
	}
	
	public HashSet<String> getPosters () {
		String poster = "";
		
		if (posters == null) {
			posters = new HashSet<String>();
		}
		
//		if (posters == null  && caseId != null) {
			for (int i = 0; i < getEntries().size(); i++) {
				poster = getEntries().get(i).getStaffAlias();
				if ((posters == null) || !posters.contains(poster)) {
					posters.add(poster);
				}
			}			
//		}

		return posters;
	}

	public int getBlanketRate() {
		
		if (blanketRate == 0) {
			blanketRate = this.getContext().getUser().getRate();
;
		}
		return blanketRate;
	}

	public void setBlanketRate(int blanketRate) {
		this.blanketRate = blanketRate;
	}

	@DefaultHandler
	public Resolution execute() {
		Logger.debug(this.getClass(), "in execute: " + caseId + ", length: " + caseId.length());		
		if (caseId == null) {
			caseId = (String) this.getContext().getRequest().getSession().getAttribute("caseId");
			Logger.debug(this.getClass(), "caseId after update: " + caseId);
		}
		if (newTimeEntry == null) {
			this.newTimeEntry = new TimeEntry();
		}
		this.newTimeEntry.setDateToday();
		
		Logger.debug(this.getClass(), "caseId: " + caseId);
		return new ForwardResolution("/invoiceEdit.jsp");
	}

	public Resolution post() {
// original post - without PW connection
		InvoiceDao dao = getInvoiceDao();
		dao.addInvoice(this);
		markInvoiced();
		return new ForwardResolution("/invoiceConf.jsp");
	}

	public Resolution postSingle() {
		Logger.debug(this.getClass(), "postSingle()");
		InvoiceDao dao = getInvoiceDao();
		TimeEntryDao ted = new TimeEntryDao ();
//		addNewEntry(); // complete data for new time entry (end of JSP page) when not empty
		PWInvoiceLine pwil = new PWInvoiceLine();
		StaffDao sdao = new StaffDao();
		User postUser = new User();
		
		pwil.setCaseId(caseId);

		/* get all users of entries, could be more than one  */
		Iterator<String> iter = this.getPosters().iterator();
		
		/* For each user */
		while (iter.hasNext()) {			
			
			amount = 0;
			totalDuration = 0.0;
			
			String poster = iter.next();
			postUser.populate(poster);

			if (!postUser.isAttorney()) {
				/* assistant */
				/* get responsible consultant */
				String respConsName = PatraWinDao.getCaseResponsible(caseId);
			
				/* get consultant data */
				postUser = sdao.getUserData(respConsName);		
			} /* default is already current user, so nothing to do if attorney */
			
			
			pwil.setRevenueCentre(postUser.getRevenueCentre());
			pwil.setStaffId(postUser.getUserName());
			pwil.setAccountCode(Utilities.padNbr(postUser.getAccountCode()));

			pwil.setComment("");
			for (int i = 0; i < entries.size(); i++) {
				if (entries.get(i).isInclude() && entries.get(i).getStaffAlias().equals(poster)) {
					pwil.setAmount(pwil.getAmount() + entries.get(i).getRevenue());
					if (entries.get(i).getComment().length() > 0) { 
						// if comment already there, append new line and current comment
						pwil.setComment(pwil.getComment().length() > 0 
							? pwil.getComment() + "\n" + entries.get(i).getComment() 
							: entries.get(i).getComment());
					}				
					pwil.setTimeInMinutes(pwil.getTimeInMinutes() + Utilities.hrsToMins(entries.get(i).getDuration()));	
					pwil.setDate(entries.get(i).getDate()); // best guess - will be last post
					pwil.setRate(entries.get(i).getRate()); // best guess - will be last post
					
					amount += entries.get(i).getRevenue();
					totalDuration += entries.get(i).getDuration(); /* duration in hours with one decimal*/
	
					// if it has ID - update
					if (entries.get(i).getId() != 0) {
						ted.update(entries.get(i));
					}
					else {
						ted.add(entries.get(i));
					}
					// else - new entry
				}
			}
			
			pwil.setAmount(amount);
			pwil.setTimeInMinutes(Utilities.hrsToMins(totalDuration));
			dao.postPWInvoiceLine(pwil);		
			/* end for each user */
	
		}
		dao.addInvoice(this);
		markInvoiced();
		return new ForwardResolution("/invoiceConf.jsp");
	}

	public Resolution postMultiple() {
		Logger.debug(this.getClass(), "postMultiple() ");
	//	addNewEntry();
		InvoiceDao dao = getInvoiceDao();
		PWInvoiceLine pwil = new PWInvoiceLine();
		
		StaffDao staffDao = new StaffDao();
		User userData;
		StaffDao sdao = new StaffDao();

		amount = 0;
		totalDuration = 0.0;
		
		for (int i = 0; i < entries.size(); i++) {
			if (entries.get(i).isInclude()) {
				pwil.setAmount(entries.get(i).getRevenue());
				pwil.setCaseId(caseId);
				pwil.setComment(entries.get(i).getComment());
				pwil.setDate(entries.get(i).getDate());
				pwil.setRate(entries.get(i).getRate());
				pwil.setTimeInMinutes(Utilities.hrsToMins(entries.get(i).getDuration()));
				pwil.setRevenueCentre(this.getContext().getUser().getRevenueCentre());

				userData = staffDao.getUserData(entries.get(i).getStaffAlias());	

				if (!userData.isAttorney()) {
					/*assistant*/
					/* get responsible consultant */
					String respConsName = PatraWinDao.getCaseResponsible(caseId);
				
					/* get consultant data */
					userData = sdao.getUserData(respConsName);		

				}
				else {
					/* individual code */

				}
				pwil.setStaffId(userData.getUserName());
				pwil.setAccountCode(Utilities.padNbr(userData.getAccountCode()));
				pwil.setRevenueCentre(userData.getRevenueCentre());
				
				amount += entries.get(i).getRevenue();
				totalDuration += entries.get(i).getDuration();
	
				dao.postPWInvoiceLine(pwil);						
			}
		}
		dao.addInvoice(this);
		markInvoiced();
		return new ForwardResolution("/invoiceConf.jsp");
	}

	
	
	public Resolution postMultipleCombinedRevenue() {
		Logger.debug(this.getClass(), "postMultipleCombinedRevenue()");
		InvoiceDao dao = getInvoiceDao();
		TimeEntryDao ted = new TimeEntryDao ();
	//	addNewEntry();
		PWInvoiceLine pwil = new PWInvoiceLine();
		PWInvoiceLine pwilText = new PWInvoiceLine();
		
		StaffDao sdao = new StaffDao();
		User postUser = new User();
		
		pwil.setCaseId(caseId);

		/* get all users of entries */
		Iterator<String> iter = this.getPosters().iterator();
		
		/* For each user */
		while (iter.hasNext()) {			
			
			amount = 0;
			totalDuration = 0.0;
			
			String poster = iter.next();
			postUser.populate(poster);

			if (!postUser.isAttorney()) {
				/* assistant */
				/* get responsible consultant */
				String respConsName = PatraWinDao.getCaseResponsible(caseId);
			
				/* get consultant data */
				postUser = sdao.getUserData(respConsName);		
			} /* default is already current user, so nothing to do if consultant */
			
			pwil.setRevenueCentre(postUser.getRevenueCentre());
			pwil.setStaffId(postUser.getUserName());
			pwil.setAccountCode(Utilities.padNbr(postUser.getAccountCode()));
			pwil.setComment("");
			
			for (int i = 0; i < entries.size(); i++) {
				if (entries.get(i).isInclude() && entries.get(i).getStaffAlias().equals(poster)) {
					pwilText.setRevenueCentre(postUser.getRevenueCentre());
					pwilText.setStaffId(postUser.getUserName());
					pwilText.setAccountCode(Utilities.padNbr(postUser.getAccountCode()));
					pwilText.setComment(entries.get(i).getComment() );			
					pwilText.setDate(entries.get(i).getDate()); 
					pwilText.setRate(entries.get(i).getRate()); 
					pwilText.setCaseId(caseId);

					pwil.setDate(entries.get(i).getDate()); 
					pwil.setRate(entries.get(i).getRate()); 
					
					amount += entries.get(i).getRevenue();
					totalDuration += entries.get(i).getDuration(); /* duration in hours with one decimal*/
					
					if (entries.get(i).getId() != 0) {
						// if it has ID - update
						ted.update(entries.get(i));
					}
					else {
						// else - new entry
						ted.add(entries.get(i));
					}
					dao.postPWInvoiceLine(pwilText); //posts text only
				}
			}
			
			pwil.setAmount(amount);
			pwil.setTimeInMinutes((int) (totalDuration*60));
			dao.postPWInvoiceLine(pwil);		
			/* end for each user */
	
		}
		dao.addInvoice(this);
		markInvoiced();
		return new ForwardResolution("/invoiceConf.jsp");
	}

	
		
	public Resolution postStats() {
		/* marks selected lines as invoiced but without posting to PW */
		Logger.debug(this.getClass(), "postStats()");
		InvoiceDao dao = getInvoiceDao();
		TimeEntryDao ted = new TimeEntryDao ();
	//	addNewEntry();

		amount = 0;
		totalDuration = 0.0;

		for (int i = 0; i < entries.size(); i++) {
			if (entries.get(i).isInclude()) {
				
				// if it has ID - update
				if (entries.get(i).getId() != 0) {
					ted.update(entries.get(i));
				}
				else {
					// else - new entry
					ted.add(entries.get(i));
				}
				amount += entries.get(i).getRevenue();
				totalDuration += entries.get(i).getDuration();
			} //end if
		}

		dao.addInvoice(this);
		markInvoiced();
		return new ForwardResolution("/invoiceConf.jsp");
	}

	public Resolution delete() {
		Logger.debug(this.getClass(), "delete() ");
		
		for (int i = 0; i<this.getEntries().size() ; i++) {
			if (entries.get(i).getId() == processEntryId) {
				TimeEntryDao ted = new TimeEntryDao();				
				ted.delete(processEntryId);
				this.totalDuration -= this.getEntries().get(i).getDuration();
				this.amount -= this.getEntries().get(i).getRevenue();
				this.getEntries().remove(i);
				break;
			}
		}
		return new ForwardResolution("/invoiceEdit.jsp");
	}
	
/*
	public int addNewEntry() {
		// Complete data for new entry
		int id = 0;
		Logger.debug(this.getClass(), "addEntry() ");
		
			if (!newTimeEntry.empty()) {
				newTimeEntry.setCaseId(caseId);
				newTimeEntry.setCaseName(caseName);
				newTimeEntry.setClient(client);
				newTimeEntry.setRate((int) (newTimeEntry.getRevenue()/newTimeEntry.getDuration()));
				TimeEntryDao ted = new TimeEntryDao();				
				id = ted.add(newTimeEntry); // new time entry added to DB
				newTimeEntry.setId(id);
				this.totalDuration += newTimeEntry.getDuration();
				this.amount += newTimeEntry.getRevenue();
				this.getEntries().add(newTimeEntry);
			}
		return newTimeEntry.getId();
	}
*/	
	

	public Resolution view() {
		// add invoice data
		InvoiceDao dao = getInvoiceDao();
		
		dao.getInvoice(this);
//		totalRevenue = amount - fixedFee - adjustment;
		// fetch time entries
		TimeEntryDao ted = new TimeEntryDao();
		entries = ted.getInvoiceEntries(this.getId(), this);
	
		return new ForwardResolution("/invoiceConf.jsp");
	}

	public Resolution ok() {
		return new RedirectResolution(se.kransellwennborg.tink.actions.EntryEditActionBean.class);
	}

	public void markInvoiced() {
		getEntries();
		for (int i = 0; i < entries.size() ; i++) {
			// set invoice id in timentry when included
			if (entries.get(i).isInclude()) {
				this.getTimeEntryDao().setInvoiceId(entries.get(i).getId(), this.getId());				
			}
		}
	}

	public void esc() {
		caseName = Utilities.esc(caseName);
		client = Utilities.esc(client);
	}
	
	public Resolution adjustRate() {
		Logger.debug(this.getClass(), "adjustRate() ");
	
		TimeEntryDao ted = new TimeEntryDao();				

		int newTotal = 0;
		int newAmount = 0;
		
		for (int i = 0; i< entries.size() ; i++) {

			entries.get(i).setRate(blanketRate);
			
			newAmount = (int) (entries.get(i).getDuration() * blanketRate);

			entries.get(i).setRevenue(newAmount);
			newTotal += newAmount;
			ted.update(entries.get(i));
		}
		
		this.setAmount(newTotal);
		
		return new RedirectResolution("/invoiceEdit.jsp").addParameter("caseId", this.caseId);
	}

}