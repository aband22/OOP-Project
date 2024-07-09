package Quizzes;

import org.junit.jupiter.api.*;

import java.sql.*;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class HistoryTest {
    private static Connection connection;
    private QuizzesHistoryDao quizzesHistoryDao;

    @BeforeAll
    static void setUp() throws SQLException {
        // Establish a connection to your database
        String url = "jdbc:mysql://localhost:3306/enigma";
        String user = "root";
        String password = "";
        connection = DriverManager.getConnection(url, user, password);
    }

    @BeforeEach
    void init() throws SQLException {
        // Initialize the DAO with the SQL implementation
        quizzesHistoryDao = new SqlQuizzesHistoryDao(connection);
        QuizDao quizzes = new QuizDao() {
            @Override
            public void add(Quiz quiz) throws SQLException {

            }

            @Override
            public List<Quiz> getQuizByCategory(String category) throws SQLException {
                return null;
            }

            @Override
            public List<Quiz> getPopularQuizzes(int num) throws SQLException {
                return null;
            }

            @Override
            public List<Quiz> getRecentQuizzes(int num) throws SQLException {
                return null;
            }

            @Override
            public List<String> getQuizCategories() {
                return null;
            }

            @Override
            public List<Quiz> getQuizzesFromSearch(String search) throws SQLException {
                return null;
            }

            @Override
            public Quiz getQuizById(int id) throws SQLException {
                return null;
            }
        }
        clearData();
    }

    @AfterAll
    static void tearDown() throws SQLException {
        // Close the connection after all tests are done
        if (connection != null) {
            connection.close();
        }
    }

    private void clearData() throws SQLException {
        // Optionally implement a method to clear data in your tables between tests
        Statement statement = connection.createStatement();
        statement.executeUpdate("DELETE FROM quizzeshistory");
    }

    @Test
    void testAddAndGetDoneQuizzesId() {
        try {
            // Assuming accounts with ID 1 and quizzes with ID 1 and 2 exist in the database
            quizzesHistoryDao.add(1, 1, 80);
            quizzesHistoryDao.add(2, 1, 90);

            List<Integer> doneQuizzesId = quizzesHistoryDao.getDoneQuizzesId(1);
            assertEquals(2, doneQuizzesId.size(), "Should retrieve 2 done quizzes for account 1");
            assertTrue(doneQuizzesId.contains(1), "Should contain quiz ID 1");
            assertTrue(doneQuizzesId.contains(2), "Should contain quiz ID 2");
        } catch (SQLException e) {
            fail("Exception thrown: " + e.getMessage());
        }
    }

    @Test
    void testGetScorersId() {
        try {
            // Assuming accounts with ID 1 and 2 and quiz with ID 1 exist in the database
            quizzesHistoryDao.add(1, 1, 80);
            quizzesHistoryDao.add(1, 2, 85);

            List<Integer> scorersId = quizzesHistoryDao.getScorersId(1);
            assertEquals(2, scorersId.size(), "Should retrieve 2 scorers for quiz 1");
            assertTrue(scorersId.contains(1), "Should contain account ID 1");
            assertTrue(scorersId.contains(2), "Should contain account ID 2");
        } catch (SQLException e) {
            fail("Exception thrown: " + e.getMessage());
        }
    }

    @Test
    void testGetScore() {
        try {
            // Assuming account with ID 1 and quiz with ID 1 exist in the database
            quizzesHistoryDao.add(1, 1, 80);

            int score = quizzesHistoryDao.getScore(1, 1);
            assertEquals(80, score, "Should retrieve score 80 for quiz 1 and account 1");
        } catch (SQLException e) {
            fail("Exception thrown: " + e.getMessage());
        }
    }

    @Test
    void testGetDate() {
        try {
            // Assuming account with ID 1 and quiz with ID 1 exist in the database
            quizzesHistoryDao.add(1, 1, 80);

            Timestamp now = new Timestamp(System.currentTimeMillis());
            Timestamp date = quizzesHistoryDao.getDate(1, 1);
            assertNotNull(date, "Date should not be null");
            assertTrue(date.before(now), "Date should be before current time");
        } catch (SQLException e) {
            fail("Exception thrown: " + e.getMessage());
        }
    }

    @Test
    void testHasDoneQuiz() {
        try {
            // Assuming account with ID 1 and quiz with ID 2 exist in the database
            quizzesHistoryDao.add(1, 1, 80);

            boolean hasDone = quizzesHistoryDao.hasDoneQuiz(1, 1);
            assertTrue(hasDone, "Quiz 2 should be marked as done for account 1");
        } catch (SQLException e) {
            fail("Exception thrown: " + e.getMessage());
        }
    }
}