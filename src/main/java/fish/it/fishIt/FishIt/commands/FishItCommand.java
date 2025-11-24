package fish.it.fishIt.commands;

import fish.it.fishIt.FishIt;
import fish.it.fishIt.utils.MessageUtils;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

public class FishItCommand implements CommandExecutor, TabCompleter {

    private final FishIt plugin;

    public FishItCommand(FishIt plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (args.length == 0) {
            sender.sendMessage(MessageUtils.colorize("&6=== &eFishIt! Plugin &6==="));
            sender.sendMessage(MessageUtils.colorize("&7/fishit help &f- Menampilkan bantuan."));
            sender.sendMessage(MessageUtils.colorize("&7/fishit info &f- Menampilkan info plugin."));
            if (sender.hasPermission("fishit.admin")) {
                sender.sendMessage(MessageUtils.colorize("&7/fishit reload &f- Me-reload konfigurasi."));
            }
            return true;
        }

        switch (args[0].toLowerCase()) {
            case "help":
                sender.sendMessage(MessageUtils.colorize("&6=== &eBantuan FishIt! &6==="));
                sender.sendMessage(MessageUtils.colorize("&7/fishit &f- Informasi plugin."));
                sender.sendMessage(MessageUtils.colorize("&7/sellfish &f- Jual semua ikan di inventory."));
                sender.sendMessage(MessageUtils.colorize("&7/shop &f- Buka toko."));
                sender.sendMessage(MessageUtils.colorize("&7/stats &f- Lihat statistik memancing Anda."));
                return true;

            case "info":
                sender.sendMessage(MessageUtils.colorize("&6Plugin: &eFishIt!"));
                sender.sendMessage(MessageUtils.colorize("&6Version: &e" + plugin.getDescription().getVersion()));
                sender.sendMessage(MessageUtils.colorize("&6Author: &e" + plugin.getDescription().getAuthors().get(0)));
                return true;

            case "reload":
                if (!sender.hasPermission("fishit.admin.reload")) {
                    sender.sendMessage(MessageUtils.colorize("&cAnda tidak memiliki izin untuk melakukan ini!"));
                    return true;
                }
                
                plugin.getConfigManager().reloadConfigs();
                sender.sendMessage(MessageUtils.colorize("&aKonfigurasi FishIt! telah berhasil di-reload!"));
                return true;

            default:
                sender.sendMessage(MessageUtils.colorize("&cSub-perintah tidak dikenal. Gunakan /fishit help untuk bantuan."));
                return true;
        }
    }

    @Override
    public @Nullable List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command, @NotNull String alias, @NotNull String[] args) {
        List<String> completions = new ArrayList<>();
        if (args.length == 1) {
            if (sender.hasPermission("fishit.admin")) {
                completions.add("reload");
            }
            completions.add("help");
            completions.add("info");
        }
        return completions;
    }
}