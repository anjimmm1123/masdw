package fish.it.fishIt.gui;

import fish.it.fishIt.FishIt;
import fish.it.fishIt.data.FishingRod;
import fish.it.fishIt.utils.ItemBuilder;
import fish.it.fishIt.utils.MessageUtils;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.persistence.PersistentDataType;

import java.util.List;

public class RodUpgradeGUI {

    private final FishIt plugin;
    private final Player player;
    private final Inventory inventory;

    public RodUpgradeGUI(FishIt plugin, Player player) {
        this.plugin = plugin;
        this.player = player;
        this.inventory = Bukkit.createInventory(null, 54, MessageUtils.colorize("&6Upgrade Pancing"));
        initializeItems();
    }

    private void initializeItems() {
        ItemStack filler = new ItemBuilder(Material.GRAY_STAINED_GLASS_PANE).setName(" ").build();
        for (int i = 0; i < inventory.getSize(); i++) {
            inventory.setItem(i, filler);
        }

        ItemStack currentRod = player.getInventory().getItemInMainHand();
        if (currentRod == null || currentRod.getType() != Material.FISHING_ROD || !currentRod.getItemMeta().getPersistentDataContainer().has(plugin.getKey("rod_id"), PersistentDataType.STRING)) {
            inventory.setItem(22, new ItemBuilder(Material.BARRIER).setName("&cPancing Tidak Valid").setLore("&7Harap pegang pancing Anda untuk upgrade.").build());
            return;
        }

        String currentRodId = currentRod.getItemMeta().getPersistentDataContainer().get(plugin.getKey("rod_id"), PersistentDataType.STRING);
        FishingRod rodData = plugin.getRodManager().getRod(currentRodId);
        if (rodData == null) {
            inventory.setItem(22, new ItemBuilder(Material.BARRIER).setName("&cData Pancing Tidak Ditemukan").build());
            return;
        }

        inventory.setItem(13, currentRod);

        String nextRodId = getNextRodId(currentRodId);
        if (nextRodId == null) {
            inventory.setItem(31, new ItemBuilder(Material.GREEN_STAINED_GLASS_PANE).setName("&aPancing Maksimal!").setLore("&7Pancing Anda sudah pada level tertinggi.").build());
            return;
        }

        FishingRod nextRodData = plugin.getRodManager().getRod(nextRodId);
        if (nextRodData == null) return;

        ItemStack upgradeItem = new ItemBuilder(nextRodData.getMaterial())
                .setName("&eUpgrade ke " + nextRodData.getDisplayName())
                .setLore(
                        "&7Keberuntungan: &f" + rodData.getLuckMultiplier() + " &7-> &a" + nextRodData.getLuckMultiplier(),
                        "&7Durabilitas: &f" + rodData.getDurability() + " &7-> &a" + nextRodData.getDurability(),
                        "",
                        "&6Harga: " + plugin.getEconomyManager().format(nextRodData.getPrice()),
                        "",
                        "&eKlik untuk upgrade!"
                )
                .setPersistentData(plugin.getKey("upgrade_to"), PersistentDataType.STRING, nextRodId)
                .build();
        inventory.setItem(31, upgradeItem);
    }

    private String getNextRodId(String currentRodId) {
        List<String> rodOrder = plugin.getConfigManager().getConfig("rods").getStringList("rod_upgrade_order");
        int currentIndex = rodOrder.indexOf(currentRodId);
        if (currentIndex != -1 && currentIndex < rodOrder.size() - 1) {
            return rodOrder.get(currentIndex + 1);
        }
        return null;
    }

    public void open() {
        player.openInventory(inventory);
    }
}