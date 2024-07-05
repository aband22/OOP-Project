<%@ page import="java.util.List,
                            Accounts.Account,
                            Quizzes.Quiz,
                            Quizzes.SqlQuizDao" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html lang="en">
<head>
    <title>SigmaQuiz</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-QWTKZyjpPEjISv5WaRU9OFeRpok6YctnYmDr5pNlyT2bRjXh0JMhjY6hW+ALEwIH" crossorigin="anonymous">
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js" integrity="sha384-YvpcrYf0tY3lHB60NNkmXc5s9fDVZLESaAA55NDzOxhy9GkcIdslK1eN7N6jIeHz" crossorigin="anonymous"></script>
    <style>
            .searchBar {
                padding-top: 40px;
                padding-bottom: 40px;
            }
            a {
              text-decoration: none;
            }
    </style>
</head>
<body>
    <div class = "home">
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
    <div class="container">
        <div class = "searchBar">
            <form class="d-flex" role="search" action = "home" method = "get">
                <input class="form-control me-2" type="search" placeholder="ძებნა" aria-label="ძებნა" name="search">
                <button class="btn btn-outline-warning" type="submit">ძებნა</button>
            </form>
        </div>
    </div>
    <div class="container">
        <div class="row">
              <% List<Quiz> quizzes = (List)application.getAttribute("quizzes");
                 for(int i = 0; i < quizzes.size(); i++){
                    Quiz quiz = quizzes.get(i);
                    int quizId = quiz.getId();
                    String name = quiz.getTitle();
                    String category = quiz.getCategory();
                    String owner = quiz.getAccount().getUsername();
                    long uploadTime = quiz.getCreationDate();
              %>
              <div class="col-md-6">
                <a class="card mb-3" style="max-width: 540px; border: 2px dashed rgb(255, 240, 0);" href="quiz?quizID=<%=quizId%>">
                  <div class="row g-0">
                    <div class="col-md-4">
                      <img src="..." class="img-fluid rounded-start" alt="...">
                    </div>
                    <div class="col-md-8">
                      <div class="card-body">
                        <h3 class="card-title"><%=name%></h5>
                        <h6 class="card-text">
                            <ul>
                                <li style="color: rgb(0, 98, 255);"><%=owner%></li>
                                <li style="color: rgb(9, 255, 0);"><%=category%></li>
                                <li>Time to</li>
                            </ul>
                        </h6>
                        <p class="card-text"><small class="text-body-secondary">Last updated 3 mins ago</small></p>
                      </div>
                    </div>
                  </div>
                </a>
              </div>
              <% } %>
        </div>
    </div>
</body>
</html>