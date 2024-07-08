package Quizzes;

import java.sql.Connection;
import java.util.Collections;
import java.util.List;

public class SqlQuizzesHistoryDao implements QuizzesHistoryDao{
    private Connection connection;

    public SqlQuizzesHistoryDao(Connection connection) {
        this.connection = connection;
    }

    @Override
    public void add(int quizId, int accountId) {

    }

    @Override
    public List<Integer> getDoneQuizzesId(int accountId) {
        return Collections.emptyList();
    }

    @Override
    public List<Integer> getScorersId(int accountId) {
        return Collections.emptyList();
    }

    @Override
    public int getScore(int quizId, int accountId) {
        return 0;
    }

    @Override
    public int getDate(int quizId, int accountId) {
        return 0;
    }
}
