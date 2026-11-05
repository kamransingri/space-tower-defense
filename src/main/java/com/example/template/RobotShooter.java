package com.example.template;

import javafx.scene.canvas.GraphicsContext;

import java.awt.*;

public class RobotShooter extends Defender {

    public RobotShooter(double x, double y, int row){
        super(x,y,"pictures/defenders/RobotShooter.png", row, 1500000000, 1000000, 80, 20);
    }

    public RobotShooter(){
        super("pictures/defenders/RobotShooter.png", 60, 100,80, 20, "Robot Shooter");
    }

    public void createNewProjectile(){
        getProjectiles().add(new Projectile(getX()+30, getY()+30, "pictures/projectiles/RobotShooterLaser.png", 20,30,30));
    }

    public void attackEnemy(){

    }

    public void moveProjectile(){

    }
}
