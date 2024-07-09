package Quizzes;

import Accounts.Achievement;
import Accounts.AchievementDao;
import Accounts.SqlAchievementDao;
import org.junit.jupiter.api.*;

import java.sql.*;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class AchievementTest {
    private static Connection connection;
    private AchievementDao achievementDao;

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
        achievementDao = new SqlAchievementDao(connection);

        // Clean up any existing data before each test (if needed)
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
        statement.executeUpdate("DELETE FROM achievements");
    }

    @Test
    void testAddAchievement() {
        try {
            Achievement achievement = new Achievement(1, "Completed a quiz");

            achievementDao.addAchievement(achievement);

            assertNotNull(achievement.getAchievementId(), "Achievement ID should be set after insertion");

            List<Achievement> achievements = achievementDao.getAll(1);
            assertEquals(1, achievements.size(), "Should retrieve 1 achievement for account 1");
            assertEquals(achievement, achievements.get(0), "Retrieved achievement should match inserted achievement");
        } catch (SQLException e) {
            fail("Exception thrown: " + e.getMessage());
        }
    }

    @Test
    void testGetAllAchievements() {
        try {
            // Insert multiple achievements into the database
            Achievement achievement1 = new Achievement(1, "Completed a quiz");
            Achievement achievement2 = new Achievement(2, "Achieved first place in a quiz");
            achievementDao.addAchievement(achievement1);
            achievementDao.addAchievement(achievement2);

            // Retrieve all achievements for account 1
            List<Achievement> achievements = achievementDao.getAll(1);

            assertEquals(2, achievements.size(), "Should retrieve 2 achievements for account 1");
            assertTrue(achievements.contains(achievement1), "Achievements should contain the first inserted achievement");
            assertTrue(achievements.contains(achievement2), "Achievements should contain the second inserted achievement");
        } catch (SQLException e) {
            fail("Exception thrown: " + e.getMessage());
        }
    }
}