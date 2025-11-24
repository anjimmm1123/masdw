package fish.it.fishIt.listeners;

import fish.it.fishIt.FishIt;
import fish.it.fishIt.data.FisherPlayer;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.UUID;

public class PlayerJoinListener implements Listener {

    private final FishIt plugin;

    public PlayerJoinListener(FishIt plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        UUID playerId = player.getUniqueId();

        FisherPlayer fisherPlayer = plugin.getPlayerDataManager().getPlayer(playerId);
        if (fisherPlayer == null) {
            plugin.getPlayerDataManager().createPlayer(playerId);
        }

        if (plugin.getQuestManager().getActiveQuest(playerId) == null) {
            LocalDate lastQuestDate = fisherPlayer.getLastQuestDate();
            LocalDate today = LocalDate.now(ZoneId.systemDefault());
            if (lastQuestDate == null || !lastQuestDate.equals(today)) {
                plugin.getQuestManager().assignDailyQuest(player);
                fisherPlayer.setLastQuestDate(today);
                plugin.getPlayerDataManager().savePlayer(playerId);
            }
        }
    }
}