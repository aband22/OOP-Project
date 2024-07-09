package Quizzes;

import java.util.ArrayList;
import java.util.List;

public class QuestionMultipleChoice implements Question{
    List<String> choices;
    List<String> answers;
    String question;
    private String photo;
    private List<String> answerPhotos;

    public QuestionMultipleChoice(String question, List<String> choices, List<String> answers){
        this.question = question;
        this.answers = answers;
        this.choices = choices;
    }

    @Override
    public String getQuestion() {
        return question;
    }

    @Override
    public void setQuestion(String question) {
        this.question = question;
    }

    public List<String> getAnswers() {
        return answers;
    }

    @Override
    public String getQuestionType() {
        return "MultipleChoice";
    }

    @Override
    public void setPhotoPath(String s) {
        this.photo = s;
    }

    @Override
    public String getPhotoPath() {
        return photo;
    }


    public void setAnswerPhotos(List<String> answerPhotos) {
        this.answerPhotos = answerPhotos;
    }

    public List<String> getAnswerPhotos(){
        return answerPhotos;
    }

    @Override
    public void setAnswers(List<String> answers) {
        this.answers = answers;
    }


    public List<String> getChoices(){
        return choices;
    }

    public void setChoices(List<String> choices){
        this.choices = choices;
    }



}
