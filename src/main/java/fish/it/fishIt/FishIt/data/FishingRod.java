package fish.it.fishIt.data;

import org.bukkit.Material;

public class FishingRod {
    private final String id;
    private final String displayName;
    private final Material material;
    private final double luckMultiplier;
    private final int durability;
    private final double price;

    public FishingRod(String id, String displayName, Material material, double luckMultiplier, int durability, double price) {
        this.id = id;
        this.displayName = displayName;
        this.material = material;
        this.luckMultiplier = luckMultiplier;
        this.durability = durability;
        this.price = price;
    }

    public String getId() {
        return id;
    }

    public String getDisplayName() {
        return displayName;
    }

    public Material getMaterial() {
        return material;
    }

    public double getLuckMultiplier() {
        return luckMultiplier;
    }

    public int getDurability() {
        return durability;
    }

    public double getPrice() {
        return price;
    }
}