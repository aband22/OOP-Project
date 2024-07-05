package Accounts;

import java.util.List;

public interface NotificationDao {
    void add(Notification notification);
    void remove(Notification notification);
    void remove(int notificationId);
    List<Notification> getAll(int accountId);
    boolean isSentFriendNotification(int accountId, int to_accountId);
}
