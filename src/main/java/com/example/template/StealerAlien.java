package com.example.template;

import java.util.ArrayList;

public class StealerAlien extends Enemy{
    private Energy stolenEnergy;
    private boolean collectingEnergy = false;
    private long stealInterval = 5000000000l;
    private long stealTimer;

    public StealerAlien(double x , double y, int row){
        super(x, y, "pictures/enemies/StealerAlien.png", row, 80, 60,60, 20);
        stealTimer = System.nanoTime();
    }

    public long getStealInterval(){
        return stealInterval;
    }

    public long getStealTimer(){
        return stealTimer;
    }

    public void setStealTimer(){
        stealTimer = System.nanoTime();
        changeMove(true);
        collectingEnergy = false;
    }

    public boolean isCollectingEnergy(){
        return collectingEnergy;
    }

    public Energy getStolenEnergy(){
        return stolenEnergy;
    }

    public void moveEnemy() {
//        if(stolenEnergy == null){
//            collectingEnergy = false;
//            changeMove(true);
//        }
        //make sure if user clicks on energy then enemy starts moving again
        if(collectingEnergy){
            if(stolenEnergy.getX()<getX()+2){
                stolenEnergy.changeX(2);
            }
            else if(stolenEnergy.getX()>getX()+2){
                stolenEnergy.changeX(-2);
            }
            if(stolenEnergy.getY()<getY()+2){
                stolenEnergy.changeY(2);
            }
            else if(stolenEnergy.getY()>getY()+2){
                stolenEnergy.changeY(-2);
            }
        }
        else{
            super.moveEnemy();
        }
    }

    public void getRandEnergy(ArrayList<Energy> energyArrayList){
        if(energyArrayList.size()>0){
            boolean found = false;
            while(!found){
                stolenEnergy = energyArrayList.get((int)(Math.random()*energyArrayList.size()));
                if(stolenEnergy.isMoving()){
                    collectingEnergy = true;
                    changeMove(false);
                    stolenEnergy.changeMove(false);
                    found = true;
                }
            }
        }
    }

    public void checkEnergyCollision(ArrayList<Energy> energyArrayList){
        if(getX() + getWidth() > stolenEnergy.getX() &&
                getX() < stolenEnergy.getX() + stolenEnergy.getWidth() &&
                getY() + getHeight() > stolenEnergy.getY() &&
                getY()< stolenEnergy.getY() + stolenEnergy.getHeight()){
            if(energyArrayList.size()>0){
                for(int i = energyArrayList.size() - 1; i>= 0; i--){
                    if(energyArrayList.get(i) == stolenEnergy){
                        energyArrayList.remove(i);
                        stolenEnergy = null;
                        addHealth(10);
                    }
                }
            }
            setStealTimer();
        }
    }
}
