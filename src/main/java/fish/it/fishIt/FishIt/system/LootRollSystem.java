package fish.it.fishIt.system;
import fish.it.fishIt.FishIt;
import fish.it.fishIt.data.FishData;

import java.util.List;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

public class LootRollSystem {

    private final FishIt plugin;
    private final Random random;

    public LootRollSystem(FishIt plugin) {
        this.plugin = plugin;
        this.random = ThreadLocalRandom.current();
    }

    public FishData rollFish(List<FishData> fishPool, double luckModifier) {
        if (fishPool.isEmpty()) {
            return null;
        }

        double totalWeight = fishPool.stream()
                .mapToDouble(fish -> fish.getWeight() + (fish.getWeight() * luckModifier))
                .sum();

        double randomWeight = random.nextDouble() * totalWeight;

        for (FishData fish : fishPool) {
            double modifiedWeight = fish.getWeight() + (fish.getWeight() * luckModifier);
            randomWeight -= modifiedWeight;
            if (randomWeight <= 0) {
                return fish;
            }
        }

        return fishPool.get(random.nextInt(fishPool.size()));
    }
}