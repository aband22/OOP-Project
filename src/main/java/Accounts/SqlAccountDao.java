package Accounts;

import java.sql.*;
import java.util.List;

public class SqlAccountDao implements AccountDao {

    Connection connection;
    public SqlAccountDao(Connection connection){
        this.connection = connection;
    }
    @Override
    public void add(Account acc) throws SQLException {
        PreparedStatement statement = connection.prepareStatement(
                "INSERT INTO accounts (email_address, username, password_hash, status_) " + "VALUES (?, ?, ?, ?);",
                Statement.RETURN_GENERATED_KEYS);
        statement.setString(1, acc.getEmail());
        statement.setString(2, acc.getUsername());
        statement.setString(3, acc.getPassword());
        statement.setString(4, "Amateur");
        statement.executeUpdate();

        ResultSet rs = statement.getGeneratedKeys();
        rs.next();
        acc.setId(rs.getInt(1));
    }

    @Override
    public List<Account> getAll() {
        return null;
    }

    @Override
    public String getAccPass(String email) throws SQLException {
        Statement statement = connection.createStatement();
        ResultSet rs = statement.executeQuery(
                "SELECT * FROM accounts Where email_address = " + '"' + email + '"'
        );
        String result = "";
        if(rs.next()){
            result = rs.getString(4);
        }
        return result;
    }

    @Override
    public String getUserName(String email) throws SQLException {
        Statement statement = connection.createStatement();
        ResultSet rs = statement.executeQuery(
                "SELECT * FROM accounts Where email_address = " + '"' + email + '"'
        );
        String result = "";
        if(rs.next()){
            result = rs.getString(3);
        }
        return result;
    }

    @Override
    public int getUserID(String email) throws SQLException {
        Statement statement = connection.createStatement();
        ResultSet rs = statement.executeQuery(
                "SELECT * FROM accounts Where email_address = " + '"' + email + '"'
        );
        int result = 0;
        if(rs.next()){
            result = rs.getInt(1);
        }
        return result;
    }

    @Override
    public boolean emailExist(String email) throws SQLException {
        Statement statement = connection.createStatement();
        ResultSet rs = statement.executeQuery(
                "SELECT * FROM accounts Where email_address = " + '"' + email + '"'
        );
        int count = 0;
        while(rs.next()){
            count++;
        }
        return count == 1;
    }

    @Override
    public String getNameById(int accountId) throws SQLException {
        String query = "SELECT username FROM accounts WHERE account_id = ?";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, accountId);
            try (ResultSet rs = statement.executeQuery()) {
                rs.next();
                return rs.getString("username");
            }
        }
    }

    @Override
    public Account getAccountById(int accountId) throws SQLException {
        String acc = "select * from accounts where account_id = ?";
        PreparedStatement statement = connection.prepareStatement(acc);
        statement.setInt(1,accountId);
        ResultSet rs = statement.executeQuery();
        rs.next();
        Account account  = new Account();
        account.setEmail(rs.getString("email_address"));
        account.setId(rs.getInt("account_id"));
        account.setUsername(rs.getString("username"));
        account.setPassword(rs.getString("password_hash"));
        account.setStatus(rs.getString("status_"));
        return account;
    }

    @Override
    public void editAccount(Account acc) throws SQLException {
       String st = "update accounts Set username = ?, password_hash = ? where account_id = ?";
       PreparedStatement statement = connection.prepareStatement(st);
       statement.setString(1, acc.getUsername());
       statement.setString(2, acc.getPassword());
       statement.setInt(3, acc.getId());
       statement.executeUpdate();

    }

    @Override
    public void setStatus(int accountId, String status) throws SQLException {
        String st = "update accounts Set status_ = ? where account_id = ?";
        PreparedStatement statement = connection.prepareStatement(st);
        statement.setString(1, status);
        statement.setInt(2, accountId);
        statement.executeUpdate();
    }
}
