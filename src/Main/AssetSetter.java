package Main;
import Entity.Enemy;
import java.util.Random;

import object.OBJ_key;

public class AssetSetter {
    GamePanel gp;
    Random random = new Random();
    public  AssetSetter(GamePanel gp){
        this.gp=gp;
    }

    public void setObject(){
        gp.obj[0]= new OBJ_key(gp);
        gp.obj[0].worldX=23 *gp.tileSize;
        gp.obj[0].worldY=7*gp.tileSize;

    }
    public void setEnemies() {
     for (int i = 0; i < gp.enemies.length; i++)

    {
        gp.enemies[i] = new Enemy(gp);

        int worldX, worldY;
        boolean spawned = false;

        while (!spawned) {
            worldX = random.nextInt(gp.maxWorldWidth);
            worldY = random.nextInt(gp.maxWorldHeight);

            int screenX = worldX - gp.player.worldX + gp.player.screenX;
            int screenY = worldY - gp.player.worldY + gp.player.screenY;

            if (screenX < -gp.tileSize || screenX > gp.screenWidth + gp.tileSize ||
                    screenY < -gp.tileSize || screenY > gp.screenHeight + gp.tileSize) {
                gp.enemies[i].worldX = worldX;
                gp.enemies[i].worldY = worldY;
                gp.enemies[i].speed = random.nextInt(3) + 1;
                spawned = true;
            }
        }
        }
    }
}