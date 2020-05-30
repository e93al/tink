<%@ taglib prefix="stripes" uri="http://stripes.sourceforge.net/stripes.tld"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<jsp:useBean id="entries" scope="request" class="se.kransellwennborg.tink.beans.PunchEntries" />
<jsp:setProperty name="entries" property="dateString" value="${actionbean.formDate}" />
<jsp:setProperty name="entries" property="staffAlias" value="${user.userName}" />
<table class="typeB" style="width:500px">
	<tr>
		<th>Date</th>
		<th>Punch in</th>
		<th>Punch out</th>
		<th>Duration</th>
		<th>Day total</th>
		<th>&nbsp;</th>
	</tr>
	<c:set var="lastDate" value="" scope="page" />
	<c:set var="dayTotal" value="0" scope="page" />
	<c:forEach items="${entries.punchEntries}" var="entry" varStatus="loop">
		<c:if test='${entry.date != lastDate}'>
			<c:set var="dayTotal" value="0" scope="page" />			
		</c:if>
		<c:set var="dayTotal" value="${dayTotal + entry.duration}" scope="page" />	
		<tr>
		<c:choose> 
			<c:when test='${entry.scheduledTime == "true"}'>
			<td style="border-width: 0px 0px 1px 0px;">${entry.date}</td>
			<td style="border-width: 0px 0px 1px 0px;" 
				colspan="2">Normal work time for day
			</td>
			<td style="border-width: 0px 0px 1px 0px;">${entry.duration}</td>
			<td style="border-width: 0px 0px 1px 0px;">
				<fmt:setLocale value="en" />
				<fmt:formatNumber value="${dayTotal}" pattern="0.0" />
			</td>
			<td style="border-width: 0px 0px 1px 0px;">&nbsp;</td>
			
		</c:when>
		<c:otherwise>
			<td>${entry.date}</td>
			<td>${entry.punchInTimeString}<c:if test='${entry.modifiedIn == "true"}'>*</c:if></td>
			<td>${entry.punchOutTimeString}<c:if test='${entry.modifiedOut == "true"}'>*</c:if></td>
			<td>${entry.duration}</td>
			<td>
			</td>
			<td><stripes:link beanclass="se.kransellwennborg.tink.actions.PunchEditActionBean">
				<stripes:param name="punchEntry.id" value="${entry.id}" />
				Edit
			</stripes:link></td>
			
		</c:otherwise>
		</c:choose>
		</tr>
		<c:set var="lastDate" value="${entry.date}" scope="page" />
	</c:forEach>
	<tr>
		<th>Total:</th>
		<th>&nbsp;</th>
		<th>&nbsp;</th>
		<th>&nbsp;</th>
		<th>${entries.totalDuration}</th>
		<th>&nbsp;</th>
	</tr>
</table>
