package Servlets;

import Accounts.Account;
import Accounts.SqlAccountDao;
import Accounts.SqlAccountInfoDao;
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

@WebServlet("/user")
@MultipartConfig
public class UserPageServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        int userId = Integer.parseInt(request.getParameter("user"));
        int curUserId = Integer.parseInt((String) request.getServletContext().getAttribute("curUser"));
        SqlAccountDao accountStore = (SqlAccountDao) getServletContext().getAttribute("accounts_db");
        SqlAccountInfoDao quizInfo = (SqlAccountInfoDao) getServletContext().getAttribute("accountInfo_db");

        List<Quiz> myQuizzes;
        List<Account> friends;
        Account account;
        try {
            account = accountStore.GetAccountById(userId);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        myQuizzes = quizInfo.getCreatedQuizzes(userId);
        friends = quizInfo.getAllFriends(userId);

        request.setAttribute("account", account);
        request.setAttribute("quizzes", myQuizzes);
        request.setAttribute("friends", friends);
        request.setAttribute("curUserId", curUserId);
        request.setAttribute("userId", userId);

        request.getRequestDispatcher("/UserPage.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
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
