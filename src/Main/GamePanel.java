package Main;

import Entity.Player;
import Entity.Enemy;
import object.OBJ_heart;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import javax.swing.JPanel;
import Tile.TileManager;
import object.SuperObject;

public class GamePanel extends JPanel implements Runnable {

    final int originalTileSize = 16;
    final int scale = 3;

    public final int tileSize = originalTileSize * scale; // 48
    public final int maxScreenCol = 16;
    public final int maxScreenRow = 12;
    public final int screenWidth = tileSize * maxScreenCol;  // 768
    public final int screenHeight = tileSize * maxScreenRow; // 576

    // WORLD SETTINGS
    public final int maxWorldCol = 50;
    public final int maxWorldRow = 50;
    public final int maxWorldWidth = tileSize * maxWorldCol;
    public final int maxWorldHeight = tileSize * maxWorldRow;

    int fps = 60;

    TileManager tileM = new TileManager(this);
    public KeyHandler keyh = new KeyHandler(this); // Pass 'this' here
    Thread gameThread;
    Sound sound = new Sound();

    public Player player;
    public SuperObject obj[] = new SuperObject[10];
    public Enemy[] enemies = new Enemy[10];
    public collisionChecker checker = new collisionChecker(this);
    public AssetSetter aSetter = new AssetSetter(this);
    public UI ui = new UI(this);

    // Health
    public OBJ_heart heart = new OBJ_heart(this);
    public int maxHealth = 100;
    public int heartsToDisplay = 5;
    public int healthPerHeart;

    public GamePanel() {
        player = new Player(this, keyh);

        this.setPreferredSize(new Dimension(screenWidth, screenHeight));
        this.setBackground(Color.black);
        this.setDoubleBuffered(true);
        this.addKeyListener(keyh); // Use the instance with the 'this' reference
        this.setFocusable(true);
        healthPerHeart = (int) Math.ceil((double) maxHealth / heartsToDisplay);
    }

    public void setupGame() {
        aSetter.setObject();
        aSetter.setEnemies();
        playMusic(0);

    }

    public void startGameThread() {
        gameThread = new Thread(this);
        gameThread.start();
    }

    public void run() {

        double drawInterval = 1000000000.0 / fps; // 0.0166666 seconds
        double delta = 0;
        long lastTime = System.nanoTime();
        long currentTime;
        long timer = 0;
        int drawCount = 0;

        while (gameThread != null) {

            currentTime = System.nanoTime();

            delta += (currentTime - lastTime) / drawInterval;
            timer += (currentTime - lastTime);
            lastTime = currentTime;

            if (delta >= 1) {
                update();
                repaint();
                delta--;
                drawCount++;
            }

            if (timer >= 1000000000) {
                System.out.println("FPS:" + drawCount);
                drawCount = 0;
                timer = 0;
            }
        }
    }

    public void update() {
        player.update();

        for (int i = 0; i < enemies.length; i++) {
            if (enemies[i] != null) {
                enemies[i].update();
                checker.checkPlayerWithEnemy(player, enemies[i]);
                checker.checkEnemyWithTile(enemies[i]); // Check for tile collision
            }
        }
    }

    @Override
    public void paintComponent(Graphics g) {

        super.paintComponent(g);

        Graphics2D g2 = (Graphics2D) g;

        // TILE
        tileM.draw(g2);

        // OBJECT
        for (int i = 0; i < obj.length; i++) {
            if (obj[i] != null) {
                obj[i].draw(g2, this);
            }
        }

        // ENEMY
        for (int i = 0; i < enemies.length; i++) {
            if (enemies[i] != null) {
                enemies[i].draw(g2);
            }
        }

        // PLAYER
        player.draw(g2);

        // UI
        drawHearts(g2);
        ui.draw(g2);

        g2.dispose();
    }


    public void playMusic(int i) {
        sound.setFile(i);
        sound.play();
        sound.loop();
    }

    public void stopMusic() {
        sound.stop();
    }

    public void playSE(int i) {
        sound.setFile(i);
        sound.play();
    }


    public void drawHearts(Graphics2D g2) {
        int x = 10;
        int y = 10;
        int healthPerHeart = maxHealth / heartsToDisplay;

        for (int i = 0; i < heartsToDisplay; i++) {
            int heartState = getHeartState(player.health, healthPerHeart, i);
            g2.drawImage(heart.images[heartState], x, y, tileSize, tileSize, null);
            x += tileSize + 10;
        }
    }

    public int getHeartState(int health, int healthPerHeart, int heartIndex) {
        if (health >= (heartIndex + 1) * healthPerHeart) {
            return 0; // Full
        } else if (health > heartIndex * healthPerHeart) {
            return 1; // Half
        } else {
            return 2; // Empty
        }
    }
}