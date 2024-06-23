package Servlets;

import Quizzes.Quiz;
import Quizzes.SqlQuizDao;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@WebServlet("/home")
public class HomeServlet extends HttpServlet {
    private static final String USER = "user";
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
//        Cookie[] cookies = request.getCookies();
//        String user = null;
//        if (cookies != null) {
//            for (Cookie c : cookies) {
//                if (c.getName().equals(USER)) {
//                    user = c.getValue();
//                }
//            }
//        }
//        if (user != null) {
//            request.setAttribute(USER, user);
//        }
        String category = request.getParameter("category");
        String searchedItem = request.getParameter("search");
        if(category == null && searchedItem == null){
            List<String> colors = new ArrayList<>();
            Collections.addAll(colors,"dark", "info", "warning", "danger", "success", "secondary", "primary");
            request.getServletContext().setAttribute("colors", colors);
            request.getRequestDispatcher("/HomePage.jsp").forward(request, response);
            return;
        }
        SqlQuizDao store = (SqlQuizDao) request.getServletContext().getAttribute("quizzes_db");
        List<Quiz> foundedQuizzes;
        if(category != null){
            foundedQuizzes = store.getQuizByCategory(category);
        } else {
            foundedQuizzes = store.getQuizzesFromSearch(searchedItem);
        }
        request.getServletContext().setAttribute("quizzes", foundedQuizzes);
        request.getRequestDispatcher("/SearchPage.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}
