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
    BufferedImage logoImage, startButtonNormal, startButtonSelected, exitButtonNormal, exitButtonSelected, titleBackground;
    ImageIcon titleBackgroundGifIcon; // For the GIF ImageIcon
    Image titleBackgroundGifImage;     // For the Image from the ImageIcon
    public int commandNum = 0; // 0 for Start, 1 for Exit
    double playTime;
    DecimalFormat dFormat = new DecimalFormat("#0.00");
    DecimalFormat mFormat = new DecimalFormat("00");
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

            titleBackgroundGifIcon = new ImageIcon(getClass().getResource("/GUI/background.gif")); // Load GIF as ImageIcon
            titleBackgroundGifImage = titleBackgroundGifIcon.getImage(); // Get the Image from ImageIcon
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void draw(Graphics2D g2) {
this.g2=g2;
        g2.setFont(arial_40);
        g2.setColor(Color.WHITE);

        //TITLE STATE
        if (gp.gameState == gp.titleState) {
            drawTitleScreen(g2);
        }

        //PLAY STATE
        if (gp.gameState == gp.playState) {
            drawPlayScreen();
        }
        if (gp.gameState == gp.pauseState) {
            drawPauseScreen();
        }
    }

    public void drawTitleScreen(Graphics2D g2) {
        //  Background Color
        if (titleBackgroundGifImage != null) {
            g2.drawImage(titleBackgroundGifImage, 0, 0, gp.screenWidth, gp.screenHeight, null);
        } else {
            // If GIF loading fails, draw solid color or static PNG background
            if (titleBackground != null) {
                g2.drawImage(titleBackground, 0, 0, gp.screenWidth, gp.screenHeight, null); // Draw static PNG
            } else {
                g2.setColor(new Color(40, 78, 64)); // Solid color background
                g2.fillRect(0, 0, gp.screenWidth, gp.screenHeight);
            }
        }


        // 2. Logo
        int xLogo = gp.screenWidth / 2 - logoImage.getWidth() / 2;
        int yLogo = gp.tileSize * 2;
        g2.drawImage(logoImage, xLogo, yLogo, null);

        // 3. Buttons
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
            playTime += (double) 1 / gp.fps;

            String timerText;
            if (playTime < 60) {
                timerText = "Time: " + dFormat.format(playTime);
            } else {
                int minutes = (int) (playTime / 60);
                double seconds = playTime % 60;
                timerText = "Time: " + mFormat.format(minutes) + ":" + dFormat.format(seconds);
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
}