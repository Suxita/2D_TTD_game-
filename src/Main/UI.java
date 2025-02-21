package Main;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.text.DecimalFormat;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;


public class UI {
    GamePanel gp;
    Graphics2D g2;
    Font arial_40, arial_80B;
    BufferedImage logoImage, startButtonNormal, startButtonSelected, exitButtonNormal, exitButtonSelected, titleBackground, reloadButtonNormal,reloadButtonSelected;
    ImageIcon titleBackgroundGifIcon; // For the GIF ImageIcon
    Image titleBackgroundGifImage;     // For the Image from the ImageIcon
    public int commandNum = 0; // 0 for Start, 1 for Exit
    public boolean gameOver = false;




    public UI(GamePanel gp) {
        this.gp = gp;
        arial_40 = new Font("Arial", Font.PLAIN, 40);
        arial_80B = new Font("Arial", Font.BOLD, 80);
        loadButtonImages();
    }

    public void loadButtonImages() {
        try {
            logoImage = ImageIO.read(getClass().getResourceAsStream("/GUI/logo.png"));
            startButtonNormal = ImageIO.read(getClass().getResourceAsStream("/GUI/start1.png"));
            startButtonSelected = ImageIO.read(getClass().getResourceAsStream("/GUI/start2.png"));
            exitButtonNormal = ImageIO.read(getClass().getResourceAsStream("/GUI/exit1.png"));
            exitButtonSelected = ImageIO.read(getClass().getResourceAsStream("/GUI/exit2.png"));
            reloadButtonNormal=ImageIO.read(getClass().getResourceAsStream("/GUI/reload1.png"));
            reloadButtonSelected=ImageIO.read(getClass().getResourceAsStream("/GUI/reload2.png"));
            titleBackgroundGifIcon = new ImageIcon(getClass().getResource("/GUI/background.gif"));
            titleBackgroundGifImage = titleBackgroundGifIcon.getImage();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void draw(Graphics2D g2) {
        this.g2 = g2;
        g2.setFont(arial_40);
        g2.setColor(Color.WHITE);

        if (gp.gameState == GameState.PLAY_STATE) {
            drawPlayScreen();
        }
        if (gp.gameState == GameState.PAUSE_STATE) {
            drawPauseScreen();
        }
        if (gp.gameState==GameState.GAME_OVER_STATE){
            drawGameOverScreen(g2);
        }
        if (gp.gameState==GameState.VICTORY_STATE){
            drawVictoryScreen(g2);
        }
    }


    public void drawTitleScreen(Graphics2D g2) {
        //  Background Color
        if (titleBackgroundGifImage != null) {
            g2.drawImage(titleBackgroundGifImage, 0, 0, gp.screenWidth, gp.screenHeight, null);
        } else {

            if (titleBackground != null) {
                g2.drawImage(titleBackground, 0, 0, gp.screenWidth, gp.screenHeight, null);
            } else {
                g2.setColor(new Color(40, 78, 64)); // Solid color background
                g2.fillRect(0, 0, gp.screenWidth, gp.screenHeight);
            }
        }

        //  Logo
        int xLogo = gp.screenWidth / 2 - logoImage.getWidth() / 2;
        int yLogo = gp.tileSize * 2;
        g2.drawImage(logoImage, xLogo, yLogo, null);

        //  Buttons
        int buttonYStart = gp.screenHeight / 2 + gp.tileSize; // Start buttons below logo

        // "Start Game"
        BufferedImage startButtonImage = (commandNum == 0) ? startButtonSelected : startButtonNormal;
        int xStartButton = gp.screenWidth / 2 - startButtonNormal.getWidth() / 2;
        int yStartButton = buttonYStart;
        g2.drawImage(startButtonImage, xStartButton, yStartButton, null);

        // "Exit Game"
        BufferedImage exitButtonImage = (commandNum == 1) ? exitButtonSelected : exitButtonNormal;
        int xExitButton = gp.screenWidth / 2 - exitButtonNormal.getWidth() / 2;
        int yExitButton = yStartButton + startButtonNormal.getHeight() + 20; // Position below "Start" with spacing
        g2.drawImage(exitButtonImage, xExitButton, yExitButton, null);


    }

    public void drawPlayScreen() {
        if (!gameOver) {
            gp.playTime += (double) 1 / gp.fps;

            String timerText;
            if (gp.playTime < 60) {
                timerText = "Time: " + gp.dFormat.format(gp.playTime);
            } else {
                int minutes = (int) (gp.playTime / 60);
                double seconds = gp.playTime % 60;
                timerText = "Time: " + gp.mFormat.format(minutes) + ":" + gp.dFormat.format(seconds);
            }

            FontMetrics metrics = g2.getFontMetrics(arial_40);
            int x = gp.screenWidth - metrics.stringWidth(timerText) - 10;
            int y = 50;

            g2.drawString(timerText, x, y);
        }
        if (gameOver) {
            String gameOverText = "Game Over";
            FontMetrics metrics = g2.getFontMetrics(arial_40);
            int x = gp.screenWidth / 2 - metrics.stringWidth(gameOverText) / 2;
            int y = gp.screenHeight / 2;

            g2.setColor(Color.RED);
            g2.drawString(gameOverText, x, y);
        }
    }

    public void drawPauseScreen() {
        g2.setFont(arial_80B);
        String text = "Paused";
        int x = getXForCenteredText(text, g2);
        int y = gp.screenHeight / 2;

        g2.drawString(text, x, y);
    }

    public int getXForCenteredText(String text, Graphics2D g2) {
        int length = (int) g2.getFontMetrics().getStringBounds(text, g2).getWidth();
        int x = gp.screenWidth / 2 - length / 2;
        return x;
    }

    public void drawVictoryScreen(Graphics2D g2) {
        // Black Background
        g2.setColor(Color.BLACK);
        g2.fillRect(0, 0, gp.screenWidth, gp.screenHeight);

        // "YOU WON!" Text
        g2.setFont(arial_80B);
        g2.setColor(Color.YELLOW);
        String text = "YOU WON!";
        int xText = getXForCenteredText(text, g2);
        int yText = gp.screenHeight / 2 - gp.tileSize * 2; // Position above buttons
        g2.drawString(text, xText, yText);

        // Buttons
        int buttonYStart = gp.screenHeight / 2 + gp.tileSize; // Start buttons below text

        // "Play Again" Button
        BufferedImage reloadButton = (commandNum == 0) ? reloadButtonSelected : reloadButtonNormal;
        int xPlayAgainButton = gp.screenWidth / 2 - reloadButtonSelected.getWidth() / 2;
        int yPlayAgainButton = buttonYStart;
        g2.drawImage(reloadButton, xPlayAgainButton, yPlayAgainButton, null);

        // "Quit" Button
        BufferedImage quitButtonImage = (commandNum == 1) ? exitButtonSelected : exitButtonNormal;
        int xQuitButton = gp.screenWidth / 2 - exitButtonNormal.getWidth() / 2;
        int yQuitButton = yPlayAgainButton + reloadButtonSelected.getHeight() + 20;
        g2.drawImage(quitButtonImage, xQuitButton, yQuitButton, null);

        //border
        g2.setColor(new Color(197, 47, 63));
        g2.setStroke(new BasicStroke(1));
        g2.drawRect(0, 0, gp.screenWidth -1 , gp.screenHeight -1);
        g2.setStroke(new BasicStroke(1));
    }
    public void drawGameOverScreen(Graphics2D g2) {
        // Black Background
        g2.setColor(Color.BLACK);
        g2.fillRect(0, 0, gp.screenWidth, gp.screenHeight);

        // "GAME OVER" Text
        g2.setFont(arial_80B);
        g2.setColor(new Color(197, 47, 63)); // Reddish color for "GAME OVER" text
        String text = "GAME OVER";
        int xText = getXForCenteredText(text, g2);
        int yText = gp.screenHeight / 2 - gp.tileSize * 2; // Position above buttons
        g2.drawString(text, xText, yText);

        // Buttons - Reusing "Play Again" (reload) and "Quit" buttons from Victory Screen
        int buttonYStart = gp.screenHeight / 2 + gp.tileSize; // Start buttons below text

        // "Play Again" Button (Reload Button)
        BufferedImage reloadButtonImage = (commandNum == 0) ? reloadButtonSelected : reloadButtonNormal;
        int xPlayAgainButton = gp.screenWidth / 2 - reloadButtonSelected.getWidth() / 2;
        int yPlayAgainButton = buttonYStart;
        g2.drawImage(reloadButtonImage, xPlayAgainButton, yPlayAgainButton, null);

        // "Quit" Button (Exit Button)
        BufferedImage quitButtonImage = (commandNum == 1) ? exitButtonSelected : exitButtonNormal;
        int xQuitButton = gp.screenWidth / 2 - exitButtonNormal.getWidth() / 2;
        int yQuitButton = yPlayAgainButton + reloadButtonSelected.getHeight() + 20;
        g2.drawImage(quitButtonImage, xQuitButton, yQuitButton, null);

        // Screen Border (same style as victory screen)
        g2.setColor(new Color(197, 47, 63)); // Reddish color for border - matches text color
        g2.setStroke(new BasicStroke(1));      // Thin border line
        g2.drawRect(0, 0, gp.screenWidth - 1, gp.screenHeight - 1);
        g2.setStroke(new BasicStroke(1));      // Reset stroke
    }
};


