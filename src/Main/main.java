package Main;

import javax.swing.*;

public class main {
    public static void main(String[] args) {
        JFrame window = new JFrame("RG1");
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setResizable(false);
        window.setSize(800, 600);

        GamePanel gamePanel = new GamePanel();
        window.add(gamePanel);
        window.pack();

        window.setLocationRelativeTo(null);
        window.setVisible(true);

        gamePanel.setupGame();
        gamePanel.startGameThread();

        gamePanel.requestFocusInWindow(); // *** Request focus for the game panel ***
    }
}