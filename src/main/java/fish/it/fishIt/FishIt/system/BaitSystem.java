package fish.it.fishIt.system;

import fish.it.fishIt.FishIt;
import fish.it.fishIt.data.BaitData;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;

import java.util.Optional;

public class BaitSystem {

    private final FishIt plugin;

    public BaitSystem(FishIt plugin) {
        this.plugin = plugin;
    }

    public double getBaitLuckModifier(ItemStack rod) {
        if (rod == null || rod.getType() == Material.AIR || !rod.hasItemMeta()) {
            return 0.0;
        }

        PersistentDataContainer pdc = rod.getItemMeta().getPersistentDataContainer();
        String baitKey = pdc.get(plugin.getKey("bait"), PersistentDataType.STRING);

        if (baitKey == null) {
            return 0.0;
        }

        Optional<BaitData> baitDataOpt = plugin.getBaitManager().getBait(baitKey);
        return baitDataOpt.map(BaitData::getLuckModifier).orElse(0.0);
    }

    public void consumeBait(ItemStack rod) {
        if (rod == null || rod.getType() == Material.AIR || !rod.hasItemMeta()) {
            return;
        }

        PersistentDataContainer pdc = rod.getItemMeta().getPersistentDataContainer();
        Integer charges = pdc.get(plugin.getKey("bait_charges"), PersistentDataType.INTEGER);

        if (charges != null && charges > 1) {
            pdc.set(plugin.getKey("bait_charges"), PersistentDataType.INTEGER, charges - 1);
            rod.getItemMeta().setPersistentDataContainer(pdc);
        } else if (charges != null && charges == 1) {
            pdc.remove(plugin.getKey("bait"));
            pdc.remove(plugin.getKey("bait_charges"));
            rod.getItemMeta().setPersistentDataContainer(pdc);
        }
    }
}