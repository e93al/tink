package se.kransellwennborg.tink.actions;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import net.sourceforge.stripes.action.DefaultHandler;
import net.sourceforge.stripes.action.ForwardResolution;
import net.sourceforge.stripes.action.Resolution;
import se.kransellwennborg.tink.Logger;

/**
 * Login action.
 */
public class SandBoxActionBean extends BaseActionBean {

	private String s;
	private String s2;
	
	public String getS() {
		return s;
	}

	public void setS(String s) {
		this.s = s;
	}

	public String getS2() {
		return s2;
	}

	public void setS2(String s2) {
		this.s2 = s2;
	}

	public String getEncodedS(){
		String res = "";
		try {
			res = URLEncoder.encode(s, "ISO-8859-1");
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Logger.debug(getClass(), "encoded to  " + res);
		return res;
	}
	
	@DefaultHandler
	public Resolution execute() {
		Logger.debug(getClass(), "in execute() with " + s);
	
		return new ForwardResolution("/test.jsp");
	}
	
}