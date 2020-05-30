<%@ taglib prefix="stripes" uri="http://stripes.sourceforge.net/stripes.tld"%>
<stripes:form beanclass="se.kransellwennborg.tink.actions.EntryEditActionBean" id="timeForm">
	<stripes:errors />
	<stripes:messages />
	<table class="typeB" style="width:400px">
		<tr>
			<th colspan="7">Time Entry</th>
		</tr>
		<tr>
			<td>Date:
			<div class="hint">yymmdd</div>
			</td>
			<td><stripes:hidden name="timeEntry.id" id="idInput" />
			<stripes:hidden name="invoiceCase" id="invoiceCaseInput" /> 
			<stripes:text
				name="timeEntry.date" id="dateInput" size="10" formatType="date" formatPattern="yyMMdd"
			/></td>
		</tr>
		<!--  tr>
			<td>Type:</td>
			<td class="external"
				onclick="document.getElementById('isInternalInput').checked=true; document.getElementById('rateInput').value=${user.rate}"
			><stripes:radio id="isInternalInput" name="timeEntry.isInternal" value="false" /> External</td>
			<td class="internal" onclick="document.getElementById('isExternalInput').checked=true; document.getElementById('rateInput').value=0;"><stripes:radio
				id="isExternalInput" name="timeEntry.isInternal" value="true"
			/>Internal</td>
		</tr-->
		<tr>
			<td>Start time:
			<div class="hint">hhmm</div>
			</td>
			<td><stripes:text id="startTimeInput" name="timeEntry.startTime" formatType="time" formatPattern="HHmm" size="4" /> 
				<input type="button" value="now" onClick="setCurrentTime('startTimeInput')"/>
			</td>
			<td>End time:
			<div class="hint">hhmm</div>
			</td>
			<td><stripes:text id="endTimeInput" name="timeEntry.endTime" formatType="time" formatPattern="HHmm" size="4" />
				<input
				type="button" value="now" onClick="setCurrentTime('endTimeInput')"/>
			</td>
		</tr>
		<tr>
			<td>Case id:</td>
			<td><stripes:text id="caseIdInput" name="timeEntry.caseId" size="10" style="text-transform: uppercase;"
				onblur="setCaseData(document.getElementById('caseIdInput').value)"
			/></td>
		</tr>
		<tr>
			<td>Case name:</td>
			<td colspan="5"><stripes:text id="caseNameInput" name="timeEntry.caseName" style="width: 310px;" /></td>
			
			
		</tr>
		<tr>
			<td>Client:</td>
			<td colspan="5"><stripes:text id="clientInput" name="timeEntry.client" style="width: 310px;" /></td>
		</tr>
		<tr>
			<td>Rate:</td>
			<td><stripes:text id="rateInput" name="timeEntry.rate" size="10" /></td>
		</tr>
		<tr>
			<td>Comment:</td>
			<!-- td colspan="4"><stripes:text size="40" id="commentInput" name="timeEntry.comment" /></td-->
			<td colspan="4"><stripes:textarea rows="4" id="commentInput" name="timeEntry.comment" style="width: 308px;"/> </td>

		</tr>
		<tr>
			<td>&nbsp;</td>
			<td><stripes:submit name="post" value="Post" /> <!--input type="button" value="Reset" onClick="resetForm()" /--></td>
			<td><stripes:submit name="postAndInvoice" value="Post and invoice" /></td>

		</tr>
	</table>
</stripes:form>
