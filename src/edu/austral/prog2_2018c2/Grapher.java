package edu.austral.prog2_2018c2;

import java.awt.*;
import javax.swing.*;
import java.util.List;

public class Grapher extends JPanel implements Commons {
    
    private int ratio;
    private String text;
    Graphics g;
    public Grapher(Graphics g) {
      ratio = GROUND + (BOARD_HEIGHT - GROUND)/3;
      this.g = g;
    }

    public void drawImage(Image image, int x, int y) {
        g.drawImage(image, x, y, this);
    }

    public void endGame(String message) {
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
    
    private void drawText(String text, int x, int y, Color color) {
        g.setColor(color);
        g.drawString(text, x, y);
    }
    
    public void drawFloor(){
        g.setColor(Color.white);
        g.drawLine(0, GROUND, BOARD_WIDTH, GROUND);
    }

    public void drawBackground(Dimension d){
        g.setColor(Color.black);
        g.fillRect(0, 0, d.width, d.height);
    }

    public void drawLives(int playerLife){
        drawText("Lives: "+playerLife,BORDER_LEFT, ratio, Color.white);
    }

    public void drawPoints(int points){
        text = "Points: " + points;
        FontMetrics font = g.getFontMetrics();
        int x = font.stringWidth(text);
        drawText(text, (BOARD_WIDTH - x)/2, ratio, Color.white);
    }
    public void drawLevel(int level){
        FontMetrics font = g.getFontMetrics();
        text = "Level: " + level;
        int x =font.stringWidth(text);
        g.setColor(Color.white);
        g.drawString(text, BOARD_WIDTH-BORDER_RIGHT-x, ratio);
    }
    public void drawScoreboard(List<Score> scoreboard){
        String text;
        g.setFont(new Font("Comic Sans", Font.PLAIN, 20));
        FontMetrics font = g.getFontMetrics();
        for (int i = scoreboard.size()-1; i > 0; i--) {
            text = scoreboard.get(i).getName() + " : " + scoreboard.get(i).getScore();
            int x = font.stringWidth(text);
            drawText(text, (BOARD_WIDTH - x)/2, 60+Math.abs(i-scoreboard.size())*20, Color.white);
        }
    }
}
