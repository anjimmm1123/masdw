package fish.it.fishIt.database;

public final class Queries {

    private Queries() {}

    public static final String CREATE_FISHER_PLAYERS_TABLE_SQLITE =
            "CREATE TABLE IF NOT EXISTS fisher_players (" +
            "player_id TEXT PRIMARY KEY," +
            "level INTEGER NOT NULL DEFAULT 1," +
            "experience INTEGER NOT NULL DEFAULT 0," +
            "collection TEXT" +
            ");";

    public static final String CREATE_FISHER_PLAYERS_TABLE_MYSQL =
            "CREATE TABLE IF NOT EXISTS fisher_players (" +
            "player_id VARCHAR(36) PRIMARY KEY," +
            "level INT NOT NULL DEFAULT 1," +
            "experience INT NOT NULL DEFAULT 0," +
            "collection TEXT" +
            ");";

    public static final String CREATE_PLAYER_STATS_TABLE_SQLITE =
            "CREATE TABLE IF NOT EXISTS player_stats (" +
            "player_id TEXT PRIMARY KEY," +
            "total_fish_caught INTEGER NOT NULL DEFAULT 0," +
            "total_earnings REAL NOT NULL DEFAULT 0.0," +
            "biggest_catch INTEGER NOT NULL DEFAULT 0," +
            "fish_caught_map TEXT" +
            ");";

    public static final String CREATE_PLAYER_STATS_TABLE_MYSQL =
            "CREATE TABLE IF NOT EXISTS player_stats (" +
            "player_id VARCHAR(36) PRIMARY KEY," +
            "total_fish_caught INT NOT NULL DEFAULT 0," +
            "total_earnings DOUBLE NOT NULL DEFAULT 0.0," +
            "biggest_catch INT NOT NULL DEFAULT 0," +
            "fish_caught_map TEXT" +
            ");";

    public static final String CREATE_PLAYER_IF_NOT_EXISTS =
            "INSERT OR IGNORE INTO fisher_players (player_id) VALUES (?);";

    public static final String CREATE_PLAYER_STATS_IF_NOT_EXISTS =
            "INSERT OR IGNORE INTO player_stats (player_id) VALUES (?);";

    public static final String SELECT_PLAYER =
            "SELECT level, experience FROM fisher_players WHERE player_id = ?;";

    public static final String SELECT_PLAYER_STATS =
            "SELECT total_fish_caught, total_earnings, biggest_catch, fish_caught_map FROM player_stats WHERE player_id = ?;";

    public static final String UPDATE_PLAYER_LEVEL =
            "UPDATE fisher_players SET level = ?, experience = ? WHERE player_id = ?;";

    public static final String UPDATE_PLAYER_EXPERIENCE =
            "UPDATE fisher_players SET experience = ? WHERE player_id = ?;";

    public static final String UPDATE_PLAYER_STATS =
            "UPDATE player_stats SET total_fish_caught = ?, total_earnings = ?, biggest_catch = ?, fish_caught_map = ? WHERE player_id = ?;";

    public static final String UPDATE_PLAYER_COLLECTION =
            "UPDATE fisher_players SET collection = ? WHERE player_id = ?;";
}