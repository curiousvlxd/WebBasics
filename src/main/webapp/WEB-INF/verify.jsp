<%@ page import="step.learning.entities.User" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%><%
    User authUser = (User) request.getSession().getAttribute("authUser");
    String verified = (String) request.getSession().getAttribute("confirmed");
%>
<div class="confirm-email">
    <span>We already send a verification  code to your email.</span>
    <% if ("Ok".equals(verified)) { %>
        <span class="success">Your email has been confirmed.</span>
   
    <% } else if (authUser != null) { %> 
        <span class="error">Please log in into system</span
    <% } else { %> 
    <% if (verified != null) { %>
        <h3 style="color:maroon"> <%=verified%> </h3>
    <% } %>
    <form>
        <input type="text" name="verify" >
        <input type="submit" value="verify">
    </form>
    <% } %>
</div>