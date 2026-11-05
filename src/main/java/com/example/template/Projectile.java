package com.example.template;

import javafx.scene.image.Image;

import java.util.ArrayList;

public class Projectile {
    private double x;
    private double y;
    private long movementTimer;
    private Image image;
    private int damage;
    private double width;
    private double height;
    public Projectile(double x, double y, String src, int damage, double width, double height){
        this.x = x;
        this.y = y;
        this.damage = damage;
        this.width = width;
        this.height = height;
        movementTimer = System.nanoTime();
        image = new Image(getClass().getResourceAsStream("/" + src));
    }

    public void moveX(double amnt){
        x+=amnt;
    }

    public void moveY(double amnt){
        y+=amnt;
    }

    public double getX(){
        return x;
    }

    public double getY(){
        return y;
    }

    public long getMovementTimer(){
        return movementTimer;
    }

    public void setMovementTimer(){
        movementTimer = System.nanoTime();
    }

    public Image getImage(){
        return image;
    }

    public int getDamage(){
        return damage;
    }

    public double getWidth(){
        return width;
    }

    public double getHeight(){
        return height;
    }

    public boolean collision(ArrayList<Enemy> enemies){
        for(int i = enemies.size()-1; i>=0; i--){
            if(x + width > enemies.get(i).getX() &&
                x < enemies.get(i).getX() + enemies.get(i).getWidth() &&
                y + height > enemies.get(i).getY() &&
                y < enemies.get(i).getY() + enemies.get(i).getHeight()){
                enemies.get(i).changeHealth(-damage);
                return true;
            }
        }
        return false;
    }


}
