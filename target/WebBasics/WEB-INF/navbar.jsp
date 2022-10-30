<%-- Блок авторизації --%>
<%@ page import="step.learning.entities.User" %>
<%@ page contentType="text/html;charset=UTF-8" %><%
    String authError = (String) request.getAttribute( "authError" ) ;
    User user = (User) request.getAttribute( "authUser" ) ;
    String contextPath = request.getContextPath() ;
%>
<nav>
    <% if( user == null ) { %>
    <form method="post" action="" >
        <label>Login: <input type="text" name="userLogin" /></label>
        <label>Password: <input type="password" name="userPassword" /></label>
        <input type="hidden" name="form-id" value="nav-auth-form" />
        <input type="submit" value="Log in" />
        <a href="<%=contextPath%>/register/">Sign up</a>
        <% if( authError != null ) { %>
        <span class="auth-error"><%= authError %></span>
        <% } %>
    </form>
    <% } else { %>
    <span class="auth-hello">Hello, <%= user.getName() %></span>
    <a href="<%=contextPath%>/profile/">
    <img class="auth-avatar"
         src="<%=contextPath%>/image/<%= user.getAvatar()%>"
         alt="<%= user.getName() %>" />
    </a>
    <a class="logout" href="?logout=true">Log out</a>
    <% } %>
</nav>