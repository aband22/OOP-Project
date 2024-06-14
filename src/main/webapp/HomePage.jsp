<%@ page import="java.util.List,
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
            .feature-icon {
                width: 4rem;
                height: 4rem;
                border-radius: .75rem;
            }
    </style>
</head>
<body>
    <div class = "home">
        <nav class="navbar bg-body-tertiary">
            <div class="container-fluid">
              <a class="navbar-brand" href="HomePage.jsp">
                <img src="photos/moai.jpg" alt="Logo" width="30" height="30" class="d-inline-block align-text-top">
                SigmaQuiz
              </a>
              <ul class="nav justify-content-end">
                <li class="nav-item">
                  <a class="nav-link active" aria-current="page" href="#"><img src="photos/bell-fill.svg" alt="Logo" width="20" height="20"></a>
                </li>
                <li class="nav-item">
                  <a class="nav-link" href="#"><img src="photos/chat-right-quote-fill.svg" alt="Logo" width="20" height="20"></a>
                </li>
                <li class="nav-item">
                  <a class="nav-link" href="#"><img src="photos/person-circle.svg" alt="Logo" width="20" height="20"></a>
                </li>
                <li class="nav-item">
                    <button class="btn btn-warning" type="submit">გამოსვლა</button>
                </li>
              </ul>
            </div>
          </nav>
    </div>
    <div class="container">
        <div class = "searchBar">
            <form class="d-flex" role="search" action = "home" method = "get">
                <input class="form-control me-2" type="search" placeholder="ძებნა" aria-label="ძებნა">
                <button class="btn btn-outline-warning" type="submit">ძებნა</button>
            </form>
        </div>
    </div>
    <div class="container">
        <div class = "categories">
            <div class="regac">
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
                                                <div class="col-md-2" type="submit">
                                                    <div class="card text-bg-<%=color%> mb-3" style="max-width: 18rem;">
                                                        <div class="card-header"><%=category%></div>
                                                        <div class="card-body">

                                                        </div>
                                                    </div>
                                                </div>
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
                                                <div class="col-md-2" type="submit">
                                                    <div class="card text-bg-<%=color%> mb-3" style="max-width: 18rem;">
                                                        <div class="card-header"><%=category%></div>
                                                        <div class="card-body">

                                                        </div>
                                                    </div>
                                                </div>
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
                    <div class="col" type="submit">
                        <div class="card card-cover h-100 overflow-hidden text-bg-dark rounded-4 shadow-lg" style="background-image: url('unsplash-photo-1.jpg');">
                            <div class="d-flex flex-column h-100 p-5 pb-3 text-white text-shadow-1">
                                    <h3 class="pt-5 mt-5 mb-4 display-6 lh-1 fw-bold">Short title, long jacket</h3>
                                    <ul class="d-flex list-unstyled mt-auto">
                                    <li class="me-auto">
                                        <img src="moai.jpg" alt="Bootstrap" width="32" height="32" class="rounded-circle border border-white">
                                    </li>
                                    <li class="d-flex align-items-center me-3">
                                        <svg class="bi me-2" width="1em" height="1em"><use xlink:href="#geo-fill"></use></svg>
                                        <small>Earth</small>
                                    </li>
                                    <li class="d-flex align-items-center">
                                        <svg class="bi me-2" width="1em" height="1em"><use xlink:href="#calendar3"></use></svg>
                                        <small>3d</small>
                                    </li>
                                    </ul>
                            </div>
                        </div>
                    </div>

                </div>
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
                        <div class="col" type="submit">
                            <div class="card card-cover h-100 overflow-hidden text-bg-dark rounded-4 shadow-lg" style="background-image: url('unsplash-photo-1.jpg');">
                                <div class="d-flex flex-column h-100 p-5 pb-3 text-white text-shadow-1">
                                        <h3 class="pt-5 mt-5 mb-4 display-6 lh-1 fw-bold">Short title, long jacket</h3>
                                        <ul class="d-flex list-unstyled mt-auto">
                                        <li class="me-auto">
                                            <img src="moai.jpg" alt="Bootstrap" width="32" height="32" class="rounded-circle border border-white">
                                        </li>
                                        <li class="d-flex align-items-center me-3">
                                            <svg class="bi me-2" width="1em" height="1em"><use xlink:href="#geo-fill"></use></svg>
                                            <small>Earth</small>
                                        </li>
                                        <li class="d-flex align-items-center">
                                            <svg class="bi me-2" width="1em" height="1em"><use xlink:href="#calendar3"></use></svg>
                                            <small>3d</small>
                                        </li>
                                        </ul>
                                </div>
                            </div>
                        </div>
                </div>
            </div>
        </div>
    </div>
</body>
</html>