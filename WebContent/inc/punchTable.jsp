<%@ taglib prefix="stripes" uri="http://stripes.sourceforge.net/stripes.tld"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<stripes:form beanclass="se.kransellwennborg.tink.actions.PunchActionBean" id="punchForm">

	<stripes:errors />
	<table class="typeB" style="width:500px">
		<tr>
			<th colspan="7">Time Balance </th>
		</tr>
		<tr>
			<td>Status: ${user.punchStatus} <c:if test="${user.punchedIn == 'true'}">at ${user.punchInTimeString}</c:if>
			</td>
			<td>Current balance: ${user.timeBalance}<stripes:hidden id="originalTime" name="originalTime" formatType="time" formatPattern="HHmm" />
		</tr>
		<tr>
			<td><stripes:submit name="${actionBean.postType}" value="${actionBean.postLabel} now" onclick="setPunchTime()"/></td>
		</tr>
		<tr>
			<td>&nbsp;</td>
		</tr>
		<tr>
			<th colspan="7">Adjusted Entry </th>
		</tr>
		<tr>
			<td>Date:
				<div class="hint">yymmdd</div>
			</td>
			<td>
				<stripes:text name="formDate" id="formDate" size="10" formatType="date" formatPattern="yyMMdd"/>
			</td>
		</tr>
		<tr>
			<td>Time:
				<div class="hint">hhmm</div>
			</td>
			<td><stripes:text name="formTime" id="formTime" formatType="time" formatPattern="HHmm" size="5" />
			<!--input
				type="button" value="now" onClick="setPunchTime()"/--></td>
		</tr>
		<tr>
			<td><stripes:submit name="${actionBean.postType}" value="${actionBean.postLabel}" /></td>
		</tr>
	</table>
</stripes:form>
