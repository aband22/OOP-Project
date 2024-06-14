package Accounts;

import Quizzes.Quiz;

import java.util.List;

public interface AccountInfoDao {
    void addFriend(Account acc);
    void addQuiz(Quiz quiz);
    List<Quiz> getCreatedQuizzes(Account acc);
    List<Account> getAllFriends(Account acc);
}
