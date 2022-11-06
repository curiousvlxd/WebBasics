<%@ page contentType="text/html;charset=UTF-8" %><%
    String regError = (String)request.getAttribute("regError");
    String regOk = (String)request.getAttribute("regOk");
%>
<form class="reg-user-form" method="post" action="" enctype="multipart/form-data" >
    <% if (regError != null) { %>
    <h4><%=regError%></h4>
    <% } %>
    <% if (regOk != null) { %>
    <h4><%=regOk%></h4>
    <% } %>
    <div class="form-group">
        <label for="userLogin">Login</label>
        <input type="text" class="form-control" id="userLogin" name="userLogin" placeholder="Login">
    </div>
    <div class="form-group">
        <label for="userPassword">Password</label>
        <input type="password" class="form-control" id="userPassword" name="userPassword" placeholder="Password">
    </div>
    <div class="form-group">
        <label for="userPassword2">Confirm Password</label>
        <input type="password" class="form-control" id="userPassword2" name="userPassword2" placeholder="Confirm Password">
    </div>
    <div class="form-group">
        <label for="userName">Name</label>
        <input type="text" class="form-control" id="userName" name="userName" placeholder="Name">
    </div>
    <div class="form-group">
        <label for="userAvatar">Avatar</label>
        <input type="file" class="form-control" id="userAvatar" name="userAvatar">
    </div>
    <div class="form-group">
        <label for="userEmail">Email</label>
        <input type="email" class="form-control" id="userEmail" name="userEmail" placeholder="Email">
    <button type="submit" class="btn btn-default">Submit</button>
</form>