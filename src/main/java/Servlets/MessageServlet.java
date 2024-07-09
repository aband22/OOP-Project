package Servlets;

import Accounts.Account;
import Accounts.AccountDao;
import Accounts.SqlAccountInfoDao;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@WebServlet("/message")
public class MessageServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        int curUserId = Integer.parseInt((String) session.getAttribute("curUser"));

        List<Message> messages = new ArrayList<>();
        if(request.getParameter("friendId") != null) messages = getMessages(curUserId, Integer.parseInt(request.getParameter("friendId")));

        StringBuilder sb = new StringBuilder();
        for (Message msg : messages) {
            String colour = "grey";
            if(msg.getAccountId() == curUserId) colour = "orange";
            sb.append("<div class = " + '"' + "chat-message ").append(colour).append('"' + ">").append(msg.getMessageText()).append("</div>");
        }

        response.setContentType("text/html;charset=UTF-8");
        response.getWriter().write(sb.toString());
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        int curUserId = Integer.parseInt((String) session.getAttribute("curUser"));

        String message = request.getParameter("message");
        if (message != null && !message.isEmpty()) {
            storeMessage(curUserId, Integer.parseInt(request.getParameter("friendId")), message);
        }
    }


    private List<Message> getMessages(int curUserId, int friendId) {
        List<Message> messages = new ArrayList<>();
        String chatName = (curUserId > friendId) ? "chat" + friendId + curUserId : "chat" + curUserId + friendId;

        String sql = "SELECT * FROM " + chatName + " ORDER BY send_date";

        try {
            Connection connection = ConnectionManager.getInstance();
            PreparedStatement statement = connection.prepareStatement(sql);
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                int messageId = resultSet.getInt("message_id");
                int accountId = resultSet.getInt("account_id");
                String messageText = resultSet.getString("message_text");
                Timestamp date = resultSet.getTimestamp("send_date");
                messages.add(new Message(messageId, accountId, messageText, date));
            }

            resultSet.close();
            statement.close();
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return messages;
    }

    private void storeMessage(int curUserId, int friendId, String message) {
        try {
            Connection conn = ConnectionManager.getInstance();
            String chatName = (curUserId > friendId) ? "chat" + friendId + curUserId : "chat" + curUserId + friendId;
            String sql = "INSERT INTO " + chatName + " (account_id, message_text, send_date) VALUES (?, ?, SYSDATE())";
            PreparedStatement statement = conn.prepareStatement(sql);
            statement.setInt(1, curUserId);
            statement.setString(2, message);
            statement.executeUpdate();
            statement.close();
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public class Message {
        private int messageId;
        private int accountId;
        private String messageText;
        private Timestamp date;

        public Message(int messageId, int accountId, String messageText, Timestamp date) {
            this.messageId = messageId;
            this.accountId = accountId;
            this.messageText = messageText;
            this.date = date;
        }

        public int getMessageId() { return messageId; }
        public int getAccountId() { return accountId; }
        public String getMessageText() { return messageText; }
        public Timestamp getDate() { return date; }
    }
}
