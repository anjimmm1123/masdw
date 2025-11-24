package fish.it.fishIt.system;

import fish.it.fishIt.FishIt;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class CooldownSystem {

    private final FishIt plugin;
    private final Map<UUID, Long> cooldowns = new HashMap<>();
    private final long cooldownDuration;

    public CooldownSystem(FishIt plugin) {
        this.plugin = plugin;
        this.cooldownDuration = plugin.getConfig().getLong("fishing-cooldown", 1000); // Default 1 detik
    }

    public boolean isOnCooldown(UUID playerId) {
        if (!cooldowns.containsKey(playerId)) {
            return false;
        }
        long lastFished = cooldowns.get(playerId);
        return (System.currentTimeMillis() - lastFished) < cooldownDuration;
    }

    public void setCooldown(UUID playerId) {
        cooldowns.put(playerId, System.currentTimeMillis());
    }

    public long getRemainingCooldown(UUID playerId) {
        if (!isOnCooldown(playerId)) {
            return 0;
        }
        long lastFished = cooldowns.get(playerId);
        return cooldownDuration - (System.currentTimeMillis() - lastFished);
    }
}