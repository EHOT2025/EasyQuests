package me.EHOT_2025.easyQuests.listeners;

import me.EHOT_2025.easyQuests.EasyQuests;
import me.EHOT_2025.easyQuests.questBuilder.QuestBuilder;
import me.EHOT_2025.easyQuests.questBuilder.QuestCondition;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.inventory.ItemStack;

public class QuestConditionsListener implements Listener {

    private final EasyQuests plugin;

    public QuestConditionsListener(EasyQuests plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onPlayerAttack(EntityDamageByEntityEvent event) {
        if (!(event.getEntity() instanceof Player)) return;
        //TODO: Здесь нужно реализовать логику блокировки прогресса целей, если нарушены условия
    }

    public static boolean checkConditions(Player player, QuestBuilder quest) {
        for (QuestCondition condition : quest.getConditions()) {
            if (condition.getConditionKey().equalsIgnoreCase("NO_ARMOR")) {
                for (ItemStack armor : player.getInventory().getArmorContents()) {
                    if (armor != null && armor.getType() != Material.AIR) {
                        return false;
                    }
                }
            }
            if (condition.getConditionKey().equalsIgnoreCase("TIME_DAY")) {
                long time = player.getWorld().getTime();
                if (time >= 12300 && time < 23850) {
                    return false;
                }
            }
        }
        return true;
    }
}