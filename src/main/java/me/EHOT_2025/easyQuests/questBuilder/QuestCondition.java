package me.EHOT_2025.easyQuests.questBuilder;

public class QuestCondition {
    private final String conditionKey; // TODO: идентификатор условия (например, NO_DEATH, TIME_LIMIT, NO_ARMOR)
    private final String value;        // TODO: аргумент (например, количество часов для таймера)

    public QuestCondition(String conditionKey, String value) {
        this.conditionKey = conditionKey;
        this.value = value;
    }

    public String getConditionKey() { return conditionKey; }
    public String getValue() { return value; }
}