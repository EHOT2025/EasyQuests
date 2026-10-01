package me.EHOT_2025.easyQuests;

import org.bukkit.entity.Player;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

public class SelectingModeManager {
    private static final Set<UUID> selectingPlayers = new HashSet<>();

    public static void setSelecting(Player player, boolean selecting) {
        if (selecting) {
            selectingPlayers.add(player.getUniqueId());
        } else {
            selectingPlayers.remove(player.getUniqueId());
        }
    }

    public static boolean isSelecting(Player player) {
        return selectingPlayers.contains(player.getUniqueId());
    }
}