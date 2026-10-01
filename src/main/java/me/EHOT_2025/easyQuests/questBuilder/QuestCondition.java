package me.EHOT_2025.easyQuests.questBuilder;

public class QuestCondition {
    private final String conditionKey;
    private final int parameter;

    public QuestCondition(String conditionKey, int parameter) {
        this.conditionKey = conditionKey;
        this.parameter = parameter;
    }

    public String getConditionKey() { return conditionKey; }
    public int getParameter() { return parameter; }
}