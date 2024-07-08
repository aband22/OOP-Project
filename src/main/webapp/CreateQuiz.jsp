<%@ page import="Accounts.Account" %>
<%@ page import="Quizzes.Quiz" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page isELIgnored="false" %>


<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Quiz Builder</title>
    <link rel="stylesheet" href="CreateQuizStyle.css">
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-QWTKZyjpPEjISv5WaRU9OFeRpok6YctnYmDr5pNlyT2bRjXh0JMhjY6hW+ALEwIH" crossorigin="anonymous">
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

<div class="quizz">
    <div id="area">
        <form action="addQuiz" method="post" enctype="multipart/form-data">
            <h6>სათაური <span style="color: red">*</span></h6>
            <input class="form-control me-2" type="text" placeholder="" aria-label="Search" id="quizName" name="quizName">

            <h6>კატეგორია <span style="color: red; padding-bottom: 20px">*</span></h6>
            <input class="form-control me-2"  type="text" placeholder="" aria-label="Search" id="quizCategory" name="quizCategory">

            <h6>აღწერა</h6>
            <div class="form-group" style="padding-bottom: 30px">
                <textarea class="form-control" id="description" name="description" rows="6"></textarea>
            </div>

            <h6>ქვიზის ხანგრძლივობა</h6>
            <div class="form-group" style="padding-bottom: 20px">
                <select class="form-select" id="timer" name="timer" >
                    <option value="00:30">00:30</option>
                    <option value="01:00">01:00</option>
                    <option value="01:30">01:30</option>
                    <option value="02:00">02:00</option>
                    <option value="02:30">02:30</option>
                    <option value="03:00">03:00</option>
                    <option value="03:30">03:30</option>
                    <option value="04:00">04:00</option>
                    <option value="04:30">04:30</option>
                    <option value="05:00">05:00</option>
                    <option value="05:30">05:30</option>
                    <option value="06:00">06:00</option>
                    <option value="06:30">06:30</option>
                    <option value="07:00">07:00</option>
                    <option value="07:30">07:30</option>
                    <option value="08:00">08:00</option>
                    <option value="08:30">08:30</option>
                </select>
            </div>

            <div id="question-container">

            </div>

            <div class="add-question-buttons">
                <h8> + </h8>
                <button class="btn btn-outline-secondary" type="button" id="add-fill-question-button">Fill</button>
                <button class="btn btn-outline-secondary" type="button" id="add-multiple-choice-question-button">Multiple Choice</button>
                <button class="btn btn-outline-secondary" type="button" id="add-multiple-response-question-button">Multiple Response</button>
                <button class="btn btn-outline-secondary" type="button" id="add-response-question-button">Response</button>
            </div>


            <label style="padding-bottom: 30px" id="drop-area" for="input-file">
                <input type="file" accept="image/*" id="input-file" name="quizPhoto" hidden>
                <div id="img-view">
                    <img src="icon.png">
                    <p>Drag and drop or click here<br>to upload image</p>
                    <span>Upload any images from desktop</span>
                </div>
            </label>

            <c:if test="${not empty quiz}">
                <input type="hidden" name="quizId" value="${quiz.id}">
            </c:if>

            <button class="btn btn-warning" id="addQuiz" type="submit">გამოქვეყნება</button>
            <button class="btn btn-secondary" id="cancelQuiz" type="button">გაუქმება</button>
        </form>
    </div>
</div>

<script src="CreateQuizScript.js"></script>
</body>
</html>
