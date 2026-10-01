package me.EHOT_2025.easyQuests.questBuilder;

import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class QuestBuilderManager {
    private static final Map<UUID, QuestBuilder> builders = new HashMap<>();
    private static final Map<UUID, QuestBuilder> activeBuilders = new HashMap<>();

    public static QuestBuilder getBuilder(UUID playerUuid) {
        return builders.computeIfAbsent(playerUuid, k -> new QuestBuilder());
    }

    public static void setBuilder(Player player, QuestBuilder builder) {
        activeBuilders.put(player.getUniqueId(), builder);
    }

    public static QuestBuilder getBuilder(Player player) {
        return activeBuilders.getOrDefault(player.getUniqueId(), new QuestBuilder());
    }

    public static void removeBuilder(Player player) {
        activeBuilders.remove(player.getUniqueId());
    }

    public static void clearBuilder(UUID playerUuid) {
        builders.remove(playerUuid);
    }
}