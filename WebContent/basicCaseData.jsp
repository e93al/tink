<head><meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1"></head><body><%@page import="java.sql.*" %>
<%
response.setHeader("Cache-Control", "no-cache, post-check=0, pre-check=0");
response.setHeader("Pragma", "no-cache");
response.setHeader("Expires", "Thu, 01 Dec 1994 16:00:00 GMT"); 
%>${actionBean.timeEntry.client}
${actionBean.timeEntry.caseName}

</body>