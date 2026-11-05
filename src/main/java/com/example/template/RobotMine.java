package com.example.template;

import java.util.ArrayList;

public class RobotMine extends Defender {
    private long activationInterval;
    private long activationTimer;
    private boolean activated = false;

    public RobotMine(double x, double y, int row){
        super(x,y,"pictures/defenders/RobotMineDeactivated.png", row, 1500000000, 1000000, 80, 200);
        activationTimer = System.nanoTime();
        activationInterval = (long)(Math.random()*6+4) * 1000000000;
        //change src
    }

    public RobotMine(){
        super("pictures/defenders/RobotMineActivated.png", 120, 250,80,200, "Robot Mine");
    }

    public long getActivationTimer(){
        return activationTimer;
    }

    public long getActivationInterval(){
        return activationInterval;
    }

    public void activateMine(){
        changeImage("pictures/defenders/RobotMineActivated.png");
        activated = true;
    }

    public boolean isActivated(){
        return activated;
    }

    public void explode(ArrayList<Enemy> enemies){
        for(int i = enemies.size()-1; i>=0; i--){
            if(getX() - enemies.get(i).getWidth() - 300  <= enemies.get(i).getX() && getX() + enemies.get(i).getWidth()  + 300 >= enemies.get(i).getX()
            &&enemies.get(i).getRow() == getRow()){
                enemies.remove(i);
            }
        }
        System.out.println(" Call Explode");
    }
}
