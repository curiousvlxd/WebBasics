<%@ page contentType="text/html;charset=UTF-8" %><%
    String pageBody = "/WEB-INF/" + request.getAttribute( "pageBody" ) ;
    String contextPath = request.getContextPath() ;
%>
<html>
<head>
    <title>Layout</title>
    <link rel="stylesheet" href="<%=contextPath%>/css/style.css" />
</head>
<body>
<jsp:include page="/WEB-INF/navbar.jsp" />
<jsp:include page="<%=pageBody%>" />
</body>
<jsp:include page="/WEB-INF/footer.jsp"/>
</html>