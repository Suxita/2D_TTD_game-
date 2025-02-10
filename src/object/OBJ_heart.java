package object;

import Main.GamePanel;

import javax.imageio.ImageIO;
import java.io.IOException;
import java.awt.image.BufferedImage; // Import BufferedImage

public class OBJ_heart extends SuperObject {
    GamePanel gp;
    public BufferedImage[] images = new BufferedImage[3]; // Array to hold heart images

    public OBJ_heart(GamePanel gp) {
        this.gp = gp;
        name = "heart";
        try {
            images[0] = ImageIO.read(getClass().getResourceAsStream("/objects/heart1.png")); // Full heart
            images[1] = ImageIO.read(getClass().getResourceAsStream("/objects/heart2.png")); // Half heart
            images[2] = ImageIO.read(getClass().getResourceAsStream("/objects/heart3.png")); // Empty heart
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}