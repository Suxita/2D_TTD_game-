package Main;

import javax.swing.*;

public class main {

    public static JFrame window;
    public static void main(String[] args) {
        window = new JFrame("Comrad Pixel");
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setResizable(false);
        window.setSize(800, 600);
        window.setUndecorated(true);
        GamePanel gamePanel = new GamePanel();
        window.add(gamePanel);
        window.pack();

        window.setLocationRelativeTo(null);
        window.setVisible(true);

        gamePanel.setupGame();
        gamePanel.setFullScreen();
        gamePanel.startGameThread();

        gamePanel.requestFocusInWindow();
    }
}