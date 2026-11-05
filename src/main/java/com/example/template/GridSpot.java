package com.example.template;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

public class GridSpot {
    private double [] bounds = new double[4];
    //Left, Top, Right, Bottom
    private boolean occupiedDefender = false;
    private int row;

    public GridSpot(double rightX, double leftX, double topY, double bottomY, int squareSize, GraphicsContext gc, int row){
        //set each bounds to a var in this class or put all in array - decide what's easier/better
        bounds[0] = leftX;
        bounds[1] = topY;
        bounds[2] = rightX;
        bounds[3] = bottomY;
        this.row = row;
        drawSpot(gc, squareSize);
    }

    public void drawSpot(GraphicsContext gc, int squareSize){
        if(((bounds[2]/squareSize)%2)==(bounds[3]/squareSize)%2){
            gc.setFill(Color.ALICEBLUE);
        }
        else{
            gc.setFill(Color.LAVENDER);
        }
        gc.fillRect(bounds[0], bounds[1], squareSize, squareSize);
    }

    public boolean checkWithinBounds(double x, double y){
        if(x>=bounds[0] && x<=bounds[2] && y>=bounds[1] && y<=bounds[3]){
            System.out.println(bounds[3]);
            System.out.println("True");
            return true;
        }
        return false;
    }

    public double getBounds(int index){
        return bounds[index];
    }

    public boolean getOccupiedDefender(){
        return occupiedDefender;
    }

    public void changeOccupiedDefender(boolean occupied){
        occupiedDefender = occupied;
    }

    public int getRow(){
        return row;
    }

}
