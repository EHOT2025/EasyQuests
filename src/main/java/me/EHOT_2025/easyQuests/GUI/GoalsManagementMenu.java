package me.EHOT_2025.easyQuests.GUI;

import me.EHOT_2025.easyQuests.questBuilder.QuestBuilder;
import me.EHOT_2025.easyQuests.questBuilder.QuestBuilderManager;
import me.EHOT_2025.easyQuests.questBuilder.QuestGoal;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;

public class GoalsManagementMenu extends Template {

    private final Player player;

    public GoalsManagementMenu(Player player) {
        super(player, 54, "&8Управление целями квеста");
        this.player = player;
    }

    @Override
    public void setMenuItems() {
        QuestBuilder builder = QuestBuilderManager.getBuilder(player.getUniqueId());

        ItemStack addBtn = new ItemStack(Material.EMERALD);
        ItemMeta addMeta = addBtn.getItemMeta();
        if (addMeta != null) {
            addMeta.setDisplayName(ChatColor.GREEN + "+ Добавить цель");
            List<String> lore = new ArrayList<>();
            lore.add(ChatColor.GRAY + "Всего целей: " + builder.getGoals().size());
            if (builder.getGoals().isEmpty()) {
                lore.add(ChatColor.YELLOW + "⚠ Первая цель должна быть постоянной.");
            }
            addMeta.setLore(lore);
            addBtn.setItemMeta(addMeta);
        }
        inventory.setItem(11, addBtn);

        // Кнопка: Удалить цель
        ItemStack delBtn = new ItemStack(Material.REDSTONE);
        ItemMeta delMeta = delBtn.getItemMeta();
        if (delMeta != null) {
            delMeta.setDisplayName(ChatColor.RED + "- Удалить цель");
            delMeta.setLore(List.of(ChatColor.GRAY + "Нажмите, чтобы удалить последнюю цель"));
            delBtn.setItemMeta(delMeta);
        }
        inventory.setItem(15, delBtn);

        int slot = 19;
        for (int i = 0; i < builder.getGoals().size(); i++) {
            if (slot > 43) break;
            QuestGoal goal = builder.getGoals().get(i);

            ItemStack item = new ItemStack(Material.PAPER);
            ItemMeta meta = item.getItemMeta();
            if (meta != null) {
                meta.setDisplayName(ChatColor.YELLOW + "Цель #" + (i + 1) + " (" + goal.getType().name() + ")");
                List<String> lore = new ArrayList<>();
                lore.add(ChatColor.GRAY + "Значение/ID: " + ChatColor.WHITE + goal.getTarget());
                lore.add(ChatColor.GRAY + "Количество: " + ChatColor.WHITE + goal.getAmount());
                lore.add(ChatColor.GRAY + "Поэтапная: " + (goal.isStaged() ? ChatColor.GREEN + "Да" : ChatColor.RED + "Нет"));
                meta.setLore(lore);
                item.setItemMeta(meta);
            }
            inventory.setItem(slot, item);
            slot++;
        }

        ItemStack back = new ItemStack(Material.BARRIER);
        ItemMeta bMeta = back.getItemMeta();
        if (bMeta != null) {
            bMeta.setDisplayName(ChatColor.RED + "← Назад к конструктору");
            back.setItemMeta(bMeta);
        }
        inventory.setItem(49, back);
    }

    @Override
    public void handleMenuClick(InventoryClickEvent event) {
        int slot = event.getRawSlot();
        QuestBuilder builder = QuestBuilderManager.getBuilder(player.getUniqueId());

        if (slot == 49) {
            new CreateQuestMenu(player).open();
        } else if (slot == 11) {
            new GoalTypeSelectionMenu(player).open();
        } else if (slot == 15) {
            if (!builder.getGoals().isEmpty()) {
                builder.getGoals().remove(builder.getGoals().size() - 1);
                player.sendMessage(ChatColor.YELLOW + "Последняя цель удалена.");
                new GoalsManagementMenu(player).open();
            } else {
                player.sendMessage(ChatColor.RED + "Список целей уже пуст!");
            }
        }
    }
}