package core;

import org.checkerframework.checker.units.qual.C;
import tileengine.TERenderer;
import tileengine.TETile;
import tileengine.Tileset;

import java.util.*;


public class Rooms {
    private static final int MINDIMENSION = 4;
    private static final int MAXDIMENSION = 13;
    private static final int BOARDWIDTH = 75;
    private static final int BOARDHEIGHT = 30;
    public static final ArrayList<List<Integer>> COORDINATES = new ArrayList<>();
    public static final ArrayList<List<Integer>> DIMENSIONS = new ArrayList<>();
    public static final HashMap<Integer, ArrayList<List<Integer>>> CORNERS = new HashMap<>();
    //hashmap of all the rooms in order as keys and then the 4 corners as values
    //RoomtoHallway connection
    Map.Entry<Integer, ArrayList<List<Integer>>> cornersFirstInd = CORNERS.entrySet().iterator().next();
    Integer cornersFirstKey = cornersFirstInd.getKey();
    ArrayList<List<Integer>> cornersFirstValue = cornersFirstInd.getValue();

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
        //System.out.println(COORDINATES);
        ter.renderFrame(world);
    }

    public static void placeRandomRooms(TETile[][] world) {
        Rooms rooms = new Rooms();
        int validRoomCount = 0;

        for (int r = 0; validRoomCount < 2; r++) {
            rooms.randomRoomDimensions();
            rooms.randomRoomCoordinates();
            if (rooms.roomOverlapChecker(world)) {
                COORDINATES.removeLast();
                //rooms.randomRoomCoordinates();
            } else if (!rooms.roomOverlapChecker(world)) {
                validRoomCount+= 1;
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

    public boolean roomOutBoundsChecker(TETile[][] world) {
        int chosenX = COORDINATES.getLast().get(0);
        int chosenY = COORDINATES.getLast().get(1);
        int roomWidth = DIMENSIONS.getLast().get(0);
        int roomHeight = DIMENSIONS.getLast().get(1);

        return chosenX + roomWidth > BOARDWIDTH - 2 || chosenY + roomHeight > BOARDHEIGHT - 2; //goes out of bounds
    }

    public boolean roomOverlapChecker(TETile[][] world) {
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
    public void roomtohallwayConnection(Hallways.QuickFindUF unionfind,int x, int y) {
        for (x = 0; x < CORNERS.size(); x++) {
            for (y = 0; y < CORNERS.size(); y++) {
            CORNERS.get(x).get(y).

            if (roomsConnected)

        //HorizontalHallway Helper
        private void horizontalHelper(int xStart, int xEnd)
                
        //VerticalHallway Helper
        private void verticalHelper(int xStart, int xEnd)

            }

    }
}

