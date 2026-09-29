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

                if (args.length < 2 || !args[1].equals("secure_token_918273465")) {
                    sender.sendMessage(EasyQuests.getPrefix() + ChatColor.RED + "Ты пытаешься использовать чёрную магию, не зли админа :)");
                    return true;
                }

                Player player = (Player) sender;

                MainMenu menu = new MainMenu(player, true);
                menu.open();
                return true;
            }

            if (isAdmin(sender)) {
                if (args.length < 2) {
                    sender.sendMessage(EasyQuests.getPrefix() + org.bukkit.ChatColor.RED + "Использование: /eq <UUID> <Имя NPC>");
                    return true;
                }

                try {
                    java.util.UUID npcUuid = java.util.UUID.fromString(args[0]);

                    StringBuilder sb = new StringBuilder();

                    org.bukkit.entity.Entity entity = org.bukkit.Bukkit.getEntity(npcUuid);
                    for (int i = 0; i < args.length; i++) {
                        sb.append(args[i]).append(" ");
                    }

                    String npcName = sb.toString().trim();

                    EasyQuests.getInstance().getDatabaseManager().saveQuestNpc(npcUuid, npcName);

                    sender.sendMessage(EasyQuests.getPrefix() + ChatColor.GREEN +
                            "NPC " + ChatColor.GRAY + npcName +
                            ChatColor.GREEN + " успешно зарегистрирован как квестодатель!");
                    return true;
                } catch (IllegalArgumentException e) {
                    sender.sendMessage(EasyQuests.getPrefix() + ChatColor.RED + "Некорректный UUID!");
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
