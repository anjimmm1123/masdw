package fish.it.fishIt.utils;

import org.bukkit.Material;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

public class GUIUtil {

    public static void fillBorder(Inventory inventory, ItemStack item) {
        int size = inventory.getSize();
        for (int i = 0; i < size; i++) {
            if (i < 9 || i >= size - 9 || i % 9 == 0 || i % 9 == 8) {
                inventory.setItem(i, item);
            }
        }
    }

    public static void fillPattern(Inventory inventory, ItemStack item, int... slots) {
        for (int slot : slots) {
            if (slot >= 0 && slot < inventory.getSize()) {
                inventory.setItem(slot, item);
            }
        }
    }

    public static void fillEmpty(Inventory inventory, ItemStack item) {
        for (int i = 0; i < inventory.getSize(); i++) {
            if (inventory.getItem(i) == null || inventory.getItem(i).getType() == Material.AIR) {
                inventory.setItem(i, item);
            }
        }
    }

    public static ItemStack createFiller(Material material) {
        return new ItemBuilder(material).setName(" ").build();
    }
}