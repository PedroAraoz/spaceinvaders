package edu.austral.prog2_2018c2;

import java.awt.*;
import javax.swing.JPanel;


public class Grapher extends JPanel implements Commons {
    
    private int ratio;
    private String text;
    public Grapher() {
      ratio = GROUND + (BOARD_HEIGHT - GROUND)/3;
    }

    public void drawImage(Graphics g, Image image, int x, int y) {
        g.drawImage(image, x, y, this);
    }

    public void endGame(Graphics g, String message) {
        g.setColor(Color.black);
        g.fillRect(0, 0, BOARD_WIDTH, BOARD_HEIGHT);
        //cuadrado del game over
        g.setColor(new Color(0, 32, 48));
        g.fillRect(50, BOARD_HEIGHT / 2 - 30, BOARD_WIDTH - 100, 50);
        g.setColor(Color.white);
        g.drawRect(50, BOARD_HEIGHT / 2 - 30, BOARD_WIDTH - 100, 50);

        Font small = new Font("Comic Sans", Font.BOLD, 14);
        FontMetrics metr = this.getFontMetrics(small);

        g.setColor(Color.white);
        g.setFont(small);
        g.drawString(message, (BOARD_WIDTH - metr.stringWidth(message)) / 2,
                BOARD_HEIGHT / 2);
    }
    
    private void drawText(Graphics g, String text, int x, int y, Color color) {
        g.setColor(color);
        g.drawString(text, x, y);
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
        drawText(g,"Lives: "+playerLife,BORDER_LEFT, ratio, Color.white);
    }

    public void drawPoints(Graphics g, int points){
        text = "Points: " + points;
        FontMetrics font = g.getFontMetrics();
        int x = font.stringWidth(text);
        drawText(g,text, (BOARD_WIDTH - x)/2, ratio, Color.white);
    }
    public void drawLevel(Graphics g, int level){
        FontMetrics font = g.getFontMetrics();
        text = "Level: " + level;
        int x =font.stringWidth(text);
        g.setColor(Color.white);
        g.drawString(text, BOARD_WIDTH-BORDER_RIGHT-x, ratio);
    }
}
