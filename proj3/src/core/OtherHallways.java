package core;

import tileengine.TETile;
import tileengine.Tileset;
import java.util.*;

public class OtherHallways {
    public static HashMap<Integer, ArrayList<List<Integer>>> storedRooms = Rooms.CORNERS;

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
        for (List <Integer> path : unionPaths) {
            singleHallwayPlacement(world,path);
        }
    }

    public static void singleHallwayPlacement(TETile[][] world, List<Integer> bothRooms) {
        boolean hallwaysTouching = true;
        ArrayList<List<Integer>> bothRandomRoomCoordinates = randomRoomCoordinates(bothRooms);
        int room1X = bothRandomRoomCoordinates.get(0).get(0);
        int room1Y = bothRandomRoomCoordinates.get(0).get(1);
        int room2X = bothRandomRoomCoordinates.get(1).get(0);
        int room2Y = bothRandomRoomCoordinates.get(1).get(1);

        int positionPath = pathTrajectory(bothRandomRoomCoordinates.get(0), bothRandomRoomCoordinates.get(1));
        Random random = new Random();
        int wheelChooser = random.nextInt(2) + 1;

        ArrayList<List<Integer>> newHallwayCoordinates = new ArrayList<>();

        if (positionPath == 0) { //upper left
            if (wheelChooser == 1) {
                //place tiles going up --> left
                for (int y = room1Y; y <= room2Y; y++) { //up
                    world[room1X][y] = Tileset.TREE;
                }
                for (int x = room2X; x <= room1X; x++) { //left but from room2
                    world[x][room2Y] = Tileset.TREE;
                }
                //place tiles going left --> up
                for (int x = room2X; x >= room1X ; x--) { //left
                    world[x][room1Y] = Tileset.MOUNTAIN;
                }
                for (int y = room2Y; y <= room1Y ; y--) { //up but from room2
                    world[room2X][y] = Tileset.MOUNTAIN;
                }
            }
        }
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
                return  numberDecider;
            } else if (room1.get(1) < room2.get(1)) { //Room2 above
                numberDecider += 7;
                return  numberDecider;
            }
        }
        return numberDecider;
    }
}
