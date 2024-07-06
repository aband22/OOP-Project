package Accounts;

import java.sql.SQLException;
import java.util.List;

public interface AchievementDao {
    void addAchievement(Achievement ach) throws SQLException;
    List<Achievement> getAll(int accountId) throws SQLException;

}
