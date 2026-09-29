package me.EHOT_2025.easyQuests.GUI;

import me.EHOT_2025.easyQuests.EasyQuests;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public class QuestGiversMenu extends Template {

    private final Player player;

    public QuestGiversMenu(Player player) {
        super(player, 54, "&0Список квестодателей");
        this.player = player;
    }

    @Override
    public void setMenuItems() {
        Map<UUID, String> givers = EasyQuests.getInstance().getDatabaseManager().getQuestNpcs();

        int slot = 0;
        if (givers != null) {
            for (Map.Entry<UUID, String> entry : givers.entrySet()) {
                if (slot >= 45) break;

                UUID npcUuid = entry.getKey();
                String npcName = entry.getValue();

                ItemStack headItem = new ItemStack(Material.PLAYER_HEAD);
                SkullMeta meta = (SkullMeta) headItem.getItemMeta();

                if (meta != null) {
                    meta.setDisplayName(ChatColor.YELLOW + npcName);

                    List<String> lore = new ArrayList<>();
                    lore.add(ChatColor.GRAY + "UUID: " + npcUuid.toString());
                    lore.add("");
                    lore.add(ChatColor.GREEN + "▶ Нажмите для управления квестами");
                    meta.setLore(lore);

                    headItem.setItemMeta(meta);
                }

                inventory.setItem(slot, headItem);
                slot++;
            }
        }

        ItemStack backItem = new ItemStack(Material.BARRIER);
        ItemMeta backMeta = backItem.getItemMeta();
        if (backMeta != null) {
            backMeta.setDisplayName(ChatColor.RED + "Назад в админ-панель");
            backItem.setItemMeta(backMeta);
        }
        inventory.setItem(49, backItem);
    }

    @Override
    public void handleMenuClick(InventoryClickEvent event) {
        int slot = event.getRawSlot();

        if (slot == 49) {
            AdminMenu adminMenu = new AdminMenu(player);
            adminMenu.open();
            return;
        }

        if (event.getCurrentItem() != null && event.getCurrentItem().getType() == Material.PLAYER_HEAD) {
            player.sendMessage(ChatColor.GRAY + EasyQuests.getPrefix() + "Управление конкретным квестодателем в разработке.");
        }
    }
}