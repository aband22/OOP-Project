package Servlets;

import Accounts.*;
import Quizzes.Quiz;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;
import java.util.List;
import java.util.Objects;

@WebServlet("/user")
@MultipartConfig
public class UserPageServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        int userId = Integer.parseInt(request.getParameter("user"));
        int curUserId = Integer.parseInt((String) request.getServletContext().getAttribute("curUser"));

        SqlAccountInfoDao accInfo = (SqlAccountInfoDao) getServletContext().getAttribute("accountInfo_db");
        SqlAccountDao accountStore = (SqlAccountDao) getServletContext().getAttribute("accounts_db");
        SqlNotificationDao notificationsInfo = (SqlNotificationDao) getServletContext().getAttribute("notifications_db");

        List<Quiz> myQuizzes;
        List<Account> friends;
        Account account;
        try {
            account = accountStore.GetAccountById(userId);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        myQuizzes = accInfo.getCreatedQuizzes(userId);
        friends = accInfo.getAllFriends(userId);
        request.setAttribute("account", account);
        request.setAttribute("quizzes", myQuizzes);
        request.setAttribute("friends", friends);
        request.setAttribute("curUserId", curUserId);
        request.setAttribute("userId", userId);

        int isFriend = 0;
        if(accInfo.isFriend(curUserId, userId)) isFriend = 1;
        if(notificationsInfo.isSentFriendNotification(curUserId, userId)) isFriend = 2;


        request.setAttribute("isFriend", "" + isFriend);

        request.getRequestDispatcher("/UserPage.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int userId = Integer.parseInt(request.getParameter("user"));
        int curUserId = Integer.parseInt((String) request.getServletContext().getAttribute("curUser"));

        SqlNotificationDao notificationsInfo = (SqlNotificationDao) getServletContext().getAttribute("notifications_db");
        if(Objects.equals(request.getParameter("addFriend"), "addFriend")){
            Notification notification = new Notification(userId, curUserId, Notification.FRIEND_REQUEST, Notification.FRIEND_REQUEST_TEXT);
            notificationsInfo.add(notification);

            //request.getRequestDispatcher("/UserPage.jsp").forward(request, response);
            response.sendRedirect("user?user=" + userId);
            return;
        } else if(Objects.equals(request.getParameter("pending"), "pending") || Objects.equals(request.getParameter("friend"), "friend")){
            Notification notification = new Notification(userId, curUserId, Notification.FRIEND_REQUEST, Notification.FRIEND_REQUEST_TEXT);
            notificationsInfo.remove(notification);

            //request.getRequestDispatcher("/UserPage.jsp").forward(request, response);
            response.sendRedirect("user?user=" + userId);
            return;
        }
        int accountId = Integer.parseInt(request.getParameter("accountId"));
        String newUsername = request.getParameter("username");
        String newEmail = request.getParameter("mail");
        String newPass = request.getParameter("newPassword");
        String errorMessage = null;


        SqlAccountDao accountStore = (SqlAccountDao) getServletContext().getAttribute("accounts_db");

        try {
            Account acc = accountStore.GetAccountById(accountId);

            acc.setUsername(newUsername);
            acc.setEmail(newEmail);

            if (newPass != null && !newPass.isEmpty()) {
                PasswordHashMaker newHashMaker = new PasswordHashMaker(newPass);
                acc.setPassword(newHashMaker.getPasswordHash());
            }

            Part filePart = request.getPart("profilePhoto");
            if (filePart != null && filePart.getSize() > 0) {
                String fileName = accountId + ".png";
                String uploadDir = getServletContext().getRealPath("/") + "photos/";
                File file = new File(uploadDir + fileName);
                file.getParentFile().mkdirs(); // Ensure the photos directory exists
                try (InputStream input = filePart.getInputStream();
                     FileOutputStream output = new FileOutputStream(file)) {
                    byte[] buffer = new byte[1024];
                    int bytesRead;
                    while ((bytesRead = input.read(buffer)) != -1) {
                        output.write(buffer, 0, bytesRead);
                    }
                }
            }

            accountStore.editAccount(acc);
        } catch (NoSuchAlgorithmException | SQLException e) {
            errorMessage = "დაფიქსირდა შეცდომა: " + e.getMessage();
        }

        try {
            request.setAttribute("account", accountStore.GetAccountById(accountId));
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        request.setAttribute("errorMessage", errorMessage);
        request.getRequestDispatcher("/UserPage.jsp").forward(request, response);
    }
}
