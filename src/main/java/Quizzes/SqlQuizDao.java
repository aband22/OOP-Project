package Quizzes;

import java.sql.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class SqlQuizDao implements QuizDao{
    Connection connection;
    public SqlQuizDao(Connection connection){
        this.connection = connection;
    }
    @Override
    public void add(Quiz quiz) throws SQLException {
        PreparedStatement statement = connection.prepareStatement(
                "INSERT INTO quizzes (email_address, quiz_title, quiz_category, quiz_creation_date, num_completed) " + "VALUES (?, ?, ?, ?, ?);",
                Statement.RETURN_GENERATED_KEYS);
        statement.setString(1, quiz.getAccount().getEmail());
        statement.setString(2, quiz.getTitle());
        statement.setString(3, quiz.getCategory());
        statement.setString(4, "SYSDATE");
        statement.setInt(5, 0);
        statement.executeUpdate();

        ResultSet rs = statement.getGeneratedKeys();
        rs.next();
        quiz.setId(rs.getInt(1));
    }

    @Override
    public List<Quiz> getAll() {
        return new ArrayList<>();
    }

    @Override
    public List<Quiz> getQuizByCategory(String category) {
        return new ArrayList<>();
    }

    @Override
    public List<Quiz> getPopularQuizzes(int num) {
        return new ArrayList<>();
    }

    @Override
    public List<Quiz> getRecentQuizzes(int num) {
        return new ArrayList<>();
    }

    @Override
    public List<String> getQuizCategories() {
        List<String> r = new ArrayList<>();
        Collections.addAll(r, "სპორტი", "მუსიკა", "ხელოვნება", "ფილმები", "ისტორია", "გეოგრაფია", "ცნობილი ადამიანები", "ქვეყნები", "თეატრი", "თამაშები");
        return r;
    }

    @Override
    public List<Quiz> getQuizzesFromSearch(String search) {
        return new ArrayList<>();
    }
}
