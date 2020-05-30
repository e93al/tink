<%@ taglib prefix="stripes" uri="http://stripes.sourceforge.net/stripes.tld"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<jsp:useBean id="entries" scope="request" class="se.kransellwennborg.tink.beans.TimeEntries" />
<jsp:setProperty name="entries" property="dateString" value="${navDate}" />
<jsp:setProperty name="entries" property="staffAlias" value="${user.userName}" />
<table class="typeB" style="width:100px">
<tr>
<th colspan=3>Metrics
	<stripes:link beanclass="se.kransellwennborg.tink.actions.StatisticsActionBean">Stats</stripes:link>
</th>
</tr>
<tr>
<th>Period</th>
<th>Hrs</th>
<th>Avg</th>
</tr>
<tr>
<td>Week</td>
<td>${user.hrsWeek}</td>
<td>${user.avgWeek}</td>
</tr>
<tr>
<td>Month</td>
<td>${user.hrsMonth}</td>
<td>${user.avgMonth}</td>
</tr>
<tr>
<td>Year</td>
<td>${user.hrsYear}</td>
<td>${user.avgYear}</td>
</tr>
</table>
