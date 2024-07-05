package Accounts;

import java.sql.SQLException;
import java.util.List;

public interface NotificationDao {
    void add(Notification notification) throws SQLException;
    void remove(Notification notification);
    void remove(int notificationId);
    List<Notification> getAll(int accountId) throws SQLException;
    boolean isSentFriendNotification(int accountId, int to_accountId);
}
