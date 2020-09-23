<%@ page import="se.kransellwennborg.tink.dao.PatraWinDao"%>
<%@ page import="se.kransellwennborg.tink.beans.*"%>
<%@ page import="java.util.*"%>
<%@ page import="se.kransellwennborg.tink.*"%>
<%@ page import="se.kransellwennborg.tink.actions.*"%>
<%@ page import="se.kransellwennborg.tink.dao.*"%>
<%@ page import="java.sql.Connection"%>

<%@ taglib prefix="stripes" uri="http://stripes.sourceforge.net/stripes.tld"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<html>
<head>
<title>asdsss</title>
	<link href="${pageContext.request.contextPath}/css/KW_style.css" rel="stylesheet" type="text/css" />
	<script type="text/javascript" src="${pageContext.request.contextPath}/js/scripts.js" > </script>
	
</head>
<body>
<h3>Formatting of Number:</h3>  
<c:set var="Amount" value="9850.17" />  
<fmt:setLocale value="sv_SE" />

<p> grouping:  
<fmt:formatNumber type="number" groupingUsed="true" value="${Amount}" /></p>  

<p> grouping, no dec:  
<fmt:formatNumber type="number" groupingUsed="true" maxFractionDigits="0" value="${Amount}" /></p>  
<p> grouping, 2 dec, explicit:  
<fmt:formatNumber type="number" groupingUsed="true" maxFractionDigits="2" value="${Amount}" /></p>  

<p> grouping, 2 dec:  
<fmt:formatNumber type="number" pattern="#,###.00" value="${Amount}" /></p> </table>

<p> Formatted Number-1:  

<fmt:formatNumber value="${Amount}" type="currency" /></p>  
<p>Formatted Number-2:  
<fmt:formatNumber type="number" groupingUsed="true" value="${Amount}" /></p>  
<p>Formatted Number-3:  
<fmt:formatNumber type="number" maxIntegerDigits="3" value="${Amount}" /></p>  
<p>Formatted Number-4:  
<fmt:formatNumber type="number" maxFractionDigits="0" value="${Amount}" /></p>  
<p>Formatted Number-5:  
<fmt:formatNumber type="percent" maxIntegerDigits="4" value="${Amount}" /></p>  
<p>Formatted Number-6:  
<fmt:formatNumber type="number" pattern="#,###" value="${Amount}" /></p> </table>
</html>











