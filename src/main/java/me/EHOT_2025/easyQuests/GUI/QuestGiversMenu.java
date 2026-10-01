package me.EHOT_2025.easyQuests.GUI;

import me.EHOT_2025.easyQuests.EasyQuests;
import me.EHOT_2025.easyQuests.SelectingModeManager;
import me.EHOT_2025.easyQuests.questBuilder.QuestBuilder;
import me.EHOT_2025.easyQuests.questBuilder.QuestBuilderManager;
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
                String rawValue = entry.getValue();

                String npcName = rawValue;
                if (rawValue.contains(" ") && rawValue.length() > 36) {
                    String[] parts = rawValue.split(" ", 2);
                    if (parts.length > 1 && parts[0].length() == 36) {
                        npcName = parts[1];
                    }
                }

                ItemStack headItem = new ItemStack(Material.PLAYER_HEAD);
                SkullMeta meta = (SkullMeta) headItem.getItemMeta();

                if (meta != null) {
                    meta.setDisplayName(ChatColor.YELLOW + npcName);

                    List<String> lore = new ArrayList<>();
                    lore.add(ChatColor.GRAY + "UUID: " + npcUuid.toString());
                    lore.add("");
                    if (SelectingModeManager.isSelecting(player)) {
                        lore.add(ChatColor.GREEN + "▶ Нажмите, чтобы выбрать этого квестодателя");
                    } else {
                        lore.add(ChatColor.GREEN + "▶ Нажмите для управления квестами");
                    }
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
            backMeta.setDisplayName(ChatColor.RED + "Назад");
            backItem.setItemMeta(backMeta);
        }
        inventory.setItem(49, backItem);
    }

    @Override
    public void handleMenuClick(InventoryClickEvent event) {
        int slot = event.getRawSlot();

        if (slot == 49) {
            if (SelectingModeManager.isSelecting(player)) {
                SelectingModeManager.setSelecting(player, false);
                new CreateQuestMenu(player).open();
            } else {
                new AdminMenu(player).open();
            }
            return;
        }

        ItemStack clicked = event.getCurrentItem();
        if (clicked != null && clicked.getType() == Material.PLAYER_HEAD) {
            ItemMeta meta = clicked.getItemMeta();
            if (meta != null && meta.hasLore()) {
                String uuidLine = ChatColor.stripColor(meta.getLore().get(0));
                if (uuidLine.startsWith("UUID: ")) {
                    UUID npcUuid = UUID.fromString(uuidLine.replace("UUID: ", "").trim());
                    String npcName = ChatColor.stripColor(meta.getDisplayName());

                    if (SelectingModeManager.isSelecting(player)) {
                        QuestBuilder builder = QuestBuilderManager.getBuilder(player.getUniqueId());
                        builder.setNpcUuid(npcUuid);
                        SelectingModeManager.setSelecting(player, false);

                        new CreateQuestMenu(player).open();
                        player.sendMessage(ChatColor.GREEN + EasyQuests.getPrefix() + "Квестодатель успешно привязан: " + ChatColor.YELLOW + npcName);
                        return;
                    }

                    player.sendMessage(ChatColor.GRAY + EasyQuests.getPrefix() + "Управление конкретным квестодателем в разработке.");
                }
            }
        }
    }
}