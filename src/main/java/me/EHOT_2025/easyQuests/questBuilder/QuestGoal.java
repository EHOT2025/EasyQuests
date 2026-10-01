package me.EHOT_2025.easyQuests.questBuilder;

public class QuestGoal {
    public enum GoalType {
        SEARCH, GATHER, KILL, CRAFT, DELIVER, GIVE, FIND
    }

    private final GoalType type;
    private final String target;
    private final int amount;
    private final boolean staged;
    private final String parentTarget;

    public QuestGoal(GoalType type, String target, int amount, boolean staged, String parentTarget) {
        this.type = type;
        this.target = target;
        this.amount = amount;
        this.staged = staged;
        this.parentTarget = parentTarget;
    }

    public GoalType getType() { return type; }
    public String getTarget() { return target; }
    public int getAmount() { return amount; }
    public boolean isStaged() { return staged; }
    public String getParentTarget() { return parentTarget; }
}