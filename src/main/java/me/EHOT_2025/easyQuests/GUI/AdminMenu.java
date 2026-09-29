package me.EHOT_2025.easyQuests.GUI;

import me.EHOT_2025.easyQuests.EasyQuests;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;

import static me.EHOT_2025.easyQuests.GUI.MainMenu.isAdmin;

public class AdminMenu extends Template {

    public AdminMenu(Player player) {
        super(player, 27, "&0Админская панель");

        if (!isAdmin(player)) {
            player.closeInventory();
            player.sendMessage(ChatColor.RED + "У вас нет доступа к этому меню!");
        }
    }

    @Override
    public void setMenuItems() {
        ItemStack addNpcItem = new ItemStack(Material.VEX_SPAWN_EGG);
        ItemMeta meta0 = addNpcItem.getItemMeta();
        if (meta0 != null) {
            meta0.setDisplayName(ChatColor.GREEN + "Создать квестодателя");
            addNpcItem.setItemMeta(meta0);
        }

        inventory.setItem(11, addNpcItem);

        ItemStack npcsListItem = new ItemStack(Material.VILLAGER_SPAWN_EGG);
        ItemMeta meta1 = npcsListItem.getItemMeta();
        if (meta1 != null) {
            meta1.setDisplayName(ChatColor.LIGHT_PURPLE + "Квестодатели");
            npcsListItem.setItemMeta(meta1);
        }
        inventory.setItem(15, npcsListItem);

        ItemStack createQuestItem = new ItemStack(Material.WRITABLE_BOOK);
        ItemMeta createMeta = createQuestItem.getItemMeta();
        if (createMeta != null) {
            createMeta.setDisplayName(ChatColor.GREEN + "Создать квест");
            List<String> lore = new ArrayList<>();
            lore.add(ChatColor.GRAY + "Запустить конструктор нового квеста");
            createMeta.setLore(lore);
            createQuestItem.setItemMeta(createMeta);
        }
        inventory.setItem(20, createQuestItem);

        ItemStack editQuestItem = new ItemStack(Material.ANVIL);
        ItemMeta editMeta = editQuestItem.getItemMeta();
        if (editMeta != null) {
            editMeta.setDisplayName(ChatColor.YELLOW + "Редактировать квест");
            editQuestItem.setItemMeta(editMeta);
        }
        inventory.setItem(22, editQuestItem);

        ItemStack deleteQuestItem = new ItemStack(Material.LAVA_BUCKET);
        ItemMeta deleteMeta = deleteQuestItem.getItemMeta();
        if (deleteMeta != null) {
            deleteMeta.setDisplayName(ChatColor.RED + "Удалить квест");
            deleteQuestItem.setItemMeta(deleteMeta);
        }
        inventory.setItem(24, deleteQuestItem);
    }

    @Override
    public void handleMenuClick(InventoryClickEvent event) {
        int slot = event.getRawSlot();

        if (slot == 11) {
            player.closeInventory();
            player.sendMessage(EasyQuests.getPrefix() + ChatColor.GOLD + "Укажите UUID и имя NPC: /eq <UUID> <имя> (UUID можете скопировать в редакторе NPC):");
        }

        if (slot == 15) {
            QuestGiversMenu giversMenu = new QuestGiversMenu(player);
            giversMenu.open();
        }

        if (slot == 20) {
            new CreateQuestMenu(player).open();
        }
    }
}