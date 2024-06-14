package Quizzes;

import java.util.Set;

public class QuestionMultipleResp implements Question{
    String question;
    Set<String> answers;
    public QuestionMultipleResp(String question, Set<String> answers){
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

    public Set<String> getAnswers(){
        return answers;
    }

    public void setAnswers(Set<String> answers){
        this.answers = answers;
    }


}
