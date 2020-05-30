<%@ page contentType="text/html;charset=iso-8859-1" language="java"%>
<%@ taglib prefix="stripes" uri="http://stripes.sourceforge.net/stripes.tld"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="java.util.*"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<stripes:layout-render name="/layout/Default.jsp">
	<stripes:layout-component name="contents">
		<td valign="top">
		<jsp:include page="/inc/invoiceEditTable.jsp" />
		</td>
	</stripes:layout-component>
</stripes:layout-render>
