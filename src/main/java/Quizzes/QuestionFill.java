package Quizzes;

import java.util.ArrayList;
import java.util.List;

public class QuestionFill implements Question{
    String question;
    List<String> answers;

    public QuestionFill(String question, ArrayList<String> answers){
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

    public List<String> getAnswer() {
        return answers;
    }


    public void setAnswer(List<String> answers) {
        this.answers = answers;
    }
}
