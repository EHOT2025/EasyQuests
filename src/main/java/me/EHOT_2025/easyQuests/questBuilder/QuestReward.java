package me.EHOT_2025.easyQuests.questBuilder;

import org.bukkit.Material;

public class QuestReward {
    private final boolean isReputation;
    private final Material material;
    private final int amount;
    private final double reputationAmount;

    public QuestReward(Material material, int amount) {
        this.isReputation = false;
        this.material = material;
        this.amount = amount;
        this.reputationAmount = 0.0;
    }

    public QuestReward(double reputationAmount) {
        this.isReputation = true;
        this.material = null;
        this.amount = 0;
        this.reputationAmount = reputationAmount;
    }

    public boolean isReputation() { return isReputation; }
    public Material getMaterial() { return material; }
    public int getAmount() { return amount; }
    public double getReputationAmount() { return reputationAmount; }
}