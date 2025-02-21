package Main;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class KeyHandler implements KeyListener {
    GamePanel gp;
    public boolean upPressed, downPressed, leftPressed, rightPressed, ePressed;

    public KeyHandler(GamePanel gp) {
        this.gp = gp;
    }

    @Override
    public void keyTyped(KeyEvent e) {}

    @Override
    public void keyPressed(KeyEvent e) {
        int code = e.getKeyCode();

        if (code == KeyEvent.VK_P) {
            handlePauseToggle();
            return;
        }

        // State-specific handling
        switch (gp.gameState) {
            case GameState.TITLE_STATE:
                handleTitleInput(code);
                break;
            case GameState.PLAY_STATE:
                handlePlayInput(code);
                break;
            case GameState.PAUSE_STATE:
                handlePauseInput(code);
                break;
            case GameState.VICTORY_STATE:
                handleVictoryInput(code);
                break;
            case GameState.GAME_OVER_STATE:
            handleGameOverInput(code);
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
        if(code == KeyEvent.VK_E) {ePressed = false;}
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
                gp.gameState= GameState.PLAY_STATE;
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
        if(code == KeyEvent.VK_E) {ePressed = true;}
    }

    private void handlePauseInput(int code) {
//just unpause and continue😭
    }

    private void handlePauseToggle() {
        if ( gp.gameState== GameState.PLAY_STATE){
         gp.gameState=GameState.PAUSE_STATE;
            gp.stopMusic();
        } else if (  gp.gameState==GameState.PAUSE_STATE) {
            gp.gameState= GameState.PLAY_STATE;
            gp.playMusic(0);
        }
    }
    private void handleVictoryInput(int code) {
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
            if (gp.ui.commandNum == 0) { // Play Again button selected
                gp.gameState = GameState.PLAY_STATE;

                gp.resetGame();
                gp.requestFocus();             // Request focus back to the game panel
            } else if (gp.ui.commandNum == 1) {
                System.exit(0);              // Exit the game
            }
        }
    }
    private void handleGameOverInput(int code) {
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

        if (code == KeyEvent.VK_ENTER) {
            if (gp.ui.commandNum == 0) {
                gp.resetGame();
                gp.gameState = GameState.PLAY_STATE; // Switch to play state
                gp.ui.gameOver = false;     // Ensure gameOver flag in UI is reset
                gp.requestFocus();
            } else if (gp.ui.commandNum == 1) {
                System.exit(0);             // Exit game
            }
        }
    }
}