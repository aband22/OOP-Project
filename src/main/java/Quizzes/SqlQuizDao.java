package Quizzes;

import Accounts.Account;

import java.sql.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

public class SqlQuizDao implements QuizDao {
    private Connection connection;

    public SqlQuizDao(Connection connection) {
        this.connection = connection;
    }

    @Override
    public void add(Quiz quiz) throws SQLException {
        String insertQuizQuery = "INSERT INTO quizzes (account_id, quiz_title, quiz_category, quiz_creation_date, num_completed, quiz_timer, quiz_points, quiz_photo) " +
                "VALUES (?, ?, ?, SYSDATE(), ?,?,?,?)";
        String insertQuestionQuery = "INSERT INTO questions (quiz_id, question_text, question_type, question_photo) VALUES (?, ?, ?,?)";
        String insertAnswerQuery = "INSERT INTO answers (question_id, answer_text, is_correct,answer_photo) VALUES (?, ?, ?,?)";

        try (PreparedStatement insertQuizStmt = connection.prepareStatement(insertQuizQuery, Statement.RETURN_GENERATED_KEYS);
             PreparedStatement insertQuestionStmt = connection.prepareStatement(insertQuestionQuery, Statement.RETURN_GENERATED_KEYS);
             PreparedStatement insertAnswerStmt = connection.prepareStatement(insertAnswerQuery, Statement.RETURN_GENERATED_KEYS)) {

            insertQuizStmt.setInt(1, quiz.getAccount().getId());
            insertQuizStmt.setString(2, quiz.getTitle());
            insertQuizStmt.setString(3, quiz.getCategory());
            insertQuizStmt.setInt(4, 0);
            insertQuizStmt.setString(5, quiz.getTimer());
            insertQuizStmt.setInt(6, quiz.getPoints());
            insertQuizStmt.setString(7,quiz.getQuizPhoto());

            insertQuizStmt.executeUpdate();

            int quizId;
            try (ResultSet rs = insertQuizStmt.getGeneratedKeys()) {
                if (rs.next()) {
                    quizId = rs.getInt(1);
                } else {
                    throw new SQLException("Failed to insert quiz, no ID obtained.");
                }
            }

            for (Question question : quiz.getQuestions()) {
                insertQuestionStmt.setInt(1, quizId);
                insertQuestionStmt.setString(2, question.getQuestion());
                insertQuestionStmt.setString(3, question.getQuestionType());
                insertQuestionStmt.setString(4,question.getPhotoPath());
                insertQuestionStmt.executeUpdate();

                //System.out.println(question.getPhotoPath());

                int questionId;
                try (ResultSet questionKeys = insertQuestionStmt.getGeneratedKeys()) {
                    if (questionKeys.next()) {
                        questionId = questionKeys.getInt(1);
                    } else {
                        throw new SQLException("Failed to insert question, no ID obtained.");
                    }
                }

                if (question instanceof QuestionResponse) {
                    String answer =  question.getAnswers().get(0);
                    insertAnswerStmt.setInt(1, questionId);
                    insertAnswerStmt.setString(2, answer);
                    insertAnswerStmt.setBoolean(3, true);
                    insertAnswerStmt.setString(4,"");
                    insertAnswerStmt.executeUpdate();
                } else if (question instanceof QuestionFill) {
                    List<String> answers = question.getAnswers();
                    for (String answer : answers) {
                        insertAnswerStmt.setInt(1, questionId);
                        insertAnswerStmt.setString(2, answer);
                        // System.out.println(answer);
                        insertAnswerStmt.setBoolean(3, true);
                        insertAnswerStmt.setString(4,"");
                        insertAnswerStmt.executeUpdate();
                    }
                } else if (question instanceof QuestionMultipleChoice) {
                    //   System.out.println( ((QuestionMultipleChoice) question).getAnswerPhotos().get(1));
                    List<String> choices = ((QuestionMultipleChoice) question).getChoices();
                    List<String> answers = question.getAnswers();
                    int answercount=0;
                    for (String choice : choices) {
                        insertAnswerStmt.setInt(1, questionId);
                        insertAnswerStmt.setString(2, choice);
                        insertAnswerStmt.setBoolean(3, answers.contains(choice));
                        insertAnswerStmt.setString(4, ((QuestionMultipleChoice) question).getAnswerPhotos().get(answercount));
                        insertAnswerStmt.executeUpdate();
                        answercount++;
                    }
                } else if (question instanceof QuestionMultipleResp) {
                    List<String> answers = question.getAnswers();
                    for (String answer : answers) {
                        insertAnswerStmt.setInt(1, questionId);
                        insertAnswerStmt.setString(2, answer);
                        insertAnswerStmt.setBoolean(3, true);
                        insertAnswerStmt.setString(4,"");
                        insertAnswerStmt.executeUpdate();
                    }
                }
            }
        }
//        Quiz quiz25 = getQuizById(7);
//        if (quiz25 != null) {
//            System.out.println("Quiz ID: " + quiz25.getId());
//            System.out.println("Title: " + quiz25.getTitle());
//            System.out.println("Category: " + quiz25.getCategory());
//            System.out.println("Creation Date: " + quiz25.getCreationDate());
//            System.out.println("Timer: " + quiz25.getTimer());
//            System.out.println("Number Completed: " + quiz25.getNumCompleted());
//
//            for (Question question : quiz25.getQuestions()) {
//                System.out.println("Question: " + question.getQuestion());
//                for (String answer : question.getAnswers()) {
//                    System.out.println("Answer: " + answer);
//                }
//            }
//        } else {
//            System.out.println("Quiz with ID 25 not found.");
//        }
    }

    private Quiz getQuiz(ResultSet rs) throws SQLException {
        Quiz curr = new Quiz();
        curr.setTitle(rs.getString("quiz_title"));
        curr.setCategory(rs.getString("quiz_category"));
        curr.setCreationDate(rs.getTimestamp("quiz_creation_date"));
        curr.setTimer(rs.getString("quiz_timer"));
        curr.setNumCompleted(rs.getInt("num_completed"));
        curr.setQuizPhoto(rs.getString("quiz_photo"));
        Account curAccount = new Account();
        int accountId = rs.getInt("account_id");
        int quizId = rs.getInt("quiz_id");
        String query = "SELECT username from accounts WHERE account_id = ?";
        try (PreparedStatement st = connection.prepareStatement(query)) {
            st.setInt(1, accountId);
            try (ResultSet result = st.executeQuery()) {
                while (result.next()) {
                    curAccount.setId(accountId);
                    curAccount.setUsername(result.getString("username"));
                }
            }
        }
        curr.setId(quizId);
        curr.setAccount(curAccount);
        List<Question> questions = getQuizQuestions(quizId);
        curr.setQuestions(questions);

//        this.questions = questions != null ? questions : new ArrayList<Question>();
        return curr;
    }

    private List<Question> getQuizQuestions(int quizId) throws SQLException {
        List<Question> quests = new ArrayList<>();
        String query = "SELECT * FROM questions where quiz_id = ?";
        PreparedStatement statement = connection.prepareStatement(query);
        statement.setInt(1, quizId);
        ResultSet rs = statement.executeQuery();
        while(rs.next()){
            String type = rs.getString("question_type");
            //Question question = null;
            if(Objects.equals(type, "Response")){
                List<String> answers = getQuestionAnswers(rs.getInt("question_id"),false);
                Question question=new QuestionResponse(rs.getString("question_text"), answers);
                question.setPhotoPath(rs.getString("question_photo"));
                quests.add(question);
            } else if (Objects.equals(type, "Fill")) {
                List<String> answers = getQuestionAnswers(rs.getInt("question_id"), false);
                Question question=new QuestionFill(rs.getString("question_text"), answers);
                question.setPhotoPath(rs.getString("question_photo"));
                quests.add(question);
            }else if (Objects.equals(type, "MultipleResponse")) {
                List<String> answers = getQuestionAnswers(rs.getInt("question_id"), false);
                Question question=new QuestionMultipleResp(rs.getString("question_text"), answers);
                question.setPhotoPath(rs.getString("question_photo"));
                quests.add(question);
            }else if (Objects.equals(type, "MultipleChoice")) {
                List<String> answers = getQuestionAnswers(rs.getInt("question_id"), true);
                List<String> choices = getQuestionAnswers(rs.getInt("question_id"), false);
                List<String> answersPhotos = getQuestionAnswersPhotos(rs.getInt("question_id"));
                QuestionMultipleChoice question=new QuestionMultipleChoice(rs.getString("question_text"),choices, answers);
                question.setPhotoPath(rs.getString("question_photo"));
                question.setAnswerPhotos(answersPhotos);
                quests.add(question);
            }

        }

        return quests;
    }

    private List<String> getQuestionAnswers(int questionId, boolean onlyCorrect) throws SQLException {
        if (questionId <= 0) {
            throw new IllegalArgumentException("Invalid question ID.");
        }

        String query = "SELECT answer_text, is_correct FROM answers WHERE question_id = ?";
        List<String> answers = new ArrayList<>();

        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, questionId);
            try (ResultSet rs = statement.executeQuery()) {
                while (rs.next()) {
                    if (!onlyCorrect || rs.getBoolean("is_correct")) {
                        answers.add(rs.getString("answer_text"));
                    }
                }
            }
        }
        return answers;
    }

    private List<String> getQuestionAnswersPhotos(int questionId) throws SQLException {
        if (questionId <= 0) {
            throw new IllegalArgumentException("Invalid question ID.");
        }
        String query = "SELECT answer_photo FROM answers WHERE question_id = ?";
        List<String> photos = new ArrayList<>();

        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, questionId);
            try (ResultSet rs = statement.executeQuery()) {
                while (rs.next()) {
                    photos.add(rs.getString("answer_photo"));

                }
            }
        }
        return photos;
    }

    @Override
    public List<Quiz> getQuizByCategory(String category) throws SQLException {
        List<Quiz> curQuizzes = new ArrayList<>();
        String query = "SELECT * FROM quizzes WHERE quiz_category = ?";
        PreparedStatement statement = connection.prepareStatement(query);
        statement.setString(1, category);
        ResultSet result = statement.executeQuery();
        while (result.next()) {
            curQuizzes.add(getQuiz(result));
        }
        return curQuizzes;
    }

    @Override
    public List<Quiz> getPopularQuizzes(int num) throws SQLException {
        List<Quiz> popularQuizzes = new ArrayList<>();
        String query = "SELECT * FROM quizzes ORDER BY num_completed DESC LIMIT ?";
        PreparedStatement statement = connection.prepareStatement(query);
        statement.setInt(1, num);
        ResultSet result = statement.executeQuery();
        while (result.next()) {
            popularQuizzes.add(getQuiz(result));
        }
        return popularQuizzes;
    }

    @Override
    public List<Quiz> getRecentQuizzes(int num) throws SQLException {
        List<Quiz> recentQuizzes = new ArrayList<>();
        String query = "SELECT * FROM quizzes ORDER BY quiz_creation_date DESC LIMIT ?";
        PreparedStatement statement = connection.prepareStatement(query);
        statement.setInt(1, num);
        ResultSet result = statement.executeQuery();
        while (result.next()) {
            recentQuizzes.add(getQuiz(result));
        }
        return recentQuizzes;
    }

    @Override
    public Quiz getQuizById(int id) throws SQLException {
        String query = "SELECT * FROM quizzes WHERE quiz_id = ?";
        PreparedStatement statement = connection.prepareStatement(query);
        statement.setInt(1, id);

        ResultSet resultSet = statement.executeQuery();
        resultSet.next();
        return getQuiz(resultSet);
    }

    @Override
    public List<String> getQuizCategories() {
        List<String> categories = new ArrayList<>();
        Collections.addAll(categories, "სპორტი", "მუსიკა", "ხელოვნება", "ფილმები", "ისტორია", "გეოგრაფია",
                "ცნობილი ადამიანები", "ქვეყნები", "თეატრი", "თამაშები");
        return categories;
    }

    @Override
    public List<Quiz> getQuizzesFromSearch(String search) throws SQLException {
        List<Quiz> searchResults = new ArrayList<>();
        String query = "SELECT * FROM quizzes WHERE quiz_title LIKE ?";
        PreparedStatement statement = connection.prepareStatement(query);
        statement.setString(1, "%" + search + "%");
        ResultSet result = statement.executeQuery();
        while (result.next()) {
            searchResults.add(getQuiz(result));
        }

        return searchResults;
    }
}