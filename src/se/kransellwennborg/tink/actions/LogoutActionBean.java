package se.kransellwennborg.tink.actions;

import net.sourceforge.stripes.action.ActionBean;
import net.sourceforge.stripes.action.ActionBeanContext;
import net.sourceforge.stripes.action.DefaultHandler;
import net.sourceforge.stripes.action.ForwardResolution;
import net.sourceforge.stripes.action.Resolution;
import net.sourceforge.stripes.validation.Validate;
import se.kransellwennborg.tink.TinkActionBeanContext;
import se.kransellwennborg.tink.beans.User;

/**
 * Login action.
 */
public class LogoutActionBean implements ActionBean {

	private TinkActionBeanContext context;

    @Validate(required=true) private String userName;
    
    public ActionBeanContext getContext() { return context; }
    public void setContext(ActionBeanContext context) { this.context = (TinkActionBeanContext) context; }

    public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	
    @DefaultHandler
    public Resolution logout() {
    	User user = null;
    	context.setUser(user);
    	context.setIsLoggedIn(false);
   		return new ForwardResolution("/index.jsp");
    }
}