package se.kransellwennborg.tink.actions;

import java.util.ArrayList;
import java.util.Date;

import net.sourceforge.stripes.action.Before;
import net.sourceforge.stripes.action.DefaultHandler;
import net.sourceforge.stripes.action.ForwardResolution;
import net.sourceforge.stripes.action.Resolution;
import net.sourceforge.stripes.controller.LifecycleStage;
import se.kransellwennborg.tink.Logger;
import se.kransellwennborg.tink.dao.InvoiceDao;

public class InvoicesActionBean extends BaseActionBean {

	private ArrayList<InvoiceActionBean> entries;

	private InvoiceDao invoiceDao;

	private String invoicedBy;
	
	private Date date;

	private String caseId;

	private String client;
	
	private String viewInvoicesFor;
	
	public String getInvoicedBy() {
		return invoicedBy;
	}

	public void setInvoicedBy(String invoicedBy) {
		this.invoicedBy = invoicedBy;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public String getCaseId() {
		return caseId;
	}

	public void setCaseId(String caseId) {
		this.caseId = caseId;
	}

	public String getClient() {
		return client;
	}

	public void setClient(String client) {
		this.client = client;
	}

	public void setInvoiceDao(InvoiceDao invoiceDao) {
		this.invoiceDao = invoiceDao;
	}


	public String getViewInvoicesFor() {
		return viewInvoicesFor;
	}

	public void setViewInvoicesFor(String viewInvoicesFor) {
		this.viewInvoicesFor = viewInvoicesFor;
	}

	public ArrayList<InvoiceActionBean> getEntries() {
		if (entries == null) {
			entries = getInvoiceDao().getEntries(this);
		}
		return entries;
	}

	public void setEntries(ArrayList<InvoiceActionBean> entries) {
		this.entries = entries;
	}

	public InvoiceDao getInvoiceDao() {
		if (invoiceDao == null) {
			invoiceDao = new InvoiceDao();
		}
		return invoiceDao;
	}

	@Before(stages = LifecycleStage.ResolutionExecution)
	void prePopulate() {
		Logger.debug(this.getClass(), "in prePopulate() for punch entry");

		if (viewInvoicesFor == null || viewInvoicesFor.equals("")) {
			viewInvoicesFor = this.getContext().getUser().getUserName();
		}
	}
	@DefaultHandler
	public Resolution execute() {
		return new ForwardResolution("/invoices.jsp");
	}
	
	public Resolution setViewInvoicesFor() {
		return new ForwardResolution("/invoices.jsp");
	}

}