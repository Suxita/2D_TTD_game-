package Main;

import Entity.Entity;
import Entity.Enemy;
import Entity.Player;
import java.awt.*;
import java.util.Iterator;

import Projectile.Bullet;
public class collisionChecker {

    GamePanel gp;

    public collisionChecker(GamePanel gp) {
        this.gp = gp;
    }

    public void checkTile(Entity entity) {
        entity.collisionOn = false;

        int entityLeftWorldX = entity.worldX + entity.solidArea.x;
        int entityRightWorldX = entity.worldX + entity.solidArea.x + entity.solidArea.width;
        int entityTopWorldY = entity.worldY + entity.solidArea.y;
        int entityBottomWorldY = entity.worldY + entity.solidArea.y + entity.solidArea.height;

        int entityLeftCol = entityLeftWorldX / gp.tileSize;
        int entityRightCol = entityRightWorldX / gp.tileSize;
        int entityTopRow = entityTopWorldY / gp.tileSize;
        int entityBottomRow = entityBottomWorldY / gp.tileSize;

        int tileNum1 = 0, tileNum2 = 0;

        switch (entity.direction) {
            case "up":
                int nextTopRow = (entityTopWorldY - entity.speed) / gp.tileSize;
                if (nextTopRow < 0) {
                    entity.collisionOn = true;
                    return;
                }

                tileNum1 = gp.tileM.mapTileNumber[Math.min(entityLeftCol, gp.maxWorldCol - 1)][Math.max(0, nextTopRow)];
                tileNum2 = gp.tileM.mapTileNumber[Math.min(entityRightCol, gp.maxWorldCol - 1)][Math.max(0, nextTopRow)];
                break;
            case "down":
                int nextBottomRow = (entityBottomWorldY + entity.speed) / gp.tileSize;
                if (nextBottomRow >= gp.maxWorldRow) {
                    entity.collisionOn = true;
                    return;
                }

                tileNum1 = gp.tileM.mapTileNumber[Math.min(entityLeftCol, gp.maxWorldCol - 1)][Math.min(nextBottomRow, gp.maxWorldRow - 1)];
                tileNum2 = gp.tileM.mapTileNumber[Math.min(entityRightCol, gp.maxWorldCol - 1)][Math.min(nextBottomRow, gp.maxWorldRow - 1)];
                break;
            case "left":
                int nextLeftCol = (entityLeftWorldX - entity.speed) / gp.tileSize;
                if (nextLeftCol < 0) {
                    entity.collisionOn = true;
                    return;
                }

                tileNum1 = gp.tileM.mapTileNumber[Math.max(0, nextLeftCol)][Math.min(entityTopRow, gp.maxWorldRow - 1)];
                tileNum2 = gp.tileM.mapTileNumber[Math.max(0, nextLeftCol)][Math.min(entityBottomRow, gp.maxWorldRow - 1)];
                break;
            case "right":
                int nextRightCol = (entityRightWorldX + entity.speed) / gp.tileSize;
                if (nextRightCol >= gp.maxWorldCol) {
                    entity.collisionOn = true;
                    return;
                }

                tileNum1 = gp.tileM.mapTileNumber[Math.min(nextRightCol, gp.maxWorldCol - 1)][Math.min(entityTopRow, gp.maxWorldRow - 1)];
                tileNum2 = gp.tileM.mapTileNumber[Math.min(nextRightCol, gp.maxWorldCol - 1)][Math.min(entityBottomRow, gp.maxWorldRow - 1)];
                break;
            default:
                return;
        }

        if (gp.tileM.tile[tileNum1].collision || gp.tileM.tile[tileNum2].collision) {
            entity.collisionOn = true;
        }
    }

    public void checkEnemyWithTile(Enemy enemy) {
        enemy.collisionOn = false;

        int enemyLeftWorldX = enemy.worldX + enemy.solidArea.x;
        int enemyRightWorldX = enemy.worldX + enemy.solidArea.x + enemy.solidArea.width;
        int enemyTopWorldY = enemy.worldY + enemy.solidArea.y;
        int enemyBottomWorldY = enemy.worldY + enemy.solidArea.y + enemy.solidArea.height;

        int enemyLeftCol = enemyLeftWorldX / gp.tileSize;
        int enemyRightCol = enemyRightWorldX / gp.tileSize;
        int enemyTopRow = enemyTopWorldY / gp.tileSize;
        int enemyBottomRow = enemyBottomWorldY / gp.tileSize;

        int tileNum1 = 0, tileNum2 = 0;


        switch (enemy.direction) {
            case "up":
                int nextTopRow = (enemyTopWorldY - enemy.speed) / gp.tileSize;
                if (nextTopRow < 0) {
                    enemy.collisionOn = true;
                    return;
                }

                tileNum1 = gp.tileM.mapTileNumber[Math.min(enemyLeftCol, gp.maxWorldCol - 1)][Math.max(0, nextTopRow)];
                tileNum2 = gp.tileM.mapTileNumber[Math.min(enemyRightCol, gp.maxWorldCol - 1)][Math.max(0, nextTopRow)];
                break;
            case "down":
                int nextBottomRow = (enemyBottomWorldY + enemy.speed) / gp.tileSize;
                if (nextBottomRow >= gp.maxWorldRow) {
                    enemy.collisionOn = true;
                    return;
                }

                tileNum1 = gp.tileM.mapTileNumber[Math.min(enemyLeftCol, gp.maxWorldCol - 1)][Math.min(nextBottomRow, gp.maxWorldRow - 1)];
                tileNum2 = gp.tileM.mapTileNumber[Math.min(enemyRightCol, gp.maxWorldCol - 1)][Math.min(nextBottomRow, gp.maxWorldRow - 1)];
                break;
            case "left":
                int nextLeftCol = (enemyLeftWorldX - enemy.speed) / gp.tileSize;
                if (nextLeftCol < 0) {
                    enemy.collisionOn = true;
                    return;
                }

                tileNum1 = gp.tileM.mapTileNumber[Math.max(0, nextLeftCol)][Math.min(enemyTopRow, gp.maxWorldRow - 1)];
                tileNum2 = gp.tileM.mapTileNumber[Math.max(0, nextLeftCol)][Math.min(enemyBottomRow, gp.maxWorldRow - 1)];
                break;
            case "right":
                int nextRightCol = (enemyRightWorldX + enemy.speed) / gp.tileSize;
                if (nextRightCol >= gp.maxWorldCol) {
                    enemy.collisionOn = true;
                    return;
                }

                tileNum1 = gp.tileM.mapTileNumber[Math.min(nextRightCol, gp.maxWorldCol - 1)][Math.min(enemyTopRow, gp.maxWorldRow - 1)];
                tileNum2 = gp.tileM.mapTileNumber[Math.min(nextRightCol, gp.maxWorldCol - 1)][Math.min(enemyBottomRow, gp.maxWorldRow - 1)];
                break;
            default:
                return;
        }

        if (gp.tileM.tile[tileNum1].collision || gp.tileM.tile[tileNum2].collision) {
            enemy.collisionOn = true;
        }
    }

    public boolean checkPlayerWithEnemy(Player player, Enemy enemy) {
        Rectangle playerRect = new Rectangle(player.worldX + player.solidArea.x, player.worldY + player.solidArea.y, player.solidArea.width, player.solidArea.height);
        Rectangle enemyRect = new Rectangle(enemy.worldX + enemy.solidArea.x, enemy.worldY + enemy.solidArea.y, enemy.solidArea.width, enemy.solidArea.height);

        if (playerRect.intersects(enemyRect)) {
            player.takeDamage(10);
            return true;
        }
        return false;
    }


    public void checkBulletEnemyCollision(Bullet bullet) {
        Rectangle bulletRect = new Rectangle(bullet.worldX + bullet.solidArea.x, bullet.worldY + bullet.solidArea.y, bullet.solidArea.width, bullet.solidArea.height);
        Iterator<Enemy> iterator = gp.enemies.iterator();
        while (iterator.hasNext()) { // While there are more enemies to check
            Enemy enemy = iterator.next(); // Get the next enemy

            if (enemy != null) {
                Rectangle enemyRect = new Rectangle(enemy.worldX + enemy.solidArea.x, enemy.worldY + enemy.solidArea.y, enemy.solidArea.width, enemy.solidArea.height);

                if (bulletRect.intersects(enemyRect)) {
                    bullet.alive = false;
                    iterator.remove(); // remove the current enemy from the ArrayList using the Iterator
                    gp.livingEnemiesCount--;
                    gp.playSE(5);
                    break;
                }
            }
        }
    }

}