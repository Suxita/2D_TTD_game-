package Main;

import Entity.Player;
import Entity.Enemy;
import object.OBJ_heart;

import java.awt.*;
import java.awt.image.BufferedImage;
import javax.swing.JPanel;
import Tile.TileManager;
import object.SuperObject;

public class GamePanel extends JPanel implements Runnable {

    final int originalTileSize = 16;
    final int scale = 3;

    public final int tileSize = originalTileSize * scale; // 48
    public final int maxScreenCol = 20;
    public final int maxScreenRow = 12;
    public final int screenWidth = tileSize * maxScreenCol;  // 768
    public final int screenHeight = tileSize * maxScreenRow; // 576
    BufferedImage temScreen;
    Graphics2D g2;
    //GAME STATE
    public int gameState;
    public static final int titleState=0;
    public static final int playState=1;
    public static final int pauseState=2;

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

    //Full screen vars
    private GraphicsDevice gd;
    public int screenWidth2;
    public int screenHeight2;

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
        this.addKeyListener(keyh);
        this.setFocusable(true);
        healthPerHeart = (int) Math.ceil((double) maxHealth / heartsToDisplay);
        GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
        gd = ge.getDefaultScreenDevice();

        healthPerHeart = (int) Math.ceil((double) maxHealth / heartsToDisplay);
    }

    public void setupGame() {
        aSetter.setObject();
        aSetter.setEnemies();
        playMusic(1);
        gameState = titleState;

        temScreen = new BufferedImage(screenWidth, screenHeight, BufferedImage.TYPE_INT_ARGB);
        g2 = (Graphics2D) temScreen.getGraphics();

        setFullScreen(); // Call setFullScreen AFTER creating the window
    }
    public void setGameState(int state) {
        this.gameState = state;
        if (gameState == playState || gameState == pauseState) {
            requestFocus(); // Request focus when switching to play or pause
        }
    }


    public void setFullScreen() {
        if (main.window != null) { // Check if window is created
            gd.setFullScreenWindow(main.window);
            if (gd.getFullScreenWindow() != null) { // Ensure fullscreen mode is set
                screenWidth2 = main.window.getWidth();
                screenHeight2 = main.window.getHeight();
            } else {
                screenWidth2 = screenWidth;
                screenHeight2 = screenHeight;
                System.err.println("Fullscreen mode not supported.");
            }
        } else {
            screenWidth2 = screenWidth;
            screenHeight2 = screenHeight;
            System.err.println("Window not yet initialized for fullscreen.");
        }
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
                drawToTempScreen();
               drawToScreen();
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
        if (gameState == playState) {
            player.update();
            for (Enemy enemy : enemies) { // Enhanced for loop - cleaner
                if (enemy != null) {
                    enemy.update();
                    checker.checkPlayerWithEnemy(player, enemy);
                    checker.checkEnemyWithTile(enemy);
                }
            }
        }
    }
    private void drawToTempScreen() {

        g2.setColor(Color.black);
        g2.fillRect(0, 0, screenWidth, screenHeight);

        if (gameState == titleState) {
            ui.drawTitleScreen(g2);
        } else {
            tileM.draw(g2);

            for (SuperObject object : obj) {
                if (object != null) {
                    object.draw(g2, this);
                }
            }

            for (Enemy enemy : enemies) {
                if (enemy != null) {
                    enemy.draw(g2);
                }
            }

            player.draw(g2);
            drawHearts(g2);
            ui.draw(g2);
        }
    }
    private void drawToScreen() {
        Graphics2D g = (Graphics2D) getGraphics();
        if (g != null) {
            g.drawImage(temScreen, 0, 0, screenWidth2, screenHeight2, null);
            g.dispose();
        }
    }    public void playMusic(int i) {
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