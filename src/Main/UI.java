package Main;

import java.awt.*;
import java.text.DecimalFormat;

public class UI {
    GamePanel gp;
    Font arial_40;
    double playTime;
    DecimalFormat dFormat = new DecimalFormat("#0.00");
    DecimalFormat mFormat = new DecimalFormat("00");
    public boolean gameOver = false; 

    public UI(GamePanel gp) {
        this.gp = gp;
        arial_40 = new Font("Arial", Font.PLAIN, 40);
    }

    public void draw(Graphics2D g2) {
        g2.setFont(arial_40);
        g2.setColor(new Color(255, 51, 51));

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
}
