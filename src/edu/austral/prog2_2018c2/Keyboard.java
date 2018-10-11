package edu.austral.prog2_2018c2;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class Keyboard extends KeyAdapter {

    //Estos son los objetos a los que necesita mandarles los mensajes cuando se presionan las teclas
    private Player player;
    private Shot shot;
    private Board board;

    public Keyboard(Board board, Player player, Shot shot){
        this.player = player;
        this.shot = shot;
        this.board = board;
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

            //removed if(ingame) before this line.
            if (!shot.isVisible()) {
                board.setShot(new Shot(x, y));
            }
        }

        this.shot = board.getShot();
    }
}
