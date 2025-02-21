## 📦 Project Structure

```
├── Main/
│   ├── GamePanel.java       // Main game logic game loop loop
│   ├── main.java            // Games starting point
│   ├── KeyHandler.java      // Input management 
│   ├── AssetSetter.java     // Handles object and enemy spawning
│   ├── Sound.java           // Sound management
│   └── collisionChecker.java// Collision detection logic
│
├── Entity/
│   ├── Entity.java          // Base class for all entities
│   ├── Player.java          // Player character logic
│   └── Enemy.java           // Enemy behavior and movement
│
├── object/
│   ├── SuperObject.java     // Base object class
│   └── OBJ_heart.java       // Heart object for health display
│
├── Tile/
│   ├── Tile.java            // Tile object 
│   └── TileManager.java     // Tile management and map loading
│
├── Projectile/
│   └──Bullet               //Bullet drawing and logic
│
└── resources/
    ├── sounds/              // Game sound effects and music
    ├── player/              // Player sprite assets
    ├── Enemy/               // Enemy sprite assets
    ├── GUI/                 // User interface assets
    └── maps/                // Game map files


```
