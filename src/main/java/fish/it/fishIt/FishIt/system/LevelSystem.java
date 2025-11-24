package fish.it.fishIt.system;

import fish.it.fishIt.FishIt;
import fish.it.fishIt.data.FisherPlayer;
import org.bukkit.entity.Player;

import java.util.Optional;

public class LevelSystem {

    private final FishIt plugin;

    public LevelSystem(FishIt plugin) {
        this.plugin = plugin;
    }

    public void addExperience(Player player, int exp) {
        Optional<FisherPlayer> fisherPlayerOpt = plugin.getPlayerDataManager().getPlayer(player.getUniqueId());
        if (fisherPlayerOpt.isEmpty()) {
            return;
        }

        FisherPlayer fisherPlayer = fisherPlayerOpt.get();
        int currentLevel = fisherPlayer.getLevel();
        int currentExp = fisherPlayer.getExperience();
        int expToNextLevel = getExpToNextLevel(currentLevel);

        fisherPlayer.setExperience(currentExp + exp);

        if (fisherPlayer.getExperience() >= expToNextLevel) {
            levelUp(player, fisherPlayer);
        }
        
        plugin.getPlayerDataManager().savePlayer(fisherPlayer);
    }

    private void levelUp(Player player, FisherPlayer fisherPlayer) {
        fisherPlayer.setLevel(fisherPlayer.getLevel() + 1);
        fisherPlayer.setExperience(fisherPlayer.getExperience() - getExpToNextLevel(fisherPlayer.getLevel() - 1));
        
        player.sendMessage(plugin.getMessageUtils().colorize("&aSelamat! Anda naik ke level " + fisherPlayer.getLevel() + "!"));
    }

    public int getExpToNextLevel(int level) {
        return 100 + (level * level * 25);
    }
}