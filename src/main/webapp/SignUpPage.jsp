<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>SigmaQuiz</title>
    <script src="signup.js"></script>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-QWTKZyjpPEjISv5WaRU9OFeRpok6YctnYmDr5pNlyT2bRjXh0JMhjY6hW+ALEwIH" crossorigin="anonymous">
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js" integrity="sha384-YvpcrYf0tY3lHB60NNkmXc5s9fDVZLESaAA55NDzOxhy9GkcIdslK1eN7N6jIeHz" crossorigin="anonymous"></script>
    <style>
      body {
        text-align: center;
        padding-top: 10%;
        padding-left: 35%;
        padding-right: 35%;
      }
      .size {
        width: 100%;
      }
    </style>
</head>
<body>
    <h1>რეგისტრაცია</h1>
    <div class = "signUp">
        <form action = "signUp" method = "post">
            <input class="form-control me-2" type="email" placeholder="ელფოსტა" aria-label="Search" name = "email" id = "email">
            <input class="form-control me-2" type="text" placeholder="სახელი" aria-label="Search" name = "username" id = "username">
            <input class="form-control me-2" type="password" placeholder="პაროლი" aria-label="Search" name = "password" id = "password">
            <button class="btn btn-success size" type="submit" id = "signup">რეგისტრაცია</button>
        </form>
    </div>
</body>
</html>