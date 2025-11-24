package fish.it.fishIt.listeners;

import fish.it.fishIt.FishIt;
import fish.it.fishIt.api.events.FishCaughtEvent;
import fish.it.fishIt.data.FishData;
import fish.it.fishIt.data.FisherPlayer;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerFishEvent;
import org.bukkit.inventory.ItemStack;

import java.util.Optional;
import java.util.UUID;

public class FishingListener implements Listener {

    private final FishIt plugin;

    public FishingListener(FishIt plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onPlayerFish(PlayerFishEvent event) {
        if (event.getState() != PlayerFishEvent.State.CAUGHT_FISH) {
            return;
        }

        Player player = event.getPlayer();
        UUID playerId = player.getUniqueId();

        Optional<FishData> fishDataOpt = plugin.getFishSystem().rollForFish(player);
        if (fishDataOpt.isEmpty()) {
            return;
        }

        FishData fishData = fishDataOpt.get();
        ItemStack fishItem = plugin.getFishManager().createFishItem(fishData);

        FishCaughtEvent fishCaughtEvent = plugin.getEventsAPI().callFishCaughtEvent(player, fishData, fishItem);
        if (fishCaughtEvent.isCancelled()) {
            event.setCancelled(true);
            return;
        }

        Item caughtEntity = (Item) event.getCaught();
        if (caughtEntity != null) {
            caughtEntity.setItemStack(fishCaughtEvent.getFishItem());
        }

        FisherPlayer fisherPlayer = plugin.getPlayerDataManager().getPlayer(playerId);
        plugin.getCollectionManager().catchFish(player, fishData);
        plugin.getQuestManager().progressQuest(player, "FISH", fishData.getId());
    }
}