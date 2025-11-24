package fish.it.fishIt.gui;

import fish.it.fishIt.FishIt;
import fish.it.fishIt.utils.ItemBuilder;
import fish.it.fishIt.utils.MessageUtils;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.Map;

public class ShopGUI {

    private final FishIt plugin;
    private final Inventory inventory;

    public ShopGUI(FishIt plugin) {
        this.plugin = plugin;
        this.inventory = Bukkit.createInventory(null, 54, MessageUtils.colorize("&6Toko Memancing"));
        initializeItems();
    }

    private void initializeItems() {
        ItemStack filler = new ItemBuilder(Material.GRAY_STAINED_GLASS_PANE).setName(" ").build();
        for (int i = 0; i < inventory.getSize(); i++) {
            inventory.setItem(i, filler);
        }

        Map<Integer, ItemStack> shopItems = plugin.getShopManager().getShopItems();
        for (Map.Entry<Integer, ItemStack> entry : shopItems.entrySet()) {
            inventory.setItem(entry.getKey(), entry.getValue());
        }
    }

    public void open(Player player) {
        player.openInventory(inventory);
    }
}