package me.EHOT_2025.easyQuests.questBuilder;

import java.util.ArrayList;
import java.util.List;

public class QuestBuilder {
    private String name;
    private String description;
    private final List<QuestGoal> goals = new ArrayList<>();
    private final List<QuestCondition> conditions = new ArrayList<>();
    private final List<QuestReward> rewards = new ArrayList<>();
    private int failureReputationPenalty = 0;

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public List<QuestGoal> getGoals() { return goals; }
    public List<QuestCondition> getConditions() { return conditions; }
    public List<QuestReward> getRewards() { return rewards; }

    public int getFailureReputationPenalty() { return failureReputationPenalty; }
    public void setFailureReputationPenalty(int failureReputationPenalty) {
        this.failureReputationPenalty = failureReputationPenalty;
    }

    public boolean isReadyToSave() {
        return name != null && description != null && !goals.isEmpty();
    }
}