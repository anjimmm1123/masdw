package fish.it.fishIt.data;

public class QuestData {
    private final String type;
    private final String target;
    private final int amount;
    private final int reward;
    private int progress;

    public QuestData(String type, String target, int amount, int reward) {
        this.type = type;
        this.target = target;
        this.amount = amount;
        this.reward = reward;
        this.progress = 0;
    }

    public String getType() {
        return type;
    }

    public String getTarget() {
        return target;
    }

    public int getAmount() {
        return amount;
    }

    public int getReward() {
        return reward;
    }

    public int getProgress() {
        return progress;
    }

    public void incrementProgress() {
        this.progress++;
    }

    public boolean isCompleted() {
        return progress >= amount;
    }
}