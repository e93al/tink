<%@ page contentType="text/html;charset=iso-8859-1" language="java"%>
<%@ taglib prefix="stripes" uri="http://stripes.sourceforge.net/stripes.tld"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="java.util.*"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<stripes:layout-render name="/layout/Default.jsp">
	<stripes:layout-component name="header">
	</stripes:layout-component>
	<stripes:layout-component name="contents">
			<table>
				<tr>
					<td>Log in to Tink - the time management system for Kransell &amp; Wennborg.</td>
				</tr>
			</table>
			<p/>
		<stripes:form beanclass="se.kransellwennborg.tink.actions.LoginActionBean" focus="">
			<stripes:errors />
			<table>
				<tr>
					<td>User name:</td>
					<td><stripes:text style="width:160px;" name="userName" /></td>
				</tr>
				<tr>
					<td>Password:</td>
					<td><stripes:password style="width:160px;" name="password" /></td>
				</tr>
				<tr>
					<td>&nbsp;</td>
					<td colspan="2">
						<stripes:hidden name="timeEntryDefault" value="blank_today" />
						<stripes:submit name="login" value="Login" />
					</td>
				</tr>
			</table>
		</stripes:form>
	</stripes:layout-component>
</stripes:layout-render>
