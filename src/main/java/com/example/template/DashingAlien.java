package com.example.template;

public class DashingAlien extends Enemy{

    private boolean dash = false;
    private long dashInterval = 4000000000l;
    private long dashTimer;

    public DashingAlien(double x, double y, int row){
        super(x, y, "pictures/enemies/DashingAlien.png", row, 80, 60,60, 20);
        dashTimer = System.nanoTime();
    }

    public void moveEnemy(){
        if(dash){
            increaseX(getSpeed()+3);
        }
        else {
            super.moveEnemy();
        }
    }

    public long getDashInterval(){
        return dashInterval;
    }

    public long getDashTimer(){
        return dashTimer;
    }

    public void setDashTimer(){
        dashTimer = System.nanoTime();
        if(dash){
            dash = false;
            dashInterval = 6000000000l;
        }
        else{
            dash = true;
            dashInterval = 2000000000;
        }
    }
}
