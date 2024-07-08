package Quizzes;

import java.util.ArrayList;
import java.util.List;

public interface Question {

    String getQuestion();

    void setQuestion(String question);


    List<String> getAnswers();
    public void setAnswers(List<String> answers);

    String getQuestionType();

    void setPhotoPath(String s);
    String getPhotoPath();

    void setAnswerPhotos(ArrayList<String> answerPhotos);
}