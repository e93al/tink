<%@ taglib prefix="stripes" uri="http://stripes.sourceforge.net/stripes.tld"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix='fn' uri='http://java.sun.com/jsp/jstl/functions' %>

<stripes:form beanclass="se.kransellwennborg.tink.actions.InvoiceActionBean" id="invoiceForm">
	<stripes:errors />
	<table class="typeB" style="width:800px">
		<tr>
			<th colspan="8">Invoice for ${actionBean.caseId}: ${actionBean.caseName} (${actionBean.client})</th>
		</tr>
		<tr>
			<td>
				<stripes:hidden name="id" id="idInput" />
				<stripes:hidden name="caseId" />
			</td>
		</tr>
		<tr>
			<th>&nbsp;</th>
			<th>User</th>
			<th>Date</th>
			<th>Duration</th>
			<th>Rate</th>
			<!-- th style="text-align: right;">Amount</th-->
			<th>Amount</th>
			<th>Comment</th>
			<th>&nbsp;		<span class="hidden" id="numEntries">${fn:length(actionBean.entries)}</span>
			</th>
		</tr>
		
		<c:forEach items="${actionBean.entries}" var="entry" varStatus="loop">
			<tr>
				<td><stripes:checkbox checked="${entry.include}"
					id="include${loop.index}" 
					name="entries[${loop.index}].include" onchange="addEntries()" /></td>
				<td>${entry.staffAlias}</td>
				<td>${entry.dateString}</td>
				<td id="duration${loop.index}">${entry.duration}</td>
				<td id="rate${loop.index}">${entry.rate}</td>

				<!-- NEW REVENUE, REMOVED> td id="revenue${loop.index}">${entry.revenue}</td-->
				<!-- NEW COMMENT, REMOVEDtd id="comment${loop.index}">${entry.comment}</td-->
				<!-- OLD DURATION EDIT td><stripes:text id="duration${loop.index}" name="entries[${loop.index}].duration" 	size="4" onblur="addEntries()" /> </td-->

				<td><stripes:text id="revenue${loop.index}" name="entries[${loop.index}].revenue" size="6" onblur="addEntries()"/> </td>				
				<td><stripes:textarea rows="4" cols="70"  id="comment${loop.index}" name="entries[${loop.index}].comment"/>

				<td>
				<!--  edit entry - make conditional on user -->
				<!-- stripes:link beanclass="se.kransellwennborg.tink.actions.InvoiceActionBean" event="delete"-->
					<!--   stripes:param name="processEntryId" value="${entry.id}" /-->
					<!--  stripes:param name="caseId" value="${actionBean.caseId}"/ -->
					<!-- Delete -->
				<!-- /stripes:link -->
				<c:if test="${user.userName == entry.staffAlias}">
				<stripes:link beanclass="se.kransellwennborg.tink.actions.EntryEditActionBean">
				<stripes:param name="timeEntry.id" value="${entry.id}" />
				<stripes:param name="fromWip" value="true" />
					Edit entry
				</stripes:link>
				</c:if>
				</td>				
			</tr>
		</c:forEach>

		<!-- tr --> <!-- And now for the new line -->
<!--
			<td><stripes:checkbox checked="true"
				id="newTimeEntry.include" 
				name="newTimeEntry.include" onchange="addEntries()" /></td>
			<td><stripes:text id="newTimeEntry.staffAlias" name="newTimeEntry.staffAlias" size="3" 
				value="${user.userName}" /></td>
			<td><stripes:text id="newTimeEntry.date" name="newTimeEntry.date" size="8" 
				formatType="date" formatPattern="yyMMdd"/></td>
			<td><stripes:text id="newTimeEntry.duration" name="newTimeEntry.duration"
				size="4" onblur="addEntries()" /> </td>

			<td><stripes:text id="newTimeEntry.rate" name="newTimeEntry.rate"
				size="4" onblur="addEntries()" /> </td>

			<td><stripes:text id="newTimeEntry.revenue" name="newTimeEntry.revenue" 
				size="6" onblur="addEntries()" /> </td>				

			<td><stripes:textarea rows="4" cols="60" id="newTimeEntry.comment" name="newTimeEntry.comment"/> </td>
		</tr>
-->
		<tr>
			<th colspan="3">Invoice amount:</th>
			<th>
				&nbsp;<span id="newDuration">${actionBean.totalDuration}</span>
				<stripes:hidden id="newDurationFormValue" name="totalDuration" />
			</th>
			<th>&nbsp;</th>
			<th>
				&nbsp;<span id="newAmount">${actionBean.amount}</span>
				<stripes:hidden id="newAmountValue" name="amount" />
			</th>
			<th>&nbsp;</th>
			<th>&nbsp;</th>
		</tr>
		<tr>
			<th colspan="4">Adjust rate for all entries</th>
			<th><stripes:text id="blanketRate" name="blanketRate" size="6" /></th>
			<th><stripes:submit name="adjustRate" value="Apply" /> </th>
			<th>&nbsp;</th>
			<th>&nbsp;</th>
			<th>&nbsp;</th>
		</tr>

		<!-- tr><td>&nbsp;</td></tr-->
		<tr>
			<td>&nbsp;</td>
			<!--td colspan="4"><stripes:submit name="post" value="Post" /> <input type="button" value="Reset"
				onClick="document.getElementById('adjustmentInput').value='';addEntries();" /></td-->
			<td colspan="4"><stripes:submit name="postSingle" value="Post to PW - combined line" /> </td>
			<td colspan="5"><stripes:submit name="postMultiple" value="Post to PW - separate lines" /> </td>
		</tr>
		<tr>
			<td colspan="5">&nbsp;</td>
			<td colspan="5"><stripes:submit name="postMultipleCombinedRevenue">Post separate lines - combined revenue</stripes:submit> </td>
		</tr>
		<tr>
			<td>&nbsp;</td>
		</tr>
		<tr>
			<td>&nbsp;</td>
			<td colspan="6"><stripes:submit name="postStats" value="Mark as invoiced (no post to PW)" /> </td>
		</tr>
	</table>
</stripes:form>
