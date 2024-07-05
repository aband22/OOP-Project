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
        int userID = Integer.parseInt((String) request.getServletContext().getAttribute("curUser"));
        SqlNotificationDao notifications = (SqlNotificationDao) request.getServletContext().getAttribute("notifications_db");
        List<Notification> userNotifications = notifications.getAll(userID);
        request.setAttribute("notifications", userNotifications);

        request.getRequestDispatcher("/NotificationsPage.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        SqlAccountInfoDao accountInfo = (SqlAccountInfoDao) getServletContext().getAttribute("accountInfo_db");
        SqlNotificationDao notificationsInfo = (SqlNotificationDao) getServletContext().getAttribute("notifications_db");
        int curUserId = Integer.parseInt((String) request.getServletContext().getAttribute("curUser"));
        int userId = Integer.parseInt(request.getParameter("user"));
        if(Objects.equals(request.getParameter("admit"), "admit")) {
            Notification notification = new Notification(userId, curUserId, Notification.FRIEND_REQUEST, Notification.FRIEND_REQUEST_TEXT);
            notificationsInfo.remove(notification);

            accountInfo.addFriend(curUserId, userId);
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
                        "send_date DATE," +
                        "PRIMARY KEY(message_id)," +
                        "FOREIGN KEY (account_id) REFERENCES accounts(account_id) ON DELETE CASCADE);");
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        } else if(Objects.equals(request.getParameter("reject"), "reject")){
            Notification notification = new Notification(userId, curUserId, Notification.FRIEND_REQUEST, Notification.FRIEND_REQUEST_TEXT);
            notificationsInfo.remove(notification);
        } else if(Objects.equals(request.getParameter("forwardToQuiz"), "forwardToQuiz")){
            int quizId = Integer.parseInt(request.getParameter("quiz"));
            int notificationId = Integer.parseInt(request.getParameter("notification"));
            notificationsInfo.remove(notificationId);
            response.sendRedirect("quiz?quiz=" + quizId);
            return;
        }
        response.sendRedirect("notifications");
    }
}
