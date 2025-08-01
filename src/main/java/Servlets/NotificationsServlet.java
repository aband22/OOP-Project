package Servlets;

import Accounts.Notification;
import Accounts.SqlAccountInfoDao;
import Accounts.SqlNotificationDao;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;
import java.util.Objects;

@WebServlet("/notifications")
public class NotificationsServlet extends HttpServlet {
    private static final String CHANGE_THIS_TO_YOUR_DB_NAME = "oop";
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String user = (String) request.getSession().getAttribute("curUser");
        if(user == null) {
            request.getRequestDispatcher("/ErrorPage.jsp").forward(request, response);
            return;
        }
        int userID = Integer.parseInt(user);
        SqlNotificationDao notifications = (SqlNotificationDao) request.getServletContext().getAttribute("notifications_db");
        List<Notification> userNotifications;
        try {
            userNotifications = notifications.getAll(userID);
        } catch (SQLException e) {
            request.getRequestDispatcher("/ErrorPage.jsp").forward(request, response);
            return;
        }
        request.getServletContext().setAttribute("notifications", userNotifications);

        request.getRequestDispatcher("/NotificationsPage.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        SqlAccountInfoDao accountInfo = (SqlAccountInfoDao) getServletContext().getAttribute("accountInfo_db");
        SqlNotificationDao notificationsInfo = (SqlNotificationDao) getServletContext().getAttribute("notifications_db");
        int curUserId = Integer.parseInt((String) request.getSession().getAttribute("curUser"));
        int userId = Integer.parseInt(request.getParameter("user"));
        if(Objects.equals(request.getParameter("admit"), "admit")) {
            Notification notification = new Notification(curUserId, userId, Notification.FRIEND_REQUEST, Notification.FRIEND_REQUEST_TEXT);
            try {
                notificationsInfo.remove(notification);
            } catch (SQLException e) {
                request.getRequestDispatcher("/ErrorPage.jsp").forward(request, response);
                return;
            }
            try {
                if(notificationsInfo.isSentFriendNotification(curUserId, userId)){
                    notification = new Notification(userId, curUserId, Notification.FRIEND_REQUEST, Notification.FRIEND_REQUEST_TEXT);
                    notificationsInfo.remove(notification);
                }
            } catch (SQLException e) {
                request.getRequestDispatcher("/ErrorPage.jsp").forward(request, response);
                return;
            }

            try {
                accountInfo.addFriend(curUserId, userId);
            } catch (SQLException e) {
                request.getRequestDispatcher("/ErrorPage.jsp").forward(request, response);
                return;
            }
            String chatName;
            if (curUserId > userId) chatName = "chat" + userId + curUserId;
            else chatName = "chat" + curUserId + userId;
            try {
                Statement stm = ConnectionManager.getInstance().createStatement();
                stm.execute("USE " + CHANGE_THIS_TO_YOUR_DB_NAME);
                stm.execute("DROP TABLE IF EXISTS " + chatName + ";");
                stm.execute("CREATE TABLE " + chatName + "(" +
                        "message_id INT AUTO_INCREMENT," +
                        "account_id INT," +
                        "message_text VARCHAR(255)," +
                        "send_date Timestamp," +
                        "PRIMARY KEY(message_id)," +
                        "FOREIGN KEY (account_id) REFERENCES accounts(account_id) ON DELETE CASCADE);");
            } catch (SQLException e) {
                request.getRequestDispatcher("/ErrorPage.jsp").forward(request, response);
                return;
            }
        } else if(Objects.equals(request.getParameter("reject"), "reject")){
            Notification notification = new Notification(curUserId, userId, Notification.FRIEND_REQUEST, Notification.FRIEND_REQUEST_TEXT);
            try {
                notificationsInfo.remove(notification);
            } catch (SQLException e) {
                request.getRequestDispatcher("/ErrorPage.jsp").forward(request, response);
                return;
            }
        } else if(Objects.equals(request.getParameter("forwardToQuiz"), "forwardToQuiz")){
            int quizId = Integer.parseInt(request.getParameter("quiz"));
            int notificationId = Integer.parseInt(request.getParameter("notification"));
            try {
                notificationsInfo.remove(notificationId);
            } catch (SQLException e) {
                request.getRequestDispatcher("/ErrorPage.jsp").forward(request, response);
                return;
            }
            response.sendRedirect("quiz?quiz=" + quizId);
            return;
        }
        response.sendRedirect("notifications");
    }
}
