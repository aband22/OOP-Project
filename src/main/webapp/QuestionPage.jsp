<%@ page import="java.util.List" %>
<%@ page import="Quizzes.Quiz" %>
<%@ page import="Quizzes.Question" %>
<%@ page import="Quizzes.QuestionFill" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="Quizzes.QuestionMultipleChoice" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page isELIgnored="false" %>
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<%
    Quiz quiz = (Quiz) request.getAttribute("quiz");
    List<Question> questions = quiz.getQuestions();
    ArrayList<String > answersJson=new ArrayList<String>();
%>

<!DOCTYPE html>
<html lang="en">

<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Complete Quiz</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-QWTKZyjpPEjISv5WaRU9OFeRpok6YctnYmDr5pNlyT2bRjXh0JMhjY6hW+ALEwIH" crossorigin="anonymous">
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js" integrity="sha384-YvpcrYf0tY3lHB60NNkmXc5s9fDVZLESaAA55NDzOxhy9GkcIdslK1eN7N6jIeHz" crossorigin="anonymous"></script>
    <title>Quiz: ${quiz.getTitle()}</title>
    <style>
        body {
            font-family: Arial, sans-serif;
            background-color: #f8f9fa;
        }
        .container {
            max-width: 800px;
            margin: 0 auto;
            padding: 20px;
            background-color: #fff;
            border-radius: 8px;
            box-shadow: 0 0 10px rgba(0,0,0,0.1);
        }
        .question-panel {
            border: 1px solid #ccc;
            padding: 15px;
            margin-bottom: 20px;
            background-color: #f5f5f5;
            border-radius: 6px;
        }
        .question-panel h6 {
            margin-top: 0;
        }
        .form-check-label {
            margin-left: 10px;
        }
        .input-group-text {
            min-width: 140px; /* Adjust width for labels */
            text-align: left;
        }
        .form-control {
            width: 60%; /* Adjust input width */
        }
        .btn-submit {
            background-color: #ffad22;
            border-color: #dc9a21;
        }
        .btn-submit:hover {
            background-color: #FFD700;
            border-color: #FFD700;
        }
    </style>
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
                    </ul>
                </c:otherwise>
            </c:choose>
        </div>
    </nav>
</div>
<form action="quiz?quiz=<%=quiz.getId()%>" method="post" id="editForm">
<div class="container">
    <h1 class="text-center"><%= quiz.getTitle() %></h1>
        <input type="hidden" name="quizId" value="<%= quiz.getId() %>"/>

        <div id="question-container">
            <% int questionCounter = 1; %>
            <c:forEach var="question" items="${quiz.getQuestions()}">
                <div class="question-panel">
                    <c:choose>
                        <c:when test="${question.getQuestionType() == 'Fill'}">
                            <%
                                QuestionFill fillQuestion = (QuestionFill) questions.get(questionCounter-1);
                                String processedQuestion = fillQuestion.getProcessedQuestion();
                                List<String> answers = fillQuestion.getAnswers();
                                String[] parts = processedQuestion.split("____");
                            %>
                            <h6> კითხვა <%= questionCounter %>:
                                <% for (int i = 0; i < parts.length; i++) { %>
                                <%= parts[i] %>
                                <% if (i < answers.size()) { %>
                                <input type="text" name="answer<%= questionCounter %>_<%= i+1 %>" id="answer<%= questionCounter %>_<%= i+1 %>" class="form-control d-inline" style="width: auto; display: inline-block;" />
                                <% } %>
                                <% } %>
                            </h6>
                        </c:when>
                        <c:when test="${question.getQuestionType() == 'MultipleChoice'}">
                            <h6> კითხვა <%= questionCounter %>: <%= questions.get(questionCounter-1).getQuestion()%></h6>
                            <% if (questions.get(questionCounter-1).getPhotoPath() != null && !questions.get(questionCounter-1).getPhotoPath().isEmpty()) { %>
                            <img src="photos/<%= questions.get(questionCounter-1).getPhotoPath() %>" alt="Question Image" class="img-fluid mb-3" />
                            <% } %>

                            <% int answerCounter = 1; %>
                            <c:forEach var="option" items="${question.getChoices()}">
                                <div class="form-check">
                                    <input class="form-check-input" type="checkbox" id="answer<%= questionCounter %>_<%= answerCounter %>" name="answer<%= questionCounter %>_<%= answerCounter %>" value="${option}" id="option<%= answerCounter %>">
                                    <label class="form-check-label" for="answer<%= questionCounter %>_<%= answerCounter %>">
                                            ${option}
                                    </label>
<%--                                    <% if (((QuestionMultipleChoice)questions.get(questionCounter-1)).getAnswerPhotos().get(answerCounter-1) != null && !((QuestionMultipleChoice)questions.get(questionCounter-1)).getAnswerPhotos().get(answerCounter).isEmpty()) { %>&ndash;%&gt;--%>
                                    <img src="photos/<%= ((QuestionMultipleChoice)questions.get(questionCounter-1)).getAnswerPhotos().get(answerCounter-1) %>" alt="Question Image" class="img-fluid mb-3" />
<%--                                                                            <% } %>--%>
                                </div>
                                <% answerCounter++; %>
                            </c:forEach>
                        </c:when>
                        <c:when test="${question.getQuestionType() == 'MultipleResponse'}">
                            <h6> კითხვა <%= questionCounter %>: <%= questions.get(questionCounter-1).getQuestion()%></h6>
                            <% if (questions.get(questionCounter-1).getPhotoPath() != null && !questions.get(questionCounter-1).getPhotoPath().isEmpty()) { %>
                            <img src="photos/<%= questions.get(questionCounter-1).getPhotoPath() %>" alt="Question Image" class="img-fluid mb-3" />
                            <% } %>                            <div class="container mt-3">
                            <%
                                answersJson = (ArrayList<String>) quiz.getQuestions().get(questionCounter).getAnswers();
                                // Convert to JSON array
                            %>                                <div class="row">
                            <div class="col-md-13 mx-auto">
                                <input type="text" id="searchBar" style="width: auto" class="form-control" placeholder="Type your answer here">
                                <table class="table table-bordered mt-3" style=" min-width: 100px; text-align: center" id="answerTable">
                                    <thead>
                                    <tr>
                                        <th scope="col" style="width: 50px; text-align: center">#</th>
                                        <th scope="col">Answer</th>
                                    </tr>
                                    </thead>
                                    <tbody id="tableBody">
                                    </tbody>
                                </table>
                            </div>
                        </div>
                        </div>
                        </c:when>
                        <c:when test="${question.getQuestionType() == 'Response'}">
                            <h6> კითხვა <%= questionCounter %>: <%= questions.get(questionCounter-1).getQuestion()%></h6>
                            <% if (questions.get(questionCounter-1).getPhotoPath() != null && !questions.get(questionCounter-1).getPhotoPath().isEmpty()) { %>
                            <img src="photos/<%= questions.get(questionCounter-1).getPhotoPath() %>" alt="Question Image" class="img-fluid mb-3" />
                            <% } %>
                            <input type="text" id="answer<%= questionCounter %>_1" name="answer<%= questionCounter %>_1" class="form-control" />
                        </c:when>
                    </c:choose>
                </div>
                <% questionCounter++; %>
            </c:forEach>
        </div>

        <div class="container mt-5 text-center">
            <div class="row">
                <div class="col-md-8 mx-auto">
                    <button type="button" class="btn btn-primary" data-bs-toggle="modal" data-bs-target="#exampleModal">
                        Finish Quiz
                    </button>
                </div>
            </div>
        </div>

</div>

<div class="modal fade" id="exampleModal" tabindex="-1" aria-labelledby="exampleModalLabel" aria-hidden="true">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <h1 class="modal-title fs-5" id="exampleModalLabel">Quiz</h1>
                <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
            </div>
            <div class="modal-body">
                Are you sure you want to submit?
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Close</button>

                    <input type="hidden" id="save" name="save">
                    <button type="submit" class="btn btn-primary" onclick="setAttributeAndSubmit('addFriend')">Save changes</button>

            </div>
        </div>
    </div>
</div>
</form>
<script>
    <%


    // Manually convert ArrayList to a JSON-like string
    StringBuilder jsonAnswers = new StringBuilder("[");
    for (int i = 0; i < answersJson.size(); i++) {
        jsonAnswers.append("\"").append(answersJson.get(i)).append("\"");
        if (i < answersJson.size() - 1) {
            jsonAnswers.append(",");
        }
    }
    jsonAnswers.append("]");
    System.out.println(jsonAnswers);
%>
    const searchBar = document.getElementById('searchBar');
    <%System.out.println(answersJson);%>
    const answers = <%= jsonAnswers%>;

    const tableBody = document.getElementById('tableBody');
    const filledAnswers = new Set();

    answers.forEach((answer, index) => {
        const row = document.createElement('tr');
        index++;
        row.id = `row-`+index;
        row.innerHTML = `<th scope="row">`+index+`</th><td>${answer}</td>`;
        tableBody.appendChild(row);

    });

    searchBar.addEventListener('input', function() {
        const query = searchBar.value.toLowerCase().trim();
        const answerIndex = answers.indexOf(query);
        if (answerIndex !== -1 && !filledAnswers.has(query)) {
            let ans = answerIndex;
            ans++;
            const row = document.getElementById(`row-`+ans);
            row.children[1].textContent = answers[answerIndex];
            filledAnswers.add(query); // Mark this answer as filled
            searchBar.value = ''; // Clear the search bar after a correct answer is typed
        }
    });

    // Countdown timer setup
    const countdownElement = document.createElement('div');
    countdownElement.className = 'countdown-container';
    countdownElement.id = 'countdown';
    document.body.appendChild(countdownElement);

    let countdownTime = 60 + 5; // 5 minutes in seconds

    function updateCountdown() {
        const minutes = Math.floor(countdownTime / 60);
        const seconds = countdownTime % 60;

        countdownElement.innerHTML = 'Time remaining: ' + minutes + 'm ' + seconds + 's';

        // if (countdownTime === 60) {
        //     // Show the toast message when 1 minute is left
        //     const toastEl = document.getElementById('toast');
        //     const toast = new bootstrap.Toast(toastEl);
        //     toast.show();
        // }

        if (countdownTime > 0) {
            countdownTime--;
            setTimeout(updateCountdown, 1000); // Update every second
        } else {
            // Time's up, handle it here (e.g., submit quiz automatically)
            alert('Time is up!');
            // Optionally, you can add code to handle what happens when time runs out
        }
    }
    updateCountdown();


    function setAttributeAndSubmit(attribute) {
        var attributeValue = attribute;
        document.getElementById(attribute).value = attributeValue;
        document.getElementById("friendStatus").submit();
    }
</script>

</body>
</html>
