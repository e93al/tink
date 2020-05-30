package se.kransellwennborg.tink.actions;

import net.sourceforge.stripes.action.DefaultHandler;
import net.sourceforge.stripes.action.DontValidate;
import net.sourceforge.stripes.action.ForwardResolution;
import net.sourceforge.stripes.action.RedirectResolution;
import net.sourceforge.stripes.action.Resolution;
import net.sourceforge.stripes.validation.SimpleError;
import net.sourceforge.stripes.validation.Validate;
import net.sourceforge.stripes.validation.ValidationErrors;
import net.sourceforge.stripes.validation.ValidationMethod;
import se.kransellwennborg.tink.Logger;
import se.kransellwennborg.tink.WorkDays;
import se.kransellwennborg.tink.beans.User;

/**
 * Login action.
 */
public class LoginActionBean extends BaseActionBean {

	@Validate(required = true)
	private String userName;
	@Validate(required = true)
	private String password;

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	@ValidationMethod(on = "login")
	public void checkLoginDetails(ValidationErrors errors) {
		if (!password.equals(userName)) {
			errors.add("user", new SimpleError("Incorrect user and/or password."));
		}
	}

	@DontValidate
	@DefaultHandler
	public Resolution forwardIfLoggedIn() {

		// if (getContext().getUser() != null) {
		if (getContext().getIsLoggedIn() == true) {
			return new RedirectResolution(PunchActionBean.class);
		} else {
			return new ForwardResolution("/login.jsp");
		}
	}

	public Resolution login() {
		Logger.debug(this.getClass(), "login()");
		User user = new User();
		user.populate(userName);
		getContext().setUser(user);
		getContext().setIsLoggedIn(true);
		//todo: remove when tested ok
		WorkDays wd = new WorkDays();
		wd.test(getContext());
		
		if (user.isAttorney()) {
			return new RedirectResolution(EntryEditActionBean.class);					
		}
		else {
			return new RedirectResolution(PunchActionBean.class);			
		}
	}
}
