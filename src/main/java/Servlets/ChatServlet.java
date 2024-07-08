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

@WebServlet("/chat")
public class ChatServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int curUserId = Integer.parseInt((String) request.getSession().getAttribute("curUser"));
        SqlAccountInfoDao accInfo = (SqlAccountInfoDao) getServletContext().getAttribute("accountInfo_db");
        AccountDao accs = (AccountDao) getServletContext().getAttribute("accounts_db");
        List<Integer> friendsIds = null;
        try {
            friendsIds = accInfo.getAllFriendsId(curUserId);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        List<Account> friendsIdAndNames = new ArrayList<>();
        for(Integer id : friendsIds) {
            try {
                Account acc = new Account();
                acc.setId(id);
                acc.setUsername(accs.getNameById(id));
                friendsIdAndNames.add(acc);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
        request.setAttribute("friendsAcc", friendsIdAndNames);

        if(request.getParameter("friendId") != null) {
            int friendId = Integer.parseInt(request.getParameter("friendId"));
            String chatName = (curUserId > friendId) ? "chat" + friendId + curUserId : "chat" + curUserId + friendId;

            String sql = "SELECT * FROM " + chatName + " ORDER BY date";
            List<Message> messages = new ArrayList<>();

            try (Connection connection = ConnectionManager.getInstance();
                 Statement statement = connection.createStatement();
                 ResultSet resultSet = statement.executeQuery(sql)) {

                while (resultSet.next()) {
                    int messageId = resultSet.getInt("message_id");
                    int accountId = resultSet.getInt("account_id");
                    String messageText = resultSet.getString("message_text");
                    Timestamp date = resultSet.getTimestamp("date");
                    messages.add(new Message(messageId, accountId, messageText, date));
                }
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
            request.setAttribute("messages", messages);
            StringBuilder sb = new StringBuilder();

            for (Message msg: messages) {
                String colour = "grey";
                if(msg.getAccountId() == curUserId) colour = "orange";
                sb.append("<div class = " + '"' + "chat-message ").append(colour).append('"' + ">").append(msg.getMessageText()).append("</div>");
            }
            response.getWriter().write(sb.toString());
            return;
        }
        request.getRequestDispatcher("/ChatPage.jsp").forward(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        int curUserId = Integer.parseInt((String) session.getAttribute("curUser"));

        String message = request.getParameter("message");
        if (message != null && !message.isEmpty()) {
            storeMessage(request, curUserId, message);
        }
    }

    private List<String> getMessages() {
        List<String> messages = new ArrayList<>();
        try {
            Connection conn = ConnectionManager.getInstance();

            String sql = "SELECT sender, message FROM messages ORDER BY sent_at";
            PreparedStatement statement = conn.prepareStatement(sql);
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                String sender = resultSet.getString("sender");
                String message = resultSet.getString("message");
                messages.add(sender + ": " + message);
            }

            resultSet.close();
            statement.close();
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return messages;
    }

    private void storeMessage(HttpServletRequest request, int curUserId, String message) {
        try {
            Connection conn = ConnectionManager.getInstance();

            int friendId = Integer.parseInt(request.getParameter("FriendId"));
            String chatName = (curUserId > friendId) ? "chat" + friendId + curUserId : "chat" + curUserId + friendId;

            String sql = "INSERT INTO " + chatName + " (account_id, message, send_date) VALUES (?, ?, SYSDATE())";
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
