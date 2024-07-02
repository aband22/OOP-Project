package Accounts;

import java.sql.*;
import java.util.List;

import static java.sql.DriverManager.getConnection;


public class SqlAccountDao implements AccountDao {

    Connection connection;
    public SqlAccountDao(Connection connection){
        this.connection = connection;
    }
    @Override
    public void add(Account acc) throws SQLException {
        PreparedStatement statement = connection.prepareStatement(
                "INSERT INTO accounts (email_address, username, password_hash) " + "VALUES (?, ?, ?);",
                Statement.RETURN_GENERATED_KEYS);
        statement.setString(1, acc.getEmail());
        statement.setString(2, acc.getUsername());
        statement.setString(3, acc.getPassword());
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
    public Account GetAccountById(int accountId) throws SQLException {
        String acc = "select * from accounts where account_id = ?";
        PreparedStatement statement = connection.prepareStatement(acc);
        statement.setInt(1,accountId);
        ResultSet rs = statement.executeQuery();
        if(rs.next()){
            Account account  = new Account();
            account.setEmail(rs.getString("email_address"));
            account.setId(rs.getInt("account_id"));
            account.setUsername(rs.getString("username"));
            account.setPassword(rs.getString("password_hash"));
            return account;

        }
        return null;
    }

    public void editAccount(Account acc){
        String st = "update accounts Set username = ?, email_address = ? where account_id = ?";
        try(PreparedStatement statement = connection.prepareStatement(st)){
            statement.setString(1, acc.getUsername());
            statement.setString(2, acc.getPassword());
            statement.setInt(3, acc.getId());

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
