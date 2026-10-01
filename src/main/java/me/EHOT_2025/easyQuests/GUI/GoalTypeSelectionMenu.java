package me.EHOT_2025.easyQuests.GUI;

import me.EHOT_2025.easyQuests.EasyQuests;
import me.EHOT_2025.easyQuests.questBuilder.GoalValidator;
import me.EHOT_2025.easyQuests.questBuilder.QuestBuilder;
import me.EHOT_2025.easyQuests.questBuilder.QuestBuilderManager;
import me.EHOT_2025.easyQuests.questBuilder.QuestGoal;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.List;

public class GoalTypeSelectionMenu extends Template {

    private final Player player;

    public GoalTypeSelectionMenu(Player player) {
        super(player, 27, "&8Выберите тип цели");
        this.player = player;
    }

    @Override
    public void setMenuItems() {
        inventory.setItem(10, createItem(Material.COMPASS, ChatColor.YELLOW + "1. Поиск", "Указать ID данжа, биома или структуры"));
        inventory.setItem(11, createItem(Material.IRON_SWORD, ChatColor.YELLOW + "2. Убийство", "ID моба и количество (по умолчанию 1)"));
        inventory.setItem(12, createItem(Material.DIAMOND, ChatColor.YELLOW + "3. Добыча", "ID предмета и количество"));
        inventory.setItem(13, createItem(Material.FURNACE, ChatColor.YELLOW + "4. Создание", "ID скрафченного предмета и количество"));
        inventory.setItem(14, createItem(Material.HOPPER, ChatColor.YELLOW + "5. Передача", "ID предмета для передачи квестодателю"));

        ItemStack back = new ItemStack(Material.BARRIER);
        ItemMeta bMeta = back.getItemMeta();
        if (bMeta != null) {
            bMeta.setDisplayName(ChatColor.RED + "← Назад");
            back.setItemMeta(bMeta);
        }
        inventory.setItem(22, back);
    }

    private ItemStack createItem(Material mat, String name, String desc) {
        ItemStack item = new ItemStack(mat);
        ItemMeta meta = item.getItemMeta();
        if (meta != null) {
            meta.setDisplayName(name);
            meta.setLore(List.of(ChatColor.GRAY + desc, "", ChatColor.AQUA + "▶ Нажмите для выбора"));
            item.setItemMeta(meta);
        }
        return item;
    }

    @Override
    public void handleMenuClick(InventoryClickEvent event) {
        int slot = event.getRawSlot();
        QuestBuilder builder = QuestBuilderManager.getBuilder(player.getUniqueId());

        if (slot == 22) {
            new GoalsManagementMenu(player).open();
            return;
        }

        QuestGoal.GoalType type = null;
        if (slot == 10) type = QuestGoal.GoalType.FIND;
        else if (slot == 11) type = QuestGoal.GoalType.KILL;
        else if (slot == 12) type = QuestGoal.GoalType.GATHER;
        else if (slot == 13) type = QuestGoal.GoalType.CRAFT;
        else if (slot == 14) type = QuestGoal.GoalType.GIVE;

        if (type != null) {
            player.closeInventory();

            boolean isFirstGoal = builder.getGoals().isEmpty();
            if (isFirstGoal) {
                player.sendMessage(ChatColor.YELLOW + EasyQuests.getPrefix() + "Первая цель обязана быть постоянной.");
                promptForTarget(player, type, false, null);
            } else {
                promptForTarget(player, type, false, null);
            }
        }
    }

    private void promptForTarget(Player player, QuestGoal.GoalType type, boolean staged, String parentTarget) {
        player.sendMessage(ChatColor.YELLOW + EasyQuests.getPrefix() + "Введите в чат ID цели и количество (например: ZOMBIE 5 или DIAMOND):");

        EasyQuests.getInstance().getChatInputManager().waitForInput(player, input -> {
            String[] parts = input.trim().split(" ");
            String targetId = parts[0];
            int amount = 1;

            if (parts.length > 1) {
                try {
                    amount = Integer.parseInt(parts[1]);
                } catch (NumberFormatException ignored) {}
            }

            if (!GoalValidator.isValidTarget(type, targetId)) {
                player.sendMessage(ChatColor.RED + EasyQuests.getPrefix() + "Ошибка! Неверный ID для выбранного типа цели.");
                new GoalTypeSelectionMenu(player).open();
                return;
            }

            QuestBuilder builder = QuestBuilderManager.getBuilder(player.getUniqueId());
            builder.addGoal(new QuestGoal(type, targetId, amount, staged, parentTarget));

            player.sendMessage(ChatColor.GREEN + EasyQuests.getPrefix() + "Цель успешно добавлена!");
            new GoalsManagementMenu(player).open();
        });
    }
}