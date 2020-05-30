<%@ taglib prefix="stripes" uri="http://stripes.sourceforge.net/stripes.tld"%>
<stripes:form beanclass="se.kransellwennborg.tink.actions.SettingsActionBean" id="settingsForm">

	<stripes:errors />
	<table class="typeB" style="width:500px">
		<tr>
			<th colspan="7">Settings for ${actionBean.user.fullName}</th>
		</tr>
		<tr>
			<td>Full Name:</td>
			<td>
				<stripes:text name="user.fullName" size="50" formatType="date"/>
			</td>
		</tr>
		<tr>
			<td>Rate:</td>
			<td>
				<stripes:text name="user.rate" size="6" formatType="date"/>
			</td>
		</tr>
		<tr>
			<td>Start tag:</td>
			<td>
				<stripes:text name="user.startTab" size="2" formatType="date"/>
			</td>
		</tr>
		<tr>
			<td><stripes:submit name="post" value="Post" /></td>
		</tr>
	</table>
</stripes:form>
