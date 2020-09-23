<%@ taglib prefix="stripes" uri="http://stripes.sourceforge.net/stripes.tld"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ page import="se.kransellwennborg.tink.Constants" %>
<jsp:useBean id="entries" scope="request" class="se.kransellwennborg.tink.beans.TimeEntries" />
<jsp:useBean id="editActionBean" scope="request" class="se.kransellwennborg.tink.actions.EntryEditActionBean" />
<jsp:setProperty name="entries" property="dateString" value="${navDate}" />
<jsp:setProperty name="entries" property="staffAlias" value="${user.userName}" />
<jsp:setProperty name="entries" property="sortOrder" value="${user.sortOrder}" />
<fmt:setLocale value="sv_SE" />

<table class="typeB">
	<tr>
		<th colspan="7">Cases in Progress, sorted by ${user.sortOrder}</th>
	</tr>
	<tr>
		<th>
			<stripes:link beanclass="se.kransellwennborg.tink.actions.EntryEditActionBean" event="changeSortOrder">
				<stripes:param name="sortOrder" value="CASE_ID" />
				Case ID
			</stripes:link>
		</th>
		<th>
				Client Ref
		</th>
		<th>
			<stripes:link beanclass="se.kransellwennborg.tink.actions.EntryEditActionBean" event="changeSortOrder">
				<stripes:param name="sortOrder" value="APPLICANT" />
				Client
			</stripes:link>
		</th>
		<th>
			<stripes:link beanclass="se.kransellwennborg.tink.actions.EntryEditActionBean" event="changeSortOrder">
				<stripes:param name="sortOrder" value="CASE_NAME" />
				Case Name
			</stripes:link>
		</th>
		<th>
			<stripes:link beanclass="se.kransellwennborg.tink.actions.EntryEditActionBean" event="changeSortOrder">
				<stripes:param name="sortOrder" value="AMOUNT" />
				Amount
			</stripes:link>
		</th>
		<th>
			<stripes:link beanclass="se.kransellwennborg.tink.actions.EntryEditActionBean" event="changeSortOrder">
				<stripes:param name="sortOrder" value="DATE" />
				(Date)
			</stripes:link>
		</th>
	</tr>
	<c:forEach items="${entries.uninvoicedEntries}" var="entry" varStatus="loop">
		<tr>
			<td id="caseId_${entry.caseId}" onClick="copyCase('${entry.caseId}')" class="link">${entry.caseId}</td>
			<td>${entry.clientRef}</td>
			<td>${entry.shortClient}</td>
			<td>${entry.shortCaseName}</td>
			<td align="right"><fmt:formatNumber type="number" pattern="#,###" value="${entry.revenue}"/></td>
			<td><stripes:link beanclass="se.kransellwennborg.tink.actions.InvoiceActionBean" charset="iso-8859-1">
					<stripes:param name="caseId" value="${entry.caseId}" />Invoice</stripes:link>
			</td>
		</tr>	
		<div class="hidden" id="client_${entry.caseId}" >${entry.client}</div>
		<div class="hidden" id="caseName_${entry.caseId}" >${entry.caseName}</div>
	</c:forEach>
	<tr>
		<th>Total:</th>
		<th>&nbsp;</th>
		<th>&nbsp;</th>
		<th>&nbsp;</th>
		<th align="right"><fmt:formatNumber type="number" pattern="#,###" value="${entries.totalRevenue}"/></th>
		<th>&nbsp;</th>
	</tr>
</table>
