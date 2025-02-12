package object;

import Main.GamePanel;
import javax.imageio.ImageIO;
import java.awt.*;
import java.io.IOException;

public class OBJ_key extends SuperObject {  // Extends SuperObject
    GamePanel gp;

    public OBJ_key(GamePanel gp) {
        this.gp = gp;
        name = "key";
        try {
            image = ImageIO.read(getClass().getResourceAsStream("/objects/key1.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void pickup() {
        gp.player.hasKey = true;
        gp.playSE(1);
        gp.obj[0] = null;
    }
}