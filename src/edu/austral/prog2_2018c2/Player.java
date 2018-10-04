package edu.austral.prog2_2018c2;

import java.awt.event.KeyEvent;

import javax.swing.ImageIcon;

public class Player extends Sprite implements Commons {

    private int points = 0;
    private int consecutiveHits = 0;

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

    public int getConsecutiveHits(){
        return consecutiveHits;
    }

    public void consecutiveHitPlus1(){
        this.consecutiveHits++;
    }

    public void resetConsecutiveHits(){
        this.consecutiveHits = 0;
    }

    public void giveSpecialPower() {
        double randomNumber = Math.random()*101; //genera un numero aleatorio entre 0 y 100. El Math.random() solo genera numeros entre 0 y 1

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
}