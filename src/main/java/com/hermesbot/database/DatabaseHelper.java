package com.hermesbot.database;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import static com.hermesbot.Main.logger;

public class DatabaseHelper {
    private static final String DB_URL = "jdbc:sqlite:reminders.db"; // SQLite database file
    private static Connection connection;

    public static Connection getConnection() throws SQLException {
        if (connection == null) {
            connection = DriverManager.getConnection(DB_URL);
        }
        logger.info("✅ Connected to SQLite database!");
        return connection;
    }

    public static void initialize() {
        try (Connection conn = getConnection()) {
            String createTableSQL = "CREATE TABLE IF NOT EXISTS reminders (" +
                    "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    "user_id TEXT NOT NULL, " +
                    "time INTEGER NOT NULL, " +
                    "message TEXT NOT NULL)";
            try (Statement stmt = conn.createStatement()) {
                stmt.execute(createTableSQL);
                logger.info("✅ Table 'reminders' created or already exists.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void insertReminder(String userId, long time, String message) {
        String sql = "INSERT INTO reminders(user_id, time, message) VALUES(?, ?, ?)";
        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, userId);
            pstmt.setLong(2, time);
            pstmt.setString(3, message);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static List<Reminder> getAllReminders() {
        List<Reminder> reminders = new ArrayList<>();
        String sql = "SELECT * FROM reminders WHERE time <= ?";
        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setLong(1, System.currentTimeMillis());
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                String userId = rs.getString("user_id");
                long time = rs.getLong("time");
                String message = rs.getString("message");
                reminders.add(new Reminder(userId, time, message));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return reminders;
    }

    public static void deleteReminder(int id) {
        String sql = "DELETE FROM reminders WHERE id = ?";
        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, id);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
