package edu.austral.prog2_2018c2;

import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.swing.ImageIcon;

public class Player extends Sprite implements Commons {
    
    private int life = 3;
    private int points = 0;
    private int consecutiveHits = 0;

    private List<Shield> shields;
    
    private final int START_Y = 280;
    private final int START_X = 270;

    private final String playerImg = "src/images/player.png";
    private int width;

    public Player() {

        initPlayer();
    }

    private void initPlayer() {

        ImageIcon ii = new ImageIcon(playerImg);

        width = ii.getImage().getWidth(null);

        setImage(ii.getImage());
        setX(START_X);
        setY(START_Y);
        shields = new ArrayList<>();
        for (int i = 0; i < 4; i++){
            shields.add(new Shield());
        }
    }

    public void act() {

        x += dx;
        // control de bordes del board
        if (x <= 2) {
            x = 2;
        }

        if (x >= BOARD_WIDTH - 2 * width) {
            x = BOARD_WIDTH - 2 * width;
        }
    }

    public void keyPressed(KeyEvent e) {

        int key = e.getKeyCode();
        // se fija si estas apretnado izq o derecha
        if (key == KeyEvent.VK_LEFT) {

            dx = -2;
        }

        if (key == KeyEvent.VK_RIGHT) {

            dx = 2;
        }
    }

    public void keyReleased(KeyEvent e) {

        int key = e.getKeyCode();
        //cuando soltas la flecha tu v = 0
        if (key == KeyEvent.VK_LEFT) {

            dx = 0;
        }

        if (key == KeyEvent.VK_RIGHT) {

            dx = 0;
        }
    }

    public int getPoints(){
        return points;
    }

    public void addPoints(int p){
        this.points = points + p;
    }

    public int getShieldsAmount(){
        int shieldsAmount = 0;
        for(int i = 0; i<shields.size(); i++){
            if(shields.get(i).isAlive()){
                shieldsAmount++;
            }
        }
        return  shieldsAmount;
    }

    public int getShieldPercentage(){
        for(int i = 0; i<shields.size(); i++){
            if(shields.get(i).isAlive()){
                return shields.get(i).getPercentage();
            }
        }
        return 0;
    }

    public int getConsecutiveHits(){
        return consecutiveHits;
    }

    public void consecutiveHitPlus1(){
        this.consecutiveHits++;
        if(consecutiveHits >= 4){
            giveSpecialPower();
        }
    }

    public void resetConsecutiveHits(){
        this.consecutiveHits = 0;
    }

    public void giveSpecialPower() {

        consecutiveHits = 0;

        int randomNumber = (int) (Math.random()*101); //genera un numero aleatorio entre 0 y 100. El Math.random() solo genera numeros entre 0 y 1

        if(randomNumber < 10){
            freezePower();
        }
        else if(randomNumber < 30){
            immunityPower();
        }
        else{
            doubleDamagePower();
        }
    }

    private void freezePower(){}

    private void immunityPower(){}

    private void doubleDamagePower(){}
    
    public void hit(){
        boolean b = false;
        for (int i = 0; i < shields.size();i++) {
            if (shields.get(i).isAlive()) {
                b = shields.get(i).hit();
                break;
            }
        }
        if (!b) {
            life--;
        }
    }
    public int getLife() {
        return life;
    }
    public List<Shield> getShields(){
        return shields;
    }
}