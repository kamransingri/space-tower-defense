package com.example.template;

public class Energy {

    private double x;
    private double y;
    private long speedInterval;
    private int energyAmount;
    private double width;
    private double height;
    private long movementTimer;
    private boolean move;

    public Energy(double x, double y, boolean move){
        this.x = x;
        this.y = y;
        this.move = move;
        energyAmount = (int)(Math.random()*4+2)*10;
        width = (int)(Math.random()*4 +3) * 10;
        height = width;
        movementTimer = System.nanoTime();
    }

    public double getX(){
        return x;
    }

    public double getY(){
        return y;
    }

    public void changeX(double num){
        x += num;
    }

    public void changeY(double num){
        y += num;
    }

    public double getWidth(){
        return width;
    }

    public double getHeight(){
        return height;
    }

    public long getMovementTimer(){
        return movementTimer;
    }

    public void setMovementTimer(){
        movementTimer = System.nanoTime();
    }

    public void moveEnergy(){
        y+=3;
    }

    public int getEnergyAmount(){
        return energyAmount;
    }

    public boolean isMoving(){
        return move;
    }

    public void changeMove(boolean move){
        this.move = move;
    }


}
