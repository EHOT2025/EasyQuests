package me.EHOT_2025.easyQuests.listeners;

import me.EHOT_2025.easyQuests.EasyQuests;
import me.EHOT_2025.easyQuests.questBuilder.QuestManager;
import me.EHOT_2025.easyQuests.questBuilder.QuestBuilder;
import me.EHOT_2025.easyQuests.questBuilder.QuestGoal;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.inventory.CraftItemEvent;
import org.bukkit.event.player.PlayerPickupItemEvent;

public class QuestTrackerListener implements Listener {

    private final EasyQuests plugin;
    private final QuestManager questManager;

    public QuestTrackerListener(EasyQuests plugin, QuestManager questManager) {
        this.plugin = plugin;
        this.questManager = questManager;
    }

    @EventHandler
    public void onEntityDeath(EntityDeathEvent event) {
        if (event.getEntity().getKiller() == null) return;
        Player player = event.getEntity().getKiller();

        String activeQuestId = questManager.getActiveQuest(player);
        if (activeQuestId == null) return;

        QuestBuilder quest = questManager.getQuest(activeQuestId);
        if (quest == null) return;

        String mobName = event.getEntity().getType().name();

        for (QuestGoal goal : quest.getGoals()) {
            if (goal.getType() == QuestGoal.GoalType.KILL) {
                if (goal.getTarget().equalsIgnoreCase(mobName)) {
                    player.sendMessage(EasyQuests.getPrefix() + ChatColor.GREEN + "Процесс цели убийства засчитан!");
                }
            }
        }
    }
}