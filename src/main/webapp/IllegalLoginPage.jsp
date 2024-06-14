<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Welcome To User Login System</title>
    <script src="login.js"></script>
</head>
<body>
    <h1> Welcome To User Login System </h1>
    <p> Email or password is incorrect </p>
    <div class = "login">
        <form action = "login" method = "post">
            Email:
            <input type = "text" name = "email" id = "email">
            <br>
            Password:
            <input type = "password" name = "password" id = "password">
            <br>
            <input type = "submit" value = "Log In" id = "login">
            <br>
        </form>
    </div>
    <a href = "SignUpPage.jsp"> Create New Account </a>
</body>
</html>