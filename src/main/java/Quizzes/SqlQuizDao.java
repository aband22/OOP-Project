package Quizzes;

import Accounts.Account;

import java.sql.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class SqlQuizDao implements QuizDao {
    private Connection connection;

    public SqlQuizDao(Connection connection) {
        this.connection = connection;
    }

    @Override
    public void add(Quiz quiz) throws SQLException {
        String query = "INSERT INTO quizzes (account_id, quiz_title, quiz_category, quiz_creation_date, num_completed) " +
                "VALUES (?, ?, ?, SYSDATE(), ?)";
        PreparedStatement st = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
        st.setInt(1, quiz.getAccount().getId());
        st.setString(2, quiz.getTitle());
        st.setString(3, quiz.getCategory());
        st.setInt(4, 0);
        st.executeUpdate();

    }

    private Quiz getQuiz(ResultSet rs) throws SQLException {
        Quiz curr = new Quiz();
        curr.setTitle(rs.getString("quiz_title"));
        curr.setCategory(rs.getString("quiz_category"));
        curr.setCreationDate(rs.getTimestamp("quiz_creation_date"));

        Account curAccount = new Account();
        int accountId = rs.getInt("account_id");
        int quizId = rs.getInt("quiz_id");
        String query = "SELECT username from accounts WHERE account_id = ?";
        PreparedStatement st = connection.prepareStatement(query);
        st.setInt(1, accountId);
        ResultSet result = st.executeQuery();
        while (result.next()) {
            curAccount.setId(accountId);
            curAccount.setUsername(result.getString("username"));
        }

        curr.setId(quizId);
        curr.setAccount(curAccount);
//        this.questions = questions != null ? questions : new ArrayList<Question>();
        return curr;
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