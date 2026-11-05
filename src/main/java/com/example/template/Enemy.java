package com.example.template;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;

public class Enemy {
    private double x;
    private double y;
    private String src;
    private Image image;
    private long movementTime;
    private double speed = 3;
    private int row;
    private int health;
    private boolean move = true;
    private long attackTimer;
    private double height;
    private double width;
    private int damage;

    public Enemy(double x, double y, String src, int row, int health, double height, double width, int damage){
        this.x =x;
        this.y = y;
        this.src = src;
        this.row = row;
        this.health = health;
        this.height = height;
        this.width = width;
        this.damage = damage;
        image = new Image(getClass().getResourceAsStream("/" + src));
        movementTime = System.nanoTime();
        attackTimer = System.nanoTime();
//        gc.drawImage(image);
    }

//    public Enemy(double x, double y, String src, int row, int health)

    public void moveEnemy(){
        x -= speed;
    }

    public void attackDefender(){

    }

    public double getX(){
        return x;
    }

    public double getY(){
        return y;
    }

    public void increaseX(double num){
        x-=num;
    }

    public void increaseY(double num){
        y+=num;
    }

    public int getRow(){
        return row;
    }

    public Image getImage(){
        return image;
    }

    public long getMovementTimer(){
        return movementTime;
    }

    public void setMovementTimer(){
        movementTime = System.nanoTime();
    }

    public long getAttackTimer(){
        return attackTimer;
    }

    public void setAttackTimer(){
        attackTimer = System.nanoTime();
    }

    public boolean getMove(){
        return move;
    }

    public void changeMove(boolean condition){
        move = condition;
    }

    public int getHealth(){
        return health;
    }

    public void addHealth(int num){
        health += num;
    }

    public void changeHealth(int change){
        health += change;
    }

    public double getHeight(){
        return height;
    }

    public double getWidth(){
        return width;
    }

    public int getDamage(){
        return damage;
    }

    public double getSpeed(){
        return speed;
    }

    public void setSpeed(int newSpeed){
        speed = newSpeed;
    }

    public Defender collision(ArrayList<Defender> defenders, int squareSize){
        for(int i = defenders.size()-1; i>=0; i--){
            if(x + width > defenders.get(i).getX() &&
                    x < defenders.get(i).getX() + squareSize &&
                    y + height > defenders.get(i).getY() &&
                    y < defenders.get(i).getY() + squareSize){
                return defenders.get(i);
            }
        }
        return null;
    }
}
