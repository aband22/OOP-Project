package Accounts;

import java.sql.SQLException;
import java.util.List;

public interface AccountDao {
    void add(Account acc) throws SQLException;
    String getAccPass(String email) throws SQLException;
    String getUserName(String email) throws SQLException;
    int getUserID(String email) throws SQLException;
    boolean emailExist(String email) throws SQLException;
    Account GetAccountById(int accountId) throws SQLException;
    String getNameById(int accountId) throws SQLException;
}
