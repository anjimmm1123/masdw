package fish.it.fishIt.system;

import fish.it.fishIt.FishIt;
import org.bukkit.World;

public class WeatherSystem {

    private final FishIt plugin;

    public WeatherSystem(FishIt plugin) {
        this.plugin = plugin;
    }

    public double getWeatherLuckModifier(World world) {
        if (world.hasStorm()) {
            if (world.isThundering()) {
                return plugin.getConfig().getDouble("weather-modifier.thunder", 0.15);
            }
            return plugin.getConfig().getDouble("weather-modifier.rain", 0.10);
        }
        return 0.0;
    }
}