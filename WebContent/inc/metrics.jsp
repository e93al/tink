<%@ taglib prefix="stripes" uri="http://stripes.sourceforge.net/stripes.tld"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<jsp:useBean id="entries" scope="request" class="se.kransellwennborg.tink.beans.TimeEntries" />
<jsp:setProperty name="entries" property="dateString" value="${navDate}" />
<jsp:setProperty name="entries" property="staffAlias" value="${user.userName}" />
<fmt:setLocale value="sv_SE" />
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
<td align = "right"><fmt:formatNumber type="number" pattern="#,###.0" value="${user.hrsWeek}" /></td>
<td align = "right"><fmt:formatNumber type="number" pattern="#,###.00" value="${user.avgWeek}" /></td>
</tr>
<tr>
<td>Month</td>
<td align = "right"><fmt:formatNumber type="number" pattern="#,###.0" value="${user.hrsMonth}" /></td>
<td align = "right"><fmt:formatNumber type="number" pattern="#,###.00" value="${user.avgMonth}" /></td>
</tr>
<tr>
<td>Year</td>
<td align = "right"><fmt:formatNumber type="number" pattern="#,###.0" value="${user.hrsYear}" /></td>
<td align = "right"><fmt:formatNumber type="number" pattern="#,###.00" value="${user.avgYear}" /></td>
</tr>


</table>
