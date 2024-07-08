<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page isELIgnored="false" %>
<%@ page import="Accounts.Account" %>
<%@ page import="java.util.List" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Chat Interface</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-QWTKZyjpPEjISv5WaRU9OFeRpok6YctnYmDr5pNlyT2bRjXh0JMhjY6hW+ALEwIH" crossorigin="anonymous">
    <style>

        a{
            text-decoration: none;
        }
        .card:hover{
            background-color: rgb(198, 198, 198);
        }
        body {
            height: 100vh;
            margin: 0;
            display: flex;
            flex-direction: column; /* Ensure columns layout */
            background-color: #f8f9fa;
        }
        .home {
            background-color: #f8f9fa;
            padding: 10px; /* Adjust padding as needed */
        }
        .chat-container {
            display: flex;
            flex: 1; /* Fill remaining vertical space */
            border: 1px solid #ddd;
            border-radius: 10px;
            overflow: hidden;
            background-color: #fff;
        }
        .friend-list {
            width: 33.33%; /* 1/3 of the container's width */
            border-right: 1px solid #ddd;
            overflow-y: auto;
            height: 100%; /* Full height of the container */
            padding: 0; /* Remove padding */
        }
        .friend-list a {
            display: flex;
            align-items: center; /* Center text vertically */
            height: calc(100% / 10); /* Each friend row will take 1/10th of the height */
            padding-left: 25px; /* Add padding to the left */
        }
        .chat-content {
            width: 66.67%; /* 2/3 of the container's width */
            display: flex;
            flex-direction: column;
            height: 100%;
        }
        .chat-messages {
            flex-grow: 1;
            overflow-y: scroll;
            padding: 20px;
            word-wrap: break-word; /* Ensure long words break and wrap correctly */
        }
        .chat-header {
            padding: 10px;
            background-color: #f8f9fa;
            border-bottom: 1px solid #ddd;
            text-align: center;
            font-weight: bold;
            font-size: 1.5em;
        }
        .chat-message {
            display: block;
            margin-bottom: 15px;
            padding: 10px;
            border-radius: 5px;
            max-width: 70%; /* Wrap text after 70% of the width */
            word-break: break-word;
            clear: both; /* Ensure messages do not float next to each other */
        }
        .chat-message.blue {
            background-color: #007bff;
            color: #fff;
            float: right; /* Align blue messages to the right */
        }
        .chat-message.grey {
            background-color: #f0f0f0;
            color: #333;
            float: left; /* Align grey messages to the left */
        }
        .chat-input {
            padding: 20px;
            border-top: 1px solid #ddd;
        }
        .chat-input textarea {
            width: 100%;
            height: 60px;
            resize: none;
            border-radius: 5px;
        }
        .chat-input button {
            margin-top: 10px;
        }
    </style>

    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.6.0/jquery.min.js"></script>
    <script type="text/javascript">
        $(document).ready(function() {
            // Function to update chat messages
            function updateChat() {
                $.ajax({
                    url: "chat",
                    type: "GET",
                    success: function(data) {
                        $("#chatMessages").html(data); // Update chat messages
                    },
                    error: function(jqXHR, textStatus, errorThrown) {
                        console.log("Error: " + textStatus);
                    }
                });
            }
            setInterval(updateChat, 1);

            // Function to send new message
            $("#sendMessage").click(function() {
                var message = $("#chatInput").val().trim();
                if (message !== "") {
                    $.post("ChatServlet", { message: message });
                    $("#chatInput").val(""); // Clear input field after sending message
                    const chatMessages = document.getElementById('chatMessages');
                    chatMessages.scrollTop = chatMessages.scrollHeight;
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
<%--<div class="chat-container">--%>
<%--    <div class="friend-list">--%>
<%--        <div class="chat-header">Chat</div>--%>
<%--        <!-- Friend list will be populated here -->--%>
<%--    </div>--%>
<%--    <div class="chat-content">--%>
<%--        <div class="chat-header" id="chatHeader">Friend 1</div>--%>
<%--        <div class="chat-messages" id="chatMessages">--%>
<%--            <!-- Chat messages will appear here -->--%>
<%--        </div>--%>
<%--        <div class="chat-input">--%>
<%--            <textarea id="chatInput" placeholder="Type a message..."></textarea>--%>
<%--            <button class="btn btn-primary w-100" onclick="sendMessage()">Send</button>--%>
<%--        </div>--%>
<%--    </div>--%>
<%--</div>--%>
<div class="container">
    <div class="row">
        <div class="col-md-4">
            <div class="list-group friend-list">

                <%
                    List<Account> friends = (List<Account>) request.getAttribute("friends");
                    for (int i = 0; i < friends.size(); i++) {
                        int friendId = friends.get(i).getId();
                        String friendName = friends.get(i).getUsername();
                %>
                <a style="max-width: 840px; border: white;" href="chat?friendId=<%=friendId%>">
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
        </div>
        <div class="col-md-8">
            <div class="chat-container">
                <div class="chat-messages" id="chatMessages">
                    <!-- Chat messages will appear here -->
                </div>
                <div class="chat-input">
                    <textarea id="chatInput" placeholder="Type a message..."></textarea>
                    <button class="btn btn-danger w-100" id = "sendMessage">Send</button>
                </div>
            </div>
        </div>
    </div>
</div>

</body>
</html>
