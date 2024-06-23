package Servlets;

import Accounts.Account;
import Accounts.AccountDao;
import Accounts.SqlAccountDao;
import Accounts.SqlAccountInfoDao;
import Quizzes.Quiz;
import Quizzes.SqlQuizDao;

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
        myQuizzes = quizInfo.getCreatedQuizzes(userId);
        friends = quizInfo.getAllFriends(userId);

        request.setAttribute("account", account);
        request.setAttribute("quizzes", myQuizzes);
        request.setAttribute("friends", friends);

        request.getRequestDispatcher("/UserPage.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

    }
}