<%@ page contentType="text/html;charset=UTF-8" %><%
    String userInput = (String) request.getAttribute( "userInput" ) ;
%>
<jsp:include page="/WEB-INF/header.jsp" />

<h1>Hello !</h1>
<p>
    Відображення для сервлету Hello
</p>
<% if( userInput != null ) { %>
<i>Раніше надіслано: </i><b><%= userInput %></b>
<% } %>
<form method="post">
    Введіть рядок: <label><input name="userInput" /></label>
    <input type="submit" value="Відправити" />
</form>
</body>
</html>