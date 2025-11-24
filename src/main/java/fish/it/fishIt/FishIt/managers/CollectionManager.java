package fish.it.fishIt.managers;

import fish.it.fishIt.FishIt;
import fish.it.fishIt.data.FisherPlayer;
import fish.it.fishIt.data.FishData;
import org.bukkit.entity.Player;

import java.util.UUID;

public class CollectionManager {

    private final FishIt plugin;

    public CollectionManager(FishIt plugin) {
        this.plugin = plugin;
    }

    public void catchFish(Player player, FishData fishData) {
        FisherPlayer fisherPlayer = plugin.getPlayerDataManager().getPlayer(player.getUniqueId());
        if (!fisherPlayer.hasCaughtFish(fishData.getId())) {
            fisherPlayer.addCaughtFish(fishData.getId());
            player.sendMessage(plugin.getMessageUtils().colorize("&aIkan baru ditangkap: " + fishData.getDisplayName() + "! Cek koleksi Anda."));

            int totalCaught = fisherPlayer.getCaughtFishCount();
            int totalFish = plugin.getFishManager().getAllFish().size();
            if (totalCaught == totalFish) {
                player.sendMessage(plugin.getMessageUtils().colorize("&6&lSELAMAT! Anda telah menangkap semua jenis ikan!"));
            }
        }
        fisherPlayer.addFishToStats(fishData.getId());
        plugin.getPlayerDataManager().savePlayer(player.getUniqueId());
    }

    public boolean hasCaughtFish(UUID playerId, String fishId) {
        FisherPlayer fisherPlayer = plugin.getPlayerDataManager().getPlayer(playerId);
        return fisherPlayer.hasCaughtFish(fishId);
    }
}