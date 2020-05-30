<%@ taglib prefix="stripes"
	uri="http://stripes.sourceforge.net/stripes.tld"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>


<div class="header" align="left">
	<div class="toppmeny" align="left">
		<ul>
			<li><stripes:link
				beanclass="se.kransellwennborg.tink.actions.EntryEditActionBean">Time entry</stripes:link></li>
			<li><stripes:link
				beanclass="se.kransellwennborg.tink.actions.PunchActionBean">Flex time</stripes:link></li>
			<li><stripes:link
				beanclass="se.kransellwennborg.tink.actions.InvoicesActionBean">Invoices</stripes:link></li>
			<li><stripes:link
				href="/se/kransellwennborg/tink/actions/Logout.action" event="logout">Logout
		        	<stripes:param name="userName" value="${user.userName}"></stripes:param></stripes:link></li>
				
			<c:if test='${user.userName == "al"}'>		
				<li><a href="${pageContext.request.contextPath}/admin.jsp">Admin</a></li>
				
			</c:if>

			<li>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</li>

			<li>

				<!--
				<stripes:link beanclass="se.kransellwennborg.tink.actions.SettingsActionBean">
				-->
				Logged in as: ${user.fullName}
				
				<!--
				</stripes:link>
				-->


			</li>
		</ul>
	</div>
</div>

