package fish.it.fishIt.listeners;

import fish.it.fishIt.FishIt;
import fish.it.fishIt.data.FisherPlayer;
import fish.it.fishIt.data.FishingRod;
import fish.it.fishIt.gui.FishCollectionGUI;
import fish.it.fishIt.gui.RodUpgradeGUI;
import fish.it.fishIt.utils.MessageUtils;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.persistence.PersistentDataType;

public class InventoryClickListener implements Listener {

    private final FishIt plugin;

    public InventoryClickListener(FishIt plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onInventoryClick(InventoryClickEvent event) {
        Player player = (Player) event.getWhoClicked();
        Inventory inventory = event.getInventory();
        ItemStack clickedItem = event.getCurrentItem();
        String title = event.getView().getTitle();

        if (title.equals(MessageUtils.colorize("&6Toko Memancing"))) {
            event.setCancelled(true);
            if (clickedItem == null || clickedItem.getType() == Material.AIR) return;

            String command = clickedItem.getItemMeta().getPersistentDataContainer().get(plugin.getKey("shop_command"), PersistentDataType.STRING);
            if (command != null) {
                player.performCommand(command);
                player.closeInventory();
            }
        } else if (title.contains(MessageUtils.colorize("&6Koleksi Ikan"))) {
            event.setCancelled(true);
            if (clickedItem == null || clickedItem.getType() == Material.AIR) return;

            if (clickedItem.getType() == Material.ARROW) {
                FishCollectionGUI gui = (FishCollectionGUI) plugin.getGuiManager().getOpenedGUI(player);
                if (clickedItem.getItemMeta().getDisplayName().contains("Sebelumnya")) {
                    gui.previousPage();
                } else if (clickedItem.getItemMeta().getDisplayName().contains("Berikutnya")) {
                    gui.nextPage();
                }
            }
        } else if (title.equals(MessageUtils.colorize("&6Upgrade Pancing"))) {
            event.setCancelled(true);
            if (clickedItem == null || clickedItem.getType() == Material.AIR) return;

            String upgradeToId = clickedItem.getItemMeta().getPersistentDataContainer().get(plugin.getKey("upgrade_to"), PersistentDataType.STRING);
            if (upgradeToId != null) {
                FishingRod nextRodData = plugin.getRodManager().getRod(upgradeToId);
                if (nextRodData != null) {
                    FisherPlayer fisherPlayer = plugin.getPlayerDataManager().getPlayer(player.getUniqueId());
                    if (plugin.getEconomyManager().getBalance(player) >= nextRodData.getPrice()) {
                        plugin.getEconomyManager().withdrawPlayer(player, nextRodData.getPrice());
                        player.getInventory().setItemInMainHand(plugin.getRodManager().createRodItem(nextRodData));
                        player.sendMessage(MessageUtils.colorize("&aBerhasil mengupgrade pancing Anda!"));
                        player.closeInventory();
                    } else {
                        player.sendMessage(MessageUtils.colorize("&cUang Anda tidak cukup!"));
                    }
                }
            }
        }
    }
}