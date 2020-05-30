package se.kransellwennborg.tink.actions;

import net.sourceforge.stripes.action.DefaultHandler;
import net.sourceforge.stripes.action.DontValidate;
import net.sourceforge.stripes.action.ForwardResolution;
import net.sourceforge.stripes.action.RedirectResolution;
import net.sourceforge.stripes.action.Resolution;
import net.sourceforge.stripes.validation.Validate;
import net.sourceforge.stripes.validation.ValidateNestedProperties;
import se.kransellwennborg.tink.Logger;
import se.kransellwennborg.tink.beans.PunchEntry;
import se.kransellwennborg.tink.converters.TinkDateConverter;
import se.kransellwennborg.tink.dao.PunchEntryDao;

/**
 * Login action.
 */
public class AdminActionBean extends BaseActionBean {

	@ValidateNestedProperties( { @Validate(field = "date", required = true, converter = TinkDateConverter.class)  })

	private PunchEntry punchEntry;
	private String message;

	public String getMessage() {
		
		return "message: " + message;
	}

	public void setMessage(String message) {
		this.message = message;
	}


	@DontValidate
	@DefaultHandler
	public Resolution standard() {
		return new ForwardResolution("/admin.jsp");
	}

	public PunchEntry getPunchEntry() {
		return punchEntry;
	}
	public void setPunchEntry(PunchEntry punchEntry) {
		this.punchEntry = punchEntry;
	}

	@DontValidate
	public Resolution adjustTime() {
		Logger.debug(this.getClass(), "in adjustTime()");
		punchEntry.setId(0);
		PunchEntryDao ped = new PunchEntryDao();
		ped.postAdjustment(punchEntry);
		this.message = "done!";
		this.punchEntry = null;
		return new RedirectResolution(AdminActionBean.class);
	}

}
