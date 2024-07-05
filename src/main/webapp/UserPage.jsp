
<%@ page import="Accounts.Account" %><%--
  Created by IntelliJ IDEA.
  User: taso
  Date: 20.06.2024
  Time: 14:39
  To change this template use File | Settings | File Templates.
--%><%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page isELIgnored="false" %>
<html>
<head>
    <title>User Page</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-QWTKZyjpPEjISv5WaRU9OFeRpok6YctnYmDr5pNlyT2bRjXh0JMhjY6hW+ALEwIH" crossorigin="anonymous">
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js" integrity="sha384-YvpcrYf0tY3lHB60NNkmXc5s9fDVZLESaAA55NDzOxhy9GkcIdslK1eN7N6jIeHz" crossorigin="anonymous"></script>
    <link rel="stylesheet" href="userPageStyle.css">
</head>
<body>
<div class="home">
    <nav class="navbar bg-body-tertiary">
        <div class="container-fluid">
            <a class="navbar-brand" href="home">
                <img src="photos/moai.jpg" alt="Logo" width="30" height="30" class="d-inline-block align-text-top">
                SigmaQuiz
            </a>
            <c:choose>
            <c:when test="${logged_in_status != null}">
                <ul class="nav justify-content-end">
                    <li class="nav-item">
                        <a class="nav-link active" aria-current="page" href="notifications"><img src="photos/bell-fill.svg" alt="Logo" width="20" height="20"></a>
                    </li>
                    <li class="nav-item">
                        <a class="nav-link" href="chat"><img src="photos/chat-right-quote-fill.svg" alt="Logo" width="20" height="20"></a>
                    </li>
                    <li class="nav-item">
                        <a class="nav-link" href="user?user=${curUser}"><img src="photos/person-circle.svg" alt="Logo" width="20" height="20"></a>
                    </li>
                    <li class="nav-item">
                        <a class="btn btn-warning" href="login">გამოსვლა</a>
                    </li>
                </ul>
            </c:when>
            <c:otherwise>
            <ul class="nav justify-content-end">
                <li class="nav-item">
                    <a class="btn btn-warning" href="login">შესვლა</a>
                </li>
                </c:otherwise>
                </c:choose>
        </div>
    </nav>
</div>
<div class="user-page">
    <div class="left-column">
        <img class="profile-photo" src="photos/<%= ((Account) request.getAttribute("account")).getId() %>.png" >

        <div class="menu">
            <div class="menu-item" onclick="showContent('info')">ინფორმაცია</div>
            <div class="menu-item" onclick="showContent('history')">ისტორია</div>
            <div class="menu-item" onclick="showContent('friends')">მეგობრები</div>
            <div class="menu-item" onclick="showContent('achievements')">მიღწევები</div>
            <div class="menu-item" onclick="showContent('quizzes')">ჩემი ქვიზები</div>
        </div>
    </div>
    <div class="right-column">
        <div class="username" id="displayUsername"><%= ((Account) request.getAttribute("account")).getUsername() %></div>
        <c:choose>
            <c:when test="${userId == curUserId}">
                <div class="edit-profile" onclick="showContent('edit')">რედაქტირება</div>
                <div class="edit-profile" onclick="showContent('addQuiz')">ქვიზის შექმნა</div>
            </c:when>
            <c:when test="${userId == 0}">

            </c:when>
            <c:otherwise>
                <form action="user?user=${userId}" method="post" id="friendStatus">
                    <c:choose>
                        <c:when test="${isFriend == 0}">
                            <input type="hidden" id="addFriend" name="addFriend">
                            <button class="btn btn-primary" type="submit" onclick="setAttributeAndSubmit('addFriend')">მეგობრის დამატება</button>
                        </c:when>
                        <c:when test="${isFriend == 2}">
                            <input type="hidden" id="pending" name="pending">
                            <button class="btn btn-primary" type="submit" onclick="setAttributeAndSubmit('pending')">მეგობრობის მოთხოვნა გაგზავნილია</button>
                        </c:when>
                        <c:otherwise>
                            <input type="hidden" id="friend" name="friend">
                            <button class="btn btn-primary" type="submit" onclick="setAttributeAndSubmit('friend')">მეგობრები</button>
                        </c:otherwise>
                    </c:choose>
                </form>
            </c:otherwise>
        </c:choose>
        <div id="info" class="content active">
            <h2>ინფორმაცია</h2>
            <p>სახელი: <%= ((Account) request.getAttribute("account")).getUsername() %></p>
            <p>ელ-ფოსტა: <%= ((Account) request.getAttribute("account")).getEmail() %></p>
            <p>პაროლი: ?</p>
        </div>
        <div id="history" class="content">
            <h2>ისტორია</h2>
            <p>ბლაბლა</p>
        </div>
        <div id="friends" class="content">
            <h2>მეგობრები</h2>
            <p>მარტოსული</p>
        </div>
        <div id="achievements" class="content">
            <h2>მიღწევები</h2>
            <p>წარმატებული ახალგაზრდა!</p>
        </div>
        <div id="quizzes" class="content">
            <h2>ჩემი ქვიზები</h2>
            <p>ქვიზი 1</p>
        </div>
        <div id="edit" class="content">
            <h2>რედაქტირება</h2>
            <form action="user" method="post" id="editForm" enctype="multipart/form-data" onsubmit="save()">
                <div class="form-edit">
                    <label for="usernameEdit">სახელი:</label>
                    <input type="text" id="usernameEdit" name="username">
                </div>
                <div class="form-edit">
                    <label for="mailEdit">ელ-ფოსტა:</label>
                    <input type="text" id="mailEdit" name="mail" value="<%= ((Account) request.getAttribute("account")).getEmail() %>">
                </div>
                <div class="form-edit">
                    <label for="newPassword">ახალი პაროლი:</label>
                    <input type="password" style="border-style: solid; border-color: #d7b370; border-bottom-width: thin; border-right-width: thin" id="newPassword" name="newPassword">
                </div>
                <div class="form-edit">
                    <label for="profilePhoto">პროფილის სურათი:</label>
                    <input type="file" id="profilePhoto" name="profilePhoto" accept="image/*">
                </div>
                <input type="hidden" name="accountId" value="<%= ((Account) request.getAttribute("account")).getId() %>">
                <button type="submit" id="submitButton">შენახვა</button>
            </form>
        </div>
    </div>
    <div id="addQuiz" class="content">
        <h2>ახალი ქვიზი</h2>
    </div>
</div>
<script>
    function showContent(section) {
        var contents = document.querySelectorAll('.content');
        contents.forEach(function(content) {
            content.classList.remove('active');
        });
        document.getElementById(section).classList.add('active');
        if (section === 'edit') {
            document.getElementById('usernameEdit').value = document.getElementById('displayUsername').innerText;
        }
    }
    function save() {
        document.getElementById('displayUsername').innerText = document.getElementById('usernameEdit').value;
        showContent('info');
    }
    function setAttributeAndSubmit(attribute) {
        var attributeValue = attribute;
        document.getElementById(attribute).value = attributeValue;
        document.getElementById("friendStatus").submit();
    }
</script>
</body>
</html>