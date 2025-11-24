package fish.it.fishIt.database;

import fish.it.fishIt.FishIt;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class MySQL extends Database {

    private final String host;
    private final String port;
    private final String database;
    private final String username;
    private final String password;

    public MySQL(FishIt plugin) {
        super(plugin);
        this.host = plugin.getConfig().getString("database.mysql.host");
        this.port = plugin.getConfig().getString("database.mysql.port");
        this.database = plugin.getConfig().getString("database.mysql.database");
        this.username = plugin.getConfig().getString("database.mysql.username");
        this.password = plugin.getConfig().getString("database.mysql.password");

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            this.connection = DriverManager.getConnection("jdbc:mysql://" + host + ":" + port + "/" + database + "?useSSL=false", username, password);
            plugin.getLogger().info("Koneksi ke MySQL berhasil.");
        } catch (SQLException | ClassNotFoundException e) {
            plugin.getLogger().severe("Tidak dapat terhubung ke database MySQL: " + e.getMessage());
            e.printStackTrace();
        }
    }

    @Override
    public Connection getConnection() throws SQLException {
        if (connection == null || connection.isClosed()) {
            plugin.getLogger().warning("Koneksi MySQL ditutup atau tidak ada. Mencoba membuka kembali...");
            this.connection = DriverManager.getConnection("jdbc:mysql://" + host + ":" + port + "/" + database