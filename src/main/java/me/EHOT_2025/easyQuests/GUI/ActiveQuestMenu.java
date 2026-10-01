package me.EHOT_2025.easyQuests.GUI;

import me.EHOT_2025.easyQuests.EasyQuests;
import me.EHOT_2025.easyQuests.questBuilder.QuestBuilder;
import me.EHOT_2025.easyQuests.questBuilder.QuestManager;
import me.EHOT_2025.easyQuests.questBuilder.QuestReward;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;

public class ActiveQuestMenu {

    public static void open(Player player, QuestManager questManager) {
        Inventory inv = Bukkit.createInventory(null, 27, ChatColor.DARK_GRAY + "Активное задание");

        String activeQuestId = questManager.getActiveQuest(player);
        if (activeQuestId == null) {
            ItemStack empty = new ItemStack(Material.BARRIER);
            ItemMeta meta = empty.getItemMeta();
            if (meta != null) {
                meta.setDisplayName(ChatColor.RED + "У вас нет активных заданий");
                empty.setItemMeta(meta);
            }
            inv.setItem(13, empty);
        } else {
            QuestBuilder quest = questManager.getQuest(activeQuestId);

            ItemStack info = new ItemStack(Material.BOOK);
            ItemMeta meta = info.getItemMeta();
            if (meta != null) {
                meta.setDisplayName(ChatColor.YELLOW + quest.getName());
                List<String> lore = new ArrayList<>();
                lore.add(ChatColor.GRAY + quest.getDescription());
                lore.add("");
                lore.add(ChatColor.GREEN + "▶ Нажмите внизу, чтобы сдать квест!");
                meta.setLore(lore);
                info.setItemMeta(meta);
            }
            inv.setItem(13, info);

            ItemStack complete = new ItemStack(Material.EMERALD_BLOCK);
            ItemMeta compMeta = complete.getItemMeta();
            if (compMeta != null) {
                compMeta.setDisplayName(ChatColor.GREEN + "Завершить и сдать квест");
                complete.setItemMeta(compMeta);
            }
            inv.setItem(22, complete);
        }

        player.openInventory(inv);
    }
}