package me.EHOT_2025.easyQuests.GUI;

import me.EHOT_2025.easyQuests.EasyQuests;
import me.EHOT_2025.easyQuests.questBuilder.QuestBuilderManager;
import me.EHOT_2025.easyQuests.questBuilder.QuestBuilder;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;

public class CreateQuestMenu extends Template {

    private final Player player;

    public CreateQuestMenu(Player player) {
        super(player, 54, "&8Конструктор квеста");
        this.player = player;
    }

    @Override
    public void setMenuItems() {
        QuestBuilder builder = QuestBuilderManager.getBuilder(player.getUniqueId());

        ItemStack infoItem = new ItemStack(Material.BOOK);
        ItemMeta infoMeta = infoItem.getItemMeta();
        if (infoMeta != null) {
            infoMeta.setDisplayName(ChatColor.GOLD + "ℹ Информация");
            List<String> lore = new ArrayList<>();
            lore.add(ChatColor.YELLOW + "* " + ChatColor.GRAY + "- обязательные для заполнения аргументы.");
            lore.add(ChatColor.GRAY + "Без них квест не сможет быть сохранен.");
            infoMeta.setLore(lore);
            infoItem.setItemMeta(infoMeta);
        }
        inventory.setItem(4, infoItem);

        ItemStack nameItem = new ItemStack(Material.NAME_TAG);
        ItemMeta nameMeta = nameItem.getItemMeta();
        if (nameMeta != null) {
            nameMeta.setDisplayName(ChatColor.YELLOW + "Название *");
            List<String> lore = new ArrayList<>();
            lore.add(ChatColor.GRAY + "Текущее: " + (builder.getName() != null ? ChatColor.GREEN + builder.getName() : ChatColor.RED + "Не задано"));
            lore.add("");
            lore.add(ChatColor.AQUA + "▶ Нажмите, чтобы изменить (3-16 символов)");
            nameMeta.setLore(lore);
            nameItem.setItemMeta(nameMeta);
        }
        inventory.setItem(19, nameItem);

        ItemStack descItem = new ItemStack(Material.PAPER);
        ItemMeta descMeta = descItem.getItemMeta();
        if (descMeta != null) {
            descMeta.setDisplayName(ChatColor.YELLOW + "Описание *");
            List<String> lore = new ArrayList<>();
            String descPreview = builder.getDescription();
            if (descPreview != null) {
                if (descPreview.length() > 30) descPreview = descPreview.substring(0, 27) + "...";
                lore.add(ChatColor.GRAY + "Текущее: " + ChatColor.GREEN + descPreview);
            } else {
                lore.add(ChatColor.GRAY + "Текущее: " + ChatColor.RED + "Не задано");
            }
            lore.add("");
            lore.add(ChatColor.AQUA + "▶ Нажмите, чтобы изменить");
            descMeta.setLore(lore);
            descItem.setItemMeta(descMeta);
        }
        inventory.setItem(21, descItem);

        ItemStack goalsItem = new ItemStack(Material.TARGET);
        ItemMeta goalsMeta = goalsItem.getItemMeta();
        if (goalsMeta != null) {
            goalsMeta.setDisplayName(ChatColor.YELLOW + "Цели *");
            List<String> lore = new ArrayList<>();
            lore.add(ChatColor.GRAY + "Создано целей: " + ChatColor.GREEN + builder.getGoals().size());
            lore.add("");
            lore.add(ChatColor.AQUA + "▶ Нажмите для управления целями");
            goalsMeta.setLore(lore);
            goalsItem.setItemMeta(goalsMeta);
        }
        inventory.setItem(23, goalsItem);

        ItemStack condItem = new ItemStack(Material.BARRIER);
        ItemMeta condMeta = condItem.getItemMeta();
        if (condMeta != null) {
            condMeta.setDisplayName(ChatColor.YELLOW + "Условия (опционально)");
            List<String> lore = new ArrayList<>();
            lore.add(ChatColor.GRAY + "Активных условий: " + ChatColor.GREEN + builder.getConditions().size());
            lore.add("");
            lore.add(ChatColor.AQUA + "▶ Нажмите для настройки условий");
            condMeta.setLore(lore);
            condItem.setItemMeta(condMeta);
        }
        inventory.setItem(28, condItem);

        ItemStack rewItem = new ItemStack(Material.CHEST);
        ItemMeta rewMeta = rewItem.getItemMeta();
        if (rewMeta != null) {
            rewMeta.setDisplayName(ChatColor.YELLOW + "Награды за выполнение *");
            List<String> lore = new ArrayList<>();
            lore.add(ChatColor.GRAY + "Добавлено наград: " + ChatColor.GREEN + builder.getRewards().size());
            lore.add("");
            lore.add(ChatColor.AQUA + "▶ Нажмите для настройки наград");
            rewMeta.setLore(lore);
            rewItem.setItemMeta(rewMeta);
        }
        inventory.setItem(30, rewItem);

        ItemStack penItem = new ItemStack(Material.NETHERITE_SCRAP);
        ItemMeta penMeta = penItem.getItemMeta();
        if (penMeta != null) {
            penMeta.setDisplayName(ChatColor.YELLOW + "Штрафы за провал");
            List<String> lore = new ArrayList<>();
            lore.add(ChatColor.GRAY + "Штраф УР: " + ChatColor.RED + builder.getFailureReputationPenalty());
            lore.add("");
            lore.add(ChatColor.AQUA + "▶ Нажмите, чтобы изменить");
            penMeta.setLore(lore);
            penItem.setItemMeta(penMeta);
        }
        inventory.setItem(32, penItem);

        boolean canSave = builder.isReadyToSave();
        ItemStack saveItem = new ItemStack(canSave ? Material.EMERALD_BLOCK : Material.REDSTONE_BLOCK);
        ItemMeta saveMeta = saveItem.getItemMeta();
        if (saveMeta != null) {
            saveMeta.setDisplayName(canSave ? ChatColor.GREEN + "✔ Сохранить квест" : ChatColor.RED + "Заполните обязательные поля (*)");
            saveItem.setItemMeta(saveMeta);
        }
        inventory.setItem(49, saveItem);
    }

    @Override
    public void handleMenuClick(InventoryClickEvent event) {
        int slot = event.getRawSlot();

        if (slot == 19) {
            player.closeInventory();
            player.sendMessage(ChatColor.YELLOW + EasyQuests.getPrefix() + "Введите название квеста в чат (от 3 до 16 символов):");

            EasyQuests.getInstance().getChatInputManager().waitForInput(player, input -> {
                if (input.length() < 3 || input.length() > 16) {
                    player.sendMessage(ChatColor.RED + EasyQuests.getPrefix() + "Ошибка! Название должно быть от 3 до 16 символов.");
                    new CreateQuestMenu(player).open();
                    return;
                }
                QuestBuilderManager.getBuilder(player.getUniqueId()).setName(input);
                player.sendMessage(ChatColor.GREEN + EasyQuests.getPrefix() + "Название успешно установлено: " + input);
                new CreateQuestMenu(player).open();
            });
        }
        else if (slot == 21) {
            player.closeInventory();
            player.sendMessage(ChatColor.YELLOW + EasyQuests.getPrefix() + "Введите описание квеста в чат:");

            EasyQuests.getInstance().getChatInputManager().waitForInput(player, input -> {
                QuestBuilderManager.getBuilder(player.getUniqueId()).setDescription(input);
                player.sendMessage(ChatColor.GREEN + EasyQuests.getPrefix() + "Описание успешно сохранено!");
                new CreateQuestMenu(player).open();
            });
        }
        else if (slot == 49) {
            QuestBuilder builder = QuestBuilderManager.getBuilder(player.getUniqueId());
            if (builder.isReadyToSave()) {
                // TODO: Сохранение квеста в бд
                player.sendMessage(ChatColor.GREEN + EasyQuests.getPrefix() + "Квест успешно создан и сохранен!");
                QuestBuilderManager.clearBuilder(player.getUniqueId());
                new AdminMenu(player).open();
            } else {
                player.sendMessage(ChatColor.RED + EasyQuests.getPrefix() + "Не все обязательные поля (*) заполнены!");
            }
        }
    }
}