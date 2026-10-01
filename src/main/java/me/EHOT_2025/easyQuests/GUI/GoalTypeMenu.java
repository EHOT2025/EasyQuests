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

public class GoalTypeMenu {

    public static void open(Player player) {
        Inventory inv = Bukkit.createInventory(null, 27, ChatColor.DARK_GRAY + "Выберите тип цели");

        inv.setItem(10, createItem(Material.COMPASS, ChatColor.YELLOW + "1. Поиск", "Найти моба, структуру, биом или данж"));
        inv.setItem(11, createItem(Material.IRON_SWORD, ChatColor.YELLOW + "2. Убийство", "Уничтожить мобов (с указанием кол-ва)"));
        inv.setItem(12, createItem(Material.DIAMOND, ChatColor.YELLOW + "3. Добыча", "Получить предметы в инвентарь"));
        inv.setItem(13, createItem(Material.FURNACE, ChatColor.YELLOW + "4. Создание", "Скрафтить, переплавить или испечь"));
        inv.setItem(14, createItem(Material.HOPPER, ChatColor.YELLOW + "5. Передать", "Сдать собранное квестодателю лично"));

        player.openInventory(inv);
    }

    private static ItemStack createItem(Material mat, String name, String desc) {
        ItemStack item = new ItemStack(mat);
        ItemMeta meta = item.getItemMeta();
        if (meta != null) {
            meta.setDisplayName(name);
            item.setItemMeta(meta);
        }
        return item;
    }
}