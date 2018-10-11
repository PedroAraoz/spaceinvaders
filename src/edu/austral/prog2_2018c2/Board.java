package edu.austral.prog2_2018c2;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Toolkit;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Random;

import javax.swing.ImageIcon;
import javax.swing.JPanel;

public class Board extends JPanel implements Runnable, Commons {

    private Dimension d;
    private ArrayList<Alien> aliens;
    private Player player;
    private Shot shot;
    private UFO ufo;
    private final int ALIEN_INIT_X = 150;
    private final int ALIEN_INIT_Y = 5;
    private int direction = -1;
    private int directionUFO;
    private int deaths = 0;

    private boolean ingame = true;
    private final String explImg = "src/images/explosion.png";
    private String message = "Game Over";

    private Thread animator;

    Grapher grapher = new Grapher();

    public Board() {

        initBoard();
    }

    private void initBoard() {


        setFocusable(true);
        d = new Dimension(BOARD_WIDTH, BOARD_HEIGHT);
        setBackground(Color.black);

        gameInit();
        setDoubleBuffered(true);
    }
    //~~~ no hace nada????
    /*@Override
    public void addNotify() {

        super.addNotify();
        gameInit();
    }*/

    public void gameInit() {

        aliens = new ArrayList<>();
        // creating the aliens
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 6; j++) {
                String[] rand = {"small", "medium", "big"};
                Alien alien = new Alien(ALIEN_INIT_X + 18 * j, ALIEN_INIT_Y + 18 * i, rand[(int)(Math.random()*3)]);
                aliens.add(alien);
            }
        }

        player = new Player();
        shot = new Shot();
        addKeyListener(new Keyboard(this, player, shot));
        directionUFO = direction;
        ufo = new UFO(directionUFO);
        
        
        if (animator == null || !ingame) {//este if podriamos sacarlo, no?

            animator = new Thread(this);
            animator.start();
        }
    }

    public void drawAliens(Graphics g) {

        Iterator it = aliens.iterator();

        for (Alien alien: aliens) {

            if (alien.isVisible()) {

                grapher.drawImage(g, alien.getImage(), alien.getX(), alien.getY());
            }
            
        }
    }

    public void drawPlayer(Graphics g) {
        grapher.drawImage(g, player.getImage(), player.getX(), player.getY());
    }

    public void drawShot(Graphics g) {

        if (shot.isVisible()) {

            grapher.drawImage(g, shot.getImage(), shot.getX(), shot.getY());
        }
    }

    public void drawBombing(Graphics g) {

        for (Alien a : aliens) {

            Bomb b = a.getBomb();

            if (!b.isDestroyed()) {

                grapher.drawImage(g, b.getImage(), b.getX(), b.getY());
            }
        }
    }
    
    public void drawUFO(Graphics g) {
        if (ufo.isVisible()) {
            
            grapher.drawImage(g, ufo.getImage(), ufo.getX(), ufo.getY());
        }
    }
    public void drawShield(Graphics g) {
        grapher.drawImage(g, player.getShields().get(0).getImage(), player.getX() - PLAYER_WIDTH - 2, player.getY() - PLAYER_HEIGHT*2);
    }
    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        grapher.drawBackground(g, d);

        if (ingame) {
            grapher.drawLives(g, player.getLife());
            grapher.drawPoints(g, player.getPoints());
            grapher.drawShieldText(g, player.getShieldsAmount(), player.getShieldPercentage());
            grapher.drawFloor(g);
            drawAliens(g);
            drawPlayer(g);
            drawShot(g);
            drawBombing(g);
            drawUFO(g);
            drawShield(g);
        }

        Toolkit.getDefaultToolkit().sync();
        g.dispose();
    }

    public void animationCycle() {

        if (deaths == NUMBER_OF_ALIENS_TO_DESTROY) {
            ingame = false;
            message = "Game won!";
        }

        // player
        player.act();

        // shot
        if (shot.isVisible()) {

            int shotX = shot.getX();
            int shotY = shot.getY();

            for (Alien alien: aliens) {

                int alienX = alien.getX();
                int alienY = alien.getY();
                // shot.isvisible dos veces?
                if (alien.isVisible()                       //logica si shot le pega al alien
                            && (shotX >= (alienX)
                            && shotX <= (alienX + alien.getWidth())
                            && shotY >= (alienY)
                            && shotY <= (alienY + alien.getHeight()))) {
                        ImageIcon ii = new ImageIcon(explImg);
                        // no grafica nunca a la imagen de explosion
                        alien.setImage(ii.getImage());
                        player.addPoints(alien.getPoints());
                        alien.die();
                        //agrego el sistema de poderes especiales
                        player.consecutiveHitPlus1();
                        deaths++;
                        shot.die();

                }
            }

            int y = shot.getY();
            y -= 4;

            if (y < 0) {
                player.resetConsecutiveHits();
                shot.die();
            } else {
                shot.setY(y);
            }
        }

        // aliens

        for (Alien alien: aliens) {

            int x = alien.getX();

            if (x >= BOARD_WIDTH - BORDER_RIGHT && direction != -1) {

                direction = -1;
                Iterator i1 = aliens.iterator();

                while (i1.hasNext()) {

                    Alien a2 = (Alien) i1.next();
                    a2.setY(a2.getY() + GO_DOWN);
                }
            }

            if (x <= BORDER_LEFT && direction != 1) {

                direction = 1;

                Iterator i2 = aliens.iterator();

                while (i2.hasNext()) {

                    Alien a = (Alien) i2.next();
                    a.setY(a.getY() + GO_DOWN);
                }
            }
        }

        Iterator it = aliens.iterator();

        while (it.hasNext()) {

            Alien alien = (Alien) it.next();

            if (alien.isVisible()) {

                int y = alien.getY();

                if (y > GROUND - alien.getHeight()) {
                    ingame = false;
                    message = "Invasion!";
                }

                alien.act(direction);
            }
        }

        // bombs
        Random generator = new Random();

        for (Alien alien: aliens) {

            int shot = generator.nextInt(15);
            Bomb b = alien.getBomb();

            if (shot == CHANCE && alien.isVisible() && b.isDestroyed()) {

                b.setDestroyed(false);
                b.setX(alien.getX());
                b.setY(alien.getY());
            }

            int bombX = b.getX();
            int bombY = b.getY();
            int playerX = player.getX();
            int playerY = player.getY();

            if (player.isVisible() && !b.isDestroyed()) {       //logica si bomb le pega al player
                if (bombX >= (playerX)
                        && bombX <= (playerX + PLAYER_WIDTH)
                        && bombY >= (playerY)
                        && bombY <= (playerY + PLAYER_HEIGHT)) {
                    if(player.getLife()==0) {
                        ImageIcon ii
                                = new ImageIcon(explImg);
                        player.setImage(ii.getImage());
                        player.die();
                        ingame = false;
                        b.setDestroyed(true);
                    }
                    else{
                        //playerLife--;
                        player.hit();
                        b.setDestroyed(true);
                    }
                }
            }

            if (!b.isDestroyed()) {

                b.setY(b.getY() + 1);

                if (b.getY() >= GROUND - BOMB_HEIGHT) {
                    b.setDestroyed(true);
                }
            }
        }
        // UFO
        if (directionUFO == 1 && ufo.getX() >= BOARD_WIDTH - ufo.getWidth()) {
            ufo.die();
        }
        if (directionUFO == -1 && ufo.getX() <= 0){
            ufo.die();
        }
        ufo.act(directionUFO);
    }

    @Override
    public void run() {

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
        grapher.endGame(this.getGraphics(), message);
    }

    public void setShot(Shot shot){
        this.shot = shot;
    }

    public Shot getShot(){
        return shot;
    }
}