package Servlets;

import Quizzes.Quiz;
import Quizzes.SqlQuizDao;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/quiz")
public class QuizServlet extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        int quizId = Integer.parseInt(request.getParameter("quizId"));
        System.out.println(quizId);
        SqlQuizDao quizInfo = (SqlQuizDao) getServletContext().getAttribute("quizzes_db");
        Quiz quiz;
        try {
            quiz = (Quiz) quizInfo.getQuizById(quizId);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        String curUser = (String) request.getSession().getAttribute("curUser");
        System.out.println(curUser);
        request.setAttribute("quiz", quiz);
        request.setAttribute("Title", quiz.getTitle());
        request.setAttribute("Category", quiz.getCategory());
        //  System.out.println(quiz.getCategory());
        request.setAttribute("AccountId", quiz.getAccount());
        request.setAttribute("Date", quiz.getCreationDate());
        request.setAttribute("curUser", curUser);


        request.getRequestDispatcher("/QuizPage.jsp").forward(request, response);
    }
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
    }
}