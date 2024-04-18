package core;

import java.util.List;
import tileengine.Tileset;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Rooms {
    private static final int MINDIMENSION = 3;
    private static final int MAXDIMENSION = 12;

    public Rooms(int width, int heigth) {

    }

    public List<Integer> randomRoomDimensions() {
        List<Integer> dimensions = new ArrayList<>();
        int roomWidth = randomDimension(MINDIMENSION, MAXDIMENSION);
        int roomHeight = randomDimension(MINDIMENSION, MAXDIMENSION);
        dimensions.add(roomWidth, roomHeight);
        return dimensions;
    }

    public List<Integer> randomRoomCoordinates() {}

    public boolean roomOverlapChecker() {
        //take in a potential rooms coordinates
        //check the surrounding 1 tile neighbors to make sure there isn't a live wall next to it
        //if it doesnt overlap then returns false
        //if it does overlap that it generates another random roomo until we get false
    }

    //returns a random width within bounds
    public int randomDimension(int minDimension, int maxDimension) {
        Random random = new Random();
        return random.nextInt(maxDimension - minDimension) + minDimension;
    }

