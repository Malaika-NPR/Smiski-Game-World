package core;

import static java.awt.image.ImageObserver.HEIGHT;
import static java.awt.image.ImageObserver.WIDTH;

public class World {
    private final int Width;
    private final int Height;
    private final long Seed;
    private final tileengine.TETile[][] World;

    public World(int Width, int Height, long Seed) {//constructor
        this.Height = Height;
        this.Width = Width;
        this.Seed = Seed;
        this.World = new tileengine.TETile[Width][Height];
    }

    public tileengine.TETile[][] createWorld() { //generating a random world
        intializingWorld(); //creating world with empty tiles
        createRooms(); //generating random roomd
        createHallways(); //generating random hallways
        roomAndHallwayConnection(); //generating the connections
        return height;
    }
    private void worldInitializer() { //initializing world with empty tiles
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                World[x][y] = Tileset.NOTHING;
            }
        }
    }





    // build your own world!

}
