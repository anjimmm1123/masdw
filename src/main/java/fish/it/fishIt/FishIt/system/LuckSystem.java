package fish.it.fishIt.system;

import fish.it.fishIt.FishIt;
import fish.it.fishIt.data.FisherPlayer;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffectType;

import java.util.Optional;

public class LuckSystem {

    private final FishIt plugin;

    public LuckSystem(FishIt plugin) {
        this.plugin = plugin;
    }

    public double calculatePlayerLuck(Player player) {
        double luck = 0.0;

        Optional<FisherPlayer> fisherPlayerOpt = plugin.getPlayerDataManager().getPlayer(player.getUniqueId());
        if (fisherPlayerOpt.isPresent()) {
            FisherPlayer fisherPlayer = fisherPlayerOpt.get();
            luck += fisherPlayer.getLevel() * 0.01;
        }

        if (player.hasPermission("fishit.luck.vip")) {
            luck += 0.10;
        }
        if (player.hasPermission("fishit.luck.mvp")) {
            luck += 0.25;
        }

        if (player.hasPotionEffect(PotionEffectType.LUCK)) {
            luck += 0.05 * (player.getPotionEffect(PotionEffectType.LUCK).getAmplifier() + 1);
        }

        return luck;
    }
}