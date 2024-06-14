package Servlets;

import Accounts.AccountDao;
import Accounts.AccountInfoDao;
import Accounts.SqlAccountDao;
import Accounts.SqlAccountInfoDao;
import Quizzes.QuizDao;
import Quizzes.SqlQuizDao;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;
import java.sql.SQLException;

@WebListener
public class ContextListener implements ServletContextListener {
    @Override
    public void contextInitialized(ServletContextEvent servletContextEvent) {
        ServletContext context = servletContextEvent.getServletContext();
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }

        AccountDao AccountStore;
        try {
            AccountStore = new SqlAccountDao(ConnectionManager.getInstance());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        AccountInfoDao AccountInfoStore;
        try {
            AccountInfoStore = new SqlAccountInfoDao(ConnectionManager.getInstance());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        QuizDao QuizzesStore;
        try {
            QuizzesStore = new SqlQuizDao(ConnectionManager.getInstance());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        System.out.println("Context Created!!!!!");
        context.setAttribute("accounts_db", AccountStore);
        context.setAttribute("accountInfo_db", AccountInfoStore);
        context.setAttribute("quizzes_db", QuizzesStore);
    }

    @Override
    public void contextDestroyed(ServletContextEvent servletContextEvent) {

    }
}
