package edu.austral.prog2_2018c2;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class Keyboard extends KeyAdapter {

    //Estos son los objetos a los que necesita mandarles los mensajes cuando se presionan las teclas
    private Player player;
    private Shot shot;
    private Shot secondShot;
    private Board board;

    public Keyboard(Board board){
        this.player = board.getPlayer();
        this.shot = board.getShot();
        this.secondShot = board.getSecondShot();
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
            // esto es para los dos tiros
            if(player.isDoubleDamage() && !shot.isVisible()){
                board.setSecondShot(new Shot(x-5, y));
                board.setShot(new Shot(x + 5, y));
            }
            // para un tiro
            if (!player.isDoubleDamage() && !shot.isVisible() && !player.isImmune()) {
                board.setShot(new Shot(x, y));
            }
            if (!player.isDoubleDamage() && !shot.isVisible() && player.isImmune()) {
                board.setShot(new Shot(x+17, y));
            }
        }
        if (key == KeyEvent.VK_R){
            board.killeverything();
        }

        this.shot = board.getShot();
    }
}
