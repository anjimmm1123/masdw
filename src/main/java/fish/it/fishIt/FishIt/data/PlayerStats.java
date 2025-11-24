package fish.it.fishIt.data;

import java.util.HashMap;
import java.util.Map;

public class PlayerStats {
    private final Map<String, Integer> fishCaught;
    private int totalFishCaught;
    private double totalEarnings;
    private int biggestCatch;

    public PlayerStats() {
        this.fishCaught = new HashMap<>();
        this.totalFishCaught = 0;
        this.totalEarnings = 0;
        this.biggestCatch = 0;
    }

    public Map<String, Integer> getFishCaught() {
        return fishCaught;
    }

    public int getTotalFishCaught() {
        return totalFishCaught;
    }

    public void setTotalFishCaught(int totalFishCaught) {
        this.totalFishCaught = totalFishCaught;
    }

    public double getTotalEarnings() {
        return totalEarnings;
    }

    public void setTotalEarnings(double totalEarnings) {
        this.totalEarnings = totalEarnings;
    }

    public int getBiggestCatch() {
        return biggestCatch;
    }

    public void setBiggestCatch(int biggestCatch) {
        this.biggestCatch = biggestCatch;
    }

    public void addFishCaught(String fishId, int size) {
        fishCaught.put(fishId, fishCaught.getOrDefault(fishId, 0) + 1);
        totalFishCaught++;
        if (size > biggestCatch) {
            biggestCatch = size;
        }
    }

    public void addEarnings(double amount) {
        this.totalEarnings += amount;
    }
}