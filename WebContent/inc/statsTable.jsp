<%@ taglib prefix="stripes" uri="http://stripes.sourceforge.net/stripes.tld"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<jsp:useBean id="stats" scope="request" class="se.kransellwennborg.tink.actions.StatisticsActionBean" />
<!--stripes:form beanclass="se.kransellwennborg.tink.actions.StatisticsActionBean" id="statisticsForm"-->

<table class="typeB">

	<tr>
		<td>
			<b>Statistics</b> 
			<!-- stripes:text id="yearInput" name="year" style="width: 60px;"/-->
			<!--stripes:submit name="post" value="Update" /--> 	
		</td>
	</tr>
	<tr>
		<th>&nbsp;</th>
		<th>&nbsp;</th>
		<th>Jan</th>
		<th>Feb</th>
		<th>Mar</th>
		<th>Apr</th>
		<th>May</th>
		<th>Jun</th>
		<th>Jul</th>
		<th>Aug</th>
		<th>Sep</th>
		<th>Oct</th>
		<th>Nov</th>
		<th>Dec</th>
		<th>Year</th>
		
	</tr>
	<c:forEach items="${stats.rows}" var="row" varStatus="loop">
		<tr>
			<td style="border-width: 0px 0px 1px 0px;">${row.staffAlias}</td>
			<td style="border-width: 0px 0px 1px 0px;">
			hrs<br/>
			avg hrs<br/>
			Invoiced in Tink (KSEK)
			workdays
			</td>
		<c:forEach items="${row.cells}" var="cell" varStatus="loop">
			<td style="border-width: 0px 0px 1px 0px;">
			<c:if test='${cell.hrs != 0}'>			
			${cell.hrs}<br/>
			</c:if>
			
			<c:if test='${cell.avgHrs != 0}'>			
			${cell.avgHrs}<br/>
			</c:if>

			<c:if test='${cell.kInvoiced != 0}'>			
			${cell.kInvoiced}<br/>
			</c:if>
			</td>
		</c:forEach>
			<td style="border-width: 0px 0px 1px 0px;">
			${row.totalHrs}<br/>
			${row.avgHrs}<br/>
			${row.kTotalInvoiced}<br/>
			</td>
		</tr>
	</c:forEach>
</table>
<!--/stripes:form-->
