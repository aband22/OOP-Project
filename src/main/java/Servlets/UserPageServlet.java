package Servlets;

import Accounts.Account;
import Accounts.AccountDao;
import Accounts.SqlAccountDao;
import Accounts.SqlAccountInfoDao;
import Quizzes.Quiz;
import Quizzes.SqlQuizDao;

import javax.persistence.criteria.CriteriaBuilder;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

@WebServlet("/user")
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
        System.out.println(userId);
        try {
            account = accountStore.GetAccountById(userId);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        //System.out.println("llaala");

        myQuizzes = quizInfo.getCreatedQuizzes(userId);
        friends = quizInfo.getAllFriends(userId);

        request.setAttribute("account", account);
        request.setAttribute("quizzes", myQuizzes);
        request.setAttribute("friends", friends);
        request.setAttribute("curUserId", curUserId);
        request.setAttribute("userId",userId);

        request.getRequestDispatcher("/UserPage.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {


        String newUsername = request.getParameter("username");
        String newEmail = request.getParameter("mail");
        String newPass = request.getParameter("password");
        int accountId = Integer.parseInt(request.getParameter("accountId"));

        SqlAccountDao accountStore = (SqlAccountDao) getServletContext().getAttribute("accounts_db");

        try {
            Account acc = accountStore.GetAccountById(accountId);
            System.out.println("Updating account: " + acc.getId());
            acc.setUsername(newUsername);
            acc.setEmail(newEmail);
            acc.setPassword(newPass);
            accountStore.editAccount(acc);
            response.sendRedirect(request.getContextPath() + "/user?user=" + accountId);
        } catch (SQLException e) {
            throw new ServletException("Error updating account", e);
        }
    }
}