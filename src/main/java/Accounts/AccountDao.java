package Accounts;

import java.sql.SQLException;
import java.util.List;

public interface AccountDao {
    void add(Account acc) throws SQLException;
    List<Account> getAll();
    String getAccPass(String email) throws SQLException;
    String getUserName(String email) throws SQLException;
    int getUserID(String email) throws SQLException;
    boolean emailExist(String email) throws SQLException;
    String getNameById(int accountId)  throws SQLException;
    Account getAccountById(int accountId) throws SQLException;
    void editAccount(Account acc) throws SQLException;
    void setStatus(int accountId, String status) throws SQLException;
}
