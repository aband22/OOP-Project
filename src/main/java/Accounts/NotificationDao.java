package Accounts;

import java.sql.SQLException;
import java.util.List;

public interface NotificationDao {
    void add(Notification notification) throws SQLException;
    void remove(Notification notification) throws SQLException;
    void remove(int notificationId) throws SQLException;
    List<Notification> getAll(int accountId) throws SQLException;
    boolean isSentFriendNotification(int accountId, int to_accountId) throws SQLException;
}
