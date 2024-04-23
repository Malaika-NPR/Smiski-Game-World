package core;

import tileengine.TERenderer;
import tileengine.TETile;
import tileengine.Tileset;

public class Main {
    public static void main(String[] args) {
        // initialize the tile rendering engine with a window of size WIDTH x HEIGHT
        TERenderer ter = new TERenderer();
        ter.initialize(Rooms.BOARDWIDTH, Rooms.BOARDHEIGHT);

        // initialize tiles
        TETile[][] world = new TETile[Rooms.BOARDWIDTH][Rooms.BOARDHEIGHT];
        for (int x = 0; x < Rooms.BOARDWIDTH; x++) {
            for (int y = 0; y < Rooms.BOARDHEIGHT; y++) {
                world[x][y] = Tileset.FLOWER;
            }
        }
        Rooms.placeRandomRooms(world);
        Hallways hallways = new Hallways(); //connection generating
        //hallways.roomConnecting(Rooms.CORNERS);
        hallways.hallwayCreation(world, Rooms.CORNERS);

        ter.renderFrame(world);
    }

    //connecting hallways to rooms
}
