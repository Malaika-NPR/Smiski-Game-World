package core;

import edu.princeton.cs.algs4.Graph;
import tileengine.TERenderer;
import tileengine.TETile;
import tileengine.Tileset;

public class BasicWorld {

    private static final int WIDTH = 80;
    private static final int HEIGHT = 35;

    public static void main(String[] args) {
        // initialize the tile rendering engine with a window of size WIDTH x HEIGHT
        TERenderer ter = new TERenderer();
        ter.initialize(WIDTH, HEIGHT);

        // initialize tiles
        TETile[][] world = new TETile[WIDTH][HEIGHT];
        for (int x = 0; x < WIDTH; x++) {
            for (int y = 0; y < HEIGHT; y++) {
                world[x][y] = Tileset.FLOOR;
            }
        }

        // fills in a block 15 tiles wide by 5 tiles tall
        for (int x = 20; x < 35; x++) {
            for (int y = 5; y < 10; y++) {
                world[x][y] = Tileset.WALL;
            }
        }

        // draws the world to the screen
        ter.renderFrame(world);
    }
}
