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
    public void addFriend(Account acc) {

    }

    @Override
    public void addQuiz(Quiz quiz) {

    }

    @Override
    public List<Quiz> getCreatedQuizzes(Account acc) {
        return null;
    }

    @Override
    public List<Account> getAllFriends(Account acc) {
        return null;
    }
}
