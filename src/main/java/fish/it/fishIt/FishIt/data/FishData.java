package fish.it.fishIt.data;

import java.util.List;

public class FishData {
    private final String id;
    private final String displayName;
    private final String rarity;
    private final double price;
    private final int minSize;
    private final int maxSize;
    private final int weight;
    private final int exp;
    private final List<String> regions;

    public FishData(String id, String displayName, String rarity, double price, int minSize, int maxSize, int weight, int exp, List<String> regions) {
        this.id = id;
        this.displayName = displayName;
        this.rarity = rarity;
        this.price = price;
        this.minSize = minSize;
        this.maxSize = maxSize;
        this.weight = weight;
        this.exp = exp;
        this.regions = regions;
    }

    public String getId() {
        return id;
    }

    public String getDisplayName() {
        return displayName;
    }

    public String getRarity() {
        return rarity;
    }

    public double getPrice() {
        return price;
    }

    public int getMinSize() {
        return minSize;
    }

    public int getMaxSize() {
        return maxSize;
    }

    public int getWeight() {
        return weight;
    }

    public int getExp() {
        return exp;
    }

    public List<String> getRegions() {
        return regions;
    }
}