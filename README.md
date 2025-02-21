# 2D game 

So this is my  first
ever made from scratch game, placeholder for it is "comrad pixel”.
The games view  is from
top to down and main goal is to survive the night (night here lasts 2 minutes).
during the night enemy hedgehog will
try to kill you so be brave and try to survive hedgehog waves :p
Doing this project had only
1 goal and it was learning

![Ingame TitleScreen](res/Markdown/gif.gif)

## 📜 Features learned

- **Player Control**: keyboard inputs handling. 
- **Collision Detection**: collision system for both the player and enemies.
- **Enemy AI**: Enemies chase the player and deal damage on collision.
- **Game drawing**: Drawing tiles using txt file.   
- **Dynamic World**: Procedural enemy spawning and tile-based world map.
- **Health System**: Player health displayed as hearts, with damage mechanics.



## 🛠️ How to Run the Game

1. **Ensure Java is installed**:

   ```bash
   java -version
   ```

2. **Compile the game**:

   ```bash
   javac Main/*.java Entity/*.java object/*.java Tile/*.java Projectile/*.java
   ```

3. **Run the game**:

   ```bash
   java Main.main
   ```

## 📊 Technical Highlights
For a detailed breakdown of the project files and projects structure, check the [STRUCTURE.md](Documantation/STRUCTURE.md) 

## 🧩 Customization
If your goal is to play around and make your own levels you can absolutely do it
- **Add New Levels**: Add `.txt` map files under `resources/maps` and load them via `TileManager.java`.
- **Change Sprites**: Replace images in the `resources/player` and `resources/Enemy` directories.
- **Sound Effects**: Modify sound files in the `resources/sounds` directory.

## 📖 Future Plans and Improvements

- Improving game performance (collision detection and so on...)
- Adding and power-ups.



## 🙌 Acknowledgements

- Special thanks to open-source sound and art asset creators.



