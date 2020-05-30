package se.kransellwennborg.tink.actions;

import net.sourceforge.stripes.action.Before;
import net.sourceforge.stripes.action.DefaultHandler;
import net.sourceforge.stripes.action.DontValidate;
import net.sourceforge.stripes.action.ForwardResolution;
import net.sourceforge.stripes.action.RedirectResolution;
import net.sourceforge.stripes.action.Resolution;
import net.sourceforge.stripes.controller.LifecycleStage;
import se.kransellwennborg.tink.Logger;
import se.kransellwennborg.tink.beans.User;

/**
 * Login action.
 */
public class SettingsActionBean extends BaseActionBean {

	// TODO: add more validation
	private User user;
	private String userName;

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	/* action methods below */
	@Before(stages = LifecycleStage.ResolutionExecution)
	void prePopulate() {
		Logger.debug(this.getClass(), "in prePopulate() for settings");

		if (user == null) {
			user = getContext().getUser();
		}
		
		if (userName == null || userName.length() < 1) {
			userName = getContext().getUser().getUserName();
		}
	}

	public Resolution post() {
		Logger.debug(getClass(), "in post(), userName:" + userName);

		return new RedirectResolution(SettingsActionBean.class);
	}


	@DontValidate
	@DefaultHandler
	public Resolution standard() {
		Logger.debug(getClass(), "in standard()");

		return new ForwardResolution("/settings.jsp");
	}

}
