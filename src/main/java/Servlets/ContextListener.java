package Servlets;

import Accounts.*;
import Quizzes.QuizDao;
import Quizzes.QuizzesHistoryDao;
import Quizzes.SqlQuizDao;
import Quizzes.SqlQuizzesHistoryDao;

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

        NotificationDao NotificationStore;
        try {
            NotificationStore = new SqlNotificationDao(ConnectionManager.getInstance());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        AchievementDao AchievementStore;
        try {
            AchievementStore = new SqlAchievementDao(ConnectionManager.getInstance());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        QuizzesHistoryDao QuizzesHistoryStore;
        try {
            QuizzesHistoryStore = new SqlQuizzesHistoryDao(ConnectionManager.getInstance());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        System.out.println("Context Created!!!!!");
        context.setAttribute("accounts_db", AccountStore);
        context.setAttribute("accountInfo_db", AccountInfoStore);
        context.setAttribute("quizzes_db", QuizzesStore);
        context.setAttribute("notifications_db", NotificationStore);
        context.setAttribute("achievements_db", AchievementStore);
        context.setAttribute("quizzesHistory_db", QuizzesHistoryStore);
    }

    @Override
    public void contextDestroyed(ServletContextEvent servletContextEvent) {

    }
}
