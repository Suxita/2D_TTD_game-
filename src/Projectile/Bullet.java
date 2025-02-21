package Projectile;

import Entity.Entity;
import Main.GamePanel;
import java.awt.*;

public class Bullet extends Entity {
    GamePanel gp;
    String direction;
    public boolean alive;

    public Bullet(GamePanel gp, int startX, int startY, String direction) {
        this.gp = gp;
        this.worldX = startX;
        this.worldY = startY;
        this.direction = direction;
        this.speed = 8;
        this.alive = true;
        solidArea = new Rectangle(0, 0, 8, 8); // bullets small hitBox
    }

    public void update() {
        switch (direction) {
            case "up": worldY -= speed; break;
            case "down": worldY += speed; break;
            case "left": worldX -= speed; break;
            case "right": worldX += speed; break;
        }
        gp.checker.checkBulletEnemyCollision(this);

        if (worldX < 0 || worldX > gp.maxWorldWidth ||
                worldY < 0 || worldY > gp.maxWorldHeight) {
            alive = false;
        }
    }

    public void draw(Graphics2D g2) {
        int screenX = worldX - gp.player.worldX + gp.player.screenX;
        int screenY = worldY - gp.player.worldY + gp.player.screenY;


        g2.setColor(Color.WHITE);
        g2.fillOval(screenX, screenY, 8, 8);
    }
}