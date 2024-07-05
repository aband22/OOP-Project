package Accounts;

public class Notification{
    public static final int WEBSITE = -1;

    public static final String FRIEND_REQUEST = "friend_request";
    public static final String CHALLENGE = "challenge";
    public static final String NEWS = "news";
    public static final String FRIEND_RESULT = "friend_result";
    public static final String FRIEND_ACHIEVEMENT = "friend_achievement";
    public static final String ACHIEVEMENT = "achievement";


    public static final String FRIEND_REQUEST_TEXT = "friend_request";
    public static final String CHALLENGE_TEXT = "challenge";
    public static final String NEWS_TEXT = "news";
    public static final String FRIEND_RESULT_TEXT = "friend_result";
    public static final String FRIEND_ACHIEVEMENT_TEXT = "friend_achievement";
    public static final String ACHIEVEMENT_TEXT = "achievement";


    private int id;
    private int acc_id;
    private int acc_from_id;
    private String type;
    private String text;
    public Notification(int acc_id, int acc_from_id, String type, String text){
        this.acc_id = acc_id;
        this.acc_from_id = acc_from_id;
        this.type = type;
        this.text = text;
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
