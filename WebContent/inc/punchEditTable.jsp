<%@ taglib prefix="stripes" uri="http://stripes.sourceforge.net/stripes.tld"%>
<stripes:form beanclass="se.kransellwennborg.tink.actions.PunchEditActionBean" id="punchForm">

	<stripes:errors />
	<table class="typeB" style="width:500px">
		<tr>
			<th colspan="7">Time Balance</th>
		</tr>
		<tr>
			<td>Date:</td>
			<td>
				<stripes:text name="punchEntry.date" size="10" formatType="date" formatPattern="yyMMdd"/>
				<stripes:hidden name="punchEntry.userName" />
				<stripes:hidden name="punchEntry.id" />
			</td>
		</tr>
		<tr>
			<td>Punch In Time:</td>
			<td><stripes:text name="punchEntry.punchInTime" formatType="time" formatPattern="HHmm" size="5" /></td>
		</tr>
		<tr>
			<td>Punch out Time:</td>
			<td><stripes:text name="punchEntry.punchOutTime" formatType="time" formatPattern="HHmm" size="5" /></td>
		</tr>
		<tr>
			<td><stripes:submit name="post" value="Post" /></td>
		</tr>
	</table>
</stripes:form>
