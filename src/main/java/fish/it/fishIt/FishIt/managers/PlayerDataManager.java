package fish.it.fishIt.managers;

import fish.it.fishIt.FishIt;
import fish.it.fishIt.data.FisherPlayer;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class PlayerDataManager {

    private final FishIt plugin;
    private final Map<UUID, FisherPlayer> players = new HashMap<>();

    public PlayerDataManager(FishIt plugin) {
        this.plugin = plugin;
    }

    public FisherPlayer getPlayer(UUID playerId) {
        if (players.containsKey(playerId)) {
            return players.get(playerId);
        }
        return loadPlayer(playerId);
    }

    private FisherPlayer loadPlayer(UUID playerId) {
        File playerFile = new File(plugin.getDataFolder() + "/playerdata", playerId + ".yml");
        if (!playerFile.exists()) {
            FisherPlayer newPlayer = new FisherPlayer(playerId);
            savePlayer(newPlayer);
            players.put(playerId, newPlayer);
            return newPlayer;
        }

        FisherPlayer fisherPlayer = FisherPlayer.fromFile(playerFile);
        players.put(playerId, fisherPlayer);
        return fisherPlayer;
    }

    public void savePlayer(UUID playerId) {
        if (players.containsKey(playerId)) {
            savePlayer(players.get(playerId));
        }
    }

    public void savePlayer(FisherPlayer player) {
        File playerFile = new File(plugin.getDataFolder() + "/playerdata", player.getPlayerId() + ".yml");
        if (!playerFile.getParentFile().exists()) {
            playerFile.getParentFile().mkdirs();
        }
        try {
            player.toFile(playerFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void saveAllPlayers() {
        players.values().forEach(this::savePlayer);
    }
}