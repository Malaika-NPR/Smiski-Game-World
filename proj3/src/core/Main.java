package core;

import tileengine.TERenderer;
import tileengine.TETile;
import tileengine.Tileset;

import java.util.ArrayList;
import java.util.List;

import static core.OtherHallways.randomConnection;
import static core.OtherHallways.randomRoomCoordinates;

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
        OtherHallways hallways = new OtherHallways(); //connection generating
        //hallways.roomConnecting(Rooms.CORNERS);
        OtherHallways.hallwayGeneration(world);

        ter.renderFrame(world);

        /*System.out.println("Dimensions Below");
        System.out.println(Rooms.DIMENSIONS);
        System.out.println("left corners Below");
        System.out.println(Rooms.COORDINATES);
        ArrayList<List<Integer>> testList = randomConnection();
        System.out.println("Test List Below");
        System.out.println(testList);
        ArrayList<List<Integer>> unionList = randomRoomCoordinates(testList.get(0));
        System.out.println("Union List Below");
        System.out.println(unionList);
        System.out.println("Path # Below");
        System.out.println(OtherHallways.pathTrajectory(unionList.get(0), unionList.get(1)));*/
    }

    //connecting hallways to rooms
}
