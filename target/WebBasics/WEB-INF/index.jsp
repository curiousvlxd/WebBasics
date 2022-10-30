<%@ page contentType="text/html;charset=UTF-8" %><%
   String host = request.getContextPath() ;  //   /WebBasics
   String header = "/WEB-INF/" + request.getAttribute( "header" ) ;
%>
<jsp:include page="<%= header %>" />

<section>
   <h1>Java Web. Основи.</h1>
   <a href="<%=host%>/filter">Сервлетні Фільтри</a>
   <a href="<%=host%>/guice.jsp">Інверсія управління - Guice</a>
   <p>
      1. Сервер:<br/>
      - Tomcat 8 (9) НЕ БРАТИ 10 <img src="<%=host%>/img/tomcat.jpg" /> <br/>
      - GlassFish 4 (5) <img src="<%=host%>/img/glassfish.jpg" />
   </p>
   <p>
      2. Проєкт<br/>
      - JavaEE-Web<br/>
      - Maven archetype (webapp)<br/>
   </p>
   <p>
      Робота з JSP - Java Server Pages<br/>
      Ідея - розширення можливостей HTML засобами мови Java.
   </p>
   <p>
      * Розділення файлу на фрагменти, підключення фрагментів "на льоту"
      <jsp:include page="fragment.jsp"/>
   </p>
   <p>
      Змінні та інші можливості мови
      <%
         int x = 10 ;
         String[] langs = new String[] { "uk", "en", "ru", "de" } ;
      %>
      x = <%= x %>
   </p>
   <p>
      Конструкції керування - умовні та циклічні блоки
      <br/>
      <% if( x < 10 ) { %>
      <b>x < 10</b>
      <% } else { %>
      <s>x < 10</s>
      <% } %>
      <br/>
      <% for( String lang : langs ) { %>
      <input type="radio" name="lang" id="lang-<%=lang%>" />
      <label for="lang-<%=lang%>"> <%= lang %> </label> &emsp;
      <% } %>
   </p>
</section>

</body>
</html>
Д.З. Реалізувати фільтр, що створює підключення до БД
Перевірити як успішне, так і неуспішне підключення.
* вивести дані з БД (випадкові числа \ users) на сторінці
<jsp:include page="/WEB-INF/footer.jsp"/>