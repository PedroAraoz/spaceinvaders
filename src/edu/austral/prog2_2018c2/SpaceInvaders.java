package edu.austral.prog2_2018c2;

import edu.austral.prog2_2018c2.rpc.HelloWorldClient;
import edu.austral.prog2_2018c2.rpc.HelloWorldPublisher;

import java.awt.EventQueue;
import javax.swing.JFrame;

public class SpaceInvaders extends JFrame implements Commons {
    public SpaceInvaders() {
        initUI();
    }

    private void initUI() {
        add(new Board());
        setTitle("Space Invaders");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(BOARD_WIDTH, BOARD_HEIGHT);
        setLocationRelativeTo(null);
        setResizable(false);
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