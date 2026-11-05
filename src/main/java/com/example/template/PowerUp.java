package com.example.template;

import javafx.scene.image.Image;

import java.util.ArrayList;

public class PowerUp {
    private double x;
    private double y;
    private String type;
    private Image image;
    private double width;
    private double height;
    private long availabilityTimer;

    public PowerUp(double x, double y, String type){
        this.x = x;
        this.y = y;
        this.type = type;
        width = (int)(Math.random()*4 +3) * 10;
        height = width;
        availabilityTimer = System.nanoTime();

        if(type.equals("SlowEnemy")){
            image = new Image(getClass().getResourceAsStream("/" + "pictures/PowerUpSlowDownEnemy.png"));
        }
        else{
            image = new Image(getClass().getResourceAsStream("/" + "pictures/PowerUpSpeedUpDefenderAttack.png"));
        }
    }

    public double getX(){
        return x;
    }

    public double getY(){
        return y;
    }

    public double getWidth(){
        return width;
    }

    public double getHeight(){
        return height;
    }

    public Image getImage(){
        return image;
    }

    public long getAvailabilityTimer(){
        return availabilityTimer;
    }

    public void powerUpSelected(ArrayList<Enemy> enemies, ArrayList<Defender> defenders, WaveManager waveManger){
        if(type.equals("SlowEnemy") && !waveManger.isEnemiesSlownDown()){
            slowEnemy(enemies);
            waveManger.changeEnemiesSlownDown(true);
        }
        else if(type.equals("FastAttack") && !waveManger.isDefendersFastAttack()){
            increaseDefenderAttackSpeed(defenders);
        }
    }

    public void slowEnemy(ArrayList<Enemy> enemies){
        for(int i = enemies.size() - 1; i>=0; i--){
            enemies.get(i).setSpeed(1);
        }
    }

    public void increaseDefenderAttackSpeed(ArrayList<Defender> defenders){
        for(int i = defenders.size() - 1; i>=0; i--){
            defenders.get(i).changeProjectileSpeedInterval(-500000000);
        }
    }

    //Make sure powerUps disapear
}
