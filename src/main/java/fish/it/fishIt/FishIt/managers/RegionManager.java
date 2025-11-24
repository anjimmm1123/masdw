package fish.it.fishIt.managers;

import fish.it.fishIt.FishIt;
import com.sk89q.worldguard.WorldGuard;
import com.sk89q.worldguard.protection.ApplicableRegionSet;
import com.sk89q.worldguard.protection.managers.RegionManager;
import com.sk89q.worldguard.protection.regions.RegionContainer;
import org.bukkit.Location;

public class RegionManager {

    private final FishIt plugin;
    private final RegionContainer regionContainer;

    public RegionManager(FishIt plugin) {
        this.plugin = plugin;
        this.regionContainer = WorldGuard.getInstance().getPlatform().getRegionContainer();
    }

    public String getRegionName(Location location) {
        try {
            RegionManager regions = regionContainer.get(com.sk89q.worldguard.bukkit.WorldGuardPlugin.inst().getWorldGuardPlatform().getMatcher().getWorldByName(location.getWorld().getName()));
            if (regions == null) {
                return null;
            }
            ApplicableRegionSet set = regions.getApplicableRegions(com.sk89q.worldguard.bukkit.BukkitAdapter.adapt(location));
            if (!set.getRegions().isEmpty()) {
                return set.getRegions().iterator().next().getId();
            }
        } catch (Exception e) {
            return null;
        }
        return null;
    }
}