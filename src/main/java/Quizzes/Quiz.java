package Quizzes;

import Accounts.Account;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

public class Quiz {
    private static final int NO_ID = -1;
    private int id;
    private String title;
    private String category;
    private Timestamp creationDate;
    private List<Question> questions;
    private Account acc;

    public Quiz(String title, String category, List<Question> questions, Account acc) {
        this.title = title;
        this.category = category;
        this.creationDate = null;
        this.questions = questions != null ? questions : new ArrayList<Question>();
        this.acc = acc;
        this.id = NO_ID;
    }

    public Quiz() {
        this.questions = new ArrayList<>();
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public Timestamp getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Timestamp creationDate) {
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
