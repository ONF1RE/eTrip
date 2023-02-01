package com.radiant.etrip;

public class HelperReward {

    String date, reward;
    int balance, spent;

    public HelperReward() {
    }

    public HelperReward(String date, String reward, int balance, int spent) {
        this.date = date;
        this.reward = reward;
        this.balance = balance;
        this.spent = spent;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getReward() {
        return reward;
    }

    public void setReward(String reward) {
        this.reward = reward;
    }

    public int getBalance() {
        return balance;
    }

    public void setBalance(int balance) {
        this.balance = balance;
    }

    public int getSpent() {
        return spent;
    }

    public void setSpent(int spent) {
        this.spent = spent;
    }
}
