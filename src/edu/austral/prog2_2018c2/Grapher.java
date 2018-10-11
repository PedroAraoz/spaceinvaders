package edu.austral.prog2_2018c2;

import java.awt.*;
import javax.swing.JPanel;

public class Grapher extends JPanel implements Commons {
    public Grapher() {

    }

    public void drawImage(Graphics g, Image image, int x, int y) {
        g.drawImage(image, x, y, this);
    }

    public void endGame(Graphics g, String message) {
        g.setColor(Color.black);
        g.fillRect(0, 0, BOARD_WIDTH, BOARD_HEIGHT);
        //cuadrado del game over
        g.setColor(new Color(0, 32, 48));
        g.fillRect(50, BOARD_WIDTH / 2 - 30, BOARD_WIDTH - 100, 50);
        g.setColor(Color.white);
        g.drawRect(50, BOARD_WIDTH / 2 - 30, BOARD_WIDTH - 100, 50);

        Font small = new Font("Comic Sans", Font.BOLD, 14);
        FontMetrics metr = this.getFontMetrics(small);

        g.setColor(Color.white);
        g.setFont(small);
        g.drawString(message, (BOARD_WIDTH - metr.stringWidth(message)) / 2,
                BOARD_WIDTH / 2);
    }

    public void drawFloor(Graphics g){
        g.setColor(Color.white);
        g.drawLine(0, GROUND, BOARD_WIDTH, GROUND);
    }

    public void drawBackground(Graphics g, Dimension d){
        g.setColor(Color.black);
        g.fillRect(0, 0, d.width, d.height);
    }

    public void drawLives(Graphics g, int playerLife){
        g.setColor(Color.white);
        g.drawString("Lives:" + playerLife, 1, 15);
    }

    public void drawPoints(Graphics g, int points){
        g.setColor(Color.white);
        g.drawString("Points:" + points, 1, 30);
    }

    public void drawShieldText(Graphics g, int shields, int percentage){
        g.setColor(Color.white);
        g.drawString("Shields:" + shields + "(" + percentage + "%" + ")", 1, 45);
    }
}
