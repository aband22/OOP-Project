package Quizzes;

import java.sql.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class SqlQuizzesHistoryDao implements QuizzesHistoryDao{
    private Connection connection;

    public SqlQuizzesHistoryDao(Connection connection) {
        this.connection = connection;
    }

    @Override
    public void add(int quizId, int accountId, int score) throws SQLException {
        PreparedStatement statement = connection.prepareStatement(
                "INSERT INTO accounts (quiz_id, account_id, score, done_date) " + "VALUES (?, ?, ?, SYSDATE());",
                Statement.RETURN_GENERATED_KEYS);
        statement.setInt(1, quizId);
        statement.setInt(2, accountId);
        statement.setInt(3, score);
        statement.executeUpdate();
    }

    @Override
    public List<Integer> getDoneQuizzesId(int accountId) throws SQLException {
        Statement statement = connection.createStatement();
        ResultSet rs = statement.executeQuery(
                "SELECT count(*) FROM quizzesHistory Where account_id = " + '"' + accountId + '"' + "ORDER BY done_date DESC"
        );
        int num = rs.getInt(1);
        return getDoneQuizzesId(accountId, num);
    }
    public List<Integer> getDoneQuizzesId(int accountId, int num) throws SQLException {
        List<Integer> quizIds = new ArrayList<Integer>();
        Statement statement = connection.createStatement();
        ResultSet rs = statement.executeQuery(
                "SELECT * FROM quizzesHistory Where account_id = " + '"' + accountId + '"' + "ORDER BY done_date DESC" + "Limit" + num
        );
        while (rs.next()) {
            quizIds.add(rs.getInt("quiz_id"));
        }
        return quizIds;
    }

    @Override
    public List<Integer> getScorersId(int quizId) throws SQLException {
        List<Integer> accountIds = new ArrayList<Integer>();
        Statement statement = connection.createStatement();
        ResultSet rs = statement.executeQuery(
                "SELECT * FROM quizzesHistory Where quiz_id = " + '"' + quizId + '"' + "ORDER BY score DESC"
        );
        while (rs.next()) {
            accountIds.add(rs.getInt("quiz_id"));
        }
        return accountIds;
    }

    @Override
    public int getScore(int quizId, int accountId) throws SQLException {
        Statement statement = connection.createStatement();
        ResultSet rs = statement.executeQuery(
                "SELECT score FROM quizzesHistory Where quiz_id = " + '"' + quizId + '"' + "AND account_id = " + '"' + accountId + '"'
        );
        rs.next();
        return rs.getInt(1);
    }

    @Override
    public Timestamp getDate(int quizId, int accountId) throws SQLException {
        Statement statement = connection.createStatement();
        ResultSet rs = statement.executeQuery(
                "SELECT done_date FROM quizzesHistory Where quiz_id = " + '"' + quizId + '"' + "AND account_id = " + '"' + accountId + '"'
        );
        rs.next();
        return rs.getTimestamp(1);
    }
}
