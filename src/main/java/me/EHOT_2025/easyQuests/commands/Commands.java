package me.EHOT_2025.easyQuests.commands;

import me.EHOT_2025.easyQuests.EasyQuests;
import me.EHOT_2025.easyQuests.GUI.MainMenu;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import static me.EHOT_2025.easyQuests.GUI.MainMenu.isAdmin;

public class Commands implements CommandExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String @NotNull [] args) {

        if (args.length > 0) {

            if (args[0].equalsIgnoreCase("reload")) {
                if (!isAdmin(sender)) {
                    sender.sendMessage(EasyQuests.getPrefix() + ChatColor.RED + "У вас нет прав на использование этой команды!");
                    return true;
                }
                EasyQuests.getInstance().reloadConfig();
                sender.sendMessage(EasyQuests.getPrefix() + ChatColor.GREEN + "Конфиг перезагружен!");
                return true;
            }

            if (args[0].equalsIgnoreCase("open")) {
                if (!(sender instanceof Player)) {
                    sender.sendMessage("Эту команду может использовать только игрок!");
                    return true;
                }

                Player player = (Player) sender;

                MainMenu menu = new MainMenu(player, true);
                menu.open();
                return true;
            }

            if (isAdmin(sender)) {
                try {
                    java.util.UUID npcUuid = java.util.UUID.fromString(args[0]);

                    org.bukkit.entity.Entity entity = org.bukkit.Bukkit.getEntity(npcUuid);
                    String npcName = "Неизвестный НПС"; // TODO: Реализовать корректное отображение имени НПС

                    if (entity != null) {
                        npcName = entity.getName();
                    }

                    EasyQuests.getInstance().getDatabaseManager().saveQuestNpc(npcUuid, npcName);

                    sender.sendMessage(EasyQuests.getPrefix() + ChatColor.GREEN +
                            "NPC " + ChatColor.GRAY + npcName +
                            ChatColor.GREEN + " успешно зарегистрирован как квестодатель!");
                    return true;
                } catch (IllegalArgumentException e) {
                    sender.sendMessage(EasyQuests.getPrefix() + ChatColor.RED + "Некорректный аргумент!");
                    return true;
                }
            } else {
                sender.sendMessage(EasyQuests.getPrefix() + ChatColor.RED + "У вас нет прав на использование этой команды!");
                return true;
            }
        }

        if (!(sender instanceof Player)) {
            sender.sendMessage(ChatColor.RED + "Эту команду может использовать только игрок!");
            return true;
        }

        Player player = (Player) sender;
        MainMenu mainMenu = new MainMenu(player);
        mainMenu.open();

        return true;
    }
}
