package fish.it.fishIt.managers;

import fish.it.fishIt.FishIt;
import fish.it.fishIt.data.FishData;
import fish.it.fishIt.data.RarityData;
import fish.it.fishIt.utils.ItemBuilder;
import fish.it.fishIt.utils.MessageUtils;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ItemStack;
import org.bukkit.persistence.PersistentDataType;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

public class FishManager {

    private final FishIt plugin;
    private final Map<String, FishData> fishMap = new HashMap<>();
    private final Map<String, RarityData> rarityMap = new HashMap<>();
    private final Random random = ThreadLocalRandom.current();

    public FishManager(FishIt plugin) {
        this.plugin = plugin;
        loadFish();
        loadRarities();
    }

    private void loadFish() {
        for (String key : plugin.getConfigManager().getConfig("fish").getConfigurationSection("fish").getKeys(false)) {
            String name = plugin.getConfigManager().getConfig("fish").getString("fish." + key + ".display_name");
            String rarity = plugin.getConfigManager().getConfig("fish").getString("fish." + key + ".rarity");
            double price = plugin.getConfigManager().getConfig("fish").getDouble("fish." + key + ".price");
            int minSize = plugin.getConfigManager().getConfig("fish").getInt("fish." + key + ".min-size");
            int maxSize = plugin.getConfigManager().getConfig("fish").getInt("fish." + key + ".max-size");
            int weight = plugin.getConfigManager().getConfig("fish").getInt("fish." + key + ".weight");
            int exp = plugin.getConfigManager().getConfig("fish").getInt("fish." + key + ".exp", 10);
            List<String> regions = plugin.getConfigManager().getConfig("fish").getStringList("fish." + key + ".regions");
            fishMap.put(key, new FishData(key, name, rarity, price, minSize, maxSize, weight, exp, regions));
        }
    }

    private void loadRarities() {
        for (String key : plugin.getConfigManager().getConfig("rarity").getConfigurationSection("rarities").getKeys(false)) {
            String color = plugin.getConfigManager().getConfig("rarity").getString("rarities." + key + ".color");
            double priceMultiplier = plugin.getConfigManager().getConfig("rarity").getDouble("rarities." + key + ".price_multiplier");
            rarityMap.put(key, new RarityData(key, color, priceMultiplier));
        }
    }

    public Map<String, FishData> getAllFish() {
        return fishMap;
    }

    public Map<String, RarityData> getAllRarities() {
        return rarityMap;
    }

    public ItemStack createFishItem(FishData fishData) {
        int size = random.nextInt(fishData.getMaxSize() - fishData.getMinSize() + 1) + fishData.getMinSize();
        RarityData rarity = rarityMap.get(fishData.getRarity());
        double finalPrice = fishData.getPrice() * rarity.getPriceMultiplier();

        String displayName = MessageUtils.colorize(rarity.getColor() + fishData.getDisplayName() + " &7(" + size + "cm)");
        List<String> lore = MessageUtils.colorizeList(List.of(
                "&7Rarity: " + rarity.getColor() + fishData.getRarity(),
                "&7Harga Jual: &6" + plugin.getEconomyManager().format(finalPrice),
                "&7Ukuran: &e" + size + "cm",
                "",
                "&eKlik kanan untuk menjual!"
        ));

        return new ItemBuilder(Material.COD)
                .setName(displayName)
                .setLore(lore)
                .setPersistentData(plugin.getKey("fish_id"), PersistentDataType.STRING, fishData.getId())
                .setPersistentData(plugin.getKey("fish_size"), PersistentDataType.INTEGER, size)
                .setPersistentData(plugin.getKey("fish_price"), PersistentDataType.DOUBLE, finalPrice)
                .build();
    }

    public boolean isCustomFish(ItemStack item) {
        if (item == null || !item.hasItemMeta() || !item.getItemMeta().getPersistentDataContainer().has(plugin.getKey("fish_id"), PersistentDataType.STRING)) {
            return false;
        }
        return true;
    }

    public FishData getFishDataFromItem(ItemStack item) {
        if (!isCustomFish(item)) {
            return null;
        }
        String fishId = item.getItemMeta().getPersistentDataContainer().get(plugin.getKey("fish_id"), PersistentDataType.STRING);
        return fishMap.get(fishId);
    }

    public double getFishPrice(ItemStack item) {
        if (!isCustomFish(item)) {
            return 0;
        }
        return item.getItemMeta().getPersistentDataContainer().getOrDefault(plugin.getKey("fish_price"), PersistentDataType.DOUBLE, 0.0);
    }
}