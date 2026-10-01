package me.EHOT_2025.easyQuests.GUI;

import me.EHOT_2025.easyQuests.EasyQuests;
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

public class QuestGoalsMenu {

    public static void open(Player player, QuestBuilder builder) {
        Inventory inv = Bukkit.createInventory(null, 27, ChatColor.DARK_GRAY + "Настройка целей квеста");

        ItemStack addGoal = new ItemStack(Material.EMERALD);
        ItemMeta addMeta = addGoal.getItemMeta();
        if (addMeta != null) {
            addMeta.setDisplayName(ChatColor.GREEN + "+ Добавить новую цель");
            List<String> lore = new ArrayList<>();
            lore.add(ChatColor.GRAY + "Текущих целей: " + builder.getGoals().size());
            if (builder.getGoals().isEmpty()) {
                lore.add(ChatColor.YELLOW + "⚠ Первая цель обязана быть постоянной!");
            } else {
                lore.add(ChatColor.YELLOW + "▶ Можно сделать поэтапную цепную цель");
            }
            addMeta.setLore(lore);
            addGoal.setItemMeta(addMeta);
        }
        inv.setItem(11, addGoal);

        ItemStack back = new ItemStack(Material.BARRIER);
        ItemMeta backMeta = back.getItemMeta();
        if (backMeta != null) {
            backMeta.setDisplayName(ChatColor.RED + "← Назад к квесту");
            back.setItemMeta(backMeta);
        }
        inv.setItem(15, back);

        player.openInventory(inv);
    }
}