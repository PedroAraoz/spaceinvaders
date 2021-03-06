package edu.austral.prog2_2018c2;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Toolkit;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;
import javax.swing.*;

public class Board extends JPanel implements Runnable, Commons {
    private Dimension d;
    private ArrayList<Alien> aliens;
    private Player player;
    private Shot shot;
    private Shot secondShot;
    private UFO ufo;
    private final int ALIEN_INIT_X = 150;
    private final int ALIEN_INIT_Y = 5;
    private int direction = -1;
    private int aliensKilled = 0;
    private int level = 1;
    private boolean ingame = true;
    private String message;
    private long timerUFO;
    private int random;
    private Thread animator;
    private List<Shield> shields;

    public Board() {
        initBoard();
    }
    private void initBoard() {

        setFocusable(true);
        d = new Dimension(BOARD_WIDTH, BOARD_HEIGHT);
        setBackground(Color.black);

        gameInit();
        setDoubleBuffered(true);
        timerUFO = System.currentTimeMillis();
        random = 45 + (int)(Math.random() * ((60 - 45) + 1));
    }


    public void gameInit() {
        
        aliens = new ArrayList<>();
        // creating the aliens
        spawnAliens();

        shields = new ArrayList<>();
        for (int i = 0; i < 4; i++){
            int separation = BOARD_WIDTH/5;
            shields.add(new Shield());
            shields.get(i).setY(200);
            shields.get(i).setX(separation-31 + separation*i);
        }

        player = new Player();
        shot = new Shot();
        secondShot = new Shot();
        addKeyListener(new Keyboard(this));
        ufo = new UFO(direction);
        ufo.die();


        if (animator == null || !ingame) {

            animator = new Thread(this);
            animator.start();
        }
    }

    private void spawnAliens() {
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 6; j++) {
                String[] rand = {"small", "medium", "big"};
                Alien alien = new Alien(ALIEN_INIT_X + 25 * j, ALIEN_INIT_Y + 18 * i, rand[(int)(Math.random()*3)]);
                aliens.add(alien);
            }
        }
    }

    public void drawAliens(Grapher grapher) {

        for (Alien alien: aliens) {

            if (alien.isVisible()) {

                grapher.drawImage(alien.getImage(), alien.getX(), alien.getY());
            }

        }
    }

    public void drawPlayer(Grapher grapher) {
        grapher.drawImage(player.getImage(), player.getX(), player.getY());
    }

    public void drawShot(Grapher grapher) {

        if (shot.isVisible()) {

            grapher.drawImage(shot.getImage(), shot.getX(), shot.getY());
        }
        if (secondShot.isVisible()) {

            grapher.drawImage(secondShot.getImage(), secondShot.getX(), secondShot.getY());
        }

    }

    public void drawBombing(Grapher grapher) {

        for (Alien a : aliens) {

            Bomb b = a.getBomb();

            if (b.isVisible()) {

                grapher.drawImage(b.getImage(), b.getX(), b.getY());
            }
        }
    }

    public void drawUFO(Grapher grapher) {
        if (ufo.isVisible()) {

            grapher.drawImage(ufo.getImage(), ufo.getX(), ufo.getY());
        }
    }

    public void drawShield(Grapher grapher) {
        for(int i = 0; i<4; i++) {
            if (shields.get(i).isVisible()) {
                grapher.drawImage(shields.get(i).getImage(), shields.get(i).getX(), shields.get(i).getY());
            }
        }
    }
    @Override
    public void paintComponent(Graphics g) {
        Grapher grapher = new Grapher(g);
    
        super.paintComponent(g);
        grapher.drawBackground(d);

        if (ingame) {
            grapher.drawLives(player.getLife());
            grapher.drawPoints(player.getPoints());
            grapher.drawFloor();
            grapher.drawLevel(level);
            drawAliens(grapher);
            drawPlayer(grapher);
            drawShot(grapher);
            drawBombing(grapher);
            drawUFO(grapher);
            drawShield(grapher);
        }

        Toolkit.getDefaultToolkit().sync();
        g.dispose();
    }

    public void animationCycle() {

        if (aliensKilled == NUMBER_OF_ALIENS_TO_DESTROY) {
            if (level < 5) {
                levelUp();
            } else {
                gameOver("Game won!");
            }
        }

        // player
        player.act();

        // shot
        shotLogic(shot);
        if(player.isDoubleDamage()){
            shotLogic(secondShot);
        }
        if(!player.isDoubleDamage()){
            secondShot.setVisible(false);
        }

        //Shields
        ShieldLogic();

        // aliens
        AlienLogic();

        // bombs
        BombLogic();
        // UFO
        UFOlogic();
    }

    private void BombLogic() {
        Random generator = new Random();

        for (Alien alien : aliens) {

            int shot = generator.nextInt(15);
            Bomb b = alien.getBomb();

            if (shot == CHANCE && alien.isVisible() && !b.isVisible()) {
                alien.spawnBomb();
            }
            //if shield gets hit
            for (int i = 0; i < 4; i++) {
                if (collides(shields.get(i), b)) {
                    shields.get(i).hit();
                    b.setVisible(false);
                }
            }
            //player gets hit
            if (collides(player, b)) {
                if (player.getLife() == 0) {
                    player.die();
                    b.setVisible(false);
                    gameOver("You Died");
                } else {
                    player.hit();
                    if (!player.isImmune()) {
                        for (Shield shield : shields) {
                            if (shield.isVisible()) {
                                player.setX(shield.getX() + (shield.getWidth() - player.getWidth()) / 2);
                                break;
                            }
                        }
                    }
                    b.setVisible(false);
                }
            }

            if (b.isVisible()) {

                b.setY(b.getY() + Math.abs(direction));

                if (b.getY() >= GROUND - b.getHeight()) {
                    b.setVisible(false);
                }
            }
        }
    }

    private void AlienLogic() {

        for (Alien alien : aliens) {
            int x = alien.getX();
            Iterator i1 = aliens.iterator();
            if ((x >= BOARD_WIDTH - BORDER_RIGHT && direction > 0)|| (x < BORDER_LEFT && direction < 0)){
                direction = -direction;
                while (i1.hasNext()) {
                    Alien a2 = (Alien) i1.next();
                    a2.goDown();
                }
            }
        }

        Iterator it = aliens.iterator();

        while (it.hasNext()) {

            Alien alien = (Alien) it.next();
            if (alien.isVisible()) {

                int y = alien.getY();

                if (y > GROUND - alien.getHeight()) {
                    gameOver("Invasion");
                }
                if (!player.isAliensFrozen()) {
                    alien.act(direction);
                }
            }
        }
    }
    
    private void ShieldLogic(){
        for (Shield shield : shields) {
            for (Alien alien : aliens) {
                if (collides(alien, shield)) {
                    alien.die();
                    shield.die();
                    aliensKilled++;
                }
            }
        }
    }
    
    private void UFOlogic(){
        long newTime = System.currentTimeMillis();
        if (newTime - timerUFO >= 1000 * random) {
            ufo = new UFO(direction);
            timerUFO = newTime;
            random = 45 + (int) (Math.random() * ((60 - 45) + 1));
        }
        
        if (collides(ufo, shot)) {
            ufo.die();
            shot.die();
            player.addPoints(ufo.getPoints());
        }
        if (direction > 0 && ufo.getX() >= BOARD_WIDTH - ufo.getWidth()) {
            ufo.die();
        }
        if (direction < 0 && ufo.getX() <= 0) {
            ufo.die();
        }
        ufo.act(ufo.getDirection());
    }
    
    public void shotLogic(Shot shot) {
        if (shot.isVisible()) {
            for (Alien alien : aliens) {
                if (collides(alien, shot)) {
                    
                    player.addPoints(alien.getPoints());
                    alien.die();
                    player.consecutiveHitPlus1();
                    aliensKilled++;
                    shot.die();
                    
                }
            }
            for (Shield shield : shields) {
                if (collides(shield, shot)) {
                    shield.hit();
                    shot.die();
                }
                
            }
            int y = shot.getY();
            int increment = 4;
            
            y -= increment;
            
            if (y < 0) {
                player.missedShot();
                shot.die();
            } else {
                shot.setY(y);
            }
        }
    }
    
    @Override
    public void run() {
        requestFocus();
        long beforeTime, timeDiff, sleep;

        beforeTime = System.currentTimeMillis();

        while (ingame) {

            repaint();
            animationCycle();


            timeDiff = System.currentTimeMillis() - beforeTime;
            sleep = DELAY - timeDiff;

            if (sleep < 0) {
                sleep = 2;
            }

            try {
                Thread.sleep(sleep);
            } catch (InterruptedException e) {
                System.out.println("interrupted");
            }

            beforeTime = System.currentTimeMillis();
        }
        new Grapher(this.getGraphics()).endGame(message);
    }

    public void setShot(Shot shot){
        this.shot = shot;
    }
    public void setSecondShot(Shot shot){ this.secondShot = shot; }

    public Shot getShot(){
        return shot;
    }
    public Shot getSecondShot() {
        return secondShot;
    }

    public boolean collides(Sprite receiver, Sprite projectile) {
        int pX = projectile.getX();
        int pY = projectile.getY();
        int rX = receiver.getX();
        int rY = receiver.getY();
        return (projectile.isVisible()
                && receiver.isVisible()
                && pX >= (rX)
                && pX <= (rX + receiver.getWidth())
                && pY >= (rY)
                && pY <= (rY + receiver.getHeight()));
    }

    public void killeverything(){
        for (Alien alien : aliens) {
            alien.die();
        }
        aliensKilled = NUMBER_OF_ALIENS_TO_DESTROY;
    }

    public void levelUp(){
        if (direction > 0) {
            direction++;
        }
        else {
            direction--;
        }
        level++;
        aliensKilled = 0;
        for (int i = 0; i < 4; i++) {
            if (shields.get(i).isVisible()) {
                shields.get(i).die();
                break;
            }
        }
        try {
            Thread.sleep(10);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        spawnAliens();
    }
    
    public Player getPlayer() {
        return player;
    }
    
    public void drawHighscore(){
        List<Score> leaderBoard = Score.load();
        new Grapher(this.getGraphics()).drawScoreboard(leaderBoard);
    }
    
    public void gameOver(String message){
        this.message = message;
        ingame = false;
        List<Score> leaderBoard = Score.load();
        Score s = new Score(JOptionPane.showInputDialog("Insert your name"), player.getPoints());
        leaderBoard.add(s);
        Score.save(leaderBoard);
        drawHighscore();
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}