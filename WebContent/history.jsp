<%@ page contentType="text/html;charset=iso-8859-1" language="java"%>
<%@ taglib prefix="stripes" uri="http://stripes.sourceforge.net/stripes.tld"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="java.util.*"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html>
<head>
<title>Tink</title>
<script src="${pageContext.request.contextPath}/js/scripts.js"></script>
<link href="${pageContext.request.contextPath}/css/KW_style.css" rel="stylesheet" type="text/css" />
</head>
<body>
<stripes:layout-render name="/layout/Default.jsp">
	<stripes:layout-component name="contents">
		<td>
		<table class="typeB" width="500px">
			<tr>
				<stripes:form beanclass="se.kransellwennborg.tink.actions.EntryEditActionBean" id="timeForm">
					<th colspan="4">Date: <stripes:link beanclass="se.kransellwennborg.tink.actions.EntryEditActionBean" event="gotoDate">
						<stripes:param name="timeEntry.date" value="${navDate.dayBefore}" />&lt;prev</stripes:link> <stripes:text name="timeEntry.date"
						id="dateInput" size="10" formatType="date" formatPattern="yyMMdd"
					/> <stripes:link beanclass="se.kransellwennborg.tink.actions.EntryEditActionBean" event="gotoDate">
						<stripes:param name="timeEntry.date" value="${navDate.dayAfter}" />next&gt;</stripes:link></th>
					<th><stripes:submit name="gotoDate" value="Goto Date" /></th>
				</stripes:form>
				<th colspan="6"><stripes:form beanclass="se.kransellwennborg.tink.actions.EntryEditActionBean" id="timeForm">
					<stripes:submit name="gotoToday" value="Today" />
				</stripes:form></th>
				<th></th>
			</tr>
			<tr>
				<th>Case ID</th>
				<th>Applicant</th>
				<th>Date</th>
				<th>Start Time</th>
				<th>End Time</th>
				<th>Duration</th>
				<th>&nbsp;</th>
				<th>&nbsp;</th>
				<th>&nbsp;</th>
			</tr>
			<c:forEach items="${entries.timeEntries}" var="entry" varStatus="loop">
				<tr>
					<td>${entry.caseId}</td>
					<td>${entry.shortClient}</td>
					<td>${entry.dateString}</td>
					<td>${entry.startTimeString}</td>
					<td>${entry.endTimeString}</td>
					<td>${entry.duration}</td>
					<td><stripes:link beanclass="se.kransellwennborg.tink.actions.EntryEditActionBean">
						<stripes:param name="timeEntry.id" value="${entry.id}" />
				Edit
			</stripes:link></td>
					<td><stripes:link beanclass="se.kransellwennborg.tink.actions.EntryEditActionBean" event="cont">
						<stripes:param name="timeEntryId" value="${entry.id}" />
				Cont
			</stripes:link></td>
					<td><stripes:link beanclass="se.kransellwennborg.tink.actions.EntryEditActionBean" event="delete">
						<stripes:param name="timeEntry.id" value="${entry.id}" />
				Delete
			</stripes:link></td>
				</tr>
			</c:forEach>
			<tr>
				<th>Total:</th>
				<th>&nbsp;</th>
				<th>&nbsp;</th>
				<th>&nbsp;</th>
				<th>&nbsp;</th>
				<th>${entries.totalDuration}</th>
				<th>&nbsp;</th>
				<th>&nbsp;</th>
				<th>&nbsp;</th>
			</tr>
		</table>
		</td>
	</stripes:layout-component>
</stripes:layout-render>
</body>
</html>
