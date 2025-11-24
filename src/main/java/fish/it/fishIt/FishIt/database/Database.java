package fish.it.fishIt.database;

import fish.it.fishIt.FishIt;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public abstract class Database {

    protected final FishIt plugin;
    protected Connection connection;

    public Database(FishIt plugin) {
        this.plugin = plugin;
    }

    public abstract Connection getConnection() throws SQLException;

    public abstract void createTables();

    public void close() {
        if (connection != null) {
            try {
                if (!connection.isClosed()) {
                    connection.close();
                }
            } catch (SQLException e) {
                plugin.getLogger().severe("Gagal menutup koneksi database: " + e.getMessage());
            }
        }
    }

    protected void executeUpdate(String query) {
        try (PreparedStatement statement = getConnection().prepareStatement(query)) {
            statement.executeUpdate();
        } catch (SQLException e) {
            plugin.getLogger().severe("Gagal menjalankan query: " + query);
            e.printStackTrace();
        }
    }
}