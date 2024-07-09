package Servlets;

import Quizzes.Question;
import Quizzes.Quiz;
import Quizzes.QuizDao;
import Quizzes.SqlQuizDao;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@WebServlet("/question")
public class QuestionPageServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        int quizId = Integer.parseInt(req.getParameter("quizId"));
        SqlQuizDao quizDao = (SqlQuizDao) getServletContext().getAttribute("quizzes_db");
        Quiz quiz;
        try {
            quiz = quizDao.getQuizById(quizId);
        } catch (SQLException e) {
            throw new ServletException(e);
        }
        req.setAttribute("quiz", quiz);
        req.getRequestDispatcher("/QuestionPage.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

    }
}
