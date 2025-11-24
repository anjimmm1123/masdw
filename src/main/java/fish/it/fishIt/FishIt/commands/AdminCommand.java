package fish.it.fishIt.commands;

import fish.it.fishIt.FishIt;
import fish.it.fishIt.utils.MessageUtils;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class AdminCommand implements CommandExecutor, TabCompleter {

    private final FishIt plugin;

    public AdminCommand(FishIt plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (!sender.hasPermission("fishit.admin")) {
            sender.sendMessage(MessageUtils.colorize("&cAnda tidak memiliki izin untuk melakukan ini!"));
            return true;
        }

        if (args.length == 0) {
            sender.sendMessage(MessageUtils.colorize("&6=== &eAdmin Command FishIt! &6==="));
            sender.sendMessage(MessageUtils.colorize("&7/fishitadmin givefish <player> <fish_id> [amount] &f- Berikan ikan kepada pemain."));
            sender.sendMessage(MessageUtils.colorize("&7/fishitadmin setlevel <player> <level> &f- Atur level pemain."));
            sender.sendMessage(MessageUtils.colorize("&7/fishitadmin addexp <player> <amount> &f- Tambah experience pemain."));
            return true;
        }

        switch (args[0].toLowerCase()) {
            case "givefish":
                if (args.length < 3) {
                    sender.sendMessage(MessageUtils.colorize("&cPenggunaan: /fishitadmin givefish <player> <fish_id> [amount]"));
                    return true;
                }
                Player target = Bukkit.getPlayer(args[1]);
                if (target == null) {
                    sender.sendMessage(MessageUtils.colorize("&cPemain '" + args[1] + "' tidak ditemukan."));
                    return true;
                }
                String fishId = args[2];
                int amount = 1;
                if (args.length >= 4) {
                    try {
                        amount = Integer.parseInt(args[3]);
                    } catch (NumberFormatException e) {
                        sender.sendMessage(MessageUtils.colorize("&cJumlah harus berupa angka."));
                        return true;
                    }
                }
                if (plugin.getFishAPI().giveFish(target, fishId, amount)) {
                    sender.sendMessage(MessageUtils.colorize("&aBerhasil memberikan &e" + amount + "x " + fishId + " &a kepada &e" + target.getName()));
                } else {
                    sender.sendMessage(MessageUtils.colorize("&cGagal memberikan ikan. Fish ID '" + fishId + "' tidak valid atau inventory penuh."));
                }
                return true;

            case "setlevel":
                if (args.length < 3) {
                    sender.sendMessage(MessageUtils.colorize("&cPenggunaan: /fishitadmin setlevel <player> <level>"));
                    return true;
                }
                Player levelTarget = Bukkit.getPlayer(args[1]);
                if (levelTarget == null) {
                    sender.sendMessage(MessageUtils.colorize("&cPemain '" + args[1] + "' tidak ditemukan."));
                    return true;
                }
                int level;
                try {
                    level = Integer.parseInt(args[2]);
                } catch (NumberFormatException e) {
                    sender.sendMessage(MessageUtils.colorize("&cLevel harus berupa angka."));
                    return true;
                }
                FisherPlayer fisherPlayer = plugin.getPlayerDataManager().getPlayer(levelTarget.getUniqueId());
                fisherPlayer.setLevel(level);
                fisherPlayer.setExperience(0);
                plugin.getPlayerDataManager().savePlayer(fisherPlayer);
                sender.sendMessage(MessageUtils.colorize("&aBerhasil mengatur level &e" + levelTarget.getName() + " &a menjadi &e" + level));
                return true;

            case "addexp":
                if (args.length < 3) {
                    sender.sendMessage(MessageUtils.colorize("&cPenggunaan: /fishitadmin addexp <player> <amount>"));
                    return true;
                }
                Player expTarget = Bukkit.getPlayer(args[1]);
                if (expTarget == null) {
                    sender.sendMessage(MessageUtils.colorize("&cPemain '" + args[1] + "' tidak ditemukan."));
                    return true;
                }
                int exp;
                try {
                    exp = Integer.parseInt(args[2]);
                } catch (NumberFormatException e) {
                    sender.sendMessage(MessageUtils.colorize("&cExperience harus berupa angka."));
                    return true;
                }
                plugin.getLevelSystem().addExperience(expTarget, exp);
                sender.sendMessage(MessageUtils.colorize("&aBerhasil menambahkan &e" + exp + " EXP &a kepada &e" + expTarget.getName()));
                return true;

            default:
                sender.sendMessage(MessageUtils.colorize("&cSub-perintah tidak dikenal. Gunakan /fishitadmin untuk bantuan."));
                return true;
        }
    }

    @Override
    public @Nullable List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command, @NotNull String alias, @NotNull String[] args) {
        List<String> completions = new ArrayList<>();
        if (args.length == 1) {
            completions.add("givefish");
            completions.add("setlevel");
            completions.add("addexp");
        } else if (args.length == 2) {
            completions = Bukkit.getOnlinePlayers().stream().map(Player::getName).collect(Collectors.toList());
        } else if (args.length == 3 && args[0].equalsIgnoreCase("givefish")) {
            completions = new ArrayList<>(plugin.getFishManager().getAllFish().keySet());
        }
        return completions;
    }
}