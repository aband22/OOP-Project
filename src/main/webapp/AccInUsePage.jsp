<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Create Account</title>
    <script src="signup.js"></script>
</head>
<body>
    <h1> Create New Account </h1>
    <p> Entered email is in use, please enter new one </p>
    <div class = "signUp">
        <form action = "signUp" method = "post">
            Email:
            <input type = "text" name = "email" id = "email">
            <br>
            User Name:
            <input type = "text" name = "username" id = "username">
            <br>
            Password:
            <input type = "password" name = "password" id = "password">
            <br>
            <input type = "submit" value = "Sign Up" id = "signup">
            <br>
        </form>
    </div>
</body>
</html>