<%@ taglib prefix="stripes" uri="http://stripes.sourceforge.net/stripes.tld"%>
<jsp:useBean id="adminActionBean" scope="request" class="se.kransellwennborg.tink.actions.AdminActionBean" />

<stripes:form beanclass="se.kransellwennborg.tink.actions.AdminActionBean" id="adminForm">
	<stripes:errors />
	<stripes:messages />

	<table class="typeB" style="width:500px">
		<tr>
			<td colspan="7">${adminActionBean.message} </td>
		</tr>
		<tr>
			<th colspan="7">Time adjustment</th>
		</tr>
		<tr>
			<td>User:</td>
			<td>
				<stripes:text name="punchEntry.userName" size="10" />
			</td>
		</tr>
		<tr>
			<td>Date:</td>
			<td>
				<stripes:text name="punchEntry.date" size="10" formatType="date" formatPattern="yyMMdd"/>
			</td>
		</tr>
		<tr>
			<td>Duration:</td>
			<td><stripes:text name="punchEntry.duration" /></td>
		</tr>
		<tr>
			<td><stripes:submit name="adjustTime" value="Post" /></td>
		</tr>		
	</table>
</stripes:form>
