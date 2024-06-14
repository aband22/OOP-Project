package Quizzes;

public class QuestionResponse implements Question{
    String answer;
    String question;
    public QuestionResponse(String question, String answer){
        this.question = question;
        this.answer = answer;
    }

    @Override
    public String getQuestion() {
        return question;
    }

    @Override
    public void setQuestion(String question) {
        this.question  = question;
    }


    public String getAnswer() {
        return answer;
    }


    public void setAnswer(String answers) {
        this.answer = answers;
    }
}
