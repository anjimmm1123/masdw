package fish.it.fishIt.data;

public class BaitData {
    private final String id;
    private final String displayName;
    private final double luckModifier;
    private final int charges;

    public BaitData(String id, String displayName, double luckModifier, int charges) {
        this.id = id;
        this.displayName = displayName;
        this.luckModifier = luckModifier;
        this.charges = charges;
    }

    public String getId() {
        return id;
    }

    public String getDisplayName() {
        return displayName;
    }

    public double getLuckModifier() {
        return luckModifier;
    }

    public int getCharges() {
        return charges;
    }
}