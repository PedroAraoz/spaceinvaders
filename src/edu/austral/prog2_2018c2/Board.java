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
    private int directionUFO; //ver comentario en gameInit directionUFO
    private int deaths = 0;

    private boolean ingame = true;
    private final String explImg = "src/images/explosion.png"; //Esto podria ir adentro de alien y que tenga un metodo para cambiar su sprite, no?
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
                Alien alien = new Alien(ALIEN_INIT_X + 25 * j, ALIEN_INIT_Y + 18 * i, rand[(int)(Math.random()*3)]);
                aliens.add(alien);
            }
        }

        player = new Player();
        shot = new Shot();
        addKeyListener(new Keyboard(this, player, shot));
        directionUFO = direction; //DirectionUFO no se puede eliminar si siempre va a ser igual a direction? ufo = new UFO(direction);
        ufo = new UFO(directionUFO);


        if (animator == null || !ingame) { //este "if" podriamos sacarlo, no?

            animator = new Thread(this);
            animator.start();
        }
    }

    public void drawAliens(Graphics g) {

        Iterator iterator = aliens.iterator(); //Esto para que lo hace??

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

            if (b.isVisible()) {

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
        if (player.getShieldsAmount() > 0) {
            grapher.drawImage(g, player.getShields().get(0).getImage(), player.getX() - PLAYER_WIDTH - 2, player.getY() - PLAYER_HEIGHT*2);
        }
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

        if (deaths == NUMBER_OF_ALIENS_TO_DESTROY) { //Se puede reemplazar el num de aliens de commons con un metodo que se fije cuantos hay
                                                    // O podriamos ir eliminando los aliens de la lista y que esto se fije si hay aliens en la lista
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

                if (collides(alien, shot)) {

                    //T0do lo que viene ahora deberia estar delegado

                    ImageIcon ii = new ImageIcon(explImg);
                    //no grafica nunca a la imagen de explosion
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

            if (x >= BOARD_WIDTH - BORDER_RIGHT && direction > 0) {

                direction = -direction;
                Iterator i1 = aliens.iterator();

                while (i1.hasNext()) {

                    Alien a2 = (Alien) i1.next();
                    a2.setY(a2.getY() + GO_DOWN);
                }
            }

            if (x <= BORDER_LEFT && direction < 0) {

                direction = -direction;

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

            if (shot == CHANCE && alien.isVisible() && !b.isVisible()) {
                //~~~ por que bomb tiene setDestroyed si Sprite ya tiene is visible
                //~~~ no son lo mismo?? solo nos arruina el polimorfismo tener
                //~~ isDestoyed.
                b.setVisible(true);
                b.setX(alien.getX());
                b.setY(alien.getY());
            }
            

            if (collides(player,b)) {
                    if(player.getLife()==0) {
                        ImageIcon ii
                                = new ImageIcon(explImg);
                        player.setImage(ii.getImage());
                        player.die();
                        ingame = false;
                        b.setVisible(false);
                    }
                    else{
                        player.hit();
                        b.setVisible(false);
                    }
            }

            if (b.isVisible()) {

                b.setY(b.getY() + 1);

                if (b.getY() >= GROUND - BOMB_HEIGHT) {
                    b.setVisible(false);
                }
            }
        }
        // UFO
        if (collides(ufo, shot)){
            ufo.die();
        }
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
}