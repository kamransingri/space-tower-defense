package com.example.template;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

import java.util.ArrayList;


public class Defender {
    private double x;
    private double y;
    private String src;
    private Image image;
    private int row;
    private ArrayList<Projectile> projectiles = new ArrayList<>();
    private long attackInterval;
    private long attackTimer;
    private long projectileSpeedInterval;
    private int health;
    private int energyCost;
    private int buyCost;
    private String name;
    private int damage;

    public Defender(double x, double y, String src, int row, long attackInterval, long projectileSpeedInterval, int health, int damage){
        this.x = x;
        this.y = y;
        this.row = row;
        this.health = health;
        this.attackInterval = attackInterval;
        this.projectileSpeedInterval = projectileSpeedInterval;
        this.src = src;
        this.damage = damage;
        attackTimer = System.nanoTime();
        image = new Image(getClass().getResourceAsStream("/" + src));
    }

    public Defender(String src, int energyCost, int buyCost, int health, int damage, String name){
            this.src = src;
            this.energyCost = energyCost;
            this.buyCost = buyCost;
            this.name = name;
            this.health = health;
            this.damage = damage;
    }

    public double getX(){
        return x;
    }

    public double getY(){
        return y;
    }

    public Image getImage(){
        return image;
    }

    public void changeImage(String newSrc){
        image = new Image(getClass().getResourceAsStream("/" + newSrc));
    }

    public String getSrc(){
        return src;
    }

    public int getRow(){
        return row;
    }

    public ArrayList<Projectile> getProjectiles(){
        return projectiles;
    }

    public long getAttackTimer(){
        return attackTimer;
    }

    public void setAttackTimer(){
        attackTimer = System.nanoTime();
    }

    public long getAttackInterval(){
        return attackInterval;
    }

    public long getProjectileSpeedInterval(){
        return projectileSpeedInterval;
    }

    public void changeProjectileSpeedInterval(long change){
        projectileSpeedInterval += change;
    }

    public boolean checkForEnemyInRow(ArrayList<Enemy> enemies, Canvas myCanvas){
        for(Enemy enemy: enemies){
            if(enemy.getRow() == row && enemy.getX()>x && enemy.getX()<=myCanvas.getWidth()){
                return true;
            }
        }
        return false;
    }
    public void createNewProjectile(){
        projectiles.add(new Projectile(x+20, y+40, "", damage,30,30));
    }

    public int getHealth(){
        return health;
    }

    public void changeHealth(int change){
        health += change;
    }

    public int getEnergyCost(){
        return energyCost;
    }

    public int getBuyCost(){
        return buyCost;
    }

    public String getName(){
        return name;
    }

    public int getDamage(){
        return damage;
    }


    public void moveProjectile(){

    }



}
