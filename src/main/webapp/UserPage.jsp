
<%@ page import="Accounts.Account" %>
<%@ page import="java.util.List" %>
<%@ page import="Accounts.Achievement" %>
<%@ page import="Quizzes.Quiz" %>
<%@ page import="java.sql.Timestamp" %>
<%@ page import="Quizzes.SqlQuizzesHistoryDao" %><%--
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
                <a class="edit-profile" href="addQuiz">ქვიზის შექმნა</a>
            </c:when>
            <c:when test="${userId == 0}">

            </c:when>
            <c:otherwise>
                <c:choose>
                    <c:when test="${logged_in_status != null}">
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
                    </c:when>
                </c:choose>
            </c:otherwise>
        </c:choose>
        <div id="info" class="content active">
            <h2>ინფორმაცია</h2>
            <p>სახელი: <%= ((Account) request.getAttribute("account")).getUsername() %></p>
            <p>სტატუსი: <%= ((Account) request.getAttribute("account")).getStatus() %></p>
        </div>
        <div id="history" class="content">
            <%  List<Quiz> quizzes = (List<Quiz>)application.getAttribute("quizzesDone");
                SqlQuizzesHistoryDao quizzesInfo = (SqlQuizzesHistoryDao)application.getAttribute("quizzesHistory_db");
                for(int i = 0; i < quizzes.size(); i++){
                    Quiz quiz = quizzes.get(i);
                    int quizId = quiz.getId();
                    String name = quiz.getTitle();
                    String category = quiz.getCategory();
                    String owner = quiz.getAccount().getUsername();
                    Timestamp uploadTime = quiz.getCreationDate();
                    Timestamp currentTimeMillis = new Timestamp(System.currentTimeMillis());
                    long durationInMillis = currentTimeMillis.getTime() - uploadTime.getTime();
                    int duration = (int)durationInMillis / (60 * 1000);
                    String timeText = "წუთის";
                    if(duration > 59){
                        duration = duration / 60;
                        timeText = "საათის";
                    }
                    if(duration > 23){
                        duration = duration / 24;
                        timeText = "დღის";
                    }
                    if(duration > 30){
                        duration = duration / 30;
                        timeText = "თვის";
                    }
                    if(duration > 11){
                        duration = duration / 12;
                        timeText = "წლის";
                    }

                    int score = quizzesInfo.getScore(quizId, ((Account) request.getAttribute("account")).getId());
            %>
            <div class="col-md-6">
                <a class="card mb-3" style="max-width: 540px; border: 2px dashed rgb(255, 240, 0);" href="quiz?quizID=<%=quizId%>">
                    <div class="row g-0">
                        <div class="col-md-4">
                            <img src="..." class="img-fluid rounded-start" alt="...">
                        </div>
                        <div class="col-md-8">
                            <div class="card-body">
                                <h5 class="card-title"><%=name%>
                                    <%=score%>/
                                </h5>
                                <h6 class="card-text">
                                    <ul>
                                        <li style="color: rgb(0, 98, 255);"><%=owner%></li>
                                        <li style="color: rgb(9, 255, 0);"><%=category%></li>
                                        <li>Time to</li>
                                    </ul>
                                </h6>
                                <p class="card-text"><small class="text-body-secondary">გამოქვეყნდა <%=duration%> <%=timeText%> წინ</small></p>
                            </div>
                        </div>
                    </div>
                </a>
            </div>
            <% } %>
        </div>
        <div id="friends" class="content">
            <%
                List<Account> friends = (List<Account>) application.getAttribute("friends");
                for (int i = 0; i < friends.size(); i++) {
                    int friendId = friends.get(i).getId();
                    String friendName = friends.get(i).getUsername();
            %>
            <a class="card ragaca" style="max-width: 840px; border: white;" href="user?user=<%=friendId%>">
                <div class="row g-0">
                    <div class="col-md-4">
                        <img src="<%=friendId%>.jpg" class="img-fluid" alt="Photo" width="50" height="50">
                    </div>
                    <div class="col-md-8">
                        <div class="card-body">
                            <h5 style="font-weight: bold;"><%=friendName%></h5>
                        </div>
                    </div>
                </div>
            </a>
            <% } %>
        </div>
        <div id="achievements" class="content">
            <%
                List<Achievement> achievements = (List<Achievement>) application.getAttribute("achievements");
                for (int i = 0; i < achievements.size(); i++) {
                    String achievementType = achievements.get(i).getType();
                    String achievementText = achievements.get(i).getText();

            %>
            <div class="card" style="max-width: 840px; border: white;">
                <div class="row g-0">
                    <div class="col-md-4">
                        <img src="<%=achievementType%>.jpg" class="img-fluid" alt="Photo" width="50" height="50">
                    </div>
                    <div class="col-md-8">
                        <div class="card-body">
                            <h6><%=achievementType%></h6>
                            <h6 class="card-text"><%=achievementText%>></h6>
                        </div>
                    </div>
                </div>
            </div>
            <% } %>
        </div>
        <div id="quizzes" class="content">
            <%
                List<Quiz> myQuizzes = (List<Quiz>) application.getAttribute("quizzes");
                for(int i = 0; i < myQuizzes.size(); i++) {
                    Quiz quiz = myQuizzes.get(i);
                    int quizId = quiz.getId();
                    String quizCategory = quiz.getCategory();
                    String quizTitle = quiz.getTitle();
                    Timestamp uploadTime = quiz.getCreationDate();
                    Timestamp currentTimeMillis = new Timestamp(System.currentTimeMillis());
                    long durationInMillis = currentTimeMillis.getTime() - uploadTime.getTime();
                    int duration = (int)durationInMillis / (60 * 1000);
                    String timeText = "წუთის";
                    if(duration > 59){
                        duration = duration / 60;
                        timeText = "საათის";
                    }
                    if(duration > 23){
                        duration = duration / 24;
                        timeText = "დღის";
                    }
                    if(duration > 30){
                        duration = duration / 30;
                        timeText = "თვის";
                    }
                    if(duration > 11){
                        duration = duration / 12;
                        timeText = "წლის";
                    }

            %>
            <a class="card mb-3" style="max-width: 840px; border: 2px dashed rgb(255, 240, 0);" href="quiz?quiz=<%=quizId%>">
                <div class="row g-0">
                    <div class="col-md-4">
                        <img src="<%=quizId%>.png" class="img-fluid rounded-start" alt="...">
                    </div>
                    <div class="col-md-8">
                        <div class="card-body">
                            <form action="notifications" method="post" id="buttonStatus">
                                <input type="hidden" id="admit" name="admit">
                                <p class="card-title"><%=quizTitle%>
                                    <button type="button" class="btn btn-outline-warning">რედაქტირება</button>
                                    <button type="button" class="btn btn-outline-danger"><svg xmlns="http://www.w3.org/2000/svg" width="16" height="16" fill="currentColor" class="bi bi-trash" viewBox="0 0 16 16">
                                        <path d="M5.5 5.5A.5.5 0 0 1 6 6v6a.5.5 0 0 1-1 0V6a.5.5 0 0 1 .5-.5m2.5 0a.5.5 0 0 1 .5.5v6a.5.5 0 0 1-1 0V6a.5.5 0 0 1 .5-.5m3 .5a.5.5 0 0 0-1 0v6a.5.5 0 0 0 1 0z"></path>
                                        <path d="M14.5 3a1 1 0 0 1-1 1H13v9a2 2 0 0 1-2 2H5a2 2 0 0 1-2-2V4h-.5a1 1 0 0 1-1-1V2a1 1 0 0 1 1-1H6a1 1 0 0 1 1-1h2a1 1 0 0 1 1 1h3.5a1 1 0 0 1 1 1zM4.118 4 4 4.059V13a1 1 0 0 0 1 1h6a1 1 0 0 0 1-1V4.059L11.882 4zM2.5 3h11V2h-11z"></path>
                                    </svg></button>
                                </p>
                            </form>

                            <h6 class="card-text">
                                <ul class="smaller">
                                    <li style="color: rgb(9, 255, 0);"><%=quizCategory%></li>
                                    <li>Time to solve: 10 min</li>
                                </ul>
                            </h6>
                            <p class="card-text"><small class="text-body-secondary">გამოქვეყნდა <%=duration%> <%=timeText%> წინ</small></p>
                        </div>
                    </div>
                </div>
            </a>
            <% } %>
        </div>
        <div id="edit" class="content">
            <h2>რედაქტირება</h2>
            <form action="user?user=${userId}" method="post" id="editForm" enctype="multipart/form-data" onsubmit="save()">
                <div class="form-edit">
                    <label for="usernameEdit">სახელი:</label>
                    <input type="text" id="usernameEdit" name="username">
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
<%--    <a id="addQuiz" class="content" href="addQuiz">--%>
<%--        <h2>ახალი ქვიზი</h2>--%>
<%--    </a>--%>

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