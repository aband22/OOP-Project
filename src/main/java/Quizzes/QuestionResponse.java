package Quizzes;

import java.util.ArrayList;
import java.util.List;

public class QuestionResponse implements Question{
    List<String> answers;
    String question;
    private String photo;

    public QuestionResponse(String question, List<String> answers){
        this.question = question;
        this.answers = answers;
    }

    @Override
    public String getQuestion() {
        return question;
    }

    @Override
    public void setQuestion(String question) {
        this.question  = question;
    }


    @Override
    public List<String> getAnswers() {
        return answers;
    }

    @Override
    public String getPhotoPath() {
        return photo;
    }

    @Override
    public void setAnswerPhotos(ArrayList<String> answerPhotos) {

    }

    @Override
    public String getQuestionType() {
        return "Response";
    }

    @Override
    public void setPhotoPath(String s) {
        this.photo = s;
    }


    @Override
    public void setAnswers(List<String> answers) {
        this.answers = answers;
    }
}
