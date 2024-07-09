package Quizzes;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class QuestionMultipleResp implements Question{
    String question;
    List<String> answers;
    private String photo;

    public QuestionMultipleResp(String question, List<String> answers){
        this.question = question;
        this.answers = answers;
    }

    @Override
    public String getQuestion() {
        return question;
    }

    @Override
    public void setQuestion(String question) {
        this.question = question;
    }

    public List<String> getAnswers(){
        return answers;
    }

    @Override
    public String getQuestionType() {
        return "MultipleResponse";
    }

    @Override
    public void setPhotoPath(String s) {
        this.photo = s;
    }
    @Override

    public String getPhotoPath() {
        return photo;
    }


    @Override
    public void setAnswers(List<String> answers){
        this.answers = answers;
    }


}
