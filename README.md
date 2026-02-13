Smiski Game World 

SmiskWorld is a 2D tile based world exploration engine built in Java. Given a numeric seed, the engine generates a fully explorable world made up of connected rooms and hallways. The same seed and the same sequence of inputs will always produce the exact same world state.
This project was mainly about managing complexity. It combines world generation, interactivity, saving and loading, and deterministic replay into one cohesive system.

Features 
  -Procedural World Generation
  
   - Generates a new world from a seed
    
   - Random number of rooms and hallways
    
   - Random room sizes and placements
    
   - Turning hallways with no dead ends
    
   - All rooms are reachable
    
-Worlds look meaningfully different across seeds
    
-Worlds are pseudo random but deterministic. The same seed always produces the same layout.

Movement and Interface 
  -Movement and Interface
    
  - Move with W, A, S, D
    
      -Collision detection prevents walking through walls
      
      -HUD displays the tile description under the mouse
      
     - Keyboard based main menu
      
       - N for new world
      
       - L for load
      
       -  Q for quit
   
Saving and Loading

      - Typing :Q saves the world and exits immediately.

      - When restarted, pressing L restores the exact same state including layout, avatar position, and random state.

Replay Through String Input

  - The engine supports:

      -getWorldFromInput(String input)

  - This simulates keyboard input without rendering and returns the final 2D tile array. It supports world creation, movement, saving, and loading while remaining fully deterministic.

Structure

  - TileEngine handles rendering and tiles

  - Core contains world logic, generation, movement, input parsing, and persistence

  - Utils supports randomness and file operations

  - Rendering and logic are kept separate to reduce bugs and keep the system modular.

Custom tile rendering engine

 - Running the Project
  
    - Run Main.java
  
    - Press N
  
    -  Enter a seed
  
    -  Press S
  
    - Move with WASD
  
    - Press :Q to save
  
- Restart and press L to load your saved world.
