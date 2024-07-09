<%--
  Created by IntelliJ IDEA.
  User: tvani
  Date: 7/8/2024
  Time: 12:56 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Error</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-QWTKZyjpPEjISv5WaRU9OFeRpok6YctnYmDr5pNlyT2bRjXh0JMhjY6hW+ALEwIH" crossorigin="anonymous">
    <style>
        body {
            font-family: Arial, sans-serif;
            background-color: #f8f9fa;
            margin: 0;
            display: flex;
            flex-direction: column;
            min-height: 100vh;
        }
        .navbar-brand img {
            margin-right: 5px;
        }
        .error-container {
            text-align: center;
            flex: 1;
            display: flex;
            justify-content: center;
            align-items: center;
            flex-direction: column;
        }
        h1 {
            font-size: 3em;
            margin-bottom: 10px;
            color: #dc3545; /* Bootstrap danger color */
        }
        h2 {
            font-size: 1.5em;
            margin-bottom: 20px;
            color: #6c757d; /* Bootstrap secondary color */
        }
        .navbar {
            background-color: #f8f9fa;
            padding: 10px;
        }
        .error-box {
            background-color: #ffffff;
            border-radius: 10px;
            box-shadow: 0px 0px 10px rgba(0, 0, 0, 0.1);
            padding: 20px;
            max-width: 400px;
            margin: 20px;
        }
    </style>
</head>
<body>

<nav class="navbar navbar-light bg-body-tertiary">
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
                </ul>
            </c:otherwise>
        </c:choose>
    </div>
</nav>

<div class="error-container">
    <div class="error-box">
        <h1>404</h1>
        <h2>This page doesn't exist</h2>
    </div>
</div>

</body>
</html>
