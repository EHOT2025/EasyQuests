package me.EHOT_2025.easyQuests.questBuilder;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class QuestBuilderManager {
    private static final Map<UUID, QuestBuilder> builders = new HashMap<>();

    public static QuestBuilder getBuilder(UUID playerUuid) {
        return builders.computeIfAbsent(playerUuid, k -> new QuestBuilder());
    }

    public static void clearBuilder(UUID playerUuid) {
        builders.remove(playerUuid);
    }
}