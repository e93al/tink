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
<br/><br/><br/><br/><br/><br/><br/><br/><br/>
<h1>Testing</h1>
<table>
<tr><td id="karl" onclick='alert(parseInt(document.getElementById("karl").innerHTML) + 2);'>341</td></tr>
<tr><td id="karl2" onclick='alert(parseFloat(document.getElementById("karl2").innerHTML) + 2);'>341.6</td></tr>
</table>
<%

//SortOrder so = new SortOrder();

out.print("c:" + Constants.CLIENT_REF + "--");
//Connection con = MysqlConnectionProvider.getNewConnection();

//out.print("Host: " + MysqlConnectionProvider.host + "--");

//TimeEntry te = new TimeEntry();

//te.setCaseId("161105EP");

//String ref = te.getClientRef();
//String s2 = PatraWinDao.getCaseResponsible("161105EP");
//out.print("Case resp: " + s2 + "<br/>"); 

//String s = PatraWinDao.getClientRef("161105EP");
//out.print("ref: " + s + "<br/>"); 
// out.print("s2: " + s2 + "<br/>"); 

//String s3 = PatraWinDao.getCaseResponsible("161105EP");
//out.print("resp: " + s3 + "<br/>"); 

%>
<form>
<input style="text-transform: uppercase;" type="text" name="fieldname"></body>

</form>
</html>











