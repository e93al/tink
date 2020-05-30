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
					<td>Case data</td>
				</tr>
			</table>
			<p/>
		<stripes:form beanclass="se.kransellwennborg.tink.actions.CaseDataActionBean" id="caseForm">
			<stripes:errors />
			<table>
				<tr>
					<td>Case ID:</td>
					<td><stripes:text id="caseIdInput" name="timeEntry.caseId" size="10"/></td>
					<td >
						<stripes:submit name="Get data" value="GetCaseData" />
					</td>
				</tr>
			</table>
		</stripes:form>
	</stripes:layout-component>
</stripes:layout-render>
