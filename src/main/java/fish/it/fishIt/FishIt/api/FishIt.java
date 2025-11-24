package fish.it.fishIt.FishIt.api;

import fish.it.fishIt.FishIt;
import fish.it.fishIt.data.FishData;
import fish.it.fishIt.data.FisherPlayer;
import fish.it.fishIt.data.PlayerStats;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Optional;
import java.util.UUID;

public class FishIt {

    private final FishIt plugin;

    public FishIt(FishIt plugin) {
        this.plugin = plugin;
    }

    public boolean isCustomFish(@Nullable ItemStack item) {
        if (item == null || !item.hasItemMeta()) {
            return false;
        }
        return plugin.getFishManager().isCustomFish(item);
    }

    @NotNull
    public Optional<FishData> getFishData(@Nullable ItemStack item) {
        if (!isCustomFish(item)) {
            return Optional.empty();
        }
        return plugin.getFishManager().getFishDataFromItem(item);
    }

    @NotNull
    public Optional<FishData> getFishData(@NotNull String fishName) {
        return Optional.ofNullable(plugin.getFishManager().getAllFish().get(fishName.toLowerCase()));
    }

    @NotNull
    public Optional<PlayerStats> getPlayerStats(@NotNull Player player) {
        return Optional.ofNullable(plugin.getPlayerDataManager().getPlayerStats(player.getUniqueId()));
    }
    
    @NotNull
    public Optional<PlayerStats> getPlayerStats(@NotNull UUID playerUUID) {
        return Optional.ofNullable(plugin.getPlayerDataManager().getPlayerStats(playerUUID));
    }

    public boolean giveFish(@NotNull Player player, @NotNull String fishName, int amount) {
        Optional<FishData> fishDataOpt = getFishData(fishName);
        if (fishDataOpt.isEmpty()) {
            return false;
        }

        ItemStack fishItem = plugin.getFishManager().createFishItem(fishDataOpt.get(), amount);
        player.getInventory().addItem(fishItem);
        return true;
    }

    public void addExperience(Player player, int exp) {
        plugin.getLevelSystem().addExperience(player, exp);
    }

    public void setLevel(Player player, int level) {
        FisherPlayer fisherPlayer = plugin.getPlayerDataManager().getPlayer(player.getUniqueId());
        if (fisherPlayer != null) {
            fisherPlayer.setLevel(level);
            fisherPlayer.setExperience(0);
            plugin.getPlayerDataManager().savePlayer(fisherPlayer);
        }
    }
}