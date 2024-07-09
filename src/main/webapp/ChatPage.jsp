<%@ page import="java.util.List,
                            java.sql.Timestamp" %>
<%@ page import="Accounts.Account" %>
<%@ page import="Accounts.SqlAccountDao" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt" %>
<%@ page isELIgnored="false" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Chat</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-QWTKZyjpPEjISv5WaRU9OFeRpok6YctnYmDr5pNlyT2bRjXh0JMhjY6hW+ALEwIH" crossorigin="anonymous">
    <link rel="stylesheet" href="ChatPageStyle.css">
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.6.0/jquery.min.js"></script>
    <script type="text/javascript">
        $(document).ready(function() {
            function updateChat() {
                $.ajax({
                    url: "message?friendId=${friendId}",
                    type: "GET",
                    success: function(data) {
                        $("#chatMessages").html(data);
                    },
                    // error: function(jqXHR, textStatus, errorThrown) {
                    //     console.log("Error: " + textStatus);
                    // }
                });
            }

            // Update chat messages every 2 seconds
            setInterval(updateChat, 500);

            // Function to send new message
            $("#sendMessage").click(function() {
                var message = $("#messageInput").val().trim();
                if (message !== "") {
                    $.post("message?friendId=${friendId}", { message: message });
                    $("#messageInput").val("");
                }
            });
        });
    </script>
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
<div class="chat-container">
    <div class="friend-list">
        <div class="chat-header">საუბრები</div>
        <%
            List<Account> friends = (List<Account>) application.getAttribute("friends");
            for (int i = 0; i < friends.size(); i++) {
                int friendId = friends.get(i).getId();
                String friendName = friends.get(i).getUsername();
        %>
        <a class="card" style="max-width: 840px; border: white;" href="chat?friendId=<%=friendId%>">
            <div class="row g-0">
                <div class="col-md-2">
                    <img src="photos/<%=friendId%>.png" class="img-fluid" alt="Photo" width="50" height="50" style="border-radius: 100%">
                </div>
                <div class="col-md-10">
                    <div class="card-body">
                        <h5 style="font-weight: bold;"><%=friendName%></h5>
                    </div>
                </div>
            </div>
        </a>
        <% } %>
    </div>
    <c:choose>
        <c:when test="${friendId != null}">
            <div class="chat-content">
                <div class="chat-header" id="chatHeader">${friendName}</div>
                <div class="chat-messages" id="chatMessages">

                </div>
                <div class="chat-input">
                    <input type="text" id="messageInput" class="form-control" placeholder="აკრიფე მესიჯი...">
                    <button class="btn btn-danger w-100 mt-2" id="sendMessage">გაგზავნე</button>
                </div>
            </div>
        </c:when>
    </c:choose>
</div>
</body>
</html>