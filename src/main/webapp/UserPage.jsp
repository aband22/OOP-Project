<%--
  Created by IntelliJ IDEA.
  User: taso
  Date: 20.06.2024
  Time: 14:39
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>User Page</title>
    <style>
        body {
            font-family: Arial, sans-serif;
            display: flex;
            justify-content: center;
            align-items: center;
            height: 100vh;
            background-color: #ffffff;
            margin: 0;
        }
        .user-page {
            width: 1000px;
            height: 600px;
            display: flex;
            background-color: #e7e7e7;
            padding: 20px;
            border-radius: 8px;
        }
        .left-column {
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
            display: flex;
            flex-direction: column;
            justify-content: flex-start;
            width: 100%;
            margin-top: 10px;
        }
        .username {
            font-size: 24px;
            font-weight: bold;
            margin-bottom: 10px;
            margin-top: 0;
        }
        .edit-profile {
            padding: 10px;
            background-color: #ffc107;;;
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
            display: none;
        }
        .content.active {
            display: block;
        }
    </style>
</head>
<body>
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
        <div class="username">Username</div>
        <div class="edit-profile">რედაქტირება</div>
        <div id="info" class="content active">
            <h2>ინფორმაცია</h2>
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
    </div>
</div>

<script>
    function showContent(section) {
        var contents = document.querySelectorAll('.content');
        contents.forEach(function(content) {
            content.classList.remove('active');
        });
        document.getElementById(section).classList.add('active');
    }
</script>
</body>
</html>
