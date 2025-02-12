package Entity;

import Main.GamePanel;
import Main.KeyHandler;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class Player extends Entity {
    GamePanel gp;
    KeyHandler keyH;
    public final int screenX;
    public final int screenY;
    public int health = 100;
    public boolean hasKey = false;

    //invincible
    private boolean isInvincible = false;
    private int invincibleCounter = 0;
    private final int invincibleDuration = 60; // 1 second at 60 FPS
    private boolean visible = true; // blinking effect

    public Player(GamePanel gp, KeyHandler keyH) {

        this.gp = gp;
        this.keyH = keyH;
        screenX = gp.screenWidth / 2 - gp.tileSize / 2;
        screenY = gp.screenHeight / 2 - gp.tileSize / 2;
        solidArea = new Rectangle();
        solidArea.x = 8;
        solidArea.y = 16;
        solidAreaDefaultX = solidArea.x;
        solidAreaDefaultY = solidArea.y;
        solidArea.width = 32;
        solidArea.height = 32;
        setDefaultValues();
        getPlayerImage();
    }

    public void setDefaultValues() {
        worldX = gp.tileSize * 23;
        worldY = gp.tileSize * 21;
        speed = 4;
        direction = "down";
    }

    public void getPlayerImage() {
        try {
            left1 = ImageIO.read(getClass().getResourceAsStream("/player/Sprite-0001.png"));
            left2 = ImageIO.read(getClass().getResourceAsStream("/player/Sprite-0002.png"));
            right1 = ImageIO.read(getClass().getResourceAsStream("/player/Sprite-0003.png"));
            right2 = ImageIO.read(getClass().getResourceAsStream("/player/Sprite-0004.png"));
            down1 = ImageIO.read(getClass().getResourceAsStream("/player/Sprite-0005.png"));
            down2 = ImageIO.read(getClass().getResourceAsStream("/player/Sprite-0006.png"));
            up1 = ImageIO.read(getClass().getResourceAsStream("/player/Sprite-0007.png"));
            up2 = ImageIO.read(getClass().getResourceAsStream("/player/Sprite-0008.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void update() {
        if (keyH.upPressed || keyH.downPressed || keyH.leftPressed || keyH.rightPressed) {
            // Determine direction based on input.
            if (keyH.upPressed) {
                direction = "up";
            } else if (keyH.downPressed) {
                direction = "down";
            } else if (keyH.leftPressed) {
                direction = "left";
            } else if (keyH.rightPressed) {
                direction = "right";
            }

            // Reset collision flag before checking.
            collisionOn = false;
            gp.checker.checkTile(this);

            // Move the player if no collision was detected.
            if (!collisionOn) {
                switch (direction) {
                    case "up":
                        worldY -= speed;
                        break;
                    case "down":
                        worldY += speed;
                        break;
                    case "left":
                        worldX -= speed;
                        break;
                    case "right":
                        worldX += speed;
                        break;
                }
            }

            // Clamp the player's position so they don't go out of bounds.
            if (worldX < 0) {
                worldX = 0;
            }
            if (worldY < 0) {
                worldY = 0;
            }
            if (worldX > gp.maxWorldWidth - gp.tileSize) {
                worldX = gp.maxWorldWidth - gp.tileSize;
            }
            if (worldY > gp.maxWorldHeight - gp.tileSize) {
                worldY = gp.maxWorldHeight - gp.tileSize;
            }

            // Sprite animation
            spriteCounter++;
            if (spriteCounter > 15) {
                spriteNum = (spriteNum == 1) ? 2 : 1;
                spriteCounter = 0;
            }
        }

        // Update invincibility timer
        if (isInvincible) {
            invincibleCounter++;

            // Blink effect
            if (invincibleCounter % 5 == 0) {
                visible = !visible;
            }

            // End invincibility
            if (invincibleCounter >= invincibleDuration) {
                isInvincible = false;
                invincibleCounter = 0;
                visible = true;
            }
        }
    }


    public void draw(Graphics2D g2) {
        if(visible) { // Only draw when visible (fo
            BufferedImage image = null;
            switch(direction) {
                case "up":
                    image = (spriteNum == 1) ? up1 : up2;
                    break;
                case "down":
                    image = (spriteNum == 1) ? down1 : down2;
                    break;
                case "left":
                    image = (spriteNum == 1) ? left1 : left2;
                    break;
                case "right":
                    image = (spriteNum == 1) ? right1 : right2;
                    break;
            }
            g2.drawImage(image, screenX, screenY, gp.tileSize, gp.tileSize, null);
        }
    }
    public void takeDamage(int damage) {
        if(!isInvincible) {
            health -= damage;
            isInvincible = true; // Start invincibility
            invincibleCounter = 0;
            visible = true;
            gp.playSE(2);

            if(health <= 0) {
                health = 0;
                gp.ui.gameOver = true;
                System.out.println("Game Over!");
            }
        }
    }
    }
