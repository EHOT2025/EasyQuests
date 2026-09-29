package me.EHOT_2025.easyQuests.questBuilder;

public class QuestReward {
    private final String itemOrRep; //TODO: айди предмета или ключевое слово для УР
    private final int amount;       //TODO: количество

    public QuestReward(String itemOrRep, int amount) {
        this.itemOrRep = itemOrRep;
        this.amount = amount;
    }

    public String getItemOrRep() { return itemOrRep; }
    public int getAmount() { return amount; }
}