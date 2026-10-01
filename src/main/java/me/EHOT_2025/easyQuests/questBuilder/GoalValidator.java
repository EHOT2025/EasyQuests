package me.EHOT_2025.easyQuests.questBuilder;

import org.bukkit.Material;
import org.bukkit.entity.EntityType;

public class GoalValidator {

    public static boolean isValidTarget(QuestGoal.GoalType type, String input) {
        if (input == null || input.isEmpty()) return false;

        switch (type) {
            case KILL:
                try {
                    EntityType.valueOf(input.toUpperCase());
                    return true;
                } catch (IllegalArgumentException e) {
                    return false;
                }
            case GATHER:
            case CRAFT:
            case GIVE:
                Material mat = Material.matchMaterial(input);
                return mat != null;
            case FIND:
                return input.length() >= 3;
            default:
                return false;
        }
    }
}