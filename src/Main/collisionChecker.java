package Main;

import Entity.Entity;
import Entity.Enemy;
import Entity.Player;
import java.awt.*;

public class collisionChecker {

    GamePanel gp;

    public collisionChecker(GamePanel gp) {
        this.gp = gp;
    }

    public void checkTile(Entity entity) {
        // ... (Your existing checkTile() method code - no changes needed)
    }

    public void checkPlayerWithEnemy(Player player, Enemy enemy) {
        Rectangle playerRect = new Rectangle(player.worldX + player.solidArea.x, player.worldY + player.solidArea.y, player.solidArea.width, player.solidArea.height);
        Rectangle enemyRect = new Rectangle(enemy.worldX + enemy.solidArea.x, enemy.worldY + enemy.solidArea.y, enemy.solidArea.width, enemy.solidArea.height);

        if (playerRect.intersects(enemyRect)) {
            player.health -= 10;
            System.out.println("Player health: " + player.health);

            if (player.health <= 0) {
                System.out.println("Game Over!");
                gp.stopMusic();
                gp.playSE(2);
                // Implement your game over logic here (e.g., stop game loop)
            }
        }
    }


    public void checkEnemyWithTile(Enemy enemy) {
        int enemyLeftWorldX = enemy.worldX + enemy.solidArea.x;
        int enemyRightWorldX = enemy.worldX + enemy.solidArea.x + enemy.solidArea.width;
        int enemyTopWorldY = enemy.worldY + enemy.solidArea.y;
        int enemyBottomWorldY = enemy.worldY + enemy.solidArea.y + enemy.solidArea.height;

        int enemyLeftCol = enemyLeftWorldX / gp.tileSize;
        int enemyRightCol = enemyRightWorldX / gp.tileSize;
        int enemyTopRow = enemyTopWorldY / gp.tileSize;
        int enemyBottomRow = enemyBottomWorldY / gp.tileSize;

        int tileNum1, tileNum2;

        switch (enemy.direction) {
            case "up":
                int nextTopRow = (enemyTopWorldY - enemy.speed) / gp.tileSize;
                if (nextTopRow < 0) {
                    enemy.collisionOn = true;
                    return;
                }
                tileNum1 = gp.tileM.mapTileNumber[enemyLeftCol][nextTopRow];
                tileNum2 = gp.tileM.mapTileNumber[enemyRightCol][nextTopRow];
                break;
            case "down":
                int nextBottomRow = (enemyBottomWorldY + enemy.speed) / gp.tileSize;
                if (nextBottomRow >= gp.maxWorldRow) {
                    enemy.collisionOn = true;
                    return;
                }
                tileNum1 = gp.tileM.mapTileNumber[enemyLeftCol][nextBottomRow];
                tileNum2 = gp.tileM.mapTileNumber[enemyRightCol][nextBottomRow];
                break;
            case "left":
                int nextLeftCol = (enemyLeftWorldX - enemy.speed) / gp.tileSize;
                if (nextLeftCol < 0) {
                    enemy.collisionOn = true;
                    return;
                }
                tileNum1 = gp.tileM.mapTileNumber[nextLeftCol][enemyTopRow];
                tileNum2 = gp.tileM.mapTileNumber[nextLeftCol][enemyBottomRow];
                break;
            case "right":
                int nextRightCol = (enemyRightWorldX + enemy.speed) / gp.tileSize;
                if (nextRightCol >= gp.maxWorldCol) {
                    enemy.collisionOn = true;
                    return;
                }
                tileNum1 = gp.tileM.mapTileNumber[nextRightCol][enemyTopRow];
                tileNum2 = gp.tileM.mapTileNumber[nextRightCol][enemyBottomRow];
                break;
            default:
                return; // or handle the default case as needed
        }

        if (gp.tileM.tile[tileNum1].collision || gp.tileM.tile[tileNum2].collision) {
            enemy.collisionOn = true;
        } else {
            enemy.collisionOn = false;
        }
    }
}