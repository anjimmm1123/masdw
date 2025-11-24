package fish.it.fishIt.commands;

import fish.it.fishIt.FishIt;
import fish.it.fishIt.api.events.FishSellEvent;
import fish.it.fishIt.data.FishData;
import fish.it.fishIt.utils.MessageUtils;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.Map;

public class SellCommand implements CommandExecutor {

    private final FishIt plugin;

    public SellCommand(FishIt plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage(MessageUtils.colorize("&cPerintah ini hanya bisa digunakan oleh pemain."));
            return true;
        }

        Player player = (Player) sender;
        if (!player.hasPermission("fishit.sell")) {
            player.sendMessage(MessageUtils.colorize("&cAnda tidak memiliki izin untuk melakukan ini!"));
            return true;
        }

        double totalEarnings = 0.0;
        Map<FishData, Integer> fishToSell = new HashMap<>();

        for (ItemStack item : player.getInventory().getContents()) {
            if (item == null || item.getType().isAir()) {
                continue;
            }

            if (plugin.getFishManager().isCustomFish(item)) {
                FishData fishData = plugin.getFishManager().getFishDataFromItem(item);
                if (fishData != null) {
                    double price = plugin.getFishManager().getFishPrice(item);
                    if (price > 0) {
                        fishToSell.merge(fishData, item.getAmount(), Integer::sum);
                        totalEarnings += price * item.getAmount();
                    }
                }
            }
        }

        if (totalEarnings <= 0) {
            player.sendMessage(MessageUtils.colorize("&7Anda tidak memiliki ikan yang bisa dijual."));
            return true;
        }

        FishSellEvent sellEvent = plugin.getEventsAPI().callFishSellEvent(player, fishToSell, totalEarnings);
        if (sellEvent.isCancelled()) {
            player.sendMessage(MessageUtils.colorize("&cPenjualan ikan dibatalkan."));
            return true;
        }
        totalEarnings = sellEvent.getTotalEarnings();

        for (Map.Entry<FishData, Integer> entry : fishToSell.entrySet()) {
            ItemStack fishItem = plugin.getFishManager().createFishItem(entry.getKey());
            fishItem.setAmount(entry.getValue());
            player.getInventory().removeItem(fishItem);
        }

        plugin.getEconomyManager().depositPlayer(player, totalEarnings);
        player.sendMessage(MessageUtils.colorize("&aBerhasil menjual ikan seharga &6" + plugin.getEconomyManager().format(totalEarnings) + "&a!"));
        return true;
    }
}