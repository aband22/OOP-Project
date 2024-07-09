package Servlets;

import Accounts.Account;
import Accounts.SqlAccountDao;
import Accounts.SqlAccountInfoDao;
import Quizzes.Question;
import Quizzes.Quiz;
import Quizzes.SqlQuizDao;
import Quizzes.SqlQuizzesHistoryDao;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@WebServlet("/quiz")
public class QuizServlet extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String quizz = request.getParameter("quiz");
        if (quizz == null) {
            request.getRequestDispatcher("/ErrorPage.jsp").forward(request, response);
            return;
        }
        int quizId = Integer.parseInt(quizz);
        SqlQuizDao quizStore = (SqlQuizDao) request.getServletContext().getAttribute("quizzes_db");
        Quiz quiz;
        try {
            quiz = quizStore.getQuizById(quizId);
        } catch (Exception e) {
            request.getRequestDispatcher("/ErrorPage.jsp").forward(request, response);
            return;
        }
        String curUser = (String) request.getSession().getAttribute("curUser");
        int curUserId = 0;
        if(curUser != null)  curUserId = Integer.parseInt(curUser);
        request.setAttribute("quiz", quiz);
        request.setAttribute("curUser", curUserId);

        SqlAccountInfoDao accInfo = (SqlAccountInfoDao) request.getServletContext().getAttribute("accountInfo_db");
        List<Integer> friendsIds = new ArrayList<>();
        try {
            friendsIds = accInfo.getAllFriendsId(curUserId);
        } catch (SQLException e) {
            request.getRequestDispatcher("/ErrorPage.jsp").forward(request, response);
            return;
        }

        List<Account> friends = new ArrayList<>();
        SqlAccountDao accs = (SqlAccountDao) request.getServletContext().getAttribute("accounts_db");
        for (Integer friendId : friendsIds) {
            try {
                friends.add(accs.getAccountById(friendId));
            } catch (SQLException e) {
                request.getRequestDispatcher("/ErrorPage.jsp").forward(request, response);
                return;
            }
        }

        SqlQuizzesHistoryDao quizInfo = (SqlQuizzesHistoryDao) request.getServletContext().getAttribute("quizzesHistory_db");
        List<Integer> scorersIds = new ArrayList<>();
        try {
            scorersIds = quizInfo.getScorersId(quizId);
        } catch (SQLException e) {
            request.getRequestDispatcher("/ErrorPage.jsp").forward(request, response);
            return;
        }

        List<Account> scorers = new ArrayList<>();
        for (Integer scorerId : scorersIds) {
            try {
                friends.add(accs.getAccountById(scorerId));
            } catch (SQLException e) {
                request.getRequestDispatcher("/ErrorPage.jsp").forward(request, response);
                return;
            }
        }

        request.setAttribute("friends", friends);
        request.setAttribute("topScorers", scorers);
        request.setAttribute("curUser", request.getSession().getAttribute("curUser"));
        request.getRequestDispatcher("/QuizPage.jsp").forward(request, response);
    }
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String quizz = request.getParameter("quiz");
        if (quizz == null) {
            request.getRequestDispatcher("/ErrorPage.jsp").forward(request, response);
            return;
        }
        int quizId = Integer.parseInt(quizz);

        SqlQuizDao quizDao = (SqlQuizDao) request.getServletContext().getAttribute("quizzes_db");
        Quiz quiz;
        try {
            quiz = quizDao.getQuizById(quizId);
        } catch (SQLException e) {
            throw new ServletException(e);
        }
        List<String> answers = new ArrayList<>();
        List<String> responces = new ArrayList<>();

        List<Question> questions = quiz.getQuestions();

        for (int i = 0; i < questions.size(); i++) {
            for(int j=0; j < questions.get(i).getAnswers().size(); j++){
                answers.add(questions.get(i).getAnswers().get(j));
                //responces.add(req.getParameter("answer"+i+"_"+j));
                //System.out.println(questions.get(i).getAnswers().get(j));
                // System.out.println(req.getParameter("answer"+i+"_"+j));
            }

        }
        for (int i = 0; i < questions.size(); i++) {
            for(int j=0; j < questions.get(i).getAnswers().size(); j++){
                responces.add(request.getParameter("answer"+(i+1)+"_"+(j+1)));

                // System.out.println(req.getParameter("answer"+(i+1)+"_"+(j+1)));

            }

        }
        int score = 0;
        System.out.println(request.getParameter("answer2_2"));

        for(int i=0; i<answers.size(); i++){
            if(responces.get(i)!=null && answers.get(i)!=null && Objects.equals(answers.get(i), responces.get(i))){
                score++;
            }
        }


        SqlQuizzesHistoryDao quizInfo = (SqlQuizzesHistoryDao) request.getServletContext().getAttribute("quizzesHistory_db");
        try {
            quizInfo.add(quizId, Integer.parseInt((String)request.getSession().getAttribute("curUser")), 0);
        } catch (SQLException e) {
            request.getRequestDispatcher("/ErrorPage.jsp").forward(request, response);
            return;
        }

        response.sendRedirect("quiz?quiz=" + quizId);
    }
}