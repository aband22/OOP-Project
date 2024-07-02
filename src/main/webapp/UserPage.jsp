<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page import="Accounts.Account" %><%--
  Created by IntelliJ IDEA.
  User: taso
  Date: 20.06.2024
  Time: 14:39
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page isELIgnored="false" %>
<html>
<head>
    <title>User Page</title>
    <title>SigmaQuiz</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-QWTKZyjpPEjISv5WaRU9OFeRpok6YctnYmDr5pNlyT2bRjXh0JMhjY6hW+ALEwIH" crossorigin="anonymous">
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js" integrity="sha384-YvpcrYf0tY3lHB60NNkmXc5s9fDVZLESaAA55NDzOxhy9GkcIdslK1eN7N6jIeHz" crossorigin="anonymous"></script>
    <style>
        body {
            font-family: Arial, sans-serif;
            background-color: #ffffff;
            margin: 0;
        }
        .navbar-costom {
            background-color: #ffffff;
            padding: 10px;
            border-radius: 8px;
            margin-button: 20px;

        }
        .user-page {
            height: 600px;
            display: flex;
            background-color: #e7e7e7;
            padding: 20px;
            border-radius: 8px;
        }
        .left-column {
            margin-left: 200px;

            width: 150px;
            display: flex;
            flex-direction: column;
            align-items: center;
        }
        .profile-photo {
            width: 150px;
            height: 150px;
            border-radius: 50%;
            background-color: #d0d0c5;
            margin-bottom: 20px;
        }
        .menu {
            display: flex;
            flex-direction: column;
            width: 100%;
        }
        .menu-item {
            cursor: pointer;
            margin: 6px 0;
            padding: 7px;
            background-color: #d0d5e1;
            border-radius: 4px;
            text-align: center;
        }
        .menu-item:hover {
            background-color: rgb(189, 200, 225);
        }
        .right-column {
            margin-left: 20px;
            margin-right: 200px;
            display: flex;
            flex-direction: column;
            justify-content: flex-start;
            width: 100%;
            /*margin-top: 10px;*/
        }
        .username {
            font-size: 24px;
            font-weight: bold;
            margin-bottom: 10px;
            margin-top: 0;
        }
        .edit-profile {
            padding: 10px;
            background-color: #ffc107;
            color: #000;
            border: none;
            border-radius: 4px;
            cursor: pointer;
            width: 150px;
            text-align: center;
        }
        .edit-profile:hover {
            background-color: #f6ce57;
        }
        .content {
            margin-top: 15px;
            display: none;
            padding: 20px;
            border: 1px solid #bdb9ab;
            border-radius: 8px;
            background-color: #fff;
            box-shadow: 0 2px 4px rgba(0,0,0,0.1);
        }
        .content.active {
            display: block;
        }
        .form-edit {
            margin-bottom: 15px;
        }
        .form-edit label {
            font-weight: bold;
            margin-right: 10px;
        }
        .form-edit input[type="text"] {
            width: 300px;
            padding: 8px;
            border: 1px solid #b6ac7b;
            border-radius: 4px;
            font-size: 14px;
            margin-top: 5px;
        }
        .form-edit input:hover{
            border-color: #cb7d0c;
        }
        .form-edit input:focus {
            border-color: #d39623;
            border-width: 2px;
            outline: none;
        }
    </style>
</head>
<body>
<div class = "home">
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
                        <a class="nav-link" href="user?user=${user}"><img src="photos/person-circle.svg" alt="Logo" width="20" height="20"></a>
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
        <div class="profile-photo"></div>
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
            </c:when>
        </c:choose>

        <div id="info" class="content active">
            <h2>ინფორმაცია</h2>
            <p>სახელი:  <%= ((Account) request.getAttribute("account")).getUsername() %></p>
            <p>ელ-ფოსტა: <%= ((Account) request.getAttribute("account")).getEmail() %></p>
            <p>პაროლი: ? </p>

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
            <form action="user" method="post" id="editForm" onsubmit="save()">
                <div class="form-edit">
                    <label for="usernameEdit">სახელი:</label>
                    <input type="text" id="usernameEdit" name="username" >
                </div>
                <div class="form-edit">
                    <label for="mailEdit">ელ-ფოსტა:</label>
                    <input type="text" id="mailEdit" name="mail" value=<%= ((Account) request.getAttribute("account")).getEmail() %>>
                </div>
                <div class="form-edit">
                    <label for="passEdit">პაროლი:</label>
                    <input type="text" id="passEdit" name="password" value=<%= ((Account) request.getAttribute("account")).getPassword().toString() %>>
                </div>
                <input type="hidden" name="accountId" value="<%= ((Account) request.getAttribute("account")).getId() %>">
                <button type="submit" id="submitButton">შენახვა</button>
            </form>
        </div>
    </div>
</div>

<script>
    function showContent(section) {
        var contents = document.querySelectorAll('.content');
        contents.forEach(function (content) {
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
</script>
</body>
</html>
