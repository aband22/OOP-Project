package Accounts;

import Accounts.Notification;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class SqlAchievementDao implements AchievementDao{
    Connection connection;
    public SqlAchievementDao(Connection connection){
        this.connection = connection;
    }
    @Override
    public void addAchievement(Achievement ach) throws SQLException {
        PreparedStatement statement = connection.prepareStatement(
                "INSERT INTO achievements (achievement_id, account_id, achievement_type, achievement_text) " + "VALUES (?, ?, ?, ?);");
        statement.setInt(1, ach.getAchievementId());
        statement.setInt(2, ach.getAccountId());
        statement.setString(3, ach.getType());
        statement.setString(4, ach.getText());
        statement.executeUpdate();

        ResultSet rs = statement.getGeneratedKeys();
        rs.next();
        ach.setAchievementId(rs.getInt(1));
    }

    @Override
    public List<Achievement> getAll(int accountId) throws SQLException {
        Statement statement = connection.createStatement();
        ResultSet rs = statement.executeQuery(
                "SELECT * FROM achievements Where account_id = " + '"' + accountId + '"'
        );
        List<Achievement> result = new ArrayList<>();
        while(rs.next()){
            Achievement ach = new Achievement(
                    rs.getInt(2),
                    rs.getString(3)
            );
            ach.setAchievementId(rs.getInt(1));
            result.add(ach);
        }
        return result;
    }
}
