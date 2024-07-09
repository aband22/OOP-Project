<%@ page import="java.util.List,
                            Quizzes.Quiz,
                            Quizzes.SqlQuizDao,
                            java.sql.Timestamp" %>
<%@ page import="Quizzes.SqlQuizzesHistoryDao" %>
<%@ page import="java.util.ArrayList" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt" %>
<%@ page isELIgnored="false" %>
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
            .feature-icon {
                width: 4rem;
                height: 4rem;
                border-radius: .75rem;
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
        <div class = "categories">
            <div>
                <nav class="navbar bg-body-tertiary">
                    <div class="container-fluid">
                    <a class="navbar-brand">
                        ყველა კატეგორია
                    </a>
                    <ul class="nav justify-content-end">
                        <div class="btn-group" role="group" aria-label="Basic mixed styles example">
                            <button type="button" class="btn btn-danger" data-bs-target="#carouselExample" data-bs-slide="prev">
                                <span class="carousel-control-prev-icon" aria-hidden="true"></span>
                                <span class="visually-hidden">Previous</span>
                            </button>
                            <button type="button" class="btn btn-danger" data-bs-target="#carouselExample" data-bs-slide="next">
                                <span class="carousel-control-next-icon" aria-hidden="true"></span>
                                <span class="visually-hidden">Next</span>
                            </button>
                        </div>
                    </ul>
                    </div>
                </nav>
            </div>
            <div class="container">
                <div class="row">
                    <div class="col-md-12">
                        <div id="carouselExample" class="carousel slide">
                            <% List<String> categories = ((SqlQuizDao)application.getAttribute("quizzes_db")).getQuizCategories();
                               int pages = categories.size() / 6;
                               int remains = categories.size() % 6;
                               if(remains != 0) pages++;
                            %>
                            <div class="carousel-inner">
                                <div class="carousel-item active">
                                    <div class="row">
                                        <% List<String> colors = (List)application.getAttribute("colors");
                                                int k = 6;
                                                if(pages == 0) k = remains;
                                                for(int i = 0; i < k; i++){
                                                    String color = colors.get(i % colors.size());
                                                    String category = categories.get(i); %>
                                                <a class="col-md-2" href="home?category=<%=category%>">
                                                    <div class="card text-bg-<%=color%> mb-3" style="max-width: 18rem;">
                                                        <div class="card-header"><%=category%></div>
                                                        <div class="card-body">

                                                        </div>
                                                    </div>
                                                </a>
                                        <% } %>
                                    </div>
                                </div>
                                <% for(int i = 1; i < pages; i++){
                                        k = 6;
                                        if(i == pages - 1)
                                             if(remains != 0)
                                                k = remains;
                                %>
                                    <div class="carousel-item">
                                        <div class="row">
                                            <% for(int j = i * 6; j < i * 6 + k; j++){
                                                    String color = colors.get(j % colors.size());
                                                    String category = categories.get(j); %>
                                                <a class="col-md-2" href="home?category=<%=category%>">
                                                    <div class="card text-bg-<%=color%> mb-3" style="max-width: 18rem;">
                                                        <div class="card-header"><%=category%></div>
                                                        <div class="card-body">

                                                        </div>
                                                    </div>
                                                </a>
                                            <% } %>
                                        </div>
                                    </div>
                                <% } %>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>

    <% SqlQuizDao store = (SqlQuizDao)application.getAttribute("quizzes_db");
       List<Quiz> popularQuizzes = store.getPopularQuizzes(9);
       List<Quiz> recentQuizzes = store.getRecentQuizzes(9);
    %>

    <%if(request.getAttribute("curUser") != null){%>
            <div class="container">
                <div class = "myQuizzes">
                    <div class="container px-4 py-5" id="custom-cards">
                        <h2 class="pb-2 border-bottom">
                            <div class="feature-icon d-inline-flex align-items-center justify-content-center text-bg-success bg-gradient fs-2 mb-3">
                                <img src="photos/clock-history.svg" alt="Logo" width="30" height="30" class="d-inline-block align-text-top">
                            </div>
                            ბოლოს ნანახი
                        </h2>

                        <div class="row row-cols-1 row-cols-lg-3 align-items-stretch g-4 py-5">
                        <%
                            SqlQuizzesHistoryDao quizHisotry = (SqlQuizzesHistoryDao)application.getAttribute("quizzesHistory_db");
                            List<Integer> quizIds = quizHisotry.getDoneQuizzesId(Integer.parseInt((String) request.getAttribute("curUser")), 3);
                            List<Quiz> quizzes = new ArrayList<>();
                            for(Integer quizId : quizIds){
                                quizzes.add(store.getQuizById(quizId));
                            }

                            for(int i = 0; i < quizzes.size(); i++){
                            Quiz quiz = quizzes.get(i);
                            String photo = quiz.getQuizPhoto();
                            int quizId = quiz.getId();
                            String name = quiz.getTitle();
                            String category = quiz.getCategory();
                            int owner = quiz.getAccount().getId();
                            Timestamp uploadTime = quiz.getCreationDate();
                            Timestamp currentTimeMillis = new Timestamp(System.currentTimeMillis());
                            long durationInMillis = currentTimeMillis.getTime() - uploadTime.getTime();
                            int duration = (int)durationInMillis / (60 * 1000);
                            String timeText = "წთ";
                            if(duration > 59){
                                duration = duration / 60;
                                timeText = "სთ";
                            }
                            if(duration > 23){
                                duration = duration / 24;
                                timeText = "დღ";
                            }
                            if(duration > 30){
                                duration = duration / 30;
                                timeText = "თვ";
                            }
                            if(duration > 11){
                                duration = duration / 12;
                                timeText = "წ";
                            }
                        %>
                            <a class="col" href="quiz?quiz=<%=quizId%>">
                                <div class="card card-cover h-100 overflow-hidden text-bg-dark rounded-4 shadow-lg" style="background-image: url('photos/<%=photo%>');">
                                    <div class="d-flex flex-column h-100 p-5 pb-3 text-white text-shadow-1">
                                        <h3 class="pt-5 mt-5 mb-4 display-6 lh-1 fw-bold"><%=name%></h3>
                                        <ul class="d-flex list-unstyled mt-auto">
                                            <li class="me-auto">
                                                <img src="photos/<%=owner%>.png" alt="Bootstrap" width="32" height="32" class="rounded-circle border border-white">
                                            </li>
                                            <li class="d-flex align-items-center me-3">
                                                <svg class="bi me-2" width="1em" height="1em"><use xlink:href="#geo-fill"></use></svg>
                                                <small><%=category%></small>
                                            </li>
                                            <li class="d-flex align-items-center">
                                                <svg class="bi me-2" width="1em" height="1em"><use xlink:href="#calendar3"></use></svg>
                                                <small><%=duration%><%=timeText%></small>
                                            </li>
                                        </ul>
                                    </div>
                                </div>
                            </a>
                        </div>
                        <% } %>
                    </div>
                </div>
            </div>
        <%}%>

    <div class="container">
        <div class = "popular">
            <div class="container px-4 py-5" id="custom-cards">
                <h2 class="pb-2 border-bottom">
                    <div class="feature-icon d-inline-flex align-items-center justify-content-center text-bg-danger bg-gradient fs-2 mb-3">
                        <img src="photos/rocket-takeoff.svg" alt="Logo" width="30" height="30" class="d-inline-block align-text-top">
                    </div>
                    პოპულარული
                </h2>

                <div class="row row-cols-1 row-cols-lg-3 align-items-stretch g-4 py-5">
                <% for(int i = 0; i < popularQuizzes.size(); i++){
                        Quiz quiz = popularQuizzes.get(i);
                        String photo = quiz.getQuizPhoto();
                        int quizId = quiz.getId();
                        String name = quiz.getTitle();
                        String category = quiz.getCategory();
                        int owner = quiz.getAccount().getId();
                        Timestamp uploadTime = quiz.getCreationDate();
                        Timestamp currentTimeMillis = new Timestamp(System.currentTimeMillis());
                        long durationInMillis = currentTimeMillis.getTime() - uploadTime.getTime();
                        int duration = (int)durationInMillis / (60 * 1000);
                        String timeText = "წთ";
                        if(duration > 59){
                            duration = duration / 60;
                            timeText = "სთ";
                        }
                        if(duration > 23){
                            duration = duration / 24;
                            timeText = "დღ";
                        }
                        if(duration > 30){
                            duration = duration / 30;
                            timeText = "თვ";
                        }
                        if(duration > 11){
                            duration = duration / 12;
                            timeText = "წ";
                        }
                %>
                    <a class="col" href="quiz?quiz=<%=quizId%>">
                        <div class="card card-cover h-100 overflow-hidden text-bg-dark rounded-4 shadow-lg" style="background-image: url('photos/<%=photo%>');">
                            <div class="d-flex flex-column h-100 p-5 pb-3 text-white text-shadow-1">
                                    <h3 class="pt-5 mt-5 mb-4 display-6 lh-1 fw-bold"><%=name%></h3>
                                    <ul class="d-flex list-unstyled mt-auto">
                                    <li class="me-auto">
                                        <img src="photos/<%=owner%>.png" alt="Bootstrap" width="32" height="32" class="rounded-circle border border-white">
                                    </li>
                                    <li class="d-flex align-items-center me-3">
                                        <svg class="bi me-2" width="1em" height="1em"><use xlink:href="#geo-fill"></use></svg>
                                        <small><%=category%></small>
                                    </li>
                                    <li class="d-flex align-items-center">
                                        <svg class="bi me-2" width="1em" height="1em"><use xlink:href="#calendar3"></use></svg>
                                        <small><%=duration%><%=timeText%></small>
                                    </li>
                                    </ul>
                            </div>
                        </div>
                    </a>
                </div>
                <% } %>
            </div>
        </div>
    </div>
    <div class="container">
        <div class = "lastOnes">
            <div class="container px-4 py-5" id="custom-cards">
                <h2 class="pb-2 border-bottom">
                    <div class="feature-icon d-inline-flex align-items-center justify-content-center text-bg-warning bg-gradient fs-2 mb-3">
                        <img src="photos/lightning.svg" alt="Logo" width="30" height="30" class="d-inline-block align-text-top">
                    </div>
                    ახალი ქვიზები
                </h2>

                <div class="row row-cols-1 row-cols-lg-3 align-items-stretch g-4 py-5">
                <% for(int i = 0; i < recentQuizzes.size(); i++){
                        Quiz quiz = recentQuizzes.get(i);
                        String photo = quiz.getQuizPhoto();
                        int quizId = quiz.getId();
                        String name = quiz.getTitle();
                        String category = quiz.getCategory();
                        int owner = quiz.getAccount().getId();
                        Timestamp uploadTime = quiz.getCreationDate();
                        Timestamp currentTimeMillis = new Timestamp(System.currentTimeMillis());
                        long durationInMillis = currentTimeMillis.getTime() - uploadTime.getTime();
                        int duration = (int)durationInMillis / (60 * 1000);
                        String timeText = "წთ";
                        if(duration > 59){
                            duration = duration / 60;
                            timeText = "სთ";
                        }
                        if(duration > 23){
                            duration = duration / 24;
                            timeText = "დღ";
                        }
                        if(duration > 30){
                            duration = duration / 30;
                            timeText = "თვ";
                        }
                        if(duration > 11){
                            duration = duration / 12;
                            timeText = "წ";
                        }
                %>
                    <a class="col" href="quiz?quiz=<%=quizId%>">
                        <div class="card card-cover h-100 overflow-hidden text-bg-dark rounded-4 shadow-lg" style="background-image: url('photos/<%=photo%>');">
                            <div class="d-flex flex-column h-100 p-5 pb-3 text-white text-shadow-1">
                                    <h3 class="pt-5 mt-5 mb-4 display-6 lh-1 fw-bold"><%=name%></h3>
                                    <ul class="d-flex list-unstyled mt-auto">
                                    <li class="me-auto">
                                        <img src="photos/<%=owner%>.png" alt="Bootstrap" width="32" height="32" class="rounded-circle border border-white">
                                    </li>
                                    <li class="d-flex align-items-center me-3">
                                        <svg class="bi me-2" width="1em" height="1em"><use xlink:href="#geo-fill"></use></svg>
                                        <small><%=category%></small>
                                    </li>
                                    <li class="d-flex align-items-center">
                                        <svg class="bi me-2" width="1em" height="1em"><use xlink:href="#calendar3"></use></svg>
                                        <small><%=duration%><%=timeText%></small>
                                    </li>
                                    </ul>
                            </div>
                        </div>
                    </a>
                </div>
                <% } %>
            </div>
        </div>
    </div>
</body>
</html>