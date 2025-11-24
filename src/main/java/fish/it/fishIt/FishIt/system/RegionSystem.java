package fish.it.fishIt.system;

import fish.it.fishIt.FishIt;
import fish.it.fishIt.data.FishData;
import org.bukkit.Location;
import org.bukkit.entity.Player;

import java.util.List;
import java.util.stream.Collectors;

public class RegionSystem {

    private final FishIt plugin;

    public RegionSystem(FishIt plugin) {
        this.plugin = plugin;
    }

    public List<FishData> getFishInPlayerRegion(Player player) {
        Location location = player.getLocation();
        String regionName = plugin.getRegionManager().getRegionName(location);

        if (regionName == null) {
            return List.of();
        }

        return plugin.getFishManager().getAllFish().values().stream()
                .filter(fishData -> fishData.getRegions().contains(regionName))
                .collect(Collectors.toList());
    }
}