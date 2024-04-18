package core;

import tileengine.TETile;
import tileengine.Tileset;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

public class Rooms {
    private static final int MINDIMENSION = 3;
    private static final int MAXDIMENSION = 12;
    private static final int BOARDWIDTH = 80;
    private static final int BOARDHEIGHT = 35;


    public Rooms(int width, int heigth) {
    }

    public ArrayList<List<Integer>> randomRoomDimensions() {
        ArrayList<List<Integer>> dimensions = new ArrayList<>();
        int roomWidth = randomDimension(MINDIMENSION, MAXDIMENSION);
        int roomHeight = randomDimension(MINDIMENSION, MAXDIMENSION);
        List<Integer> pair = new ArrayList<>();
        pair.add(roomWidth);
        pair.add(roomHeight);

        dimensions.add(pair);
        return dimensions;
    }

    public ArrayList<List<Integer>> randomRoomCoordinates(int boardWidth, int boardHeight) {
        boardWidth = BOARDWIDTH - 2;
        boardHeight = BOARDHEIGHT - 2;
        ArrayList<List<Integer>> coordinates = new ArrayList<>();
        Random random = new Random();
        int x = random.nextInt(boardWidth - 3) + 3;
        int y = random.nextInt(boardHeight - 3) + 3;
        List<Integer> pair = new ArrayList<>();

        coordinates.add(pair);
        return coordinates;
    }

    public boolean roomOverlapChecker(HashMap<Integer, Integer> coordinates, HashMap<Integer, Integer> dimensions, TETile world) {
        //take in a potential rooms coordinates
        //check the surrounding 1 tile neighbors to make sure there isn't a live wall next to it
        //if it doesnt overlap then returns false
        //if it does overlap that it generates another random roomo until we get false
        int choosenX = coordinates.get;
        return false;
    }
    //returns a random width within bounds
    public int randomDimension(int minDimension, int maxDimension) {
        Random random = new Random();
        return random.nextInt(maxDimension - minDimension) + minDimension;
    }
}
