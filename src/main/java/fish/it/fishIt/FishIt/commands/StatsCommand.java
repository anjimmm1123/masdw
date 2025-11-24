package fish.it.fishIt.commands;

import fish.it.fishIt.FishIt;
import fish.it.fishIt.data.FisherPlayer;
import fish.it.fishIt.utils.MessageUtils;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.Map;

public class StatsCommand implements CommandExecutor {

    private final FishIt plugin;

    public StatsCommand(FishIt plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage(MessageUtils.colorize("&cPerintah ini hanya bisa digunakan oleh pemain."));
            return true;
        }

        Player player = (Player) sender;
        if (!player.hasPermission("fishit.stats")) {
            player.sendMessage(MessageUtils.colorize("&cAnda tidak memiliki izin untuk melakukan ini!"));
            return true;
        }

        FisherPlayer fisherPlayer = plugin.getPlayerDataManager().getPlayer(player.getUniqueId());
        if (fisherPlayer == null) {
            player.sendMessage(MessageUtils.colorize("&cData pemain tidak ditemukan."));
            return true;
        }

        player.sendMessage(MessageUtils.colorize("&6=== &eStatistik Memancing Anda &6==="));
        player.sendMessage(MessageUtils.colorize("&7Level: &e" + fisherPlayer.getLevel()));
        player.sendMessage(MessageUtils.colorize("&7Experience: &e" + fisherPlayer.getExperience() + " &7/ &e" + plugin.getLevelSystem().getExpToNextLevel(fisherPlayer.getLevel())));
        player.sendMessage(MessageUtils.colorize("&7Total Ikan Ditangkap: &e" + fisherPlayer.getStats().getTotalFishCaught()));
        player.sendMessage(MessageUtils.colorize("&7Total Penghasilan: &6" + plugin.getEconomyManager().format(fisherPlayer.getStats().getTotalEarnings())));
        player.sendMessage(MessageUtils.colorize("&7Ikan Terbesar: &e" + fisherPlayer.getStats().getBiggestCatch() + "cm"));
        player.sendMessage(MessageUtils.colorize("&7Jenis Ikan Ditangkap: &e" + fisherPlayer.getCollection().size() + " &7/ &e" + plugin.getFishManager().getAllFish().size()));

        return true;
    }
}