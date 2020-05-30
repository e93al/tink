package se.kransellwennborg.tink.actions;

import net.sourceforge.stripes.action.Before;
import net.sourceforge.stripes.action.DefaultHandler;
import net.sourceforge.stripes.action.DontValidate;
import net.sourceforge.stripes.action.ForwardResolution;
import net.sourceforge.stripes.action.RedirectResolution;
import net.sourceforge.stripes.action.Resolution;
import net.sourceforge.stripes.controller.LifecycleStage;
import net.sourceforge.stripes.validation.Validate;
import net.sourceforge.stripes.validation.ValidateNestedProperties;
import se.kransellwennborg.tink.Logger;
import se.kransellwennborg.tink.Utilities;
import se.kransellwennborg.tink.beans.PunchEntry;
import se.kransellwennborg.tink.converters.TinkDateConverter;
import se.kransellwennborg.tink.converters.TinkTimeConverter;
import se.kransellwennborg.tink.dao.PunchEntryDao;

/**
 * Login action.
 */
public class PunchEditActionBean extends BaseActionBean {

	// TODO: add more validation
	@ValidateNestedProperties( { @Validate(field = "punchInTime", required = true, converter = TinkTimeConverter.class),
			@Validate(field = "punchOutTime", required = true, converter = TinkTimeConverter.class),
			@Validate(field = "date", required = true, converter = TinkDateConverter.class)  })
	
			
	private PunchEntry punchEntry;
	private int id;
	private PunchEntryDao ped;
	
	
	public PunchEntryDao getPed() {
		if (ped == null) {
			ped = new PunchEntryDao();
		}
		return ped;
	}

	public void setPed(PunchEntryDao ped) {
		this.ped = ped;
	}

	public PunchEntry getPunchEntry() {
		return punchEntry;
	}

	public void setPunchEntry(PunchEntry punchEntry) {
		this.punchEntry = punchEntry;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	@Before(stages = LifecycleStage.ResolutionExecution)
	void prePopulate() {
		Logger.debug(this.getClass(), "in prePopulate()");

		if (punchEntry == null) {
			punchEntry = new PunchEntry();
		}

		if (punchEntry.getId() > 0) {
			punchEntry = getPed().read(punchEntry.getId());
		} else {
			Logger.error(this.getClass(), "In edit for no ID - should not happen!");
		}
	}

	@DontValidate
	@DefaultHandler
	public Resolution standard() {
		Logger.debug(getClass(), "in standard(), punchEntry:" + punchEntry);
		return new ForwardResolution("/punchEdit.jsp");
	}

	public Resolution post() {
		Logger.debug(getClass(), "in post(), punchEntry:" + punchEntry);
		calculateFields();
		punchEntry.setModifiedIn(true);
		punchEntry.setModifiedOut(true);
		
		getPed().post(punchEntry);

		return new RedirectResolution(PunchActionBean.class);
	}

	void calculateFields() {
		// First we need to calculate duration with one decimal
		double duration = Utilities.calcDuration(punchEntry.getPunchInTime(), punchEntry.getPunchOutTime());
		punchEntry.setDuration(duration);
	}
}
