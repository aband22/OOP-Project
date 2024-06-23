package Accounts;

import Quizzes.Quiz;

import java.util.List;

public interface AccountInfoDao {
    void addFriend(int accountId);
    void addQuiz(Quiz quiz);
    List<Quiz> getCreatedQuizzes(int accountId);
    List<Account> getAllFriends(int accountId);
}
