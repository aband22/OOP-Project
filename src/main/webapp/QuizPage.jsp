<%@ page import="Quizzes.Quiz" %>
<%@ page import="Accounts.Account" %>
<%@ page import="java.util.List" %>
<%@ page import="Quizzes.SqlQuizzesHistoryDao" %>
<%@ page import="Accounts.SqlAccountDao" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt" %>

<%@ page isELIgnored="false" %>

<html xmlns:c="http://www.w3.org/1999/XSL/Transform" xmlns:margin-left="http://www.w3.org/1999/xhtml">
<head>
    <title>Quiz Details</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-QWTKZyjpPEjISv5WaRU9OFeRpok6YctnYmDr5pNlyT2bRjXh0JMhjY6hW+ALEwIH" crossorigin="anonymous">
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js" integrity="sha384-YvpcrYf0tY3lHB60NNkmXc5s9fDVZLESaAA55NDzOxhy9GkcIdslK1eN7N6jIeHz" crossorigin="anonymous"></script>
    <style>
        a {
            text-decoration: none;
        }
        .page-header {
            margin: 2rem 0;
            text-align: center;
        }
        .table-container {
            position: absolute;
            top: 150px;
            right: 0;
            width: 35%;
            height: calc(100vh - 150px);
            overflow-y: auto;
            padding: 1rem;
        }
        .table {
            width: 100%;
            border-radius: 10px;
            overflow: hidden;
        }
        .table thead {
            background-color: #f8f9fa;
        }
        .table tbody tr {
            height: calc((100vh - 150px) / 10);
        }
        .table thead th {
            border-top-left-radius: 10px;
            border-top-right-radius: 10px;
        }
        .table tbody tr:last-child td {
            border-bottom-left-radius: 10px;
            border-bottom-right-radius: 10px;
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
</div>
<div>
    <h2 class="page-header"><b><%=((Quiz) request.getAttribute("quiz")).getTitle()%></b></h2>

    <div class="list-group" style="margin-right: 700px; padding: 50px; margin-left: 80px;">
        <%  SqlAccountDao accs = (SqlAccountDao)application.getAttribute("accounts_db");
            String username = accs.getAccountById(((Quiz) request.getAttribute("quiz")).getAccount().getId()).getUsername();%>
        <a href="home?category=<%=((Quiz) request.getAttribute("quiz")).getCategory()%>" class="list-group-item list-group-item-action list-group-item-secondary"> კატეგორია: <%=((Quiz) request.getAttribute("quiz")).getCategory()%></a>
        <a href="user?user=<%=((Quiz) request.getAttribute("quiz")).getAccount().getId()%>" class="list-group-item list-group-item-action list-group-item-secondary"> სახელი: <%=username%></a>
        <li class="list-group-item list-group-item-secondary"> შექმნის თარიღი: <%=((Quiz) request.getAttribute("quiz")).getCreationDate().toString()%></li>
        <%  SqlQuizzesHistoryDao history = (SqlQuizzesHistoryDao) application.getAttribute("quizzesHistory_db");;
            int quizId = 0;
            if(request.getAttribute("curUser") != null){%>
                <%
                    quizId = ((Quiz) request.getAttribute("quiz")).getId();
                    int accId = Integer.parseInt((String)request.getAttribute("curUser"));
                    boolean hasDone = history.hasDoneQuiz(quizId, accId);
                    int quizPoint = ((Quiz) request.getAttribute("quiz")).getPoints();
                    int score = 0;
                    if(hasDone){
                        score = history.getScore(quizId, accId);
                    }
                %>
                <c:choose>
                    <c:when test="<%=hasDone%>">
                        <li class="list-group-item list-group-item-secondary"> ჩემი შედეგი: <%=score%> / <%=quizPoint%></li>
                    </c:when>
                </c:choose>
        <%}%>
    </div>

    <c:choose>
        <c:when test="${logged_in_status != null}">
            <div class="dropdown" style="left: 130px;">
                <button class="btn btn-secondary dropdown-toggle" type="button" data-bs-toggle="dropdown" aria-expanded="false">
                    გამოიწვიე მეგობარი
                </button>
                <ul class="dropdown-menu">
                    <%
                        List<Account> friends = (List<Account>) request.getAttribute("friends");
                        for (int i = 0; i < friends.size(); i++) {
                            Account acc = friends.get(i);
                            String name = acc.getUsername();
                            int friendId = acc.getId();
                    %>
                    <form action="quiz?quiz=<%=quizId%>&friendId=<%=friendId%>" method="post" id="challengeButton">
                        <input type="hidden" id="challenge" name="challenge">
                        <button type="submit" class="dropdown-item" onclick="setAttributeAndSubmit('challenge')"><%=name%></button>
                    </form>
                    <% } %>
                </ul>
            </div>

            <div class="text-center mb-4">
                <a href="question?quizId=<%=((Quiz) request.getAttribute("quiz")).getId()%>" class="btn btn-warning col-2">დაიწყე ქვიზი</a>
            </div>
        </c:when>
    </c:choose>

    <div class="table-container">
        <table class="table table-hover">
            <thead>
            <tr>
                <th scope="col" colspan="3" class="text-center" style="width:100%">Top Rated</th>
            </tr>
            <tr>
                <th scope="col" style="width:5%">#</th>
                <th scope="col" style="width:15%">სახელი</th>
                <th scope="col">ქულა</th>
            </tr>
            </thead>
            <%
                List<Account> topScorers = (List<Account>) request.getAttribute("topScorers");
                System.out.println(topScorers.size());
                for (int i = 0; i < topScorers.size(); i++) {
                      int accID = topScorers.get(i).getId();
                      System.out.print(accID);
                      String accName = topScorers.get(i).getUsername();
                      System.out.println(accName);
                      int score = history.getScore(quizId, accID);
                      System.out.print(score);
            %>
            <tbody>
            <tr>
                <th scope="row"><%=i+1%></th>
                <td><a href="user?user=<%=accID%>"><%=accName%></a></td>
                <td><%=score%></td>
            </tr>
            </tbody>
            <% } %>
        </table>
    </div>
</div>
</body>
<script>
    function setAttributeAndSubmit(attribute) {
        var attributeValue = attribute;
        document.getElementById(attribute).value = attributeValue;
        document.getElementById("challengeButton").submit();
    }
</script>
</html>
