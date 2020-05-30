<%@ taglib prefix="stripes" uri="http://stripes.sourceforge.net/stripes.tld"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<jsp:useBean id="invoices" scope="request" class="se.kransellwennborg.tink.actions.InvoicesActionBean" />
<jsp:setProperty name="invoices" property="invoicedBy" value="${actionBean.viewInvoicesFor}" />

<table class="typeB" style="width:500px">

<stripes:form beanclass="se.kransellwennborg.tink.actions.InvoicesActionBean" id="timeForm">
	<tr>
		<td>View invoices for</td>
		<td><stripes:text id="viewInvoicesForInput" name="viewInvoicesFor" size="5"/></td>		
		<td><stripes:submit name="setViewInvoicesFor" value="submit"/></td>
	</tr>
</stripes:form>
	<tr>
		<th>Case ID</th>
		<th>Client</th>
		<th>Invoiced By</th>
		<th width="90" >Date</th>
		<th>Amount</th>
		<th>&nbsp;</th>
		<th>5-30'</th>
		<th>&gt;30'</th>
	</tr>
	<c:set var="countA" value="0" scope="page"/>
	<c:set var="countB" value="0" scope="page"/>
	
	<c:forEach items="${invoices.entries}" var="entry" varStatus="loop">
		<tr>
			<td>${entry.caseId}</td>
			<td>${entry.client}</td>
			<td>${entry.invoicedBy}</td>
			<td>${entry.date}</td>
			<td>${entry.amount}</td>
			<td><stripes:link beanclass="se.kransellwennborg.tink.actions.InvoiceActionBean" event="view">
				<stripes:param name="id" value="${entry.id}" />
				view
			</stripes:link></td>
			<td>
			<c:if test="${entry.amount < 30000 && entry.amount >= 5000}">
			<c:set var="countA" value="${countA + 1}" scope="page"/>
			${countA}
			</c:if>
			</td>
			<td>
			<c:if test="${entry.amount >= 30000 }">
			<c:set var="countB" value="${countB + 1}" scope="page"/>
			${countB}
			</c:if>
			</td>
			<td></td>
		</tr>
	</c:forEach>
</table>
