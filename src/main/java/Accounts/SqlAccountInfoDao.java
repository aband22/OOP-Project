package Accounts;
import Accounts.Account;
import Accounts.AccountInfoDao;
import Quizzes.Quiz;
import java.sql.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static javax.ws.rs.HttpMethod.DELETE;

public class SqlAccountInfoDao implements AccountInfoDao {
    private Connection connection;

    public SqlAccountInfoDao(Connection connection) {
        this.connection = connection;
    }

    @Override
    public void addFriend(int accountId, int friendId) throws SQLException {
        String insertQuery = "INSERT INTO friends (account_id, fr_account_id, friendship_date) " +
                "VALUES (?, ?, SYSDATE())";
        int id = 0;
        PreparedStatement st = connection.prepareStatement(insertQuery, Statement.RETURN_GENERATED_KEYS);
        st.setInt(1, accountId);
        st.setInt(2, friendId);
        st.executeUpdate();

        ResultSet rs = st.getGeneratedKeys();
        rs.next();
        id = rs.getInt(1);

        String selectQuery = "SELECT friendship_date FROM friends WHERE friendship_id = ?";
        Timestamp timestamp = null;
        st = connection.prepareStatement(selectQuery);
        st.setInt(1, id);
        rs = st.executeQuery();
        rs.next();
        timestamp = rs.getTimestamp("friendship_date");
            //System.out.println("Friendship Date: " + timestamp);


        insertQuery = "INSERT INTO friends (account_id, fr_account_id, friendship_date) " +
                "VALUES (?, ?, ?)";
        st = connection.prepareStatement(insertQuery, Statement.RETURN_GENERATED_KEYS);
        st.setInt(2, accountId);
        st.setInt(1, friendId);
        st.setTimestamp(3, timestamp);
        st.executeUpdate();

    }

    @Override
    public List<Quiz> getCreatedQuizzes(int accountId) throws SQLException {
        List<Quiz> quizzes = new ArrayList<>();
        String query = "SELECT * FROM quizzes WHERE account_id = ?";
        PreparedStatement statement = connection.prepareStatement(query);
        statement.setString(1, String.valueOf(accountId));
        ResultSet rs = statement.executeQuery();
        while (rs.next()) {
            Quiz curr = new Quiz();
            curr.setTitle(rs.getString("quiz_title"));
            curr.setCategory(rs.getString("quiz_category"));
            curr.setCreationDate(rs.getTimestamp("quiz_creation_date"));
            curr.setId(rs.getInt("quiz_id"));
            curr.setQuizPhoto(rs.getString("quiz_photo"));
            quizzes.add(curr);
        }
        return quizzes;
    }

    @Override
    public List<Integer> getAllFriendsId(int accountId) throws SQLException {
        List<Integer> friends = new ArrayList<>();
        String query = "SELECT * FROM friends WHERE account_id = ? ORDER BY friendship_date DESC";
        PreparedStatement statement = connection.prepareStatement(query);
        statement.setString(1, String.valueOf(accountId));
        ResultSet rs = statement.executeQuery();
        while (rs.next()) {
            int friendId = rs.getInt("fr_account_id");
            friends.add(friendId);
        }
        return friends;
    }

    @Override
    public boolean isFriend(int accountId, int fr_accountId) throws SQLException {
        String query = "SELECT * FROM friends WHERE account_id = ? AND fr_account_id = ?";
        PreparedStatement st = connection.prepareStatement(query);
        st.setInt(1, accountId);
        st.setInt(2, fr_accountId);
        ResultSet rs = st.executeQuery();

        return rs.next();
    }

    @Override
    public void removeFriend(int accountId, int friendId) throws SQLException {
        String query = "DELETE FROM friends WHERE account_id = ? AND fr_account_id = ?";
        PreparedStatement st = connection.prepareStatement(query);
        st.setInt(1, accountId);
        st.setInt(2, friendId);
        st.execute();
        st = connection.prepareStatement(query);
        st.setInt(2, accountId);
        st.setInt(1, friendId);
        st.execute();
    }
}