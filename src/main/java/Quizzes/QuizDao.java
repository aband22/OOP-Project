package Quizzes;

import java.sql.SQLException;
import java.util.List;
public interface QuizDao {
    void add(Quiz quiz) throws SQLException;
    List<Quiz> getAll();
    List<Quiz> getQuizByCategory(String category);
    List<Quiz> getPopularQuizzes(int num);
    List<Quiz> getRecentQuizzes(int num);
    List<String> getQuizCategories();
    List<Quiz> getQuizzesFromSearch(String search);
}
