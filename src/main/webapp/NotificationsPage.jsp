<%@ page import="java.util.List,
                            Accounts.SqlAccountDao,
                            Accounts.Notification,
                            java.util.Objects" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt" %>
<%@ page isELIgnored="false" %>
<html lang="en">
<head>
    <title>SigmaQuiz</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-QWTKZyjpPEjISv5WaRU9OFeRpok6YctnYmDr5pNlyT2bRjXh0JMhjY6hW+ALEwIH" crossorigin="anonymous">
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js" integrity="sha384-YvpcrYf0tY3lHB60NNkmXc5s9fDVZLESaAA55NDzOxhy9GkcIdslK1eN7N6jIeHz" crossorigin="anonymous"></script>
    <style>
            a{
              text-decoration: none;
            }
            p{
              font-size:xx-small;
            }
            .card:hover{
              background-color: rgb(198, 198, 198);
            }
            .img-fluid{
              border-radius: 100%;
              align-items: center;
            }
            .center{
              padding-left: 25%;
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

    <div class="container center">
          <%
                List<Notification> notifications = (List)application.getAttribute("notifications");
                if(notifications.isEmpty()){%>
                <h6 style="align-content: center; padding-top: 200px">ცნობები არ არის</h6>
          <%    } else {
                    for(int i = 0; i < notifications.size(); i++){
                        Notification notification = notifications.get(i);
                        int notification_id = notification.getId();
                        int quizId = notification.getQuizId();
                        String title = notification.getType();
                        String text = notification.getText();
                        int from_id = notification.getFromId();
                        String name;
                        if(from_id != -1) name = ((SqlAccountDao)application.getAttribute("accounts_db")).getNameById(from_id);
                        else name = "";
          %>
          <div class="card mb-9 btn" style="max-width: 540px; border: white;">
            <div class="row g-0">
              <div class="col-md-4">
                <img src="photos/<%=from_id%>.png" class="img-fluid" alt="Photo" width="115" height="115">
              </div>
              <div class="col-md-8">
                <div class="card-body">
                  <h6 class="card-title"><%=title%></h6>
                  <p class="card-text"><%=name%><%=text%></p>
                  <p class="card-text"><small class="text-body-secondary">გამოგზავნილია 3 წუთის წინ</small></p>
                      <form action="notifications?user=<%=from_id%>&notification=<%=notification_id%>&quiz=<%=quizId%>" method="post" id="buttonStatus">
                      <% if(Objects.equals(title, Notification.FRIEND_REQUEST)){%>
                      <input type="hidden" id="admit" name="admit">
                      <button type="submit" class="btn btn-primary" onclick="setAttributeAndSubmit('admit')">დადასტურება</button>
                      <input type="hidden" id="reject" name="reject">
                      <button type="submit" class="btn btn-secondary" onclick="setAttributeAndSubmit('reject')">უარყოფა</button>
                      <% } else if(Objects.equals(title, Notification.NEWS)){ %>
                      <input type="hidden" id="forwardToQuiz" name="forwardToQuiz">
                      <button type="submit" class="btn btn-warning" onclick="setAttributeAndSubmit('forwardToQuiz')">ქვიზის გაკეთება</button>
                      <% } else if(Objects.equals(title, Notification.CHALLENGE)){ %>
                      <input type="hidden" id="forwardToQuiz" name="forwardToQuiz">
                      <button type="submit" class="btn btn-warning" onclick="setAttributeAndSubmit('forwardToQuiz')">ქვიზის დაწყება</button>
                      <% } else if(Objects.equals(title, Notification.FRIEND_RESULT)){ %>
                      <input type="hidden" id="forwardToQuiz" name="forwardToQuiz">
                      <button type="submit" class="btn btn-warning" onclick="setAttributeAndSubmit('forwardToQuiz')">ქვიზზე გადასვლა</button>
                      <% } %>
                      </form>
                </div>
              </div>
            </div>
          </div>
          <% }} %>
    </div>
</body>
<script>
    function setAttributeAndSubmit(attribute) {
        var attributeValue = attribute;
        document.getElementById(attribute).value = attributeValue;
        document.getElementById("buttonStatus").submit();
    }
</script>
</html>