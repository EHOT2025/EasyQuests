package me.EHOT_2025.easyQuests.GUI;

import me.EHOT_2025.easyQuests.questBuilder.QuestBuilder;
import me.EHOT_2025.easyQuests.questBuilder.QuestGoal;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;

public class GoalsListMenu {

    public static void open(Player player, QuestBuilder builder) {
        Inventory inv = Bukkit.createInventory(null, 27, ChatColor.DARK_GRAY + "Цели квеста: Список");

        ItemStack add = new ItemStack(Material.EMERALD);
        ItemMeta addMeta = add.getItemMeta();
        if (addMeta != null) {
            addMeta.setDisplayName(ChatColor.GREEN + "+ Добавить цель");
            List<String> lore = new ArrayList<>();
            lore.add(ChatColor.GRAY + "Всего целей: " + builder.getGoals().size());
            if (builder.getGoals().isEmpty()) {
                lore.add(ChatColor.YELLOW + "⚠ Первая цель — постоянная (обязательна).");
            } else {
                lore.add(ChatColor.YELLOW + "▶ Последующие можно сделать поэтапными.");
            }
            addMeta.setLore(lore);
            add.setItemMeta(addMeta);
        }
        inv.setItem(11, add);

        int slot = 19;
        for (int i = 0; i < builder.getGoals().size(); i++) {
            if (slot > 25) break;
            QuestGoal goal = builder.getGoals().get(i);

            ItemStack goalItem = new ItemStack(Material.PAPER);
            ItemMeta gMeta = goalItem.getItemMeta();
            if (gMeta != null) {
                gMeta.setDisplayName(ChatColor.YELLOW + "Цель #" + (i + 1) + ": " + goal.getType().name());
                List<String> lore = new ArrayList<>();
                lore.add(ChatColor.GRAY + "Цель/ID: " + ChatColor.WHITE + goal.getTarget());
                lore.add(ChatColor.GRAY + "Количество: " + ChatColor.WHITE + goal.getAmount());
                lore.add(ChatColor.GRAY + "Поэтапная: " + (goal.isStaged() ? ChatColor.GREEN + "Да" : ChatColor.RED + "Нет"));
                if (goal.isStaged() && goal.getParentTarget() != null) {
                    lore.add(ChatColor.GRAY + "Привязана к: " + ChatColor.YELLOW + goal.getParentTarget());
                }
                lore.add("");
                lore.add(ChatColor.RED + "▶ Нажмите, чтобы удалить");
                gMeta.setLore(lore);
                goalItem.setItemMeta(gMeta);
            }
            inv.setItem(slot, goalItem);
            slot++;
        }

        ItemStack back = new ItemStack(Material.BARRIER);
        ItemMeta bMeta = back.getItemMeta();
        if (bMeta != null) {
            bMeta.setDisplayName(ChatColor.RED + "← Назад к квесту");
            back.setItemMeta(bMeta);
        }
        inv.setItem(15, back);

        player.openInventory(inv);
    }
}