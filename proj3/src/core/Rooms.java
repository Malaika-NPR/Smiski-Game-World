package core;

import tileengine.TERenderer;
import tileengine.TETile;
import tileengine.Tileset;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Rooms {
    private static final int MINDIMENSION = 3;
    private static final int MAXDIMENSION = 13;
    private static final int BOARDWIDTH = 80;
    private static final int BOARDHEIGHT = 35;
    public static final ArrayList<List<Integer>> COORDINATES = new ArrayList<>();
    public static final ArrayList<List<Integer>> DIMENSIONS = new ArrayList<>();

    public static void main(String[] args) {
        // initialize the tile rendering engine with a window of size WIDTH x HEIGHT
        TERenderer ter = new TERenderer();
        ter.initialize(BOARDWIDTH, BOARDHEIGHT);

        // initialize tiles
        TETile[][] world = new TETile[BOARDWIDTH][BOARDHEIGHT];
        for (int x = 0; x < BOARDWIDTH; x++) {
            for (int y = 0; y < BOARDHEIGHT; y++) {
                world[x][y] = Tileset.FLOWER;
            }
        }

        placeRandomRooms(world);

        // draws the world to the screen
        ter.renderFrame(world);
    }

    public static void placeRandomRooms(TETile[][] world) {
        Rooms rooms = new Rooms();

        for (int r = 0; r < 40; r++) {
            //ArrayList<List<Integer>> rDimensions  = rooms.randomRoomDimensions();
            //ArrayList<List<Integer>> rCoordinates  = rooms.randomRoomCoordinates();
            rooms.randomRoomDimensions();
            rooms.randomRoomCoordinates();
            System.out.println(COORDINATES);
            System.out.println(DIMENSIONS);
            if (rooms.roomOverlapChecker(world)) {
                //there is overlap or its out of bounds
                //replace new coordinates
                System.out.println("ahhh overlap");
                //rCoordinates = rooms.randomRoomCoordinates();
                COORDINATES.removeLast();
                rooms.randomRoomCoordinates();
            } else if (!rooms.roomOverlapChecker(world)) {
                System.out.println("ahhh no overlap");
                placeSingleRandomRoom(world);
            }
        }
    }

    public static void placeSingleRandomRoom(TETile[][] world) {
        System.out.println(COORDINATES);
        int x = COORDINATES.getLast().get(0);
        System.out.println(x);
        int y = COORDINATES.getLast().get(1);
        int roomWidth = DIMENSIONS.getLast().get(0);
        int roomHeight = DIMENSIONS.getLast().get(1);
        for (int a = x; a < x + roomWidth; a++) {
            for (int b = y; b < y + roomHeight; b++) {
                world[a][b] = Tileset.FLOOR;
            }
        }
    }

    public boolean roomOutBoundsChecker(TETile[][] world) {
        int chosenX = COORDINATES.getLast().get(0);
        int chosenY = COORDINATES.getLast().get(1);
        int roomWidth = DIMENSIONS.getLast().get(0);
        int roomHeight = DIMENSIONS.getLast().get(1);

        return chosenX + roomWidth > BOARDWIDTH - 2 || chosenY + roomHeight > BOARDHEIGHT - 2; //goes out of bounds
    }

    public boolean roomOverlapChecker(TETile[][] world) {
        System.out.println(COORDINATES);
        int choosenX = COORDINATES.getLast().get(0);
        int choosenY = COORDINATES.getLast().get(1);
        int roomWidth = DIMENSIONS.getLast().get(0);
        int roomHeight = DIMENSIONS.getLast().get(1);
        if (!roomOutBoundsChecker(world)) { //room is not out of bounds
            for (int a = choosenX - 2; a < choosenX + roomWidth + 2; a++) {
                for (int b = choosenY - 2; b < choosenY + roomHeight + 2; b++) {
                    if (world[a][b] == Tileset.FLOOR) {
                        return true; //overlap
                    }
                }
            }
            return false;
        }
        return true;
    }

    public void randomRoomDimensions() {
        //ArrayList<List<Integer>> dimensions = new ArrayList<>();
        int roomWidth = randomDimension();
        int roomHeight = randomDimension();
        List<Integer> pair = new ArrayList<>();
        pair.add(roomWidth);
        pair.add(roomHeight);

        DIMENSIONS.add(pair);
    }

    public void randomRoomCoordinates() {
        int boardWidth = BOARDWIDTH - 2;
        int boardHeight = BOARDHEIGHT - 2;
        //ArrayList<List<Integer>> coordinates = new ArrayList<>();
        Random random = new Random();
        int x = random.nextInt(boardWidth - 3) + 3;
        int y = random.nextInt(boardHeight - 3) + 3;
        List<Integer> pair = new ArrayList<>();
        pair.add(x);
        pair.add(y);

        COORDINATES.add(pair);
    }
    //returns a random width within bounds
    public int randomDimension() {
        Random random = new Random();
        return random.nextInt(MAXDIMENSION - MINDIMENSION) + MINDIMENSION;
    }
}

//Method to use Hallways in QuickFind in Rooms take points in room1 and room2  and
//connect those two points in hallway 2



//Treemap
//Reference for each room a list of room have a list  of room s and go through each room and a way you
//can reference it and draw a path of reference point to the rooms

//disjoint set --> still need to a reference which is an arraylist
// or do a treemap

//choose a lower left corner of each room and make the path that way to a hallway

//First Value of hashmap

