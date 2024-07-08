package Servlets;

import Accounts.*;
import Quizzes.Quiz;
import Quizzes.SqlQuizDao;
import Quizzes.SqlQuizzesHistoryDao;

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
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

@WebServlet("/user")
@MultipartConfig
public class UserPageServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String user = request.getParameter("user");
        if(user == null) {
            request.getRequestDispatcher("/ErrorPage.jsp").forward(request, response);
            return;
        }
        int userId = Integer.parseInt(user);
        request.setAttribute("userId", userId);
        user = (String) request.getSession().getAttribute("curUser");
        int curUserId = 0;
        if(user != null)  curUserId = Integer.parseInt(user);

        SqlAccountInfoDao accInfo = (SqlAccountInfoDao) getServletContext().getAttribute("accountInfo_db");
        SqlAccountDao accountStore = (SqlAccountDao) getServletContext().getAttribute("accounts_db");
        SqlNotificationDao notificationsInfo = (SqlNotificationDao) getServletContext().getAttribute("notifications_db");
        SqlAchievementDao achievementStore = (SqlAchievementDao) getServletContext().getAttribute("achievements_db");
        SqlQuizzesHistoryDao quizzesInfo = (SqlQuizzesHistoryDao) getServletContext().getAttribute("quizzesHistory_db");
        SqlQuizDao quizStore = (SqlQuizDao) getServletContext().getAttribute("quizzes_db");

        List<Quiz> myQuizzes;
        List<Integer> friendsId;
        Account account;
        try {
            account = accountStore.GetAccountById(userId);
        } catch (SQLException e) {
            request.getRequestDispatcher("/ErrorPage.jsp").forward(request, response);
            return;
        }

        try {
            myQuizzes = accInfo.getCreatedQuizzes(userId);
        } catch (SQLException e) {
            request.getRequestDispatcher("/ErrorPage.jsp").forward(request, response);
            return;
        }
        try {
            friendsId = accInfo.getAllFriendsId(userId);
        } catch (SQLException e) {
            request.getRequestDispatcher("/ErrorPage.jsp").forward(request, response);
            return;
        }
        List<Account> friends = new ArrayList<>();
        for (Integer integer : friendsId) {
            try {
                friends.add(accountStore.GetAccountById(integer));
            } catch (SQLException e) {
                request.getRequestDispatcher("/ErrorPage.jsp").forward(request, response);
                return;
            }
        }

        List<Achievement> achievements;
        try {
            achievements = achievementStore.getAll(userId);
        } catch (SQLException e) {
            request.getRequestDispatcher("/ErrorPage.jsp").forward(request, response);
            return;
        }

        List<Integer> quizzesIds = quizzesInfo.getDoneQuizzesId(userId);
        List<Quiz> quizzes = new ArrayList<>();
        for (Integer integer : quizzesIds) {
            try {
                quizzes.add(quizStore.getQuizById(integer));
            } catch (SQLException e) {
                request.getRequestDispatcher("/ErrorPage.jsp").forward(request, response);
                return;
            }
        }

        request.setAttribute("account", account);
        request.getServletContext().setAttribute("quizzes", myQuizzes);
        request.getServletContext().setAttribute("achievements", achievements);
        request.getServletContext().setAttribute("friends", friends);
        request.getServletContext().setAttribute("quizzesDone", quizzes);
        request.setAttribute("curUserId", curUserId);
        request.setAttribute("userId", userId);

        int isFriend = 0;
        try {
            if(accInfo.isFriend(curUserId, userId)) isFriend = 1;
        } catch (SQLException e) {
            request.getRequestDispatcher("/ErrorPage.jsp").forward(request, response);
            return;
        }
        try {
            if(notificationsInfo.isSentFriendNotification(curUserId, userId)) isFriend = 2;
        } catch (SQLException e) {
            request.getRequestDispatcher("/ErrorPage.jsp").forward(request, response);
            return;
        }

        request.setAttribute("isFriend", "" + isFriend);

        request.getRequestDispatcher("/UserPage.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String user = request.getParameter("user");
        if(user == null) {
            request.getRequestDispatcher("/ErrorPage.jsp").forward(request, response);
            return;
        }
        int userId = Integer.parseInt(user);
        request.setAttribute("userId", userId);
        user = (String) request.getSession().getAttribute("curUser");
        if(user == null) {
            request.getRequestDispatcher("/ErrorPage.jsp").forward(request, response);
            return;
        }
        int curUserId = Integer.parseInt(user);

        SqlNotificationDao notificationsInfo = (SqlNotificationDao) getServletContext().getAttribute("notifications_db");
        if(Objects.equals(request.getParameter("addFriend"), "addFriend")){
            Notification notification = new Notification(userId, curUserId, Notification.FRIEND_REQUEST, Notification.FRIEND_REQUEST_TEXT);
            try {
                notificationsInfo.add(notification);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }

            //request.getRequestDispatcher("/UserPage.jsp").forward(request, response);
            response.sendRedirect("user?user=" + userId);
            return;
        } else if(Objects.equals(request.getParameter("pending"), "pending")){
            Notification notification = new Notification(userId, curUserId, Notification.FRIEND_REQUEST, Notification.FRIEND_REQUEST_TEXT);
            try {
                notificationsInfo.remove(notification);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }

            //request.getRequestDispatcher("/UserPage.jsp").forward(request, response);
            response.sendRedirect("user?user=" + userId);
            return;
        } else if (Objects.equals(request.getParameter("friend"), "friend")) {
            SqlAccountInfoDao accInfo = (SqlAccountInfoDao) getServletContext().getAttribute("accountInfo_db");
            try {
                accInfo.removeFriend(curUserId, userId);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
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
