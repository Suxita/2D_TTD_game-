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
        int entityLeftWorldX = entity.worldX + entity.solidArea.x;
        int entityRightWorldX = entity.worldX + entity.solidArea.x + entity.solidArea.width;
        int entityTopWorldY = entity.worldY + entity.solidArea.y;
        int entityBottomWorldY = entity.worldY + entity.solidArea.y + entity.solidArea.height;

        int entityLeftCol = entityLeftWorldX / gp.tileSize;
        int entityRightCol = entityRightWorldX / gp.tileSize;
        int entityTopRow = entityTopWorldY / gp.tileSize;
        int entityBottomRow = entityBottomWorldY / gp.tileSize;

        int tileNum1, tileNum2;

        switch (entity.direction) {
            case "up":
                int nextTopRow = (entityTopWorldY - entity.speed) / gp.tileSize;
                tileNum1 = gp.tileM.mapTileNumber[entityLeftCol][nextTopRow];
                tileNum2 = gp.tileM.mapTileNumber[entityRightCol][nextTopRow];
                if (gp.tileM.tile[tileNum1].collision || gp.tileM.tile[tileNum2].collision) {
                    entity.collisionOn = true;
                }
                break;
            case "down":
                int nextBottomRow = (entityBottomWorldY + entity.speed) / gp.tileSize;
                tileNum1 = gp.tileM.mapTileNumber[entityLeftCol][nextBottomRow];
                tileNum2 = gp.tileM.mapTileNumber[entityRightCol][nextBottomRow];
                if (gp.tileM.tile[tileNum1].collision || gp.tileM.tile[tileNum2].collision) {
                    entity.collisionOn = true;
                }
                break;
            case "left":
                int nextLeftCol = (entityLeftWorldX - entity.speed) / gp.tileSize;
                tileNum1 = gp.tileM.mapTileNumber[nextLeftCol][entityTopRow];
                tileNum2 = gp.tileM.mapTileNumber[nextLeftCol][entityBottomRow];
                if (gp.tileM.tile[tileNum1].collision || gp.tileM.tile[tileNum2].collision) {
                    entity.collisionOn = true;
                }
                break;
            case "right":
                int nextRightCol = (entityRightWorldX + entity.speed) / gp.tileSize;
                tileNum1 = gp.tileM.mapTileNumber[nextRightCol][entityTopRow];
                tileNum2 = gp.tileM.mapTileNumber[nextRightCol][entityBottomRow];
                if (gp.tileM.tile[tileNum1].collision || gp.tileM.tile[tileNum2].collision) {
                    entity.collisionOn = true;
                }
                break;
            case "upleft":  // Handle diagonal cases
                nextTopRow = (entityTopWorldY - entity.speed) / gp.tileSize;
                nextLeftCol = (entityLeftWorldX - entity.speed) / gp.tileSize;
                tileNum1 = gp.tileM.mapTileNumber[nextLeftCol][nextTopRow];
                tileNum2 = gp.tileM.mapTileNumber[entityRightCol][nextTopRow]; // Check right as well
                int tileNum3 = gp.tileM.mapTileNumber[nextLeftCol][entityBottomRow]; // Check bottom as well

                if (gp.tileM.tile[tileNum1].collision || gp.tileM.tile[tileNum2].collision || gp.tileM.tile[tileNum3].collision) {
                    entity.collisionOn = true;
                }
                break;
            case "upright":
                nextTopRow = (entityTopWorldY - entity.speed) / gp.tileSize;
                nextRightCol = (entityRightWorldX + entity.speed) / gp.tileSize;
                tileNum1 = gp.tileM.mapTileNumber[entityLeftCol][nextTopRow];
                tileNum2 = gp.tileM.mapTileNumber[nextRightCol][nextTopRow];
                int tileNum4 = gp.tileM.mapTileNumber[nextRightCol][entityBottomRow];

                if (gp.tileM.tile[tileNum1].collision || gp.tileM.tile[tileNum2].collision || tileNum4.collision) {
                    entity.collisionOn = true;
                }
                break;
            case "downleft":
                nextBottomRow = (entityBottomWorldY + entity.speed) / gp.tileSize;
                nextLeftCol = (entityLeftWorldX - entity.speed) / gp.tileSize;
                tileNum1 = gp.tileM.mapTileNumber[nextLeftCol][nextBottomRow];
                tileNum2 = gp.tileM.mapTileNumber[entityRightCol][nextBottomRow];
                int tileNum5 = gp.tileM.mapTileNumber[nextLeftCol][entityTopRow];
                if (gp.tileM.tile[tileNum1].collision || gp.tileM.tile[tileNum2].collision || tileNum5.collision) {
                    entity.collisionOn = true;
                }
                break;
            case "downright":
                nextBottomRow = (entityBottomWorldY + entity.speed) / gp.tileSize;
                nextRightCol = (entityRightWorldX + entity.speed) / gp.tileSize;
                tileNum1 = gp.tileM.mapTileNumber[entityLeftCol][nextBottomRow];
                tileNum2 = gp.tileM.mapTileNumber[nextRightCol][nextBottomRow];
                int tileNum6 = gp.tileM.mapTileNumber[nextRightCol][entityTopRow];

                if (gp.tileM.tile[tileNum1].collision || gp.tileM.tile[tileNum2].collision || tileNum6.collision) {
                    entity.collisionOn = true;
                }
                break;
        }
    }

     
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
                return; 
        }

        if (gp.tileM.tile[tileNum1].collision || gp.tileM.tile[tileNum2].collision) {
            enemy.collisionOn = true;
        } else {
            enemy.collisionOn = false;
        }
    }
}
