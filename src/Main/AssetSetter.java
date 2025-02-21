package Main;
import Entity.Enemy;
import java.util.Random;

public class AssetSetter {
    GamePanel gp;
    Random random = new Random();
    public  AssetSetter(GamePanel gp){
        this.gp=gp;
    }

    public void setObject(){


    }
    public void setEnemies() {

        for (int i = 0; i < gp.enemyAmount; i++) { // Iterate up to enemyAmount
            Enemy enemy = new Enemy(gp);
            setEnemyPosition(enemy); // Position the enemy
            gp.enemies.add(enemy);   // Add the enemy to the ArrayList
        }
    }
    public void setEnemyPosition(Enemy enemy) {
        int worldX, worldY;
        boolean spawned = false;

        while (!spawned) {
            worldX = random.nextInt(gp.maxWorldWidth);
            worldY = random.nextInt(gp.maxWorldHeight);

            int screenX = worldX - gp.player.worldX + gp.player.screenX;
            int screenY = worldY - gp.player.worldY + gp.player.screenY;

            if (screenX < -gp.tileSize || screenX > gp.screenWidth + gp.tileSize ||
                    screenY < -gp.tileSize || screenY > gp.screenHeight + gp.tileSize) {
                enemy.worldX = worldX;
                enemy.worldY = worldY;
                spawned = true;
            }
        }
    }
}