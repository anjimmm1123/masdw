package fish.it.fishIt.database;

import fish.it.fishIt.FishIt;

import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class SQLite extends Database {

    public SQLite(FishIt plugin) {
        super(plugin);
        try {
            File dataFolder = plugin.getDataFolder();
            if (!dataFolder.exists()) {
                dataFolder.mkdirs();
            }
            File databaseFile = new File(dataFolder, "database.db");
            if (!databaseFile.exists()) {
                databaseFile.createNewFile();
            }
            Class.forName("org.sqlite.JDBC");
            this.connection = DriverManager.getConnection("jdbc:sqlite:" + databaseFile.getAbsolutePath());
            plugin.getLogger().info("Koneksi ke SQLite berhasil.");
        } catch (SQLException | ClassNotFoundException | IOException e) {
            plugin.getLogger().severe("Tidak dapat terhubung ke database SQLite: " + e.getMessage());
            e.printStackTrace();
        }
    }

    @Override
    public Connection getConnection() throws SQLException {
        if (connection == null || connection.isClosed()) {
            plugin.getLogger().warning("Koneksi SQLite ditutup atau tidak ada. Mencoba membuka kembali...");
            File databaseFile = new File(plugin.getDataFolder(), "database.db");
            this.connection = DriverManager.getConnection("jdbc:sqlite:" + databaseFile.getAbsolutePath());
        }
        return connection;
    }

    @Override
    public void createTables() {
        executeUpdate(Queries.CREATE_FISHER_PLAYERS_TABLE_SQLITE);
        executeUpdate(Queries.CREATE_PLAYER_STATS_TABLE_SQLITE);
        plugin.getLogger().info("Tabel SQLite berhasil dibuat/diperiksa.");
    }
}