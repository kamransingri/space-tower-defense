package com.example.template;

public class RobotShield extends Defender{

    public RobotShield(double x, double y, int row){
        super(x,y,"pictures/defenders/RobotShield.png", row, 1500000000, 1000000, 160, 0);
    }

    public RobotShield(){
        super("pictures/defenders/RobotShield.png", 80, 200, 160, 0, "Robot Shield");
    }
}
