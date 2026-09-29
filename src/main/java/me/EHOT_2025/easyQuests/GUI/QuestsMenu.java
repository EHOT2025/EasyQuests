package me.EHOT_2025.easyQuests.GUI;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;

public class QuestsMenu extends Template{

    private final Player player;

    public QuestsMenu(Player player){
        super(player, 27, "&0Задания");
        this.player = player;
    }

    @Override
    public void setMenuItems() {
//        ItemStack availableQuest = new ItemStack(Material.LIME_TERRACOTTA);
//        ItemMeta meta1 = availableQuest.getItemMeta();
//        if (meta1 != null){
//            meta1.setDisplayName(ChatColor.GREEN + "Знакомство");
//            List<String> lore1 = new ArrayList<>();
//            lore1.add(ChatColor.GRAY + "Описание: Для начала нашего сотрудничества давай ты покажешь своё умение быстро добывать то что необходимо.");
//            lore1.add("");
//            lore1.add(ChatColor.GRAY + "Цели:");
//            lore1.add(ChatColor.GRAY + " - Собрать: железо [40].");
//            lore1.add(ChatColor.GRAY + " - Собрать: алмазы [10].");
//            lore1.add(ChatColor.GRAY + " - Передать: железо [40].");
//            lore1.add(ChatColor.GRAY + " - Передать: алмазы [10].");
//            lore1.add("");
//            lore1.add(ChatColor.GRAY + "Условия:");
//            lore1.add(ChatColor.GRAY + " - не погибать.");
//            lore1.add("");
//            lore1.add(ChatColor.GRAY + "Срок:");
//            lore1.add(ChatColor.GRAY + " - 20 минут");
//            lore1.add("");
//            lore1.add(ChatColor.GRAY + "Награды за выполнение:");
//            lore1.add(ChatColor.GRAY + " - +0.5 УР у Ферри");
//            lore1.add(ChatColor.GRAY + " - Железная кирка [3]");
//            lore1.add(ChatColor.GRAY + " - Железный шлем");
//            lore1.add(ChatColor.GRAY + " - Железный нагрудник");
//            lore1.add(ChatColor.GRAY + " - Железные поножи");
//            lore1.add(ChatColor.GRAY + " - Железные ботинки");
//            lore1.add(ChatColor.GRAY + " - Железный меч");
//            lore1.add(ChatColor.GRAY + "");
//            lore1.add(ChatColor.GRAY + "Штраф за провал:");
//            lore1.add(ChatColor.GRAY + "- -0.2 УР у Ферри");
//            lore1.add(ChatColor.GREEN + "(КЛИК) Берусь.");
//            meta1.setLore(lore1);
//            availableQuest.setItemMeta(meta1);
//        }
//        inventory.setItem(11, availableQuest);
//
//        ItemStack lockedQuest = new ItemStack(Material.CYAN_TERRACOTTA);
//        ItemMeta meta2 = lockedQuest.getItemMeta();
//        if (meta2 != null) {
//            meta2.setDisplayName(ChatColor.RED + "Тайны старой шахты");
//            List<String> lore2 = new ArrayList<>();
//            lore2.add(ChatColor.GRAY + "Описание: Исследуйте заброшенные тоннели.");
//            lore2.add("");
//            lore2.add(ChatColor.DARK_RED + "Требования для разблокировки:");
//            lore2.add(ChatColor.RED + " ✖ Выполнить квест: «Свежая партия»");
//            lore2.add(ChatColor.RED + " ✖ Репутация с квестодателем: 4 / 4");
//            meta2.setLore(lore2);
//            lockedQuest.setItemMeta(meta2);
//        }
//        inventory.setItem(15, lockedQuest);
    }

    @Override
    public void handleMenuClick(InventoryClickEvent event) {
        int slot = event.getRawSlot();

        if (slot == 11) {
            player.closeInventory();
            player.sendMessage(ChatColor.GREEN + "Вы успешно приняли задание: Знакомство");
        } else if (slot == 15) {
            player.closeInventory();
            player.sendMessage(ChatColor.RED + "Задание вам недоступно, выполните требования для разблокировки.");
        }
    }
}
