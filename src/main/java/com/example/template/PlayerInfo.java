package com.example.template;

import java.util.ArrayList;

public class PlayerInfo {
    private int energy = 100;
    private int coins = 500;
    private ArrayList<String> selectedDefenderSrc = new ArrayList<>();
    private ArrayList<Defender> defendersOwned = new ArrayList<>();
    private ArrayList<Defender> defendersInShop = new ArrayList<>();

    public PlayerInfo(){
        selectedDefenderSrc.add("pictures/defenders/RobotShooter.png");
        defendersOwned.add(new RobotShooter());
        //Shop Array (down)
        defendersInShop.add(new RobotShield());
        defendersInShop.add(new RobotMine());
    }

    public int getEnergy(){
        return energy;
    }

    public void increaseEnergy(int energyAmnt){
        energy+=energyAmnt;
    }

    public void setEnergy(int num){
        energy = num;
    }

    public ArrayList<String> getSelectedDefenderSrc(){
        return selectedDefenderSrc;
    }

    public ArrayList<Defender> getDefendersOwned(){
        return defendersOwned;
    }

    public ArrayList<Defender> getDefendersInShop(){
        return defendersInShop;
    }

    public int getCoins(){
        return coins;
    }

    public void changeCoins(int num){
        coins += num;
    }
}
