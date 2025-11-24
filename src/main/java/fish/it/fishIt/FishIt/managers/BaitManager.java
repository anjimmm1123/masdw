package fish.it.fishIt.managers;

import fish.it.fishIt.FishIt;
import fish.it.fishIt.data.BaitData;

import java.util.HashMap;
import java.util.Map;

public class BaitManager {

    private final FishIt plugin;
    private final Map<String, BaitData> baitMap = new HashMap<>();

    public BaitManager(FishIt plugin) {
        this.plugin = plugin;
        loadBaits();
    }

    private void loadBaits() {
        for (String key : plugin.getConfigManager().getConfig("baits").getConfigurationSection("baits").getKeys(false)) {
            String name = plugin.getConfigManager().getConfig("baits").getString("baits." + key + ".display_name");
            double luckModifier = plugin.getConfigManager().getConfig("baits").getDouble("baits." + key + ".luck_modifier");
            int charges = plugin.getConfigManager().getConfig("baits").getInt("baits." + key + ".charges", 1);
            baitMap.put(key, new BaitData(key, name, luckModifier, charges));
        }
    }

    public Map<String, BaitData> getAllBaits() {
        return baitMap;
    }

    public BaitData getBait(String baitId) {
        return baitMap.get(baitId);
    }
}