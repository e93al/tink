<%@ taglib prefix="stripes" uri="http://stripes.sourceforge.net/stripes.tld"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<jsp:useBean id="entries" scope="request" class="se.kransellwennborg.tink.beans.TimeEntries" />
<jsp:setProperty name="entries" property="dateString" value="${navDate}" />
<jsp:setProperty name="entries" property="staffAlias" value="${user.userName}" />
<table class="typeB" style="width:400px">
	<tr>
		<stripes:form beanclass="se.kransellwennborg.tink.actions.EntryEditActionBean" id="timeForm">
		<th colspan="1">
			Date:
			</th>
		<th colspan="4">
			<stripes:link beanclass="se.kransellwennborg.tink.actions.EntryEditActionBean" event="gotoDate">
				<stripes:param name="timeEntry.date" value="${navDate.dayBefore}" />&lt;prev</stripes:link>
			<stripes:text name="timeEntry.date" id="dateInput" size="6" formatType="date" formatPattern="yyMMdd"/>
			<stripes:link beanclass="se.kransellwennborg.tink.actions.EntryEditActionBean" event="gotoDate">
				<stripes:param name="timeEntry.date" value="${navDate.dayAfter}" />next&gt;</stripes:link>
			</th>
			<th>
			<stripes:submit name="gotoDate" value="Goto Date" /> 
		</th>
		</stripes:form>
		<th colspan="4">
		<stripes:form beanclass="se.kransellwennborg.tink.actions.EntryEditActionBean" id="timeForm">
			<stripes:submit name="gotoToday" value="Today" /> 
		</stripes:form>
		</th>
	</tr>
	<tr>
		<th>Case ID</th>
		<th style="min-width:70px">Client</th>
		<th>Date</th>
		<th>Start</th>
		<th>End</th>
		<th colspan="4">Dur.</th>
	</tr>
	<c:forEach items="${entries.timeEntries}" var="entry" varStatus="loop">
		<tr>
			<td>${entry.caseId}</td>
			<td>${entry.shortShortClient}</td>
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
