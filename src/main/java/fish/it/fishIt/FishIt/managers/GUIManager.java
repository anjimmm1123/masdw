package fish.it.fishIt.managers;

import fish.it.fishIt.FishIt;
import fish.it.fishIt.gui.FishCollectionGUI;
import fish.it.fishIt.gui.RodUpgradeGUI;
import fish.it.fishIt.gui.ShopGUI;
import org.bukkit.entity.Player;

public class GUIManager {

    private final FishIt plugin;

    public GUIManager(FishIt plugin) {
        this.plugin = plugin;
    }

    public void openShopGUI(Player player) {
        new ShopGUI(plugin).open(player);
    }

    public void openFishCollectionGUI(Player player) {
        new FishCollectionGUI(plugin).open(player);
    }

    public void openRodUpgradeGUI(Player player) {
        new RodUpgradeGUI(plugin).open(player);
    }
}