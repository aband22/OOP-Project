package Quizzes;

import java.util.ArrayList;
import java.util.List;

public class QuestionMultipleChoice implements Question{
    List<String> choices;
    List<String> answers;
    String question;
    public QuestionMultipleChoice(String question, ArrayList<String> choices, ArrayList<String> answers){
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

    public List<String> getAnswer(String quizId, int ind) {
        return answers;
    }

    public void setAnswer(List<String> answers) {
        this.choices = choices;
    }

    public List<String> getChoices(){
        return choices;
    }

    public void setChoices(List<String> choices){
        this.choices = choices;
    }
}
