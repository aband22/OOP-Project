package Accounts;

import Quizzes.Quiz;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.sql.*;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class AccountInfoDaoTest {
    private static final String JDBC_URL = "jdbc:mysql://localhost:3306/enigma";
    private static final String DB_USER = "root";
    private static final String DB_PASSWORD = "";
    private static AccountInfoDao accountInfoDao;

    static Connection con;

    @BeforeAll
    public static void setup() throws SQLException {
        con = DriverManager.getConnection(JDBC_URL, DB_USER, DB_PASSWORD);
        accountInfoDao = new SqlAccountInfoDao(con);
    }

    @AfterAll
    public static void cleanup() throws SQLException {
        try (Connection conn = DriverManager.getConnection(JDBC_URL, DB_USER, DB_PASSWORD);
             Statement stmt = conn.createStatement()) {
            stmt.executeUpdate("DELETE FROM friends");
            stmt.executeUpdate("DELETE FROM quizzes");
            stmt.executeUpdate("DELETE FROM accounts");
        }
    }

    private static void createTestData() throws SQLException {
        try (Connection conn = DriverManager.getConnection(JDBC_URL, DB_USER, DB_PASSWORD);
             Statement stmt = conn.createStatement()) {

            ResultSet resultSet = stmt.executeQuery("SELECT COUNT(*) AS count FROM accounts WHERE account_id IN (1, 2)");
            resultSet.next();
            int count = resultSet.getInt("count");
            if (count == 0) {
                stmt.executeUpdate("INSERT INTO accounts (account_id, email_address, username, password_hash) " +
                        "VALUES (1, 'user1@example.com', 'User One', 'password1'), " +
                        "(2, 'user2@example.com', 'User Two', 'password2')");
            }

            stmt.executeUpdate("INSERT INTO friends (friendship_id, account_id, fr_account_id) " +
                    "VALUES (3, 1, 2) ON DUPLICATE KEY UPDATE friendship_id=VALUES(friendship_id)");
            stmt.executeUpdate("INSERT INTO friends (friendship_id, account_id, fr_account_id) " +
                    "VALUES (4, 2, 1) ON DUPLICATE KEY UPDATE friendship_id=VALUES(friendship_id)");
        }
    }

    @Test
    public void testAddFriend() throws SQLException {
        createTestData();
        int accountId = 1;
        int friendId = 2;
        assertTrue(accountInfoDao.isFriend(accountId, friendId));
    }

    @Test
    public void testRemoveFriend() throws SQLException {
        createTestData();
        int accountId = 1;
        int friendId = 2;

        assertDoesNotThrow(() -> {
            accountInfoDao.removeFriend(accountId, friendId);
        }, "Removing a friend should not throw SQLException");
        assertFalse(accountInfoDao.isFriend(accountId, friendId), "Friendship should be removed");
    }

    @Test
    public void testGetCreatedQuizzes() throws SQLException {
        createTestData();
        int accountId = 1;

        try (Statement stmt = con.createStatement()) {
            stmt.executeUpdate("INSERT INTO quizzes (quiz_id, account_id, quiz_title, quiz_category, quiz_creation_date, num_completed) " +
                    "VALUES (1, 1, 'Sample Quiz', 'This is a sample quiz', SYSDATE(), 0) " +
                    "ON DUPLICATE KEY UPDATE quiz_title=VALUES(quiz_title)");
        }

        List<Quiz> quizzes = accountInfoDao.getCreatedQuizzes(accountId);
        assertFalse(quizzes.isEmpty(), "Quizzes list should not be empty");
        assertEquals(1, quizzes.size(), "Quizzes list should contain exactly one quiz");
        assertEquals("Sample Quiz", quizzes.get(0).getTitle(), "The quiz title should match the inserted quiz");
    }

    @Test
    public void testGetAllFriendsId() throws SQLException {
        createTestData();
        int accountId = 1;
        List<Integer> friendsIds = accountInfoDao.getAllFriendsId(accountId);
        assertNotNull(friendsIds, "Friends IDs list should not be null");
        assertTrue(friendsIds.contains(2), "The friends IDs list should contain the ID 2");
    }

    @Test
    public void testIsFriend() throws SQLException {
        createTestData();
        int accountId = 1;
        int friendId = 2;
        assertTrue(accountInfoDao.isFriend(accountId, friendId), "The two accounts should be friends");
        accountInfoDao.removeFriend(accountId, friendId);
        assertFalse(accountInfoDao.isFriend(accountId, friendId), "The two accounts should not be friends after removal");
    }

    @Test
    public void testAddFriend2() {
        int accountId = 1;
        int friendId = 2;
        try {
            accountInfoDao.addFriend(accountId, friendId);

            assertTrue(accountInfoDao.isFriend(accountId, friendId), "Friendship should exist after adding.");
            assertTrue(accountInfoDao.isFriend(friendId, accountId), "Reverse friendship should exist after adding.");
        } catch (SQLException e) {
            fail("Exception thrown while adding friend: " + e.getMessage());
        }
    }
}
