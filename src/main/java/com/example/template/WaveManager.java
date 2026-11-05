package com.example.template;

import javafx.scene.canvas.GraphicsContext;

import java.util.ArrayList;
import java.util.Map;

public class WaveManager {
    private int wave = 0;
    private int totalWaves;
    private long waveInterval = 30000000000l;
    private long waveTimer;
    private long enemySpawnInterval = 4000000000l;
    private long energySpawnInterval = 6000000000l;
    private long enemySpawnTimer;
    private long energySpawnTimer;
    private boolean enemiesSlownDown = false;
    private boolean defendersFastAttack = false;
    public long enemySlowDownTimer;
    public long defenderFastAttackTimer;

    public WaveManager(){
        waveTimer = System.nanoTime();
        enemySpawnTimer = System.nanoTime();
        energySpawnTimer = System.nanoTime();
        totalWaves = (int)(Math.random()*2+3);

    }

    public void spawnEnemy(ArrayList<Enemy> enemies, GridSpot[][] spots){
        int row = (int)(Math.random()*spots.length);
        createEnemy(row, enemies, spots);
//        int randEnemy = (int)(Math.random()*10);
////        enemies.add(new StealerAlien((Math.random()*100) + 800, spots[row][0].getBounds(1), row));
//        if(randEnemy<5){
//            enemies.add(new BasicAlien((Math.random()*100) + 800, spots[row][0].getBounds(1), row));
//        }
//        else if(randEnemy<7){
//            enemies.add(new DashingAlien((Math.random()*100) + 800, spots[row][0].getBounds(1), row));
//        }
//        else{
//            enemies.add(new StealerAlien((Math.random()*100) + 800, spots[row][0].getBounds(1), row));
//        }

        enemySpawnInterval = (long)(Math.random()*6+3)*1000000000;
    }

    public void spawnWave(ArrayList<Enemy> enemies, GridSpot[][] spots){
        for(int i = 0; i<spots.length; i++){
            int enemyAmnt = (int)(Math.random()*3) + 2;
            for(int j = 0; j<enemyAmnt; j++){
                createEnemy(i, enemies, spots);
//                enemies.add(new BasicAlien((Math.random()*100) + 800, spots[i][0].getBounds(1), j));
            }
        }
        wave++;
    }

    public void createEnemy(int row, ArrayList<Enemy> enemies, GridSpot[][] spots){
        int randEnemy = (int)(Math.random()*10);
        if(randEnemy<5){
            enemies.add(new BasicAlien((Math.random()*100) + 800, spots[row][0].getBounds(1), row));
        }
        else if(randEnemy<7){
            enemies.add(new DashingAlien((Math.random()*100) + 800, spots[row][0].getBounds(1), row));
        }
        else{
            enemies.add(new StealerAlien((Math.random()*100) + 800, spots[row][0].getBounds(1), row));
        }
    }

    public void createEnergy(ArrayList<Energy> energyArray, double width){
        int numEnergy = (int)(Math.random()*3+1);
        for(int i = 0; i<numEnergy; i++){
            energyArray.add(new Energy(Math.random()*width, Math.random()*-50, true));
        }
    }

    public void enemyDroppings(Enemy enemy, ArrayList<Energy> energyArrayList, ArrayList<PowerUp> powerUps){
        int randNum = (int)(Math.random()*30);
        if(randNum<5){
            energyArrayList.add(new Energy(enemy.getX(), enemy.getY(), false));
        }
        else if(randNum<7){
            powerUps.add(new PowerUp(enemy.getX(), enemy.getY(), "SlowEnemy"));
        }
        else if(randNum<9){
            powerUps.add(new PowerUp(enemy.getX(), enemy.getY(), "FastAttack"));
        }
    }

    public long getWaveInterval(){
        return waveInterval;
    }

    public long getWaveTimer(){
        return waveTimer;
    }

    public void setWaveTimer(){
        waveTimer = System.nanoTime();
    }

    public long getEnemySpawnInterval(){
        return enemySpawnInterval;
    }

    public long getEnemySpawnTimer(){
        return enemySpawnTimer;
    }

    public void setEnemySpawnTimer(){
        enemySpawnTimer = System.nanoTime();
    }

    public long getEnergySpawnInterval(){
        return energySpawnInterval;
    }

    public long getEnergySpawnTimer(){
        return energySpawnTimer;
    }

    public void setEnergySpawnTimer(){
        energySpawnTimer = System.nanoTime();
        energySpawnInterval = (long) (Math.random()*10 + 4) * 1000000000;
    }

    public int getWave(){
        return wave;
    }

    public boolean isLastWave(){
        return (wave==totalWaves);
    }

    public boolean isEnemiesSlownDown(){
        return enemiesSlownDown;
    }

    public boolean isDefendersFastAttack(){
        return defendersFastAttack;
    }

    public long getEnemySlowDownTimer(){
        return enemySlowDownTimer;
    }

    public long getDefenderFastAttackTimer(){
        return defenderFastAttackTimer;
    }

    public void changeEnemiesSlownDown(boolean change){
        if(change){
            enemySlowDownTimer = System.nanoTime();
        }
        enemiesSlownDown = change;
    }

    public void changeDefendersFastAttack(boolean change){
        if(change){
            defenderFastAttackTimer = System.nanoTime();
        }
        defendersFastAttack = change;
    }

    public void resetEnemySpeed(ArrayList<Enemy> enemies){
        for(int i =enemies.size() - 1; i>=0; i--){
            enemies.get(i).setSpeed(3);
        }
    }

    public void resetDefenderAttackSpeed(ArrayList<Defender> defenders){
        for(int i = defenders.size() - 1; i>=0; i--){
            defenders.get(i).changeProjectileSpeedInterval(500000000);
        }
    }


}
