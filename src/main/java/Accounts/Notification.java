package Accounts;

import java.sql.Timestamp;

public class Notification{
    public static final int WEBSITE = -1;

    public static final String FRIEND_REQUEST = "მეგობრობის მოთხოვნა";
    public static final String CHALLENGE = "გამოწვევა";
    public static final String NEWS = "ახალი ქვიზი";
    public static final String FRIEND_RESULT = "მეგობრის ახალი შედეგი";
    public static final String FRIEND_ACHIEVEMENT = "მეგობრის ახალი მიღწევა";
    public static final String ACHIEVEMENT = "მიღწევა";


    public static final String FRIEND_REQUEST_TEXT = "-მა(მ) გამოგიგზავნათ მეგობრობის მოთხოვნა";
    public static final String CHALLENGE_TEXT = "-მა(მ) გამოგიწვიათ ქვიზში";
    public static final String NEWS_TEXT = "-მა(მ) ახალი ქვიზი შექმნა";
    public static final String FRIEND_RESULT_TEXT = "-მა(მ) დაასრულა ქვიზი, იხილეთ მისი შედეგები";
    public static final String FRIEND_ACHIEVEMENT_TEXT = "-მა(მ) მიიღო ახალი სტატუსი: ";
    public static final String ACHIEVEMENT_TEXT = "თქვენ მიიღეთ ახალი სტატუსი: ";


    private int id;
    private int acc_id;
    private int acc_from_id;
    private Timestamp creationDate;
    private String type;
    private String text;
    public Notification(int acc_id, int acc_from_id, String type, String text){
        this.acc_id = acc_id;
        this.acc_from_id = acc_from_id;
        this.type = type;
        this.text = text;
        this.creationDate = null;
        this.id = -1;
    }

    public int getId() {
        return id;
    }

    public int getAccId() {
        return acc_id;
    }

    public int getFromId() {
        return acc_from_id;
    }

    public String getType() {
        return type;
    }

    public String getText() {
        return text;
    }

    public Timestamp getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Timestamp creationDate) {
        this.creationDate = creationDate;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setAccId(int acc_id) {
        this.acc_id = acc_id;
    }
    public void setFromId(int acc_from_id) {
        this.acc_from_id = acc_from_id;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setText(String text) {
        this.text = text;
    }
}
