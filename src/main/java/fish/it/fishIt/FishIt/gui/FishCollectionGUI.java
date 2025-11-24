package fish.it.fishIt.gui;

import fish.it.fishIt.FishIt;
import fish.it.fishIt.data.FishData;
import fish.it.fishIt.data.FisherPlayer;
import fish.it.fishIt.utils.ItemBuilder;
import fish.it.fishIt.utils.MessageUtils;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class FishCollectionGUI {

    private final FishIt plugin;
    private final Player player;
    private final FisherPlayer fisherPlayer;
    private int page = 0;
    private final List<Inventory> pages = new ArrayList<>();

    public FishCollectionGUI(FishIt plugin, Player player) {
        this.plugin = plugin;
        this.player = player;
        this.fisherPlayer = plugin.getPlayerDataManager().getPlayer(player.getUniqueId());
        buildPages();
    }

    private void buildPages() {
        List<ItemStack> fishItems = new ArrayList<>();
        Map<String, FishData> allFish = plugin.getFishManager().getAllFish();

        for (FishData fish : allFish.values()) {
            if (fisherPlayer.hasCaughtFish(fish.getId())) {
                fishItems.add(plugin.getFishManager().createFishItem(fish));
            } else {
                fishItems.add(new ItemBuilder(Material.BARRIER)
                        .setName("&c???")
                        .setLore(MessageUtils.colorizeList("&7Tidak Ditemukan", "&7Tangkap ikan ini untuk membuka kuncinya!"))
                        .build());
            }
        }

        int itemsPerPage = 45;
        int totalPages = (int) Math.ceil((double) fishItems.size() / itemsPerPage);

        for (int i = 0; i < totalPages; i++) {
            Inventory page = Bukkit.createInventory(null, 54, MessageUtils.colorize("&6Koleksi Ikan &7(Hal " + (i + 1) + "/" + totalPages + ")"));
            ItemStack filler = new ItemBuilder(Material.GRAY_STAINED_GLASS_PANE).setName(" ").build();
            for (int j = 0; j < 54; j++) {
                page.setItem(j, filler);
            }

            int startIndex = i * itemsPerPage;
            int endIndex = Math.min(startIndex + itemsPerPage, fishItems.size());
            int slot = 0;
            for (int j = startIndex; j < endIndex; j++) {
                page.setItem(slot, fishItems.get(j));
                slot++;
            }

            if (i > 0) {
                page.setItem(45, new ItemBuilder(Material.ARROW).setName("&eHalaman Sebelumnya").build());
            }
            if (i < totalPages - 1) {
                page.setItem(53, new ItemBuilder(Material.ARROW).setName("&eHalaman Berikutnya").build());
            }
            pages.add(page);
        }
    }

    public void open() {
        if (pages.isEmpty()) {
            player.sendMessage(MessageUtils.colorize("&cTidak ada ikan untuk ditampilkan."));
            return;
        }
        player.openInventory(pages.get(page));
    }

    public void nextPage() {
        if (page < pages.size() - 1) {
            page++;
            player.openInventory(pages.get(page));
        }
    }

    public void previousPage() {
        if (page > 0) {
            page--;
            player.openInventory(pages.get(page));
        }
    }
}