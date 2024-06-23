<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt" %>
<%@ page isELIgnored="false" %>
<html>
<head>
    <title>SigmaQuiz</title>
    <script src="login.js"></script>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-QWTKZyjpPEjISv5WaRU9OFeRpok6YctnYmDr5pNlyT2bRjXh0JMhjY6hW+ALEwIH" crossorigin="anonymous">
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js" integrity="sha384-YvpcrYf0tY3lHB60NNkmXc5s9fDVZLESaAA55NDzOxhy9GkcIdslK1eN7N6jIeHz" crossorigin="anonymous"></script>
    <style>
      body {
        text-align: center;
        padding-top: 5%;
        padding-left: 35%;
        padding-right: 35%;
      }
      .size {
        width: 100%;
      }
    </style>
</head>
<body>
    <a href = "home">
        <img class="mb-4" src="photos/moai.jpg" alt="" width="72" height="72">
    </a>
    <h1>SigmaQuiz </h1>
    <c:choose>
        <c:when test="${illegal != null}">
            <h6 style="color: red">ელფოსტა ან პაროლი არასწორია, სცადეთ თავიდან</h6>
        </c:when>
    </c:choose>
    <div class = "login">
        <form action = "login" method = "post">
            <input class="form-control me-2" type="email" placeholder="ელფოსტა" aria-label="Search" name = "email" id = "email">
            <input class="form-control me-2" type="password" placeholder="პაროლი" aria-label="Search" name = "password" id = "password">
            <button class="btn btn-warning size" type="submit" id = "login">შესვლა</button>
        </form>
    </div>
    <a class="btn btn-outline-success" href = "signUp"> ახალი ანგარიშის შექმნა </a>
</body>
</html>