package Main;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class KeyHandler implements KeyListener {
    GamePanel gp;
    public boolean upPressed, downPressed, leftPressed, rightPressed;

    public KeyHandler(GamePanel gp) {
        this.gp = gp;
    }

    @Override
    public void keyTyped(KeyEvent e) {}

    @Override
    public void keyPressed(KeyEvent e) {
        int code = e.getKeyCode();

        // Handle pause toggle for play and pause states
        if (code == KeyEvent.VK_P) {
            handlePauseToggle();
            return;
        }

        // State-specific handling
        switch (gp.gameState) {
            case GamePanel.titleState:
                handleTitleInput(code);
                break;
            case GamePanel.playState:
                handlePlayInput(code);
                break;
            case GamePanel.pauseState:
                handlePauseInput(code);
                break;
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        int code = e.getKeyCode();
        if (code == KeyEvent.VK_W) upPressed = false;
        if (code == KeyEvent.VK_S) downPressed = false;
        if (code == KeyEvent.VK_A) leftPressed = false;
        if (code == KeyEvent.VK_D) rightPressed = false;
    }

    private void handleTitleInput(int code) {
        // Menu navigation
        if (code == KeyEvent.VK_W || code == KeyEvent.VK_UP) {
            gp.ui.commandNum--;
            if (gp.ui.commandNum < 0) gp.ui.commandNum = 1;
            gp.playSE(3);

        }
        if (code == KeyEvent.VK_S || code == KeyEvent.VK_DOWN) {
            gp.ui.commandNum++;
            if (gp.ui.commandNum > 1) gp.ui.commandNum = 0;
            gp.playSE(3);

        }

        // Menu selection
        if (code == KeyEvent.VK_ENTER) {
            if (gp.ui.commandNum == 0) { // Start Game
                gp.gameState = GamePanel.playState;
                gp.playMusic(0);
                gp.requestFocus();
            } else if (gp.ui.commandNum == 1) { // Exit Game
                System.exit(0);
            }
        }
    }

    private void handlePlayInput(int code) {
        if (code == KeyEvent.VK_W) upPressed = true;
        if (code == KeyEvent.VK_S) downPressed = true;
        if (code == KeyEvent.VK_A) leftPressed = true;
        if (code == KeyEvent.VK_D) rightPressed = true;
    }

    private void handlePauseInput(int code) {

    }

    private void handlePauseToggle() {
        if (gp.gameState == GamePanel.playState){
            gp.gameState = GamePanel.pauseState;
            gp.stopMusic();
        } else if (gp.gameState == GamePanel.pauseState) {
            gp.gameState = GamePanel.playState;
            gp.playMusic(0);
        }
    }
}