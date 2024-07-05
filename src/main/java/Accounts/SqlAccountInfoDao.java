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
    public void addFriend(int accountId) {

    }

    @Override
    public void addQuiz(Quiz quiz) {

    }

    @Override
    public List<Quiz> getCreatedQuizzes(int accountId) {
        return null;
    }

    @Override
    public List<Account> getAllFriends(int accountId) {
        return null;
    }
}
