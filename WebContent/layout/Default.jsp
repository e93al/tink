<%@ page contentType="text/html;charset=iso-8859-1" language="java"%>
<%@ taglib prefix="stripes" uri="http://stripes.sourceforge.net/stripes.tld"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!-- TODO: Change layout to new logo -->
<stripes:layout-definition>
	<html>
	<head>
	<title>Tink</title>
	<script type="text/javascript" src="${pageContext.request.contextPath}/js/scripts.js" > </script>
	<link href="${pageContext.request.contextPath}/css/KW_style.css" rel="stylesheet" type="text/css" />
	<stripes:layout-component name="html_head" />
	<link rel="icon" type="image/png" href="${pageContext.request.contextPath}/favicon.png" />
	</head>
	<body>
	<stripes:layout-component name="header">
		<jsp:include page="/layout/_header.jsp" />
	</stripes:layout-component>
	<div class="pageContent">
	<div style="position: absolute; top: 130px; left: 28px;">
	<table cellpadding="10px">
		<tr>
			<stripes:layout-component name="contents" />
		</tr>
	</table>
	</div>
	</div>
	<stripes:layout-component name="footer">
		<jsp:include page="/layout/_footer.jsp" />
	</stripes:layout-component>
	<div style="position: absolute; top: 0px; left: 0px; z-index:-1"><img src="<c:url value='/img/header.jpg' />"/></div>
	</body>
	</html>
</stripes:layout-definition>
