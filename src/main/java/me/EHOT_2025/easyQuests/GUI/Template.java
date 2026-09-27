package me.EHOT_2025.easyQuests.GUI;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public abstract class Template implements InventoryHolder {
    protected Inventory inventory;
    protected Player player;

    public Template(Player player, int size, String title) {
        this.player = player;
        this.inventory = Bukkit.createInventory(this, size, ChatColor.translateAlternateColorCodes('&', title));
    }

    public abstract void setMenuItems();

    public void open() {
        setMenuItems();
        fillEmptySlots();
        addCloseButton();
        player.openInventory(inventory);
    }

    protected void fillEmptySlots() {
        ItemStack glass = new ItemStack(Material.GRAY_STAINED_GLASS_PANE);
        ItemMeta meta = glass.getItemMeta();
        if (meta != null) {
            meta.setDisplayName(" ");
            glass.setItemMeta(meta);
        }

        for (int i = 0; i < inventory.getSize() - 1; i++) {
            if (inventory.getItem(i) == null) {
                inventory.setItem(i, glass);
            }
        }
    }

    protected void addCloseButton() {
        int lastSlot = inventory.getSize() - 1;
        ItemStack closeButton = new ItemStack(Material.OAK_DOOR);
        ItemMeta meta = closeButton.getItemMeta();
        if (meta != null) {
            meta.setDisplayName(ChatColor.RED + "Закрыть");
            closeButton.setItemMeta(meta);
        }
        inventory.setItem(lastSlot, closeButton);
    }

    public void handleClick(InventoryClickEvent event) {
        event.setCancelled(true);

        int slot = event.getRawSlot();
        if (slot == inventory.getSize() - 1) {
            player.closeInventory();
            return;
        }

        handleMenuClick(event);
    }

    public abstract void handleMenuClick(InventoryClickEvent event);

    @Override
    public Inventory getInventory() {
        return inventory;
    }
}
