package me.EHOT_2025.easyQuests.GUI;

import me.EHOT_2025.easyQuests.questBuilder.QuestBuilder;
import me.EHOT_2025.easyQuests.questBuilder.QuestBuilderManager;
import me.EHOT_2025.easyQuests.questBuilder.QuestCondition;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;

public class ConditionsManagementMenu extends Template {

    private final Player player;

    public ConditionsManagementMenu(Player player) {
        super(player, 54, "&8Условия квеста");
        this.player = player;
    }

    @Override
    public void setMenuItems() {
        QuestBuilder builder = QuestBuilderManager.getBuilder(player.getUniqueId());

        String[][] availableConditions = {
                {"NO_ARMOR", "Без брони", "IRON_CHESTPLATE"},
                {"NO_WEAPON", "Без оружия", "WOODEN_SWORD"},
                {"TIME_DAY", "Только днём", "CLOCK"},
                {"TIME_NIGHT", "Только ночью", "COMPASS"},
                {"NO_DEATH", "Не погибая", "TOTEM_OF_UNDYING"},
                {"PACIFIST_PEACEFUL", "Без убийства мирных", "PIG_SPAWN_EGG"},
                {"PACIFIST_NEUTRAL", "Без убийства нейтральных", "WOLF_SPAWN_EGG"},
                {"PACIFIST_HOSTILE", "Без убийства враждебных", "ZOMBIE_SPAWN_EGG"}
        };

        int slot = 0;
        for (String[] cond : availableConditions) {
            if (slot >= 27) break;
            ItemStack item = new ItemStack(Material.valueOf(cond[2]));
            ItemMeta meta = item.getItemMeta();
            if (meta != null) {
                meta.setDisplayName(ChatColor.YELLOW + "+ " + cond[1]);
                meta.setLore(List.of(ChatColor.GRAY + "Нажмите, чтобы добавить условие"));
                item.setItemMeta(meta);
            }
            inventory.setItem(slot, item);
            slot++;
        }

        int activeSlot = 36;
        for (QuestCondition condition : builder.getConditions()) {
            if (activeSlot > 44) break;
            ItemStack activeItem = new ItemStack(Material.PAPER);
            ItemMeta meta = activeItem.getItemMeta();
            if (meta != null) {
                meta.setDisplayName(ChatColor.GREEN + "Активно: " + condition.getConditionKey());
                meta.setLore(List.of(ChatColor.RED + "▶ Нажмите для удаления"));
                activeItem.setItemMeta(meta);
            }
            inventory.setItem(activeSlot, activeItem);
            activeSlot++;
        }

        ItemStack back = new ItemStack(Material.BARRIER);
        ItemMeta bMeta = back.getItemMeta();
        if (bMeta != null) {
            bMeta.setDisplayName(ChatColor.RED + "← Назад к конструктору");
            back.setItemMeta(bMeta);
        }
        inventory.setItem(49, back);
    }

    @Override
    public void handleMenuClick(InventoryClickEvent event) {
        int slot = event.getRawSlot();
        QuestBuilder builder = QuestBuilderManager.getBuilder(player.getUniqueId());

        if (slot == 49) {
            new CreateQuestMenu(player).open();
            return;
        }

        if (slot < 27 && event.getCurrentItem() != null) {
            // Маппинг кликов на ключи условий
            String[] keys = {"NO_ARMOR", "NO_WEAPON", "TIME_DAY", "TIME_NIGHT", "NO_DEATH", "PACIFIST_PEACEFUL", "PACIFIST_NEUTRAL", "PACIFIST_HOSTILE"};
            if (slot < keys.length) {
                String key = keys[slot];
                boolean exists = builder.getConditions().stream().anyMatch(c -> c.getConditionKey().equals(key));
                if (!exists) {
                    builder.getConditions().add(new QuestCondition(key, 0));
                    player.sendMessage(ChatColor.GREEN + "Условие добавлено!");
                    new ConditionsManagementMenu(player).open();
                }
            }
        } else if (slot >= 36 && slot <= 44) {
            int index = slot - 36;
            if (index < builder.getConditions().size()) {
                builder.getConditions().remove(index);
                player.sendMessage(ChatColor.YELLOW + "Условие удалено.");
                new ConditionsManagementMenu(player).open();
            }
        }
    }
}