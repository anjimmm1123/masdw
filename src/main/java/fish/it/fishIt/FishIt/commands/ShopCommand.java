package fish.it.fishIt.commands;

import fish.it.fishIt.FishIt;
import fish.it.fishIt.utils.MessageUtils;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class ShopCommand implements CommandExecutor {

    private final FishIt plugin;

    public ShopCommand(FishIt plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage(MessageUtils.colorize("&cPerintah ini hanya bisa digunakan oleh pemain."));
            return true;
        }

        Player player = (Player) sender;
        if (!player.hasPermission("fishit.shop")) {
            player.sendMessage(MessageUtils.colorize("&cAnda tidak memiliki izin untuk melakukan ini!"));
            return true;
        }

        plugin.getGuiManager().openShopGUI(player);
        return true;
    }
}