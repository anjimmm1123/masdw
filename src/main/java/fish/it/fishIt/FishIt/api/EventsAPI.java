package fish.it.fishIt.api;

import fish.it.fishIt.FishIt;
import fish.it.fishIt.api.events.FishCaughtEvent;
import fish.it.fishIt.api.events.FishSellEvent;
import fish.it.fishIt.data.FishData;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

import java.util.Map;

public class EventsAPI {

    private final FishIt plugin;

    public EventsAPI(FishIt plugin) {
        this.plugin = plugin;
    }

    /**
     * Memanggil {@link FishCaughtEvent}.
     *
     * @param player
     * @param fishData
     * @param fishItem
     * @return
     */
    @NotNull
    public FishCaughtEvent callFishCaughtEvent(@NotNull Player player, @NotNull FishData fishData, @NotNull ItemStack fishItem) {
        FishCaughtEvent event = new FishCaughtEvent(player, fishData, fishItem);
        plugin.getServer().getPluginManager().callEvent(event);
        return event;
    }

    /**
     * Memanggil {@link FishSellEvent}.
     *
     * @param player Pemain yang menjual ikan.
     * @param fishSold Peta ikan yang dijual beserta jumlahnya.
     * @param totalEarnings Total penghasilan awal.
     * @return Instance dari FishSellEvent setelah dipanggil, bisa di-check apakah dibatalkan.
     */
    @NotNull
    public FishSellEvent callFishSellEvent(@NotNull Player player, @NotNull Map<FishData, Integer> fishSold, double totalEarnings) {
        FishSellEvent event = new FishSellEvent(player, fishSold, totalEarnings);
        plugin.getServer().getPluginManager().callEvent(event);
        return event;
    }
}