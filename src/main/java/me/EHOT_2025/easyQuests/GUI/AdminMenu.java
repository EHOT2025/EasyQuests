package me.EHOT_2025.easyQuests.GUI;

import me.EHOT_2025.easyQuests.EasyQuests;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

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
    }

    @Override
    public void handleMenuClick(InventoryClickEvent event) {
        int slot = event.getRawSlot();

        if (slot == 11) {
            player.closeInventory();
            player.sendMessage(EasyQuests.getPrefix() + ChatColor.GOLD + "Укажите UUID NPC: /eq <UUID> (UUID можете скопировать в редакторе NPC):");
        }

        if (slot == 15) {
            player.closeInventory();
            player.sendMessage(EasyQuests.getPrefix() + "Список квестодателей в разработке!");
        }
    }
}