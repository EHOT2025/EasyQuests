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

    }

    @Override
    public void handleMenuClick(InventoryClickEvent event) {
        int slot = event.getRawSlot();

        if (slot == 11) {
            player.closeInventory();
            player.sendMessage(ChatColor.GREEN + "Вы успешно приняли задание: " );
        } else if (slot == 15) {
            player.closeInventory();
            player.sendMessage(ChatColor.RED + "Задание вам недоступно, выполните требования для разблокировки.");
        }
    }
}
