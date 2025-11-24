package fish.it.fishIt.utils;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

public class CooldownUtil {

    private static final Map<String, Long> cooldowns = new HashMap<>();

    public static void setCooldown(UUID playerId, String cooldownName, long duration, TimeUnit timeUnit) {
        long expiryTime = System.currentTimeMillis() + timeUnit.toMillis(duration);
        cooldowns.put(playerId.toString() + ":" + cooldownName, expiryTime);
    }

    public static boolean isOnCooldown(UUID playerId, String cooldownName) {
        String key = playerId.toString() + ":" + cooldownName;
        if (!cooldowns.containsKey(key)) {
            return false;
        }
        return cooldowns.get(key) > System.currentTimeMillis();
    }

    public static long getRemainingTime(UUID playerId, String cooldownName) {
        String key = playerId.toString() + ":" + cooldownName;
        if (!isOnCooldown(playerId, cooldownName)) {
            return 0;
        }
        return cooldowns.get(key) - System.currentTimeMillis();
    }

    public static String getRemainingTimeString(UUID playerId, String cooldownName) {
        long remainingMillis = getRemainingTime(playerId, cooldownName);
        if (remainingMillis <= 0) {
            return "0s";
        }

        long seconds = remainingMillis / 1000;
        long minutes = seconds / 60;
        long hours = minutes / 60;
        long days = hours / 24;

        if (days > 0) {
            return days + "d " + (hours % 24) + "h";
        } else if (hours > 0) {
            return hours + "h " + (minutes % 60) + "m";
        } else if (minutes > 0) {
            return minutes + "m " + (seconds % 60) + "s";
        } else {
            return seconds + "s";
        }
    }

    public static void removeCooldown(UUID playerId, String cooldownName) {
        cooldowns.remove(playerId.toString() + ":" + cooldownName);
    }

    public static void clearCooldowns() {
        cooldowns.clear();
    }
}