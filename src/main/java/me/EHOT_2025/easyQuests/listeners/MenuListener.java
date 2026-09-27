package me.EHOT_2025.easyQuests.listeners;

import me.EHOT_2025.easyQuests.EasyQuests;
import me.EHOT_2025.easyQuests.GUI.MainMenu;
import me.EHOT_2025.easyQuests.GUI.Template;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.InventoryHolder;

public class MenuListener implements Listener {

    @EventHandler
    public void onInventoryClick(InventoryClickEvent event) {
        InventoryHolder holder = event.getInventory().getHolder();

        if (holder instanceof Template) {
            Template menu = (Template) holder;
            menu.handleClick(event);
        }
    }

    @EventHandler
    public void onEntityClick(org.bukkit.event.player.PlayerInteractEntityEvent event) {
        String clickedUuid = event.getRightClicked().getUniqueId().toString();

        if (EasyQuests.getInstance().getDatabaseManager().isQuestNpc(clickedUuid)) {
            event.setCancelled(true);
            MainMenu menu = new MainMenu(event.getPlayer(), true);
            menu.open();
        }
    }
}