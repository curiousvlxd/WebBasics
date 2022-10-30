<%@ page import="java.util.List" %>
<%@ page contentType="text/html;charset=UTF-8" %><%
    String fromFilter = (String) request.getAttribute( "from-filter" ) ;
    // !! unchecked cast !! List<String> randData = (List<String>) request.getAttribute( "randData" ) ;
    String[] randData = (String[]) request.getAttribute( "randData" ) ;
%>
<jsp:include page="/WEB-INF/header.jsp" />

<h1>Сервлетні фільтри</h1>
<p>
    Фільтри є реалізацією принципу Middleware - каскадне проходження
    оброблення різними шарами програмного забезпечення.
    У відповідності до поставлених задач (робота з мережними запитами)
    фільтри класифікуються як сервлети, щоб не виникало плутанини
    їх називають сервлетними фільтрами.
</p>
<p>
    Фільтр проходить три життєвих події: init, doFilter, destroy
    У основній події doFilter передається: request, response а також chain -
    ланцюг фільтрів, який має бути або продовженим, або припиненим.
    Віповідно, doFilter має містити інструкцію виклику продовження ланцюга
    (chain.doFilter(...)), можливо, у рамках умовного блоку.
    Місце виклику chain.doFilter дозволяє розділити код фільтра на "прямий" та
    "зворотний" шлях. Те, що виконується до виклику chain.doFilter, працює на
    прямому шляху оброблення запиту, ці результати будуть доступні у всіх
    подальших ланках, а також у цьому місці можна припинити ланку фільтрів.
    На зворотному шляху можна виконати завершальні дії, зокрема, перегенерувати
    токени авторизації.
</p>
<p>
    Завдання: зробити фільтр перемикання кодування (на UTF-8), забезпечити його
    виконання найпершим.

</p>

<% for( String str : randData ) { %>
<p><%= str %></p>
<% } %>

<p>
    <% if( fromFilter == null ) { %>
    <i>Атрибут порожній</i>
    <% } else { %>
    <b><%= fromFilter %></b>
    <% } %>
</p>
</body>
</html>