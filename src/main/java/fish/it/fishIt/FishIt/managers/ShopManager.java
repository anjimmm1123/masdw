package fish.it.fishIt.managers;

import fish.it.fishIt.FishIt;
import fish.it.fishIt.utils.ItemBuilder;
import org.bukkit.Material;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.inventory.ItemStack;

import java.util.HashMap;
import java.util.Map;

public class ShopManager {

    private final FishIt plugin;
    private final Map<Integer, ItemStack> shopItems = new HashMap<>();

    public ShopManager(FishIt plugin) {
        this.plugin = plugin;
        loadShopItems();
    }

    private void loadShopItems() {
        ConfigurationSection itemsSection = plugin.getConfigManager().getConfig("shop").getConfigurationSection("items");
        if (itemsSection == null) return;

        for (String key : itemsSection.getKeys(false)) {
            int slot = Integer.parseInt(key);
            String materialName = itemsSection.getString(key + ".material");
            String name = itemsSection.getString(key + ".name");
            double price = itemsSection.getDouble(key + ".price");
            String command = itemsSection.getString(key + ".command");

            ItemStack item = new ItemBuilder(Material.valueOf(materialName))
                    .setName(name)
                    .setLore(plugin.getMessageUtils().colorizeList("&7Harga: &6" + plugin.getEconomyManager().format(price)))
                    .build();

            shopItems.put(slot, item);
        }
    }

    public Map<Integer, ItemStack> getShopItems() {
        return shopItems;
    }
}