package se.kransellwennborg.tink;

import java.util.Calendar;
import java.util.HashMap;

import javax.servlet.http.Cookie;

import net.sourceforge.stripes.action.ActionBeanContext;
import se.kransellwennborg.tink.beans.User;


public class TinkActionBeanContext extends ActionBeanContext {
	public static String DBDATA_LABEL = "dbData";

	private static final String LOGGED_IN_MAP = "LOGGED_IN_MAP";

	@SuppressWarnings("unchecked")
	public void setIsLoggedIn(Boolean isLoggedIn) {
		HashMap<String, Boolean> map;
		if (getRequest().getAttribute(LOGGED_IN_MAP) == null) {
			map = new HashMap<String, Boolean>();
		} else {
			map = (HashMap<String, Boolean>) getRequest().getAttribute(LOGGED_IN_MAP);
		}
		try {
			map.put(getUser().getUserName(), new Boolean(isLoggedIn));
		} catch (NullPointerException e) { /* do nothing */ }
		getRequest().setAttribute(LOGGED_IN_MAP, map);

	}

	@SuppressWarnings("unchecked")
	public boolean getIsLoggedIn() {
		boolean result = false;

		if (getRequest().getAttribute(LOGGED_IN_MAP) == null) {
			result = false;
		} else {
			HashMap<String, Boolean> map = (HashMap<String, Boolean>) getRequest().getAttribute(LOGGED_IN_MAP);
			try {
				result = map.get(getUser().getUserName()).booleanValue();
			}
			catch (NullPointerException e ) {/* do nothing */}
		}

		return result;
	}

	public void setUser(User user) {
		Cookie userCookie = null;
		if (user != null) {
			userCookie = new Cookie("user", user.getUserName());
			userCookie.setMaxAge(365 * 24 * 60 * 60);
		}
		else {
			userCookie = new Cookie("user", "");
			userCookie.setMaxAge(0);
		}
		
		getResponse().addCookie(userCookie);
		this.getRequest().setAttribute("user", user);
		if (user != null) {
			getServletContext().setAttribute(user.getUserName(), user);
		}
	}

	public User getUser() {
		User result = null;

		result = (User) getRequest().getAttribute("user");
		if (result == null) {
			Cookie[] cookies = getRequest().getCookies();
			for (int i = 0; (cookies != null) && (i < cookies.length); i++) {
				Cookie cookie = cookies[i];
				if (cookie.getName().equals("user")) {
					String userName = cookie.getValue();
					result = (User) getServletContext().getAttribute(userName);
					getRequest().getSession().setAttribute("user", result);
	//				break;
				}
			}
		}
		return result;
	}

	public void setNavDate(TinkDate date) {
		getRequest().getSession().setAttribute("navDate", date);
	}

	public TinkDate getNavDate() {
		TinkDate res;
		res = (TinkDate) getRequest().getSession().getAttribute("navDate");
		if (res == null) {
			res = new TinkDate(Calendar.getInstance().getTime());
			setNavDate(res);
		}
		return res;
	}

//	public void setDBdata(DbData dbData) {
//		this.getServletContext().setAttribute(TinkActionBeanContext.DBDATA_LABEL, dbData);
//	}
//
//	public DbData getDBdata() {
//		return (DbData) this.getServletContext().getAttribute(TinkActionBeanContext.DBDATA_LABEL);
//	}
}
