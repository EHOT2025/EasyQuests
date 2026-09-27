package me.EHOT_2025.easyQuests.GUI;

import me.EHOT_2025.easyQuests.EasyQuests;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class MainMenu extends Template {

    private final Player player;
    private final boolean openedByNpc;

    public static boolean isAdmin (CommandSender sender) {
        if (!(sender instanceof Player)) {
            return true;
        }
        Player player = (Player) sender;
        return player.hasPermission("easyquests.admin") || player.isOp();
    }

    public MainMenu(Player player,  boolean openedByNpc) {
        super(player, 27, "&0Квесты");
        this.player = player;
        this.openedByNpc = openedByNpc;
    }

    public MainMenu(Player player) {
        this(player, false);
    }

    @Override
    public void setMenuItems() {
        if (openedByNpc) {
            ItemStack questsItem = new ItemStack(Material.BOOK);
            ItemMeta meta0 = questsItem.getItemMeta();
            if (meta0 != null) {
                meta0.setDisplayName(ChatColor.GREEN + "Посмотреть задания");
                questsItem.setItemMeta(meta0);
            }
            inventory.setItem(0, questsItem);
        }

        ItemStack questsItem = new ItemStack(Material.PLAYER_HEAD); // TODO: Сейчас голова дефолтного стива, сделать чтобы отображало голову игрока.
        ItemMeta meta1 = questsItem.getItemMeta();
        if (meta1 != null) {
            meta1.setDisplayName(ChatColor.GREEN + "Статистика");
            questsItem.setItemMeta(meta1);
        }
        inventory.setItem(11, questsItem);

        ItemStack activeItem = new ItemStack(Material.COMPASS);
        ItemMeta meta2 = activeItem.getItemMeta();
        if (meta2 != null) {
            meta2.setDisplayName(ChatColor.YELLOW + "Активное задание");
            activeItem.setItemMeta(meta2);
        }
        inventory.setItem(13, activeItem);

        ItemStack helpItem = new ItemStack(Material.PAPER);
        ItemMeta meta3 = helpItem.getItemMeta();
        if (meta3 != null) {
            meta3.setDisplayName(ChatColor.AQUA + "Помощь по заданиям");
            helpItem.setItemMeta(meta3);
        }
        inventory.setItem(15, helpItem);

        if (isAdmin(player)) {
            ItemStack adminItem = new ItemStack(Material.NETHER_STAR);
            ItemMeta meta4 = adminItem.getItemMeta();
            if (meta4 != null) {
                meta4.setDisplayName(ChatColor.RED + "Админ-панель");
                adminItem.setItemMeta(meta4);
            }
            inventory.setItem(22, adminItem);
        }
    }

    @Override
    public void handleMenuClick(InventoryClickEvent event) {

        int slot = event.getRawSlot();

        if (slot == 0 && openedByNpc) {
            player.closeInventory();
            player.sendMessage(ChatColor.GRAY + EasyQuests.getPrefix() + "Раздел с квестами в разработке.");
        }

        if (slot == 11) {
            player.closeInventory();
            player.sendMessage(ChatColor.GRAY + EasyQuests.getPrefix() + "Раздел со статистикой в разработке.");
        } else if (slot == 13) {
            player.closeInventory();
            player.sendMessage(ChatColor.GRAY + EasyQuests.getPrefix() + "Активное задание в разработке.");
        } else if (slot == 15) {
            player.closeInventory();
            player.sendMessage(ChatColor.GRAY + EasyQuests.getPrefix() + "Помощь по заданиям в разработке.");
        } else if (slot == 22 && (isAdmin(player))) {
            AdminMenu adminMenu = new AdminMenu(player);
            adminMenu.open();
        }
    }
}