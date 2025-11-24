package fish.it.fishIt.utils;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import.util.ArrayList;
import java.util.List;

public class ItemUtil {

    public static boolean isSimilar(ItemStack item1, ItemStack item2) {
        if (item1 == null || item2 == null) {
            return item1 == item2;
        }
        if (item1.getType() != item2.getType()) {
            return false;
        }
        if (!item1.hasItemMeta() || !item2.hasItemMeta()) {
            return !item1.hasItemMeta() && !item2.hasItemMeta();
        }
        ItemMeta meta1 = item1.getItemMeta();
        ItemMeta meta2 = item2.getItemMeta();
        if (!meta1.hasDisplayName() || !meta2.hasDisplayName()) {
            return !meta1.hasDisplayName() && !meta2.hasDisplayName() && meta1.equals(meta2);
        }
        return meta1.getDisplayName().equals(meta2.getDisplayName()) && meta1.equals(meta2);
    }

    public static String getItemName(ItemStack item) {
        if (item == null || item.getType() == Material.AIR) {
            return "";
        }
        if (item.hasItemMeta() && item.getItemMeta().hasDisplayName()) {
            return item.getItemMeta().getDisplayName();
        }
        return formatMaterialName(item.getType());
    }

    public static String formatMaterialName(Material material) {
        String name = material.name().toLowerCase().replace('_', ' ');
        String[] parts = name.split(" ");
        StringBuilder formattedName = new StringBuilder();
        for (String part : parts) {
            if (part.length() > 0) {
                formattedName.append(Character.toUpperCase(part.charAt(0))).append(part.substring(1)).append(" ");
            }
        }
        return formattedName.toString().trim();
    }

    public static List<String> getLore(ItemStack item) {
        if (item == null || !item.hasItemMeta() || !item.getItemMeta().hasLore()) {
            return new ArrayList<>();
        }
        return item.getItemMeta().getLore();
    }
}