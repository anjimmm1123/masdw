package fish.it.fishIt.managers;

import fish.it.fishIt.FishIt;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class ConfigManager {

    private final FishIt plugin;
    private final Map<String, FileConfiguration> configs = new HashMap<>();
    private final Map<String, File> configFiles = new HashMap<>();

    public ConfigManager(FishIt plugin) {
        this.plugin = plugin;
        loadConfigs();
    }

    private void loadConfigs() {
        String[] configNames = {"config", "fish", "rods", "baits", "shop", "quests", "collection", "messages", "rarity"};
        for (String name : configNames) {
            File configFile = new File(plugin.getDataFolder(), name + ".yml");
            if (!configFile.exists()) {
                plugin.saveResource(name + ".yml", false);
            }
            configFiles.put(name, YamlConfiguration.loadConfiguration(configFile));
            configs.put(name, YamlConfiguration.loadConfiguration(configFile));
        }
    }

    public void reloadConfigs() {
        configs.clear();
        loadConfigs();
    }

    public FileConfiguration getConfig(String name) {
        return configs.get(name);
    }

    public void saveConfig(String name) {
        try {
            configs.get(name).save(configFiles.get(name));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}