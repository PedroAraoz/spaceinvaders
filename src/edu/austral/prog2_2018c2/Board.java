package edu.austral.prog2_2018c2;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Toolkit;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
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
    private int level = 1;
    //~~~ podemos polimorfisar los grapher drawShields, drawPoints, drawLives
    private boolean ingame = true;
    private final String explImg = "src/images/explosion.png"; //Esto podria ir adentro de alien y que tenga un metodo para cambiar su sprite, no?
    private String message = "Game Over";

    private Thread animator;

    private List<Shield> shields;
    private Grapher grapher = new Grapher();

    public Board() {

        initBoard();
    }

    private void initBoard() {


        setFocusable(true);
        d = new Dimension(BOARD_WIDTH, BOARD_HEIGHT);
        setBackground(Color.black);

        gameInit();//Para que hace esto un metodo aparte? Nunca lo llama en otro lado a gameInit()
        setDoubleBuffered(true);
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
        addKeyListener(new Keyboard(this, player, shot));
        directionUFO = direction;
        ufo = new UFO(directionUFO);


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
        for(int i = 0; i<4; i++) {
            if (shields.get(i).isVisible()) {
                grapher.drawImage(g, shields.get(i).getImage(), shields.get(i).getX(), shields.get(i).getY());
                grapher.drawText(g, (shields.get(i).getPercentage()) + "%" , shields.get(i).getX()+11, shields.get(i).getY()+30,  Color.white);
            }
        }
    }
    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        grapher.drawBackground(g, d);

        if (ingame) {
            grapher.drawLives(g, player.getLife());
            grapher.drawPoints(g, player.getPoints());
            grapher.drawFloor(g);
            grapher.drawLevel(g, level);
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
            if (level < 5) {
                if (direction > 0) {
                    direction++;
                } else {
                    direction--;
                }
                level++;
                deaths = 0;
                System.out.println("Level passed");
                spawnAliens();
            } else {
                ingame = false;
                message = "Game won!";
            }
        }

        // player
        player.act();

        // shot
        if (shot.isVisible()) {

            for (Alien alien: aliens) {

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
            int increment = 4;
            if(player.isDoubleDamage()){
                increment = increment*2;
            }
            y -= increment;

            if (y < 0) {
                player.missedShot();
                shot.die();
            } else {
                shot.setY(y);
            }
        }

        // aliens
        //Esto se puede mover a aliens en vez de que este en board?

        for (Alien alien : aliens) {
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
                if(!player.isAliensFrozen()) {
                    alien.act(direction);
                }
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

            for(int i = 0; i<4; i++) {
                if (collides(shields.get(i), b)) {
                    shields.get(i).hit();
                    b.setVisible(false);
                }
            }

            if (collides(player,b)) {
                    if(player.getLife()==0) {
                        ImageIcon ii = new ImageIcon(explImg);
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

                b.setY(b.getY() + Math.abs(direction));

                if (b.getY() >= GROUND - BOMB_HEIGHT) {
                    b.setVisible(false);
                }
            }
        }
        // UFO
        if (collides(ufo, shot)){
            ufo.die();
            player.addPoints(ufo.getPoints());
        }
        if (directionUFO > 0 && ufo.getX() >= BOARD_WIDTH - ufo.getWidth()) {
            ufo.die();
        }
        if (directionUFO < 0 && ufo.getX() <= 0){
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

    public void killeverything(){
        for (int i = 0; i < aliens.size(); i++) {
            aliens.get(i).die();
        }
        deaths = NUMBER_OF_ALIENS_TO_DESTROY;
    }
}