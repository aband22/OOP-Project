package Accounts;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class SqlNotificationDao implements NotificationDao{
    Connection connection;
    public SqlNotificationDao(Connection connection){
        this.connection = connection;
    }
    @Override
    public void add(Notification notification) throws SQLException {
        PreparedStatement statement = connection.prepareStatement(
                "INSERT INTO notifications (account_id, from_account_id, notification_type, notification_text, notification_date) " + "VALUES (?, ?, ?, ?, SYSDATE());",
                Statement.RETURN_GENERATED_KEYS);
        statement.setInt(1, notification.getAccId());
        statement.setInt(2, notification.getFromId());
        statement.setString(3, notification.getType());
        statement.setString(4, notification.getText());
        statement.executeUpdate();

        ResultSet rs = statement.getGeneratedKeys();
        rs.next();
        notification.setId(rs.getInt(1));
    }

    @Override
    public void remove(Notification notification) throws SQLException {
        Statement statement = connection.createStatement();
        statement.execute(
                "DELETE FROM notifications Where account_id = " + '"' + notification.getAccId() + '"' +
                        " AND from_account_id = " + '"' + notification.getFromId() + '"' +
                        " AND notification_type = " + '"' + Notification.FRIEND_REQUEST + '"'
        );
    }

    @Override
    public void remove(int notificationId) throws SQLException {
        Statement statement = connection.createStatement();
        statement.execute(
                "DELETE FROM notifications Where notification_id = " + '"' + notificationId + '"'
        );
    }

    @Override
    public List<Notification> getAll(int accountId) throws SQLException {
        Statement statement = connection.createStatement();
        ResultSet rs = statement.executeQuery(
                "SELECT * FROM notifications Where account_id = " + '"' + accountId + '"'
        );
        List<Notification> result = new ArrayList<>();
        while(rs.next()){
            Notification notification = new Notification(
                    rs.getInt(2),
                    rs.getInt(3),
                    rs.getString(4),
                    rs.getString(5)
            );
            notification.setCreationDate(rs.getTimestamp(6));
            notification.setId(rs.getInt(1));
            result.add(notification);
        }
        return result;
    }

    @Override
    public boolean isSentFriendNotification(int accountId, int to_accountId) throws SQLException {
        Statement statement = connection.createStatement();
        ResultSet rs = statement.executeQuery(
                "SELECT * FROM notifications Where account_id = " + '"' + to_accountId + '"' +
                        " AND from_account_id = " + '"' + accountId + '"' +
                        " AND notification_type = " + '"' + Notification.FRIEND_REQUEST + '"'
        );
        return rs.next();
    }
}
