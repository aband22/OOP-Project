package Accounts;

import org.junit.jupiter.api.*;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class NotificationTest {
    private Connection connection;
    private SqlNotificationDao notificationDao;

    private static final String JDBC_URL = "jdbc:mysql://localhost:3306/enigma";
    private static final String DB_USER = "root";
    private static final String DB_PASSWORD = "";

    @BeforeEach
    public void setUp() throws SQLException {
        // Connect to the actual database
        connection = DriverManager.getConnection(JDBC_URL, DB_USER, DB_PASSWORD);
        notificationDao = new SqlNotificationDao(connection);

        // Ensure the tables are created before each test
        createTables();
    }

    @AfterEach
    public void tearDown() throws SQLException {
        // Clean up after each test: drop tables and close connection
        dropTables();
        if (connection != null && !connection.isClosed()) {
            connection.close();
        }
    }

    @Test
    public void testAddNotification() throws SQLException {
        Notification notification = new Notification(1, 2, Notification.FRIEND_REQUEST, "Test friend request notification");

        // Add the notification
        notificationDao.add(notification);

        // Retrieve notifications for account 1
        List<Notification> notifications = notificationDao.getAll(1);

        // Verify the notification was added
        assertEquals(1, notifications.size());
        Notification retrievedNotification = notifications.get(0);
        assertEquals(notification.getAccId(), retrievedNotification.getAccId());
        assertEquals(notification.getFromId(), retrievedNotification.getFromId());
        assertEquals(notification.getType(), retrievedNotification.getType());
        assertEquals(notification.getText(), retrievedNotification.getText());
    }

    @Test
    public void testRemoveNotification() throws SQLException {
        Notification notification = new Notification(1, 2, Notification.FRIEND_REQUEST, "Test friend request notification");

        // Add the notification
        notificationDao.add(notification);

        // Remove the notification
        notificationDao.remove(notification);

        // Verify the notification was removed
        List<Notification> notifications = notificationDao.getAll(1);
        assertEquals(0, notifications.size());
    }

    @Test
    public void testGetAllNotifications() throws SQLException {
        Notification notification1 = new Notification(1, 2, Notification.FRIEND_REQUEST, "Test friend request notification 1");
        Notification notification2 = new Notification(1, 3, Notification.CHALLENGE, "Test challenge notification");

        // Add notifications
        notificationDao.add(notification1);
        notificationDao.add(notification2);

        // Retrieve all notifications for account 1
        List<Notification> notifications = notificationDao.getAll(1);

        // Verify the correct number of notifications
        assertEquals(2, notifications.size());

        // Verify the content of each notification
        Notification retrieved1 = notifications.get(0);
        assertEquals(notification1.getAccId(), retrieved1.getAccId());
        assertEquals(notification1.getFromId(), retrieved1.getFromId());
        assertEquals(notification1.getType(), retrieved1.getType());
        assertEquals(notification1.getText(), retrieved1.getText());

        Notification retrieved2 = notifications.get(1);
        assertEquals(notification2.getAccId(), retrieved2.getAccId());
        assertEquals(notification2.getFromId(), retrieved2.getFromId());
        assertEquals(notification2.getType(), retrieved2.getType());
        assertEquals(notification2.getText(), retrieved2.getText());
    }

    @Test
    public void testIsSentFriendNotification() throws SQLException {
        Notification notification = new Notification(1, 2, Notification.FRIEND_REQUEST, "Test friend request notification");

        // Add the notification
        notificationDao.add(notification);

        // Check if the notification exists for the recipient account
        assertTrue(notificationDao.isSentFriendNotification(2, 1));
        assertFalse(notificationDao.isSentFriendNotification(1, 3));
    }

    // Helper method to create necessary tables in the database
    private void createTables() throws SQLException {
        String createTableQuery = "CREATE TABLE IF NOT EXISTS notifications (" +
                "notification_id INT AUTO_INCREMENT PRIMARY KEY, " +
                "account_id INT NOT NULL, " +
                "from_account_id INT NOT NULL, " +
                "notification_type VARCHAR(255) NOT NULL, " +
                "notification_text VARCHAR(255) NOT NULL, " +
                "notification_date TIMESTAMP NOT NULL" +
                ")";
        connection.createStatement().executeUpdate(createTableQuery);
    }

    // Helper method to drop tables after tests are done
    private void dropTables() throws SQLException {
        String dropTableQuery = "DROP TABLE IF EXISTS notifications";
        connection.createStatement().executeUpdate(dropTableQuery);
    }
}