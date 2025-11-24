package fish.it.fishIt.managers;

import fish.it.fishIt.FishIt;
import fish.it.fishIt.data.FisherPlayer;
import fish.it.fishIt.data.QuestData;
import org.bukkit.entity.Player;

import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

public class QuestManager {

    private final FishIt plugin;
    private final Map<UUID, QuestData> activeQuests = new ConcurrentHashMap<>();

    public QuestManager(FishIt plugin) {
        this.plugin = plugin;
    }

    public void assignDailyQuest(Player player) {
        List<Map<?, ?>> quests = plugin.getConfigManager().getConfig("quests").getMapList("daily_quests");
        Map<?, ?> randomQuestMap = quests.get((int) (Math.random() * quests.size()));
        
        String type = (String) randomQuestMap.get("type");
        String target = (String) randomQuestMap.get("target");
        int amount = (int) randomQuestMap.get("amount");
        int reward = (int) randomQuestMap.get("reward");

        QuestData quest = new QuestData(type, target, amount, reward);
        activeQuests.put(player.getUniqueId(), quest);
        player.sendMessage(plugin.getMessageUtils().colorize("&eQuest harian baru: Tangkap " + amount + "x " + target + ". Hadiah: " + reward + " exp!"));
    }

    public void progressQuest(Player player, String fishId) {
        QuestData quest = activeQuests.get(player.getUniqueId());
        if (quest != null && quest.getType().equalsIgnoreCase("FISH") && quest.getTarget().equalsIgnoreCase(fishId)) {
            quest.incrementProgress();
            if (quest.isCompleted()) {
                FisherPlayer fisherPlayer = plugin.getPlayerDataManager().getPlayer(player.getUniqueId());
                plugin.getLevelSystem().addExperience(player, quest.getReward());
                player.sendMessage(plugin.getMessageUtils().colorize("&aQuest selesai! Anda mendapatkan " + quest.getReward() + " exp."));
                activeQuests.remove(player.getUniqueId());
            }
        }
    }
}