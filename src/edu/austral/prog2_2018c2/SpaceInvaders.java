package edu.austral.prog2_2018c2;

import edu.austral.prog2_2018c2.rpc.HelloWorldClient;
import edu.austral.prog2_2018c2.rpc.HelloWorldPublisher;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;

public class SpaceInvaders extends JFrame implements Commons {
    private JPanel jpanel;
    private JButton startButton;
    private JButton highscoreButton;
    public SpaceInvaders() {
        initUI();
    }

    private void initUI() {
        setTitle("Space Invaders");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(BOARD_WIDTH, BOARD_HEIGHT);
        setLocationRelativeTo(null);
        setResizable(false);
        
        jpanel = new JPanel();
        startButton = new JButton();
        highscoreButton = new JButton();
        jpanel.setBackground(Color.black);
        startButton.setBackground(Color.black);
        highscoreButton.setBackground(Color.white);
        startButton.setIcon(new ImageIcon("src/images/playbutton.png"));
        highscoreButton.setIcon(new ImageIcon("src/images/highbutton.png"));
        highscoreButton.setBackground(Color.black);
        startButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                remove(jpanel);
                add(new Board());
                revalidate();
            }
        });
        highscoreButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                remove(jpanel);
                Board b = new Board();
                add(b);
                revalidate();
                b.drawHighscore();
                remove(b);
                add(jpanel);
                revalidate();
            }
        });
        jpanel.add(startButton);
        jpanel.add(highscoreButton);
        add(jpanel);
    }

    public static void main(String[] args) {
        if (args.length == 0){
            
            EventQueue.invokeLater(() -> {
                SpaceInvaders ex = new SpaceInvaders();
                ex.setVisible(true);
            });
        }
        else if (args.length == 1 && args[0].equals("server")) {
            try {
                HelloWorldClient.main(args);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        else if (args.length == 1 && args[0].equals("player")){
            HelloWorldPublisher.main(args);
        }
    }
}