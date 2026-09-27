package me.EHOT_2025.easyQuests.database;

import me.EHOT_2025.easyQuests.EasyQuests;

import java.io.File;
import java.sql.*;
import java.util.UUID;

public class DatabaseManager {
    private Connection connection;

    public void connect() {
        File dataFolder = EasyQuests.getInstance().getDataFolder();
        if (!dataFolder.exists()) {
            dataFolder.mkdirs();
        }

        File dbFile = new File(dataFolder, "database.db");
        String url = "jdbc:sqlite:" + dbFile.getAbsolutePath();

        try {
            connection = DriverManager.getConnection(url);
            EasyQuests.getInstance().getLogger().info("База данных подключена");
            createTables();
        } catch (SQLException e) {
            EasyQuests.getInstance().getLogger().severe("Не удалось подключить базу данных!");
        }
    }

    private void createTables() {
        String sqlNpcs = "CREATE TABLE IF NOT EXISTS quest_npcs (" +
                "uuid TEXT PRIMARY KEY," +
                "name TEXT NOT NULL);";

        try (Statement statement = connection.createStatement()) {
            statement.execute(sqlNpcs);
            EasyQuests.getInstance().getLogger().info("Таблица quest_npcs успешно проверена/создана!");
        } catch (SQLException e) {
            EasyQuests.getInstance().getLogger().severe("Ошибка создания таблиц: " + e.getMessage());
        }
    }

    public void saveQuestNpc(UUID uuid, String name) {
        String sql = "INSERT OR REPLACE INTO quest_npcs (uuid, name) VALUES (?, ?);";
        try (PreparedStatement pstmt = connection.prepareStatement(sql)){
            pstmt.setString(1, uuid.toString());
            pstmt.setString(2, name);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            EasyQuests.getInstance().getLogger().severe("Ошибка при сохранении NPC " + e.getMessage());
        }
    }

    public boolean isQuestNpc(String uuid) {
        String sql = "SELECT 1 FROM quest_npcs WHERE uuid = ?;";
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setString(1, uuid);
            try (ResultSet rs = pstmt.executeQuery()) {
                return rs.next();
            }
        } catch (SQLException e) {
            EasyQuests.getInstance().getLogger().severe("Ошибка при проверке NPC: " + e.getMessage());
            return false;
        }
    }

    public void disconnect() {
        try {
            if (connection != null && !connection.isClosed()) {
                connection.close();
                EasyQuests.getInstance().getLogger().info("Разорвано соединение с базой данных");
            }
        } catch (SQLException e) {
            EasyQuests.getInstance().getLogger().severe("Ошибка при закрытии базы данных: " + e.getMessage());
        }
    }
}
