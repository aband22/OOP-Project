package Accounts;

import Quizzes.Quiz;

import java.util.List;

public interface AccountInfoDao {
    void addFriend(int accountId, int friendId);
    List<Quiz> getCreatedQuizzes(int accountId);
    List<Integer> getAllFriendsId(int accountId);
    boolean isFriend(int accountId, int fr_accountId);
}
