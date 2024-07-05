package Accounts;

import Accounts.Account;
import Accounts.AccountInfoDao;
import Quizzes.Quiz;

import java.sql.Connection;
import java.util.List;

public class SqlAccountInfoDao implements AccountInfoDao {
    public SqlAccountInfoDao(Connection instance) {

    }

    @Override
    public void addFriend(int accountId, int  friendId) {

    }

    @Override
    public List<Quiz> getCreatedQuizzes(int accountId) {
        return null;
    }

    @Override
    public List<Account> getAllFriends(int accountId) {
        return null;
    }

    @Override
    public boolean isFriend(int accountId, int fr_accountId) {
        return false;
    }
}
