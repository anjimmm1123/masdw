package fish.it.fishIt.data;

public class RarityData {
    private final String name;
    private final String color;
    private final double priceMultiplier;

    public RarityData(String name, String color, double priceMultiplier) {
        this.name = name;
        this.color = color;
        this.priceMultiplier = priceMultiplier;
    }

    public String getName() {
        return name;
    }

    public String getColor() {
        return color;
    }

    public double getPriceMultiplier() {
        return priceMultiplier;
    }
}