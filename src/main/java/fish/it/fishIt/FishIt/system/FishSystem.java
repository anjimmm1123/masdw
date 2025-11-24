package fish.it.fishIt.system;

import fish.it.fishIt.FishIt;
import fish.it.fishIt.data.FishData;
import fish.it.fishIt.data.FisherPlayer;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class FishSystem {

    private final FishIt plugin;
    private final LootRollSystem lootRollSystem;
    private final LuckSystem luckSystem;
    private final LevelSystem levelSystem;
    private final BaitSystem baitSystem;
    private final WeatherSystem weatherSystem;
    private final RegionSystem regionSystem;
    private final CooldownSystem cooldownSystem;

    public FishSystem(FishIt plugin) {
        this.plugin = plugin;
        this.lootRollSystem = new LootRollSystem(plugin);
        this.luckSystem = new LuckSystem(plugin);
        this.levelSystem = new LevelSystem(plugin);
        this.baitSystem = new BaitSystem(plugin);
        this.weatherSystem = new WeatherSystem(plugin);
        this.regionSystem = new RegionSystem(plugin);
        this.cooldownSystem = new CooldownSystem(plugin);
    }

    public Optional<FishData> rollForFish(Player player) {
        UUID playerId = player.getUniqueId();

        if (cooldownSystem.isOnCooldown(playerId)) {
            return Optional.empty();
        }
        cooldownSystem.setCooldown(playerId);

        List<FishData> availableFish = regionSystem.getFishInPlayerRegion(player);
        if (availableFish.isEmpty()) {
            return Optional.empty();
        }

        double totalLuck = luckSystem.calculatePlayerLuck(player);
        totalLuck += weatherSystem.getWeatherLuckModifier(player.getWorld());
        totalLuck += baitSystem.getBaitLuckModifier(player.getInventory().getItemInMainHand());

        FishData caughtFish = lootRollSystem.rollFish(availableFish, totalLuck);

        if (caughtFish != null) {
            levelSystem.addExperience(player, caughtFish.getExperience());
            baitSystem.consumeBait(player.getInventory().getItemInMainHand());
        }

        return Optional.ofNullable(caughtFish);
    }
}