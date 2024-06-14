package Quizzes;

import Accounts.Account;

import java.util.ArrayList;
import java.util.List;

public class Quiz {
    private String title;
    private String category;
    private long creationDate;
    private List<Question> questions;
    private Account acc;

    public Quiz(String title, String category, long creationDate, List<Question> questions, Account acc) {
        this.title = title;
        this.category = category;
        this.creationDate = creationDate;
        this.questions = questions != null ? questions : new ArrayList<Question>();
        this.acc = acc;
    }

    public Quiz() {
        this.questions = new ArrayList<>();
    }

    public long getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(long creationDate) {
        this.creationDate = creationDate;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public List<Question> getQuestions() {
        return questions;
    }

    public void setQuestions(List<Question> questions) {
        this.questions = questions != null ? questions : new ArrayList<Question>();
    }

    public Account getAccount() { return acc; }

    public void setAccount(Account acc) { this.acc = acc; }
    @Override
    public String toString() {
        return "Quiz{" +
                "title='" + title + '\'' +
                ", category='" + category + '\'' +
                ", creationDate=" + creationDate +
                ", questions=" + questions +
                '}';
    }
}
