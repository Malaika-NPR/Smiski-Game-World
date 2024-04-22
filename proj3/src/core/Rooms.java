package core;

import tileengine.TERenderer;
import tileengine.TETile;
import tileengine.Tileset;
import java.util.*;

public class Rooms {
    private static final int MINDIMENSION = 4; // min + max: room randomization w+h
    private static final int MAXDIMENSION = 13;
    private static final int BOARDWIDTH = 75; //dimensions of board
    private static final int BOARDHEIGHT = 30;
    public static final ArrayList<List<Integer>> COORDINATES = new ArrayList<>(); //list stores bottom left coord of room
    public static final ArrayList<List<Integer>> DIMENSIONS = new ArrayList<>(); //w + h of room
    public static final HashMap<Integer, ArrayList<List<Integer>>> CORNERS = new HashMap<>(); //key: room id; val: 4 corners coord

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
        //bottomWallGeneration(world);
        //System.out.println(COORDINATES);
        ter.renderFrame(world);
    }

    public static void placeRandomRooms(TETile[][] world) {
        Rooms rooms = new Rooms();
        int validRoomCount = 0;

        Random random = new Random();
        int randomRoomNumber = random.nextInt(12 - 5) + 5;

        for (int r = 0; validRoomCount < randomRoomNumber; r++) {
            rooms.randomRoomDimensions();
            rooms.randomRoomCoordinates();
            if (rooms.roomOverlapChecker(world)) {
                COORDINATES.removeLast();
                DIMENSIONS.removeLast();
                //rooms.randomRoomCoordinates();
            } else if (!rooms.roomOverlapChecker(world)) {
                validRoomCount += 1;
                //if it alr has a lot of rooms before hitting 14 then we break
                int roomWidth = DIMENSIONS.getLast().get(0);
                int roomHeight = DIMENSIONS.getLast().get(1);
                int currentX = COORDINATES.getLast().get(0);
                int currentY = COORDINATES.getLast().get(1);
                //current coordinates
                ArrayList<Integer> bottomLeft = new ArrayList<>();
                bottomLeft.add(currentX);
                bottomLeft.add(currentY);
                //top left coordinates
                ArrayList<Integer> topLeft = new ArrayList<>();
                topLeft.add(currentX);
                topLeft.add(currentY + roomHeight - 1);
                //top right coordinates, diagonal
                ArrayList<Integer> topRight = new ArrayList<>();
                topRight.add(currentX + roomWidth - 1);
                topRight.add(currentY + roomHeight - 1);
                //bottom right coordinates
                ArrayList<Integer> bottomRight = new ArrayList<>();
                bottomRight.add(currentX + roomWidth - 1);
                bottomRight.add(currentY);
                //complete corner list
                ArrayList<List<Integer>> allFourCornerList = new ArrayList<>();
                allFourCornerList.add(bottomLeft);
                allFourCornerList.add(topLeft);
                allFourCornerList.add(topRight);
                allFourCornerList.add(bottomRight);

                CORNERS.put(validRoomCount, allFourCornerList);
                placeSingleRandomRoom(world);
            }
        }
        wallGeneration(world);
        System.out.println(DIMENSIONS);
        System.out.println(CORNERS);
    }

    public static void placeSingleRandomRoom(TETile[][] world) {
        int x = COORDINATES.getLast().get(0);
        int y = COORDINATES.getLast().get(1);
        int roomWidth = DIMENSIONS.getLast().get(0);
        int roomHeight = DIMENSIONS.getLast().get(1);
        for (int a = x; a < x + roomWidth; a++) {
            for (int b = y; b < y + roomHeight; b++) {
                world[a][b] = Tileset.FLOOR;
            }
        }
    }

    public static void wallGeneration(TETile[][] world) {
        for (int i = 1; i < CORNERS.size() + 1; i++) {
            //bottom
            for (int x = CORNERS.get(i).getFirst().get(0) - 1; x <= ((DIMENSIONS.get(i - 1).getFirst()) + (CORNERS.get(i).getFirst().get(0))); x++) {
                //System.out.println("Width: " + DIMENSIONS.get(i - 1).getFirst());
                //System.out.println("Xcoord: " + CORNERS.get(i).getFirst().get(0));
                //System.out.println("Last: " + ((DIMENSIONS.get(i - 1).getFirst()) + (CORNERS.get(i).getFirst().get(0))));
                int y = CORNERS.get(i).getFirst().get(1) - 1;
                world[x][y] = Tileset.WALL;
            }
            //top
            for (int x = CORNERS.get(i).getFirst().get(0) - 1; x <= ((DIMENSIONS.get(i - 1).getFirst()) + (CORNERS.get(i).getFirst().get(0))); x++) {
                int y = CORNERS.get(i).getFirst().get(1) + DIMENSIONS.get(i - 1).get(1);
                world[x][y] = Tileset.WALL;
            }
            //left
            for (int y = CORNERS.get(i).getFirst().get(1); y < ((DIMENSIONS.get(i - 1).get(1)) + (CORNERS.get(i).getFirst().get(1))); y++) {
                int x = CORNERS.get(i).getFirst().get(0) - 1;
                world[x][y] = Tileset.WALL;
            }
            //right
            for (int y = CORNERS.get(i).getFirst().get(1); y < ((DIMENSIONS.get(i - 1).get(1)) + (CORNERS.get(i).getFirst().get(1))); y++) {
                int x = CORNERS.get(i).getFirst().get(0) + DIMENSIONS.get(i - 1).get(0);
                world[x][y] = Tileset.WALL;
            }
        }
    }

    public boolean roomOutBoundsChecker(TETile[][] world) {
        int chosenX = COORDINATES.getLast().get(0);
        int chosenY = COORDINATES.getLast().get(1);
        int roomWidth = DIMENSIONS.getLast().get(0);
        int roomHeight = DIMENSIONS.getLast().get(1);

        return chosenX + roomWidth > BOARDWIDTH - 3 || chosenY + roomHeight > BOARDHEIGHT - 3; //goes out of bounds
    }

    public boolean roomOverlapChecker(TETile[][] world) {
        int choosenX = COORDINATES.getLast().get(0);
        int choosenY = COORDINATES.getLast().get(1);
        int roomWidth = DIMENSIONS.getLast().get(0);
        int roomHeight = DIMENSIONS.getLast().get(1);
        if (!roomOutBoundsChecker(world)) { //room is not out of bounds
            for (int a = choosenX - 3; a < choosenX + roomWidth + 3; a++) {
                for (int b = choosenY - 3; b < choosenY + roomHeight + 3; b++) {
                    if (world[a][b] == Tileset.FLOOR) {
                        return true; //overlap
                    }
                }
            }
            return false;
        }
        return true;
    }

    //random dimension [w, h]; adds to DIMENSIONS
    public void randomRoomDimensions() {
        int roomWidth = randomDimension();
        int roomHeight = randomDimension();
        List<Integer> pair = new ArrayList<>();
        pair.add(roomWidth);
        pair.add(roomHeight);

        DIMENSIONS.add(pair);
    }

    //random coordinate [x, y] within the board; adds to COORDINATES
    public void randomRoomCoordinates() {
        int boardWidth = BOARDWIDTH - 2;
        int boardHeight = BOARDHEIGHT - 2;
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
    /*
    public void roomtohallwayConnection(Hallways.QuickFindUF unionfind,int x, int y) {
        for (x = 0; x < CORNERS.size(); x++) {
            for (y = 0; y < CORNERS.size(); y++) {
            CORNERS.get(x).get(y).

            if (roomsConnected)

        //HorizontalHallway Helper
        private void horizontalHelper(int xStart, int yEnd)

        //VerticalHallway Helper
        private void verticalHelper(int yStart, int xEnd)
                for (int y = Math.Min(yStart, yEnd);
    } */
}

