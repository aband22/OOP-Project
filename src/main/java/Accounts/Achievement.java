package Accounts;
public class Achievement {
    int accountId;
    int challengerId;
    int quizId;
    int achievementId;
    String type;
    String text;
    public static final int NO_ID = -1;
    public static final String CREATED1 = "შექმენით ერთი ქვიზი";
    public static final String CREATED5 = "შექმენით ხუთი ქვიზი";

    public static final String CREATED10 = "შექმენით ათი ქვიზი";
    public static final String CREATED50 = "შექმენით ორმოცდაათი ქვიზი";
    public static final String CREATED100 = "შექმენით ასი ქვიზი";

    public static final String COMPLETED1 = "შეასრულეთ ერთი ქვიზი";
    public static final String COMPLETED5 = "შეასრულეთ ხუთი ქვიზი";

    public static final String COMPLETED10 = "შეასრულეთ ათი ქვიზი";

    public static final String COMPLETED50 = "შეასრულეთ ორმოცდაათი ქვიზი";
    public static final String COMPLETED100 = "შეასრულეთ ასი ქვიზი";

    public static final String CHALLANGEWON = "გამოგიწვიათ მეგობარმა და მოიგეთ";

    public static final String TOP1 = "გახვედით პირველ ადგილზე";
    public static final String TOP2 = "გახვედით მეორე ადგილზე";
    public static final String TOP3 = "გახვედით მესამე ადგილზე";
    public static final String TOP10 = "გახვედით პირველ ათეულში";

    public Achievement() {}

    public Achievement(int accountId, String type) {
        this.accountId = accountId;
        this.type = type;
        this.text = "";
        this.achievementId = NO_ID;
    }

    public int getAccountId() {
        return accountId;
    }

    public void setAccountId(int accountId) {
        this.accountId = accountId;
    }

    public int getAchievementId() {
        return achievementId;
    }

    public void setAchievementId(int achievementId) {
        this.achievementId = achievementId;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public int getQuizId() {
        return quizId;
    }

    public void setQuizId(int quizId) {
        this.quizId = quizId;
    }

    public int getChallengerId() {
        return challengerId;
    }

    public void setChallengerId(int challengerId) {
        this.challengerId = challengerId;
    }
}
