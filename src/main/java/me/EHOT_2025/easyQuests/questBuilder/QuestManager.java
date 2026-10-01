package me.EHOT_2025.easyQuests.questBuilder;

import me.EHOT_2025.easyQuests.questBuilder.QuestBuilder;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import java.util.*;

public class QuestManager {
    private final Map<String, QuestBuilder> quests = new HashMap<>();

    private final Map<UUID, String> activePlayerQuests = new HashMap<>();

    private final Map<UUID, Map<UUID, Double>> playerReputations = new HashMap<>();

    public void registerQuest(QuestBuilder quest) {
        quests.put(quest.getId(), quest);
    }

    public QuestBuilder getQuest(String id) {
        return quests.get(id);
    }

    public Collection<QuestBuilder> getAllQuests() {
        return quests.values();
    }

    public List<QuestBuilder> getQuestsForNpc(UUID npcUuid) {
        List<QuestBuilder> result = new ArrayList<>();
        for (QuestBuilder quest : quests.values()) {
            if (npcUuid.equals(quest.getNpcUuid())) {
                result.add(quest);
            }
        }
        return result;
    }

    public double getReputation(Player player, UUID npcUuid) {
        return playerReputations
                .computeIfAbsent(player.getUniqueId(), k -> new HashMap<>())
                .getOrDefault(npcUuid, 0.00);
    }

    public void addReputation(Player player, UUID npcUuid, double amount) {
        UUID uuid = player.getUniqueId();
        playerReputations.putIfAbsent(uuid, new HashMap<>());
        Map<UUID, Double> npcReps = playerReputations.get(uuid);

        double current = npcReps.getOrDefault(npcUuid, 0.00);
        double updated = Math.min(10.00, Math.max(0.00, current + amount));
        npcReps.put(npcUuid, updated);
    }

    public boolean takeQuest(Player player, String questId) {
        QuestBuilder quest = quests.get(questId);
        if (quest == null) return false;

        double currentRep = getReputation(player, quest.getNpcUuid());
        if (currentRep < quest.getRequiredReputation()) {
            return false;
        }

        activePlayerQuests.put(player.getUniqueId(), questId);
        return true;
    }

    public String getActiveQuest(Player player) {
        return activePlayerQuests.get(player.getUniqueId());
    }

    public void completeActiveQuest(Player player) {
        activePlayerQuests.remove(player.getUniqueId());
    }

    public void rewardPlayer(Player player, QuestBuilder quest) {
        for (QuestReward reward : quest.getRewards()) {
            if (reward.isReputation()) {
                addReputation(player, quest.getNpcUuid(), reward.getReputationAmount());
                player.sendMessage(ChatColor.GOLD + "+ " + reward.getReputationAmount() + " УР с квестодателем!");
            } else {
                player.getInventory().addItem(new org.bukkit.inventory.ItemStack(reward.getMaterial(), reward.getAmount()));
                player.sendMessage(ChatColor.GREEN + "Вы получили награду: " + reward.getAmount() + "x " + reward.getMaterial().name());
            }
        }

        completeActiveQuest(player);
    }
}