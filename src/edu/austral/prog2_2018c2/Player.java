package edu.austral.prog2_2018c2;

import java.awt.event.KeyEvent;
import java.util.Timer;
import java.util.TimerTask;
import javax.swing.ImageIcon;

public class Player extends Sprite implements GameObject, Movable, Commons {
    
    private int life = 3;
    private int points = 0;
    private int consecutiveHits = 0;
    private boolean Immune = false;
    private boolean frozenAliens = false;
    private boolean doubleDamage = false;

    private boolean powerIsOn = false;

    private final int START_Y = 280;
    private final int START_X = 270;

    private String playerImg;
    private final String ShipImg = "src/images/player.png";
    private final String ImmunityImg = "src/images/shield.gif";
    
    public Player() {

        initPlayer();
    }

    private void initPlayer() {

        playerImg = ShipImg;
        ImageIcon ii = new ImageIcon(playerImg);

        setWidth(ii.getImage().getWidth(null));
        setHeight(ii.getImage().getHeight(null));
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

        if (x >= BOARD_WIDTH - 2 * getWidth()) {
            x = BOARD_WIDTH - 2 * getWidth();
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

    public void consecutiveHitPlus1(){
        if(!powerIsOn) {
            this.consecutiveHits++;
            if (consecutiveHits >= 4) {
                giveSpecialPower();
            }
        }
    }

    public void missedShot(){
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
        powerIsOn = true;
    }

    private void freezePower(){
        System.out.println("Freeze Power");
        Timer timer = new Timer();
        frozenAliens = true;
        timer.schedule(new TimerTask() {
            public void run() {
                frozenAliens = false;
                powerIsOn = false;
            }
        }, 3*1000); //3 segundos

    }

    private void immunityPower(){
        System.out.println("Immunity Power");
        Timer timer = new Timer();
        Immune = true;
        playerImg = ImmunityImg;//Hay que hacer que el board actualize la imagen del player
        int time = 3 + (int)(Math.random() * ((5 - 3) + 1));
        timer.schedule(new TimerTask() {
            public void run() {
                Immune = false;
                playerImg = ShipImg;
                powerIsOn = false;
            }
        }, time*1000); //3-5 segundos

    }

    private void doubleDamagePower(){
        System.out.println("Double Damage");
        Timer timer = new Timer();
        doubleDamage = true;
        int time = 3 + (int)(Math.random() * ((5 - 3) + 1));
        timer.schedule(new TimerTask() {
            public void run() {
                powerIsOn = false;
            }
        }, time*1000); //3-5 segundos
    }
    
    public void hit(){
        if(!Immune) {
            life--;
        }
    }
    public int getLife() {
        return life;
    }

    public boolean isAliensFrozen(){
        return frozenAliens;
    }

    public boolean isDoubleDamage() {
        return doubleDamage;
    }
    
    public boolean isImmune() {
        return Immune;
    }
    
    public String getName() {return "" + Math.random();}
    
    
    
    
    @Override
    public void collided(Movable m) {
        m.collideWithPlayer(this);
    }
    @Override
    public void collideWithBomb(Bomb b) {
        System.out.println("player collided with bomb");
    }
    
}