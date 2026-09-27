package me.EHOT_2025.easyQuests;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

public class TabCompleter implements org.bukkit.command.TabCompleter {
    @Override
    public @Nullable List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String @NotNull [] args) {
        List<String> completions = new ArrayList<>();

        if(args.length == 1) {
            boolean isAdmin = (sender.hasPermission("easyquests.admin") || sender.isOp());

            if (isAdmin) {
                completions.add("reload");
            }
        }

        return completions;
    }
}
