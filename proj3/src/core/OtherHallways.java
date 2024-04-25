package core;

import tileengine.TETile;
import tileengine.Tileset;
import java.util.*;

public class OtherHallways {
    public static HashMap<Integer, ArrayList<List<Integer>>> storedRooms = Rooms.CORNERS;
    private static final Set<List<Integer>> existingHallways = new HashSet<>();
    private static final Set<List<Integer>> verticalHallways = new HashSet<>();
    private static final Set<List<Integer>> horizontalHallways = new HashSet<>();
    private static final HashMap<List<Integer>, Integer> previousTileType = new HashMap<>();
    public static final int BOARDWIDTH = 75; //dimensions of board
    public static final int BOARDHEIGHT = 30;

    //creates the list with pairs of rooms to connect
    public static ArrayList<List<Integer>> randomConnection() {
        Set<Integer> roomIDs = storedRooms.keySet();
        List<Integer> randomOrganizedRooms = randomizedSetToList(roomIDs);
        ArrayList<List<Integer>> unionizedRooms = new ArrayList<>();
        for (int a = 1; a < randomOrganizedRooms.size(); a++) {
            List<Integer> pairs = new ArrayList<>();
            pairs.add(randomOrganizedRooms.get(a - 1));
            pairs.add(randomOrganizedRooms.get(a));
            unionizedRooms.add(pairs);
        }
        return unionizedRooms;
    }

    //creates a list of the rooms shuffled
    public static List<Integer> randomizedSetToList(Set<Integer> set) {
        List<Integer> randomListOutput = new ArrayList<>();
        randomListOutput.addAll(set);
        Collections.shuffle(randomListOutput);
        return randomListOutput;
    }

    public static void hallwayGeneration(TETile[][] world) {
        ArrayList<List<Integer>> unionPaths = randomConnection();
        for (List<Integer> path : unionPaths) {
            singleHallwayPlacement(world, path);
        }
    }

    public static void placingFloors(TETile[][] world) {
        for (ArrayList<List<Integer>> c : storedRooms.values()) {
            //bottomLeftX, bottomLeftY, topRightX, topRightY
            for (int x = c.getFirst().getFirst(); x <= c.get(2).getFirst(); x++) {
                for (int y = c.getFirst().get(1); y <= c.get(2).get(1); y++) {
                    world[x][y] = Tileset.FLOOR;
                }
            }
        }
    }

    public static void hallwayWallGeneration(TETile[][] world) {
        for (int x = 0; x < BOARDWIDTH; x++) {
            for (int y = 0; y < BOARDHEIGHT; y++) {
                if (needsWall(world, x, y)) {
                    world[x][y] = Tileset.WALL;
                }
            }
        }
    }

    public static void sandReplacement(TETile[][] world) {
        for (int x = 0; x < BOARDWIDTH; x++) {
            for (int y = 0; y < BOARDHEIGHT; y++) {
                if (world[x][y] == Tileset.SAND) {
                    world[x][y] = Tileset.FLOOR;
                }
            }
        }
    }

    public static boolean needsWall(TETile[][] world, int x, int y) {
        int boundX = world.length - 1;
        int boundY = world[0].length - 1;
        //check right
        //check below
        //check left
        //check above
        return world[x][y] == Tileset.FLOWER && ((x + 1 <= boundX && world[x + 1][y] == Tileset.SAND) ||
                (y - 1 >= 0 && world[x][y - 1] == Tileset.SAND) ||
                (x - 1 >= 0 && world[x - 1][y] == Tileset.SAND) ||
                (y + 1 <= boundY && world[x][y + 1] == Tileset.SAND));
    }


    public static void clearHallwayTiles(TETile[][] world, ArrayList<List<Integer>> hallwayCoordinates) {
        for (List<Integer> coordinate : hallwayCoordinates) {
            int x = coordinate.get(0);
            int y = coordinate.get(1);
            world[x][y] = Tileset.NOTHING;
            existingHallways.remove(Arrays.asList(x, y));
        }
    }

    public static void singleHallwayPlacement(TETile[][] world, List<Integer> bothRooms) {
        boolean hallwayPlaced = false;
        while (!hallwayPlaced) {
            ArrayList<List<Integer>> bothRandomRoomCoordinates = randomRoomCoordinates(bothRooms);
            int room1X = bothRandomRoomCoordinates.get(0).get(0);
            int room1Y = bothRandomRoomCoordinates.get(0).get(1);
            int room2X = bothRandomRoomCoordinates.get(1).get(0);
            int room2Y = bothRandomRoomCoordinates.get(1).get(1);

            /*if (adjacentTileChecker(world, room1X, room1Y) || adjacentTileChecker(world, room2X, room2Y)) {
                continue;
            }*/

            int positionPath = pathTrajectory(bothRandomRoomCoordinates.get(0), bothRandomRoomCoordinates.get(1));
            Random random = new Random();
            int wheelChooser = random.nextInt(2) + 1;

            if (positionPath == 0) { //upper left
                if (wheelChooser == 1) {
                    //place tiles going up --> left
                    for (int y = room1Y; y <= room2Y; y++) { //up
                        //PLACING PREVIOUS TILE INTO HASHMAP
                        previousTilePlacer(world, room1X, y);
                        if (verticalNeighborChecker(world, room1X, y)) {
                            //true: choose new coordinates, replace path
                            for (int prevY = y; prevY >= room1Y; prevY--) {
                                tileReplacer(world, room1X, prevY);
                            }
                            //generate process again
                            //singleHallwayPlacement(world, bothRooms);
                        } else {
                            //false: place tile
                            world[room1X][y] = Tileset.SAND;
                        }
                    }
                    for (int x = room2X; x < room1X; x++) { //left but from room2
                        //PLACING PREVIOUS TILE INTO HASHMAP
                        previousTilePlacer(world, x, room2Y);
                        if (horizontalNeighborChecker(world, x, room2Y)) {
                            //true: choose new coordinates, replace path
                            for (int prevX = x; prevX >= room2X; prevX--) { //back to r2
                                tileReplacer(world, prevX, room2Y);
                            }
                            for (int y = room1Y; y < room2Y; y++) {
                                tileReplacer(world, room1X, y);
                            }
                            //generate process again
                            //singleHallwayPlacement(world, bothRooms);
                        } else {
                            //false: place tile
                            world[x][room2Y] = Tileset.SAND;
                            //existingHallways.add(Arrays.asList(x, room2Y));
                        }
                    }
                } else {
                    //place tiles going left --> up
                    for (int x = room2X; x < room1X; x++) { //left
                        //PLACING PREVIOUS TILE INTO HASHMAP
                        previousTilePlacer(world, x, room1Y);
                        if (horizontalNeighborChecker(world, x, room1Y)) {
                            //true: choose new coordinates, replace path
                            for (int prevX = x; prevX >= room2X; prevX--) {
                                tileReplacer(world, prevX, room1Y);
                            }
                            //generate process again
                            //singleHallwayPlacement(world, bothRooms);
                        } else {
                            //false: place tile
                            world[x][room1Y] = Tileset.SAND;
                        }
                    }
                    for (int y = room2Y; y > room1Y; y--) { //up but from room2
                        //PLACING PREVIOUS TILE INTO HASHMAP
                        previousTilePlacer(world, room2X, y);
                        if (verticalNeighborChecker(world, room2X, y)) {
                            //true: choose new coordinates, replace path
                            for (int prevY = y; prevY <= room2Y; prevY++) {
                                tileReplacer(world, room2X, prevY);
                            }
                            for (int x = room2X; x < room1X; x++) {
                                tileReplacer(world, x, room1Y);
                            }
                        } else {
                            //false: place tile
                            world[room2X][y] = Tileset.SAND;
                        }
                    }
                }
            }
            //lower left
            else if (positionPath == 1) {
                if (wheelChooser == 1) {
                    //down --> left
                    for (int y = room1Y; y >= room2Y; y--) { //down
                        //PLACING PREVIOUS TILE INTO HASHMAP
                        previousTilePlacer(world, room1X, y);
                        if (verticalNeighborChecker(world, room1X, y)) {
                            //true: replace path
                            for (int prevY = y; prevY <= room1Y; prevY++) {
                                tileReplacer(world, room1X, prevY);
                            }
                        } else {
                            //false: coast is clear
                            world[room1X][y] = Tileset.SAND;
                        }
                    }
                    for (int x = room2X; x < room1X; x++) { //left but from room2
                        //PLACING PREVIOUS TILE INTO HASHMAP
                        previousTilePlacer(world, x, room2Y);
                        if (horizontalNeighborChecker(world, x, room2Y)) {
                            for (int prevX = x; prevX > room2X; prevX--) {
                                tileReplacer(world, prevX, room2Y);
                            }
                            for (int y = room1Y; y >= room2Y; y--) {
                                tileReplacer(world, room1X, y);
                            }
                        } else {
                            //false: coast is clear
                            world[x][room2Y] = Tileset.SAND;
                        }
                    }
                } else {
                    //left --> down
                    for (int x = room1X; x >= room2X; x--) { //left
                        //PLACING PREVIOUS TILE INTO HASHMAP
                        previousTilePlacer(world, x, room1Y);
                        if (horizontalNeighborChecker(world, x, room1Y)) {
                            for (int prevX = x; prevX <= room1X; prevX++) {
                                tileReplacer(world, prevX, room1Y);
                            }
                        } else {
                            //false: coast is clear
                            world[x][room1Y] = Tileset.SAND;
                        }
                    }
                    for (int y = room2Y; y < room1Y; y++) { //down but from room2
                        //PLACING PREVIOUS TILE INTO HASHMAP
                        previousTilePlacer(world, room2X, y);
                        if (verticalNeighborChecker(world, room2X, y)) {
                            for (int prevY = y; prevY >= room2Y; prevY--) {
                                tileReplacer(world, room2X, prevY);
                            }
                            for (int x = room2X; x < room1X; x++) {
                                tileReplacer(world, x, room1Y);
                            }
                        } else {
                            //false: coast is clear
                            world[room2X][y] = Tileset.SAND;
                        }
                    }
                }
            }
            //room2 straight across to left
            else if (positionPath == 2) {
                for (int x = room1X; x >= room2X; x--) {
                    //PLACING PREVIOUS TILE INTO HASHMAP
                    previousTilePlacer(world, x, room1Y);
                    if (horizontalNeighborChecker(world, x, room1Y)) {
                        for (int prevX = x; prevX < room1X; prevX++) { //TOOK OFF EQUAL
                            tileReplacer(world, prevX, room1Y);
                        }
                    } else {
                        world[x][room1Y] = Tileset.SAND;
                    }
                }
            }
            //room2 lower right
            else if (positionPath == 3) {
                if (wheelChooser == 1) {
                    //down --> right
                    for (int y = room1Y; y >= room2Y; y--) { //down
                        //PLACING PREVIOUS TILE INTO HASHMAP
                        previousTilePlacer(world, room1X, y);
                        if (verticalNeighborChecker(world, room1X, y)) {
                            for (int prevY = y; prevY <= room1Y; prevY++) {
                                tileReplacer(world, room1X, prevY);
                            }
                        } else {
                            world[room1X][y] = Tileset.SAND;
                        }
                    }
                    for (int x = room2X; x > room1X; x--) { //right but drawing from room2
                        //PLACING PREVIOUS TILE INTO HASHMAP
                        previousTilePlacer(world, x, room2Y);
                        if (horizontalNeighborChecker(world, x, room2Y)) {
                            for (int prevX = x; prevX <= room2X; prevX++) {
                                tileReplacer(world, prevX, room2Y);
                            }
                            for (int y = room1Y; y > room2Y; y--) { //down
                                tileReplacer(world, room1X, y);
                            }
                        } else {
                            world[x][room2Y] = Tileset.SAND; //FLAG
                        }
                    }
                } else {
                    //right --> down
                    for (int x = room1X; x <= room2X; x++) { //right
                        //PLACING PREVIOUS TILE INTO HASHMAP
                        previousTilePlacer(world, x, room1Y);
                        if (horizontalNeighborChecker(world, x, room1Y)) {
                            for (int prevX = x; prevX >= room1X; prevX--) {
                                tileReplacer(world, prevX, room1Y);
                            }
                        } else {
                            world[x][room1Y] = Tileset.SAND;
                        }
                    }
                    for (int y = room2Y; y < room1Y; y++) { //down but drawing from room2
                        //PLACING PREVIOUS TILE INTO HASHMAP
                        previousTilePlacer(world, room2X, y);
                        if (verticalNeighborChecker(world, room2X, y)) { //FLAG
                            for (int prevY = y; prevY >= room2Y; prevY--) {
                                tileReplacer(world, room2X, prevY);
                            }
                            for (int x = room1X; x < room2X; x++) { //right
                                tileReplacer(world, x, room1Y);
                            }
                        } else {
                            world[room2X][y] = Tileset.SAND;
                        }
                    }
                }
            }
            //room2 up right
            else if (positionPath == 4) {
                //world[room1X][room1Y] = Tileset.FLOOR; //placing tile at the start
                if (wheelChooser == 1) {
                    //place tiles going up --> right
                    for (int y = room1Y; y <= room2Y; y++) { //up
                        //PLACING PREVIOUS TILE INTO HASHMAP
                        previousTilePlacer(world, room1X, y);
                        if (verticalNeighborChecker(world, room1X, y)) {
                            for (int prevY = y; prevY >= room1Y; prevY--) {
                                tileReplacer(world, room1X, prevY);
                            }
                        } else {
                            world[room1X][y] = Tileset.SAND;
                        }
                    }
                    for (int x = room2X; x > room1X; x--) { //right
                        //PLACING PREVIOUS TILE INTO HASHMAP
                        previousTilePlacer(world, x, room2Y);
                        if (horizontalNeighborChecker(world, x, room2Y)) {
                            for (int prevX = x; prevX <= room2X; prevX++) {
                                tileReplacer(world, prevX, room2Y);
                            }
                            for (int y = room1Y; y < room2Y; y++) {
                                tileReplacer(world, room1X, y);
                            }
                        } else {
                            world[x][room2Y] = Tileset.SAND;
                        }
                    }
                } else {
                    //place tiles going right --> up
                    for (int x = room1X; x <= room2X; x++) { //right
                        //PLACING PREVIOUS TILE INTO HASHMAP
                        previousTilePlacer(world, x, room1Y);
                        if (horizontalNeighborChecker(world, x, room1Y)) {
                            for (int prevX = x; prevX >= room1X; prevX--) {
                                tileReplacer(world, prevX, room1Y);
                            }
                        } else {
                            world[x][room1Y] = Tileset.SAND;
                        }
                    }
                    for (int y = room2Y; y > room1Y; y--) { //up
                        //PLACING PREVIOUS TILE INTO HASHMAP
                        previousTilePlacer(world, room2X, y);
                        if (verticalNeighborChecker(world, room2X, y)) {
                            for (int prevY = y; prevY <= room2Y; prevY++) {
                                tileReplacer(world, room2X, prevY);
                            }
                            for (int x = room1X; x < room2X; x++) {
                                tileReplacer(world, x, room1Y);
                            }
                        } else {
                            world[room2X][y] = Tileset.SAND;
                        }
                    }
                }
                //room 5 straight across to right
            } else if (positionPath == 5) {
                for (int x = room1X; x <= room2X; x++) {
                    //PLACING PREVIOUS TILE INTO HASHMAP
                    previousTilePlacer(world, x, room1Y);
                    if (horizontalNeighborChecker(world, x, room1Y)) {
                        for (int prevX = x; prevX >= room1X; prevX--) {
                            tileReplacer(world, prevX, room1Y);
                        }
                    } else {
                        world[x][room1Y] = Tileset.SAND;
                    }
                }
            }
            //room2 straight down
            else if (positionPath == 6) {
                for (int y = room1Y; y >= room2Y; y--) {
                    //PLACING PREVIOUS TILE INTO HASHMAP
                    previousTilePlacer(world, room2X, y);
                    if (verticalNeighborChecker(world, room2X, y)) {
                        for (int prevY = y; prevY <= room1Y; prevY++) {
                            tileReplacer(world, room2X, prevY);
                        }
                    } else {
                        world[room2X][y] = Tileset.SAND;
                    }
                }
            }
            //room2 straight up
            else if (positionPath == 7) {
                for (int y = room1Y; y <= room2Y; y++) {
                    //PLACING PREVIOUS TILE INTO HASHMAP
                    previousTilePlacer(world, room2X, y);
                    if (verticalNeighborChecker(world, room2X, y)) {
                        for (int prevY = y; prevY >= room1Y; prevY--) {
                            tileReplacer(world, room2X, prevY);
                        }
                    } else {
                        world[room2X][y] = Tileset.SAND;
                    }
                }
            }
            hallwayPlaced = true;
        }
    }

    //used if needed to redo path brings world back to old path
    private static void tileReplacer(TETile[][] world, int x, int y) {
        List<Integer> prevCoord = new ArrayList<>();
        prevCoord.add(x);
        prevCoord.add(y);
        int tileType = previousTileType.get(prevCoord);
        if (tileType == 0) {
            world[x][y] = Tileset.FLOOR;
        } else if (tileType == 1) {
            world[x][y] = Tileset.SAND;
        } else if (tileType == 2) {
            world[x][y] = Tileset.FLOWER;
        } else if (tileType == 3) {
            world[x][y] = Tileset.WALL;
        }
    }

    //used to check adjacent tiles
    //y + 1<= boundY && world[x][y + 1] == Tileset.SAND || y - 1 >= 0 && world[x][y - 1] == Tileset.SAND ||
    private static boolean horizontalNeighborChecker(TETile[][] world, int x, int y) {
        int boundX = world.length - 1;
        int boundY = world[0].length - 1;
        return (world[x - 1][y + 1] == Tileset.SAND && world[x][y + 1] == Tileset.SAND) ||
                (world[x + 1][y + 1] == Tileset.SAND && world[x][y + 1] == Tileset.SAND) ||
                (world[x][y - 1] == Tileset.SAND && world[x + 1][y - 1] == Tileset.SAND) ||
                (world[x][y - 1] == Tileset.SAND && world[x - 1][y - 1] == Tileset.SAND);
                //world[x][y] == Tileset.WALL && world[x + 1][y] == Tileset.WALL||
                //world[x][y] == Tileset.WALL && world[x - 1][y] == Tileset.WALL;
    }
    //used to check adjacent tiles
    //x - 1 >= 0 && world[x - 1][y] == Tileset.SAND || x + 1 <=boundX && world[x + 1][y] == Tileset.SAND
    private static boolean verticalNeighborChecker(TETile[][] world, int x, int y) {
        int boundX = world.length - 1;
        return (world[x - 1][y + 1] == Tileset.SAND && world[x - 1][y] == Tileset.SAND) ||
                (world[x - 1][y - 1] == Tileset.SAND && world[x - 1][y] == Tileset.SAND) ||
                (world[x + 1][y + 1] == Tileset.SAND && world[x + 1][y] == Tileset.SAND) ||
                (world[x + 1][y - 1] == Tileset.SAND && world[x + 1][y] == Tileset.SAND);
                //world[x][y] == Tileset.WALL && world[x][y + 1] == Tileset.WALL ||
                //world[x][y] == Tileset.WALL && world[x][y - 1] == Tileset.WALL;
    }

    //returns a number for type of tile
    private static int typeOfTile(TETile[][] world, int x, int y) {
        int type = 0;
        if (world[x][y] == Tileset.FLOOR) {
            return type += 0;
        } else if (world[x][y] == Tileset.SAND) {
            return type += 1;
        } else if (world[x][y] == Tileset.FLOWER) {
            return type += 2;
        } else if (world[x][y] == Tileset.WALL) {
            return type += 3;
        }
        return type;
    }

    //checker to see if surrounding tile is a hallway
    private static boolean isHallway(TETile[][] world, int x, int y) {
        TETile tile = world[x][y];
        return tile == Tileset.SAND;
    }

    /*private static boolean adjacentTileChecker(TETile[][] world, int x, int y) {
        for (int i = x - 1; i <= x + 1; i++) {
            for (int s = y - 1; s <= y + 1; s++) {
                if (isHallway(i, s)) {
                    return true;
                }
            }
        }
<<<<<<< HEAD
        //lower left
        else if (positionPath == 1) {
            if (wheelChooser == 1) {
                //down --> left
                for (int y = room1Y; y >= room2Y; y--) { //down
                    world[room1X][y] = Tileset.WATER;
                }
                for (int x = room2X; x <= room1X; x++) { //left but from room2
                    world[x][room2Y] = Tileset.WATER;
                }
            } else {
                //left --> down
                for (int x = room1X; x >= room2X; x--) { //left
                    world[x][room1Y] = Tileset.SAND;
                }
                for (int y = room2Y; y <= room1Y ; y++) { //down but from room2
                    world[room2X][y] = Tileset.SAND;
                }
            }
        }
        else if (positionPath == 2) { //room2 straight across to left
            for (int x = room1X; x >= room2X; x--) {
                world[x][room2Y] = Tileset.MOUNTAIN;
            }
        }
        //room2 lower right
        else if (positionPath == 3) {
            if (wheelChooser == 1) {
                //down --> right
                for (int y = room1Y; y >= room2Y; y--) { //down
                    world[room1X][y] = Tileset.GRASS;
                }
                for (int x = room2X; x >= room1X; x--) { //right but drawing from room2
                    world[x][room2Y] = Tileset.GRASS;
                }
            } else {
                //right --> down
                for (int x = room1X; x <= room2X; x++) { //right
                    world[x][room1Y] = Tileset.GRASS;
                }
                for (int y = room2Y; y <= room1Y; y++) { //down but drawing from room2
                    world[room2X][y] = Tileset.GRASS;
                }
            }
        }
        //room2 up right
        else if (positionPath == 4) {
            //world[room1X][room1Y] = Tileset.FLOOR; //placing tile at the start
            if (wheelChooser == 1) {
                //place tiles going up --> right
                for (int y = room1Y; y <= room2Y; y++) { //up
                    world[room1X][y] = Tileset.GRASS;
                }
                for (int x = room2X; x >= room1X; x--) { //right
                    world[x][room2Y] = Tileset.GRASS;
                }
            } else {
                //place tiles going right --> up
                for(int x = room1X; x <= room2X; x++) { //right
                    world[x][room1Y] = Tileset.GRASS;
                }
                for (int y = room2Y; y >= room1Y; y--) { //up
                    world[room2X][y] = Tileset.GRASS;
                }
            }
            //room 5 straight across to right
        } else if (positionPath == 5) {
            for (int x = room1X; x <= room2X; x++) {
                world[x][room2Y] = Tileset.GRASS;
            }
        }
        //room2 straight down
        else if (positionPath == 6) {
            for (int y = room1Y; y >= room2Y; y--) {
                world[room2X][y] = Tileset.GRASS;
            }
        }
        //room2 straight up
        else if (positionPath == 7) {
            for (int y = room1Y; y <= room2Y; y++) {
                world[room2X][y] = Tileset.GRASS;
            }
        }


        //pick a random coordinate from room1 & room2
        //get room2s X cordinate distance and position from room1
        //figure out rotation

        //left:
        //upper left (0): randomly choose to go up --> left || left --> up
        //lower left (1): randomly choose to go down --> left || left --> down
        //straight across left(2)

        //right:
        //lower right (3): we randomly choose to go down --> right || right --> down
        //upper right (4): we randomly choose to go up --> right || right --> up
        //straight across right (5)

        //down: straight down (6)
        //up: straight up (7)
=======
        return false;
    }*/
>>>>>>> 6e80bf5fb810ef3638277cc127228b3ed04c4774

    //place tile into prevTileType HashMap
    private static void previousTilePlacer(TETile[][] world, int x, int y) {
        List<Integer> pair = new ArrayList<>();
        pair.add(x);
        pair.add(y);
        previousTileType.put(pair, typeOfTile(world, pair.getFirst(), pair.get(1)));
    }

    //returns a list "[[3, 6], [2, 9]]" of chosen random room1 & room2 coordinates to union
    public static ArrayList<List<Integer>> randomRoomCoordinates(List<Integer> bothRooms) {
        //if errors make sure out coordinate is correctly added within the bounds of W + H
        List<Integer> room1Coordinates = Rooms.CORNERS.get(bothRooms.get(0)).getFirst();
        List<Integer> room2Coordinates = Rooms.CORNERS.get(bothRooms.get(1)).getFirst();

        int room1BottomX = room1Coordinates.getFirst();
        int room1BottomY = room1Coordinates.get(1);
        int room2BottomX = room2Coordinates.getFirst();
        int room2BottomY = room2Coordinates.get(1);

        List<Integer> room1Dimensions = Rooms.DIMENSIONS.get(bothRooms.get(0) - 1);
        List<Integer> room2Dimensions = Rooms.DIMENSIONS.get(bothRooms.get(1) - 1);

        int room1Width = room1Dimensions.getFirst();
        int room1Height = room1Dimensions.get(1);
        int room2Width = room2Dimensions.getFirst();
        int room2Height = room2Dimensions.get(1);


        Random random = new Random();
        int randomRoom1Width = random.nextInt(room1Width);
        int RandomRoom1Height = random.nextInt(room1Height);
        int randomRoom2Width = random.nextInt(room2Width);
        int RandomRoom2Height = random.nextInt(room2Height);

        List<Integer> room1RandomCoordinates = new ArrayList<>();
        room1RandomCoordinates.add(room1BottomX + randomRoom1Width);
        room1RandomCoordinates.add(room1BottomY + RandomRoom1Height);

        List<Integer> room2RandomCoordinates = new ArrayList<>();
        room2RandomCoordinates.add(room2BottomX + randomRoom2Width);
        room2RandomCoordinates.add(room2BottomY + RandomRoom2Height);

        ArrayList<List<Integer>> finalRandomRoomCoordinates = new ArrayList<>();
        finalRandomRoomCoordinates.add(room1RandomCoordinates);
        finalRandomRoomCoordinates.add(room2RandomCoordinates);

        return finalRandomRoomCoordinates;
    }

    //helper method for "singleHallwayPlacement()"
    // returns a specific number if the room 2 coordinates are left, right, down, or up of room1
    public static Integer pathTrajectory(List<Integer> room1, List<Integer> room2) {
        int numberDecider = 0;
        //left:
        if (room1.get(0) > room2.get(0)) { //if room2 left
            if (room1.get(1) < room2.get(1)) { //upper left
                numberDecider += 0;
                return numberDecider;
            } else if (room1.get(1) > room2.get(1)) { //lower left
                numberDecider += 1;
                return numberDecider;
            } else if (room1.get(1) == room2.get(1)) { //straight left
                numberDecider += 2;
                return numberDecider;
            }
            //right
        } else if (room1.get(0) < room2.get(0)) { // if room2 right
            if (room1.get(1) > room2.get(1)) { //lower right
                numberDecider += 3;
                return numberDecider;
            } else if (room1.get(1) < room2.get(1)) { //upper right
                numberDecider += 4;
                return numberDecider;
            } else if (room1.get(1) == room2.get(1)) { //straight right
                numberDecider += 5;
                return numberDecider;
            }
        } else if (room1.get(0) == room2.get(0)) { //x's equal
            if (room1.get(1) > room2.get(1)) { //Room2 below
                numberDecider += 6;
                return numberDecider;
            } else if (room1.get(1) < room2.get(1)) { //Room2 above
                numberDecider += 7;
                return numberDecider;
            }
        }
        return numberDecider;
    }
}
