package Servlets;

import Accounts.Account;
import Accounts.SqlAccountDao;
import Quizzes.*;

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
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;


@WebServlet("/addQuiz")
@MultipartConfig
public class CreateQuizServlet extends HttpServlet {
    private SqlQuizDao quizDao;

    @Override
    public void init() throws ServletException {
        super.init();
        quizDao = (SqlQuizDao) getServletContext().getAttribute("quizzes_db");
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.getRequestDispatcher("/CreateQuiz.jsp").forward(request,response);
    }


    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        String quizName = request.getParameter("quizName");
        String quizCategory = request.getParameter("quizCategory");
        String description = request.getParameter("description");
        String timer = request.getParameter("timer");
        String quizPhoto = null;
        Part filePart2 = request.getPart("quizPhoto");
        if (filePart2 != null && filePart2.getSize() > 0) {
            System.out.println("yy");

            quizPhoto =  "quiz_"+UUID.randomUUID().toString()+".png";
            String uploadDirr = getServletContext().getRealPath("/") + "photos/";
            File file = new File(uploadDirr + quizPhoto);
            file.getParentFile().mkdirs(); // Ensure the photos directory exists
            try (InputStream input = filePart2.getInputStream();
                 FileOutputStream output = new FileOutputStream(file)) {
                byte[] buffer = new byte[1024];
                int bytesRead;
                while ((bytesRead = input.read(buffer)) != -1) {
                    output.write(buffer, 0, bytesRead);
                }
            }
        }




        if (quizCategory == null || quizName == null || quizName.isEmpty()) {
            request.setAttribute("error", "Quiz name and Category are required!");
            request.getRequestDispatcher("CreateQuiz.jsp").forward(request, response);
            return;
        }

        List<Question> questions = new ArrayList<>();
        Account acc;

        try {
            SqlAccountDao accountStore = (SqlAccountDao) getServletContext().getAttribute("accounts_db");
            int accountId = Integer.parseInt((String) request.getSession().getAttribute("curUser"));
            acc = accountStore.getAccountById(accountId);
        } catch (SQLException e) {
            request.setAttribute("error", "Error retrieving account information");
            request.getRequestDispatcher("CreateQuiz.jsp").forward(request, response);
            return;
        }
        int questionCounter = 1;
        while (true) {
            String questionType = request.getParameter("questionType" + questionCounter);
            String questionText = request.getParameter("questionText" + questionCounter);

            if (questionText == null || questionText.isEmpty() || questionType == null || questionType.isEmpty()) {
                break;
            }

            ArrayList<String> choices = new ArrayList<>();
            ArrayList<String> answers = new ArrayList<>();
            ArrayList<String> answerPhotos = new ArrayList<>();
            int answerCounter = 1;
            while (true) {
                String answer = request.getParameter("answer" + questionCounter + "_" + answerCounter);
                String choice = request.getParameter("choice" + questionCounter + "_" + answerCounter);
              //  System.out.println("answer" + questionCounter + "_" + answerCounter);
                if (answer == null || answer.isEmpty()) {
                    break;
                }
                if (questionType.equals("multipleChoice")) {
                    choices.add(answer);
                    if(choice!=null) {
                        answers.add(answer);
                    }

                }else {
                    answers.add(answer);
                }

         if(questionType.equals("multipleChoice")) {
                Part filePart1 = request.getPart("answerimage" + questionCounter + "_" + answerCounter);
                System.out.println("answerimage" + questionCounter + "_" + answerCounter);
            // System.out.println("answerimage" + 1 + "_" + 1);
             if (filePart1 != null && filePart1.getSize() > 0) {

                    String fileName = "answer_" + questionCounter + "_" + UUID.randomUUID().toString() + ".png";
                    String uploadDirr = getServletContext().getRealPath("/") + "photos/";
                    File file = new File(uploadDirr + fileName);
                    file.getParentFile().mkdirs(); // Ensure the photos directory exists
                    try (InputStream input = filePart1.getInputStream();
                         FileOutputStream output = new FileOutputStream(file)) {
                        byte[] buffer = new byte[1024];
                        int bytesRead;
                        while ((bytesRead = input.read(buffer)) != -1) {
                            output.write(buffer, 0, bytesRead);
                        }
                    }
                    answerPhotos.add(fileName);
                    // question.setPhotoPath(fileName);
                }else{
                    answerPhotos.add("");
                }
        }
                answerCounter++;
            }



            Question question;

            switch (questionType) {
                case "fill":
                    question = new QuestionFill(questionText, answers);
                    break;
                case "multipleResponse":
                    question = new QuestionMultipleResp(questionText, answers);
                    break;
                case "multipleChoice":
                    question = new QuestionMultipleChoice(questionText, choices, answers);
                    ((QuestionMultipleChoice) question).setAnswerPhotos(answerPhotos);
                    break;
                case "response":
                    question = new QuestionResponse(questionText, answers);
                    break;
                default:
                    request.setAttribute("error", "Invalid question type");
                    request.getRequestDispatcher("CreateQuiz.jsp").forward(request, response);
                    return;
            }

            Part filePart = request.getPart("questionImage"+questionCounter);
            if (filePart != null && filePart.getSize() > 0) {
                System.out.println("yy");

                String fileName =  "question_" + questionCounter + "_" + UUID.randomUUID().toString() + ".png";
                String uploadDirr = getServletContext().getRealPath("/") + "photos/";
                File file = new File(uploadDirr + fileName);
                file.getParentFile().mkdirs(); // Ensure the photos directory exists
                try (InputStream input = filePart.getInputStream();
                     FileOutputStream output = new FileOutputStream(file)) {
                    byte[] buffer = new byte[1024];
                    int bytesRead;
                    while ((bytesRead = input.read(buffer)) != -1) {
                        output.write(buffer, 0, bytesRead);
                    }
                }
                question.setPhotoPath( fileName);
            }

            questions.add(question);
            questionCounter++;
        }

        Quiz quiz = new Quiz(quizName, quizCategory, description, questions, acc, timer);
        quiz.setQuizPhoto(quizPhoto);
        quiz.setPoints(questions.size());
        try {
            quizDao.add(quiz);

            request.setAttribute("success", "New Quiz Created Successfully!");
        } catch (SQLException e) {
            request.setAttribute("error", "Error While Creating New Quiz");
            request.getRequestDispatcher("CreateQuiz.jsp").forward(request, response);
            return;
        }

        request.getRequestDispatcher("CreateQuiz.jsp").forward(request, response);
    }

}