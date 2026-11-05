package com.example.template;

import javafx.animation.Animation;
import javafx.animation.AnimationTimer;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;

import java.awt.event.MouseAdapter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Supplier;

public class HelloController {
    public AnchorPane anchorPane;
    public GridPane gridPaneDefenderButtons;
    public Tab tabGameScreen, tabDefenderShop;
    public Label lblEnergyAmount, lblCoins, lblFeedback;
    public ImageView imgViewShopDefender;
    @FXML
    private Canvas myCanvas = new Canvas(800,640);
    //width, height
    private GridSpot[][] board = new GridSpot[8][10];
    //row, column (height, width)
    private GraphicsContext gc = myCanvas.getGraphicsContext2D();
    private int squareSize = 80;
    @FXML
    private ArrayList<Enemy> enemies = new ArrayList<>();
    private ArrayList<Defender> defenders = new ArrayList<>();
    private ArrayList<Energy> energyArray = new ArrayList<>();
    private ArrayList<PowerUp> powerUps = new ArrayList<>();
    private WaveManager waveManager;
    private PlayerInfo playerInfo = new PlayerInfo();
    private Button[] defenderButtons;
    private boolean defenderSelected = false;
    private String selectedSrc = "";
    private boolean gameOver;
    boolean win = false;
    private Map<Button, Defender> buttonDefenderMap;
    private Defender buttonDefenderSelected;

    @FXML
    private ListView listViewDefenderShop, listViewDefenderStats;
    @FXML
    private TabPane tabPane;


    public void initialize(){
        anchorPane.getChildren().add(myCanvas);
        createGrid();
        createDefenderButtons();
        setUpDefenderShop();
    }

    public void createGrid(){
        for(int i = squareSize; i<=myCanvas.getHeight(); i+=squareSize){
            for( int j = squareSize; j<=myCanvas.getWidth(); j+=squareSize){
                board[(i/squareSize)-1][(j/squareSize)-1] =  new GridSpot(j, j-squareSize, i-squareSize, i, squareSize, gc,(i/squareSize)-1);
            }
        }

        EventHandler z = new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                double x = event.getX();
                double y = event.getY();
                if(!findEnergy(x,y) && !findPowerUP(x,y) && defenderSelected){
                    placeCharacter(x, y);
//                    resetButtons();
                    defenderSelected = false;
                }
                System.out.println(x + ", " + y);

            }
        };
        myCanvas.setOnMouseClicked(z);
    }

    public void createDefenderButtons(){
        defenderButtons = new Button[playerInfo.getDefendersOwned().size()];
        gridPaneDefenderButtons.setVisible(true);
        buttonDefenderMap = new HashMap<>();
        for(int i = 0; i<playerInfo.getDefendersOwned().size(); i++){
            defenderButtons[i] = new Button();
            defenderButtons[i].setPrefSize(80,90);
            defenderButtons[i].setMinSize(60, 70);
            defenderButtons[i].setMaxSize(100, 110);
            buttonDefenderMap.put(defenderButtons[i], playerInfo.getDefendersOwned().get(i));
            Image image = new Image(getClass().getResource("/" + playerInfo.getDefendersOwned().get(i).getSrc()).toExternalForm());
            ImageView imageView = new ImageView(image);
            imageView.setFitHeight(90);
            imageView.setFitWidth(80);
            imageView.setPreserveRatio(true);
            defenderButtons[i].setGraphic(imageView);
//            defenderButtons[i].setText(playerInfo.getDefendersOwned().get(i).getCost() + "");
            gridPaneDefenderButtons.add(defenderButtons[i], 1, i);
        }
        EventHandler z = new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
//                setButtons();
                defenderSelected = true;
                buttonDefenderSelected =  buttonDefenderMap.get((Button) event.getSource());
                ((Button) event.getSource()).setDisable(true);
                if(((Button) event.getSource()).getGraphic() instanceof ImageView imageView){
                    Image image = imageView.getImage();
                    selectedSrc = image.getUrl();
                    selectedSrc = selectedSrc.substring(selectedSrc.indexOf("pictures"));
                    System.out.println(selectedSrc);
                }
                //when clicking on the defender buttons get what they click and then can use this to determine what object they place on board
                //once images are put on the button find a way to derive the src
                //Use hash map with the src's of the object as the key to the object type or use many if statements

            }
        };
        for(int i =0; i<defenderButtons.length; i++){
            defenderButtons[i].setOnAction(z);
        }
    }

    public void setButtons(){
        for(int i = 0; i<defenderButtons.length; i++){
            System.out.println(buttonDefenderMap.get(defenderButtons[i]).getEnergyCost());
            if(buttonDefenderMap.get(defenderButtons[i]).getEnergyCost()<= playerInfo.getEnergy()){
                defenderButtons[i].setDisable(false);
            }
            else{
                defenderButtons[i].setDisable(true);
            }
        }
    }

//    public void resetButtons(){
//        for(int i = 0; i<defenderButtons.length; i++){
//            defenderButtons[i].setDisable(false);
//        }
//    }



    public void placeCharacter(double x, double y){
        if(findSpot(x, y)!= null){
            GridSpot spot = findSpot(x, y);
            if(!spot.getOccupiedDefender()){
                if(selectedSrc.equals("pictures/defenders/RobotShooter.png")){
                    defenders.add(new RobotShooter(spot.getBounds(0), spot.getBounds(1), spot.getRow()));
                }
                else if(selectedSrc.equals("pictures/defenders/RobotShield.png")){
                    defenders.add(new RobotShield(spot.getBounds(0), spot.getBounds(1), spot.getRow()));
                }
                else if(selectedSrc.equals("pictures/defenders/RobotMineActivated.png")){
                    defenders.add(new RobotMine(spot.getBounds(0), spot.getBounds(1), spot.getRow()));
                }
                playerInfo.increaseEnergy(-buttonDefenderSelected.getEnergyCost());
                spot.changeOccupiedDefender(true);
            }
        }
        setButtons();
    }

    public GridSpot findSpot(double x, double y){
        for(int i = 0; i<board.length; i++){
            for(int j = 0; j<board[0].length; j++){
                if(board[i][j].checkWithinBounds(x, y)){
                    return board[i][j];
                }
            }
        }
        return null;
    }

    public boolean findEnergy(double x, double y){
        for(int i = energyArray.size() - 1; i>=0; i--){
            Energy energy = energyArray.get(i);
            if(energy.getX()<=x && energy.getX()+energy.getWidth()>=x && energy.getY()<=y && energy.getY()+energy.getHeight()>=y){
                playerInfo.increaseEnergy(energy.getEnergyAmount());
                checkStealerAliens(energy);
                energyArray.remove(i);
                setButtons();
                return true;
            }
        }
        return false;
    }

    public boolean findPowerUP(double x, double y){
        for(int i = powerUps.size() - 1; i>=0; i--){
            PowerUp powerUp = powerUps.get(i);
            if(powerUp.getX()<=x && powerUp.getX()+powerUp.getWidth()>=x && powerUp.getY()<=y && powerUp.getY()+powerUp.getHeight()>=y){
                powerUp.powerUpSelected(enemies, defenders, waveManager);
                powerUps.remove(i);
                return true;
            }
        }
        return false;
    }

    public void checkStealerAliens(Energy energy){
        for(int i = enemies.size()-1; i>=0; i--){
            if(enemies.get(i) instanceof StealerAlien){
                if(((StealerAlien) enemies.get(i)).getStolenEnergy() == energy){
                    ((StealerAlien) enemies.get(i)).setStealTimer();
                }
            }
        }
    }

    public boolean energyCanBeTaken(){
        for(int i = energyArray.size() -1; i>= 0; i--){
            if(energyArray.get(i).isMoving()){
                return true;
            }
        }
        return false;
    }

    public void handleStartAnimation(){
        win = false;
        tabDefenderShop.setDisable(true);
        gameOver = false;
        waveManager = new WaveManager();
        playerInfo.setEnergy(100);
        setButtons();
        new AnimationTimer(){
            @Override
            public void handle(long now) {
                if(!gameOver){
                    if(enemies.size()>0){
                        for(int i = enemies.size()-1; i>=0; i--){
                            if(enemies.get(i).getHealth()<=0){
                                if(enemies.get(i) instanceof StealerAlien && ((StealerAlien) enemies.get(i)).getStolenEnergy() != null){
                                    ((StealerAlien) enemies.get(i)).getStolenEnergy().changeMove(true);
                                }
                                waveManager.enemyDroppings(enemies.get(i), energyArray, powerUps);
                                enemies.remove(i);
                            }

                            if(enemies.get(i) instanceof DashingAlien){
                                if(now - ((DashingAlien) enemies.get(i)).getDashTimer() > ((DashingAlien) enemies.get(i)).getDashInterval()){
                                    ((DashingAlien) enemies.get(i)).setDashTimer();
                                }
                            }
                            if(enemies.get(i) instanceof StealerAlien){
                                if(now - ((StealerAlien) enemies.get(i)).getStealTimer() > ((StealerAlien) enemies.get(i)).getStealInterval() && !((StealerAlien) enemies.get(i)).isCollectingEnergy() && energyCanBeTaken()){
                                    ((StealerAlien) enemies.get(i)).getRandEnergy(energyArray);
                                }
                                if(((StealerAlien) enemies.get(i)).getStolenEnergy() != null){
                                    ((StealerAlien) enemies.get(i)).checkEnergyCollision(energyArray);
                                }
                            }

                            if(now - enemies.get(i).getMovementTimer() > 50000000 && enemies.get(i).getMove()){
                                enemies.get(i).moveEnemy();
                                enemies.get(i).setMovementTimer();
                            }
                            if(enemies.get(i).collision(defenders, squareSize) != null){
                                enemies.get(i).changeMove(false);
                                Defender defender = enemies.get(i).collision(defenders, squareSize);
                                if(defender instanceof RobotMine && ((RobotMine) defender).isActivated()){
                                    ((RobotMine) defender).explode(enemies);
                                    defender.changeHealth(-defender.getHealth());
                                }
                                if(now - enemies.get(i).getAttackTimer() > 1000000000){
                                    defender.changeHealth(-enemies.get(i).getDamage());
                                    enemies.get(i).setAttackTimer();
                                }
                            }
                            else{
                                enemies.get(i).changeMove(true);
                            }
                            if(enemies.get(i).getX()<0){
                                gameOver = true;
                            }
                        }
                    }
                    if(defenders.size()>0){
                        for(int i = defenders.size()-1; i>=0; i--){
                            if(defenders.get(i) instanceof RobotMine){
                                if(!((RobotMine) defenders.get(i)).isActivated() && now - ((RobotMine) defenders.get(i)).getActivationTimer() > ((RobotMine) defenders.get(i)).getActivationInterval()){
                                    ((RobotMine) defenders.get(i)).activateMine();
                                }
                            }
                            if(!(defenders.get(i) instanceof RobotShield) && !(defenders.get(i) instanceof RobotMine)){
                                //Robot shield and mine doesn't attack
                                if(defenders.get(i).checkForEnemyInRow(enemies, myCanvas)){
                                    if(now - defenders.get(i).getAttackTimer() > defenders.get(i).getAttackInterval()){
                                        defenders.get(i).createNewProjectile();
                                        defenders.get(i).setAttackTimer();
                                    }

                                }

                                if(defenders.get(i).getProjectiles().size()>0){
                                    for(int j = defenders.get(i).getProjectiles().size() - 1; j>=0; j--){
                                        if(now - defenders.get(i).getProjectiles().get(j).getMovementTimer() > defenders.get(i).getProjectileSpeedInterval()){
                                            defenders.get(i).getProjectiles().get(j).moveX(3);
                                            defenders.get(i).getProjectiles().get(j).setMovementTimer();
                                            if(defenders.get(i).getProjectiles().get(j).collision(enemies)){
                                                defenders.get(i).getProjectiles().remove(j);
                                            }
                                        }
                                    }

                                }

                            }
                            if(defenders.get(i).getHealth()<=0){
                                defenders.remove(i);
                            }
                        }
                    }
                    if(energyArray.size()>0){
                        for(int i = energyArray.size() -1; i>=0; i--){
                            if(now - energyArray.get(i).getMovementTimer() > 80000000){
                                energyArray.get(i).setMovementTimer();
                                if(energyArray.get(i).isMoving()){
                                    energyArray.get(i).moveEnergy();
                                    if(energyArray.get(i).getY()>150){
                                        energyArray.remove(i);
                                    }
                                }
                            }
                        }
                    }

                    if(powerUps.size()>0){
                        for(int i = powerUps.size() - 1; i>=0; i--){
                            if(now - powerUps.get(i).getAvailabilityTimer() > 5000000000l){
                                powerUps.remove(i);
                            }
                        }
                    }

                    if(now - waveManager.getWaveTimer() > waveManager.getWaveInterval()){
                        waveManager.spawnWave(enemies, board);
                        waveManager.setWaveTimer();
                    }
                    if(now - waveManager.getEnemySpawnTimer() > waveManager.getEnemySpawnInterval()){
                        System.out.println("enemy spawned");
                        waveManager.spawnEnemy(enemies, board);
                        waveManager.setEnemySpawnTimer();
                    }
                    if(now - waveManager.getEnergySpawnTimer() > waveManager.getEnergySpawnInterval()){
                        waveManager.createEnergy(energyArray, myCanvas.getWidth());
                        waveManager.setEnergySpawnTimer();
                    }
                    if(waveManager.isEnemiesSlownDown() && now - waveManager.getEnemySlowDownTimer() > 6000000000l){
                        waveManager.changeEnemiesSlownDown(false);
                        waveManager.resetEnemySpeed(enemies);
                    }
                    if(waveManager.isDefendersFastAttack() && now - waveManager.getDefenderFastAttackTimer() > 6000000000l){
                        waveManager.changeDefendersFastAttack(false);
                        waveManager.resetDefenderAttackSpeed(defenders);
                    }

                    if(enemies.isEmpty() && waveManager.isLastWave()){
                        win = true;
                        gameOver = true;
                        resetGame();
                    }


                    updateScreen();
                }
            }

        }.start();
    }

    public void resetGame(){
        tabDefenderShop.setDisable(false);
        defenders.clear();
        enemies.clear();
        energyArray.clear();
        gc.setFill(Color.BLACK);
        gc.fillRect(0,0,myCanvas.getWidth(),myCanvas.getHeight());
        gc.setFill(Color.WHITE);
        if(win){
            gc.fillText("Congratulations! You defeated all Aliens", myCanvas.getWidth()/2, myCanvas.getHeight()/2);
            int coinsEarned = (int)(Math.random()*15+10) * 10;
            playerInfo.changeCoins(coinsEarned);
            lblFeedback.setText("Congratulations you earned " + coinsEarned + " coins");
        }
        else{
            gc.fillText("Game Over. You have lost", myCanvas.getWidth()/2, myCanvas.getHeight()/2);
        }

    }

    public void updateScreen(){
        for(int i = 0; i<board.length; i++){
            for(int j =0; j<board[0].length; j++){
                board[i][j].drawSpot(gc, squareSize);
            }
        }

        for(Defender defender: defenders){
            gc.drawImage(defender.getImage(), defender.getX(), defender.getY(), 80, 80);
            for(Projectile projectile: defender.getProjectiles()){
                gc.drawImage(projectile.getImage(), projectile.getX(),projectile.getY(), projectile.getWidth(), projectile.getHeight() );
            }
        }

        for(Enemy enemy: enemies){
            gc.drawImage(enemy.getImage(), enemy.getX(), enemy.getY(), enemy.getWidth(), enemy.getHeight());
        }

        for(PowerUp powerUp: powerUps){
            gc.drawImage(powerUp.getImage(), powerUp.getX(), powerUp.getY(), powerUp.getWidth(), powerUp.getHeight());
        }

        for(Energy energy: energyArray){
            gc.drawImage(new Image(getClass().getResourceAsStream("/pictures/Energy.png")), energy.getX(), energy.getY(), energy.getWidth(), energy.getHeight());
        }

        if(gameOver){
            resetGame();
        }

        lblEnergyAmount.setText("Energy: " + playerInfo.getEnergy());
    }

    //Shop
    public void setUpDefenderShop(){
        listViewDefenderShop.getItems().clear();
        for(Defender defender: playerInfo.getDefendersInShop()){
            listViewDefenderShop.getItems().add(defender.getName() + ":  " + defender.getBuyCost() + " coins");
        }
    }


    public void showDefenderStats(MouseEvent mouseEvent) {
        listViewDefenderStats.getItems().clear();
        int index = listViewDefenderShop.getSelectionModel().getSelectedIndex();
        Defender selectedDefender = playerInfo.getDefendersInShop().get(index);
        imgViewShopDefender.setImage(new Image(getClass().getResourceAsStream("/" + selectedDefender.getSrc())));
        listViewDefenderStats.getItems().add("Energy Cost: " + selectedDefender.getEnergyCost());
        listViewDefenderStats.getItems().add("Health: " + selectedDefender.getHealth());
        listViewDefenderStats.getItems().add("Damage: " + selectedDefender.getDamage());
    }


    public void handleBuyDefender(ActionEvent actionEvent) {
        int index = listViewDefenderShop.getSelectionModel().getSelectedIndex();
        if(playerInfo.getCoins() >= playerInfo.getDefendersInShop().get(index).getBuyCost()){
            playerInfo.getDefendersOwned().add(playerInfo.getDefendersInShop().get(index));
            playerInfo.changeCoins(-playerInfo.getDefendersInShop().get(index).getBuyCost());
            playerInfo.getDefendersInShop().remove(index);
            listViewDefenderShop.getItems().remove(index);
        }
        lblCoins.setText("Coins: " + playerInfo.getCoins());
        createDefenderButtons();
    }

    public void handleUpdateShop(Event event) {
        lblCoins.setText("Coins: " + playerInfo.getCoins());
    }


    //Shop
}