package Accounts;

import java.sql.Connection;
import java.util.List;

public class SqlNotificationDao implements NotificationDao{
    Connection connection;
    public SqlNotificationDao(Connection connection){
        this.connection = connection;
    }
    @Override
    public void add(Notification notification) {

    }

    @Override
    public void remove(Notification notification) {

    }

    @Override
    public void remove(int notificationId) {

    }

    @Override
    public List<Notification> getAll(int accountId) {
        return null;
    }

    @Override
    public boolean isSentFriendNotification(int accountId, int to_accountId) {
        return false;
    }
}
