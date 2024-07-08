package Quizzes;

import java.sql.SQLException;
import java.util.List;
public interface QuizDao {
    void add(Quiz quiz) throws SQLException;
    List<Quiz> getQuizByCategory(String category) throws SQLException;
    List<Quiz> getPopularQuizzes(int num) throws SQLException;
    List<Quiz> getRecentQuizzes(int num) throws SQLException;
    List<String> getQuizCategories();
    List<Quiz> getQuizzesFromSearch(String search) throws SQLException;
    Quiz getQuizById(int id) throws SQLException;
}
