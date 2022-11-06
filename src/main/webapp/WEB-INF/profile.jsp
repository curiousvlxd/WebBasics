<%@ page import="step.learning.entities.User" %>
<%@ page contentType="text/html;charset=UTF-8" %><%
    User authUser = (User) request.getAttribute( "authUser" ) ;
    String contextPath = request.getContextPath() ;
%>
<div class="user-profile">
    <h1>Кабінет користувача</h1>
    <img class="profile-avatar"
         src="<%=contextPath%>/image/<%=authUser.getAvatar()%>"
         alt="<%= authUser.getName() %>" />
    <fieldset class="profile-fieldset">
        <legend>Доступні для зміни</legend>
        <div>
            <span>Ім'я: </span><b data-fieldname="name"><%=authUser.getName()%></b>
            <span class="profile-edit">&#x270E;</span>
            <span class="profile-cancel">&#x274C;</span>
            <span class="profile-confirm">&#x2705;</span>
        </div>
        <div>
            <span>Логін: </span><b data-fieldname="login"><%=authUser.getLogin()%></b>
            <span class="profile-edit">&#x270E;</span>
            <span class="profile-cancel">&#x274C;</span>
            <span class="profile-confirm">&#x2705;</span>
        </div>
        <div>
            <span>Пошта:</span><b data-fieldname="email"><%=authUser.getEmail()%></b>
            <span class="profile-edit">&#x270E;</span>
            <span class="profile-cancel">&#x274C;</span>
            <span class="profile-confirm">&#x2705;</span>
        </div>
    </fieldset>
</div>
<script>
    document.addEventListener("DOMContentLoaded",() => {
        for (let elem of document.getElementsByClassName("profile-edit")){
            elem.addEventListener("click", editClick);
        } 
        for (let elem of document.getElementsByClassName("profile-cancel")){
            elem.addEventListener("click", cancelClick);
        }
        for (let elem of document.getElementsByClassName("profile-confirm")){
            elem.addEventListener("click", confirmClick);
        }
    });
    function editClick(e){
        const b = e.target.closest('div').querySelector("b")
        if (b){
            b.setAttribute("contenteditable", "true");
            b.savedText = b.innerText;
            b.focus();
        } 
    }
    function cancelClick(e){
        const b = e.target.closest('div').querySelector("b")
        if (b){
            b.removeAttribute("contenteditable");
            b.innerText = b.savedText;
        } 
    }
    function confirmClick(e){
        const b = e.target.closest('div').querySelector("b")
        if (b){
            b.removeAttribute("contenteditable");
            if (b.savedText && b.savedText !== b.innerText){
                const fieldName = b.getAttribute("data-fieldname");
                const url = `/WebBasics/register/?` + fieldName + "=" + b.innerText;
                console.log(b.innerText);
                fetch(
                    url,
                    {
                        method: "PUT",
                        headers: {
                            "Connection": "close"
                        },
                        body: ""
                    }).then(r => r.text()).then(t => {
                        console.log(t);
                    if (t === "OK") {
                       location = location;
                    } else {
                        alert(t);
                    }
                })
            }
        } 
    }
</script>
