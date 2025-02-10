package Entity;

import Main.GamePanel;
import java.awt.image.BufferedImage;
import java.awt.*;
import java.io.IOException;
import javax.imageio.ImageIO;
import java.util.Random;

public class Enemy extends Entity {
    GamePanel gp;
    Random random = new Random();

   
    BufferedImage up1, up2, up3, down1, down2, down3, left1, left2, left3, right1, right2, right3;


    public Enemy(GamePanel gp) {
        this.gp = gp;
        solidArea = new Rectangle(0, 0, 48, 48);
        speed = 2;
        direction = "down";
        getPlayerImage();
    }

    public void getPlayerImage() {
        try {
            up1 = ImageIO.read(getClass().getResourceAsStream("/Enemy/up1.png"));
            up2 = ImageIO.read(getClass().getResourceAsStream("/Enemy/up2.png"));
            up3 = ImageIO.read(getClass().getResourceAsStream("/Enemy/up3.png")); 
            down1 = ImageIO.read(getClass().getResourceAsStream("/Enemy/down1.png"));
            down2 = ImageIO.read(getClass().getResourceAsStream("/Enemy/down2.png"));
            down3 = ImageIO.read(getClass().getResourceAsStream("/Enemy/down3.png")); 
            left1 = ImageIO.read(getClass().getResourceAsStream("/Enemy/left1.png"));
            left2 = ImageIO.read(getClass().getResourceAsStream("/Enemy/left2.png"));
            left3 = ImageIO.read(getClass().getResourceAsStream("/Enemy/left3.png")); 
            right1 = ImageIO.read(getClass().getResourceAsStream("/Enemy/right1.png"));
            right2 = ImageIO.read(getClass().getResourceAsStream("/Enemy/right2.png"));
            right3 = ImageIO.read(getClass().getResourceAsStream("/Enemy/right3.png")); 
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void update() {
    
        int deltaX = gp.player.worldX - worldX;
        int deltaY = gp.player.worldY - worldY;
        double distance = Math.sqrt(deltaX * deltaX + deltaY * deltaY);

        if (distance > 0) { 
            double dirX = deltaX / distance;
            double dirY = deltaY / distance;

          
            if (Math.abs(dirX) > Math.abs(dirY)) {
                direction = dirX > 0 ? "right" : "left";
            } else {
                direction = dirY > 0 ? "down" : "up";
            }

       
            worldX += dirX * speed;
            worldY += dirY * speed;
        }

        spriteCounter++;
        if (spriteCounter > 10) { 
            spriteNum++;
            if (spriteNum > 3) {
                spriteNum = 1;
            }
            spriteCounter = 0;
        }
    }

    public void draw(Graphics2D g2) {
        BufferedImage image = null;

        switch (direction) {
            case "up":
                if (spriteNum == 1) image = up1;
                else if (spriteNum == 2) image = up2;
                else if (spriteNum == 3) image = up3;
                break;
            case "down":
                if (spriteNum == 1) image = down1;
                else if (spriteNum == 2) image = down2;
                else if (spriteNum == 3) image = down3;
                break;
            case "left":
                if (spriteNum == 1) image = left1;
                else if (spriteNum == 2) image = left2;
                else if (spriteNum == 3) image = left3;
                break;
            case "right":
                if (spriteNum == 1) image = right1;
                else if (spriteNum == 2) image = right2;
                else if (spriteNum == 3) image = right3;
                break;
        }

        int screenX = worldX - gp.player.worldX + gp.player.screenX;
        int screenY = worldY - gp.player.worldY + gp.player.screenY;

        if (image != null) {
            g2.drawImage(image, screenX, screenY, gp.tileSize, gp.tileSize, null);
        }
    }
}
