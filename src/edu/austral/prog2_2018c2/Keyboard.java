package edu.austral.prog2_2018c2;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class Keyboard extends KeyAdapter {

    Player player;
    Shot shot;

    public Keyboard(Player player, Shot shot){
        this.player = player;
        this.shot = shot;
    }

    @Override
    public void keyReleased(KeyEvent e) {

        player.keyReleased(e);
    }

    @Override
    public void keyPressed(KeyEvent e) {

        player.keyPressed(e);

        int x = player.getX();
        int y = player.getY();

        int key = e.getKeyCode();

        if (key == KeyEvent.VK_SPACE) {

            //removed if(ingame) before this line

            if (!shot.isVisible()) {
                shot = new Shot(x, y); //esto reempza el shot de keyboard, no de board. Cuando implementemos el grapher hay q cambiar eso
            }
        }
    }
}
