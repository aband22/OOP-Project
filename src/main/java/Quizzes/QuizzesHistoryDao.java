package Quizzes;

import java.util.List;

public interface QuizzesHistoryDao {
    void add(int quizId, int accountId);
    List<Integer> getDoneQuizzesId(int accountId);
    List<Integer> getScorersId(int accountId);
    int getScore(int quizId, int accountId);
    int getDate(int quizId, int accountId);
}
