package Quizzes;

import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.List;

public interface QuizzesHistoryDao {
    void add(int quizId, int accountId, int score) throws SQLException;
    List<Integer> getDoneQuizzesId(int accountId) throws SQLException;
    List<Integer> getScorersId(int quizId) throws SQLException;
    int getScore(int quizId, int accountId) throws SQLException;
    Timestamp getDate(int quizId, int accountId) throws SQLException;
    boolean hasDoneQuiz(int quizId, int accountId) throws SQLException;
}
