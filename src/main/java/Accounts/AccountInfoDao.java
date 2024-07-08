package Accounts;

import Quizzes.Quiz;

import java.sql.SQLException;
import java.util.List;

public interface AccountInfoDao {
    void addFriend(int accountId, int friendId) throws SQLException;
    List<Quiz> getCreatedQuizzes(int accountId) throws SQLException;
    List<Integer> getAllFriendsId(int accountId) throws SQLException;
    boolean isFriend(int accountId, int fr_accountId) throws SQLException;
    void removeFriend(int accountId, int friendId) throws SQLException;
}
