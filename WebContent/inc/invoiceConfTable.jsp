<%@ taglib prefix="stripes" uri="http://stripes.sourceforge.net/stripes.tld"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<fmt:setLocale value="sv_SE" />
<stripes:form beanclass="se.kransellwennborg.tink.actions.InvoiceActionBean" id="invoiceForm">
	<stripes:errors />
	<table class="typeB" style="width:500px">
		<tr>
			<!--  >th colspan="5">Invoice recorded</th-->
		</tr>
		<tr>
			<th colspan="5">Invoice for ${actionBean.caseId}: ${actionBean.caseName} (${actionBean.client})</th>
		</tr>
		<tr>
			<th>User</th>
			<th>Date</th>
			<th>Duration</th>
			<th style="text-align: right;">Amount</th>
			<th>Comment</th>
		</tr>
		<c:forEach items="${actionBean.entries}" var="entry" varStatus="loop">
			<!--c:if test='${entry.invoiceId == actionBean.id}'-->
				<tr>
					<td>${entry.staffAlias}</td>
					<td>${entry.dateString}</td>
					<td>${entry.duration}</td>
					<td style="text-align: right;"><fmt:formatNumber type="number" pattern="#,###" value="${entry.revenue}"/></td>
					<td>${entry.comment}</td>
				</tr>
			<!--  /c:if-->
		</c:forEach>
		<tr>
			<th colspan="3">Total invoice amount for ${user.userName}:</th>
			<th style="text-align: right;"><span id="newTotal">${actionBean.amount}</span></th>
			<th>&nbsp;</th>
		</tr>
		<tr>
			<td>&nbsp;</td>
			<td>&nbsp;</td>
			<td><input type="button" onclick="JavaScript:window.print();" value="Print"/></td>
			<td><stripes:submit name="ok" value="Ok" /></td>
			<td>&nbsp;</td>
		</tr>
	</table>
</stripes:form>
