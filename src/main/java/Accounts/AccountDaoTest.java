package Accounts;

import org.junit.jupiter.api.*;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.*;

public class AccountDaoTest {

    private static final String JDBC_URL = "jdbc:mysql://localhost:3306/enigma";
    private static final String USERNAME = "root";
    private static final String PASSWORD = "";

    private static Connection connection;
    private static SqlAccountDao accountDao;

    @BeforeAll
    public static void setUp() throws SQLException {
        connection = DriverManager.getConnection(JDBC_URL, USERNAME, PASSWORD);
        accountDao = new SqlAccountDao(connection);
    }

    @AfterEach
    public void cleanUp() throws SQLException {
        // Clean up after each test
        connection.prepareStatement("DELETE FROM accounts WHERE email_address = 'test@example.com'").executeUpdate();
    }

    @AfterAll
    public static void tearDown() throws SQLException {
        // Close connection after all tests
        if (connection != null) {
            connection.close();
        }
    }

    @Test
    public void testAddAccount() throws SQLException {
        Account testAccount = new Account("test1@example.com", "password123", "testuser");

        // Add account
        accountDao.add(testAccount);

        // Retrieve account from database by email
        String retrievedPassword = accountDao.getAccPass("test1@example.com");

        assertEquals("password123", retrievedPassword, "Password should match added account");
    }

    @Test
    public void testGetAccountById() throws SQLException {
        // Add a test account
        Account testAccount = new Account("test@example.com", "password123", "testuser");
        accountDao.add(testAccount);

        // Get the ID of the added account
        int accountId = accountDao.getUserID("test@example.com");

        // Retrieve account by ID
        Account retrievedAccount = accountDao.getAccountById(accountId);

        assertEquals("test@example.com", retrievedAccount.getEmail(), "Email should match");
        assertEquals("testuser", retrievedAccount.getUsername(), "Username should match");
        assertEquals("password123", retrievedAccount.getPassword(), "Password should match");
    }

    @Test
    public void testEditAccount() throws SQLException {
        // Add a test account
        Account testAccount = new Account("test@example.com", "password123", "testuser");
        accountDao.add(testAccount);

        // Get the ID of the added account
        int accountId = accountDao.getUserID("test@example.com");

        // Update account details
        testAccount.setId(accountId);
        testAccount.setUsername("newusername");
        testAccount.setPassword("newpassword");

        accountDao.editAccount(testAccount);

        // Retrieve updated account
        Account updatedAccount = accountDao.getAccountById(accountId);

        assertEquals("newusername", updatedAccount.getUsername(), "Username should be updated");
        assertEquals("newpassword", updatedAccount.getPassword(), "Password should be updated");
    }

    @Test
    public void testEmailExist() throws SQLException {
        // Add a test account
        Account testAccount = new Account("test@example.com", "password123", "testuser");
        accountDao.add(testAccount);

        // Check if email exists
        boolean emailExists = accountDao.emailExist("test@example.com");

        assertTrue(emailExists, "Email should exist in the database");
    }

    @Test
    public void testGetAccPass() throws SQLException {
        // Add a test account
        Account testAccount = new Account("test@example.com", "password123", "testuser");
        accountDao.add(testAccount);

        // Retrieve account password by email
        String retrievedPassword = accountDao.getAccPass("test@example.com");

        assertEquals("password123", retrievedPassword, "Password should match");
    }

    @Test
    public void testGetUserName() throws SQLException {
        // Add a test account
        Account testAccount = new Account("test@example.com", "password123", "testuser");
        accountDao.add(testAccount);

        // Retrieve account username by email
        String retrievedUsername = accountDao.getUserName("test@example.com");

        assertEquals("testuser", retrievedUsername, "Username should match");
    }

    @Test
    public void testGetUserID() throws SQLException {
        // Add a test account
        Account testAccount = new Account("test@example.com", "password123", "testuser");
        accountDao.add(testAccount);

        // Retrieve account ID by email
        int retrievedId = accountDao.getUserID("test@example.com");

        assertNotEquals(0, retrievedId, "User ID should not be zero");
    }

    @Test
    public void testGetNameById() throws SQLException {
        // Add a test account
        Account testAccount = new Account("test@example.com", "password123", "testuser");
        accountDao.add(testAccount);

        // Get the ID of the added account
        int accountId = accountDao.getUserID("test@example.com");

        // Retrieve account name by ID
        String retrievedName = accountDao.getNameById(accountId);

        assertEquals("testuser", retrievedName, "Name should match");
    }
}
