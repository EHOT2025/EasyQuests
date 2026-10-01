package me.EHOT_2025.easyQuests.questBuilder;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class QuestBuilder {
    private String id;
    private String name;
    private String description;
    private UUID npcUuid;

    private final List<QuestGoal> goals = new ArrayList<>();
    private final List<QuestCondition> conditions = new ArrayList<>();
    private final List<QuestReward> rewards = new ArrayList<>();
    private final List<QuestReward> penalties = new ArrayList<>();
    private int timeLimitSeconds = 0;
    private double requiredReputation = 0.0;
    private int failureReputationPenalty = 0;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public UUID getNpcUuid() { return npcUuid; }
    public void setNpcUuid(UUID npcUuid) { this.npcUuid = npcUuid; }

    public List<QuestGoal> getGoals() { return goals; }
    public List<QuestCondition> getConditions() { return conditions; }
    public List<QuestReward> getRewards() { return rewards; }
    public List<QuestReward> getPenalties() { return penalties; }

    public int getTimeLimitSeconds() { return timeLimitSeconds; }
    public void setTimeLimitSeconds(int timeLimitSeconds) { this.timeLimitSeconds = timeLimitSeconds; }

    public double getRequiredReputation() { return requiredReputation; }
    public void setRequiredReputation(double requiredReputation) { this.requiredReputation = requiredReputation; }

    public int getFailureReputationPenalty() {
        return failureReputationPenalty;
    }

    public void setFailureReputationPenalty(int failureReputationPenalty) {
        this.failureReputationPenalty = failureReputationPenalty;
    }

    public boolean isReadyToSave() {
        return id != null && name != null && description != null && !goals.isEmpty() && npcUuid != null;
    }

    public void addGoal(QuestGoal goal) {
        this.goals.add(goal);
    }
}