package com.example.template;

public class BasicAlien extends Enemy{
    public BasicAlien(double x, double y, int row){
        super(x,y,"pictures/enemies/BasicAlien.png", row, 100, 60, 60, 20);
    }

    public void moveEnemy(){
        super.moveEnemy();
    }
}
