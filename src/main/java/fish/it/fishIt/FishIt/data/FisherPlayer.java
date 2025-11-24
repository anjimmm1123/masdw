package fish.it.fishIt.data;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.configuration.serialization.ConfigurationSerializable;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public class FisherPlayer {
    private final UUID playerId;
    private int level;
    private int experience;
    private final PlayerStats stats;
    private final Map<String, Boolean> collection;

    public FisherPlayer(UUID playerId) {
        this.playerId = playerId;
        this.level = 1;
        this.experience = 0;
        this.stats = new PlayerStats();
        this.collection = new HashMap<>();
    }

    public UUID getPlayerId() {
        return playerId;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public int getExperience() {
        return experience;
    }

    public void setExperience(int experience) {
        this.experience = experience;
    }

    public PlayerStats getStats() {
        return stats;
    }

    public Map<String, Boolean> getCollection() {
        return collection;
    }

    public boolean hasCaughtFish(String fishId) {
        return collection.containsKey(fishId);
    }

    public void addCaughtFish(String fishId) {
        collection.put(fishId, true);
    }

    public void addFishToStats(String fishId, int size) {
        stats.addFishCaught(fishId, size);
    }

    public void addEarnings(double amount) {
        stats.addEarnings(amount);
    }

    public void toFile(File file) throws IOException {
        FileConfiguration config = YamlConfiguration.loadConfiguration(file);
        config.set("player_id", playerId.toString());
        config.set("level", level);
        config.set("experience", experience);
        config.set("stats.total_fish_caught", stats.getTotalFishCaught());
        config.set("stats.total_earnings", stats.getTotalEarnings());
        config.set("stats.biggest_catch", stats.getBiggestCatch());
        config.set("stats.fish_caught_map", stats.getFishCaught());
        config.set("collection", collection.keySet());
        config.save(file);
    }

    public static FisherPlayer fromFile(File file) {
        FileConfiguration config = YamlConfiguration.loadConfiguration(file);
        UUID playerId = UUID.fromString(config.getString("player_id"));
        FisherPlayer fisherPlayer = new FisherPlayer(playerId);
        fisherPlayer.setLevel(config.getInt("level"));
        fisherPlayer.setExperience(config.getInt("experience"));
        fisherPlayer.getStats().setTotalFishCaught(config.getInt("stats.total_fish_caught"));
        fisherPlayer.getStats().setTotalEarnings(config.getDouble("stats.total_earnings"));
        fisherPlayer.getStats().setBiggestCatch(config.getInt("stats.biggest_catch"));

        Map<String, Integer> fishCaughtMap = new HashMap<>();
        if (config.contains("stats.fish_caught_map")) {
            config.getConfigurationSection("stats.fish_caught_map").getKeys(false).forEach(key -> {
                fishCaughtMap.put(key, config.getInt("stats.fish_caught_map." + key));
            });
        }
        fishCaughtMap.forEach(fisherPlayer.getStats().getFishCaught()::put);

        List<String> collectionList = config.getStringList("collection");
        collectionList.forEach(fishId -> fisherPlayer.getCollection().put(fishId, true));

        return fisherPlayer;
    }
}