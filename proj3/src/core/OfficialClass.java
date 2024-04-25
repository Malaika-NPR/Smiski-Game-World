package core;

import tileengine.TETile;
import tileengine.Tileset;

import java.util.*;

public class OfficialClass {
    //keep track of coordinates we place, so if needed we can backtrack and replace that path
    //based on the placement we add it to either a existing vertical or existing horizontal list
    //when placing tiles, we check the particular left or right neighbors depending on horizontal
    //or vertical placement
    //if they end up being hallway specific tiles, we go into the existing tiles
    //replace the tiles one by one to what it was before
        //tile can only be: flower, sand, or floor, check what it is and then update hashmap
    //call random coordinates on same tile and single hallways to hopefully get a good path this time
    //before placing the tile we check what type of tile it is and then add it to the hasmap
    private static final Set<List<Integer>> existingHallways = new HashSet<>();
    private static final Set<List<Integer>> verticalHallways = new HashSet<>();
    private static final Set<List<Integer>> horizontalHallways = new HashSet<>();
    private static final HashMap<List<Integer>, Integer> previousTileType = new HashMap<>();

    //place tile into prevTileType HashMap
    private void previousTilePlacer(TETile[][] world, int x, int y) {
        List<Integer> pair = new ArrayList<>();
        pair.add(x);
        pair.add(y);
        previousTileType.put(pair, typeOfTile(world, pair.getFirst(), pair.get(1)));
    }

    //returns a number for type of tile
    private int typeOfTile(TETile[][] world, int x, int y) {
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
    //used if needed to redo path brings world back to old path
    private void tileReplacer(TETile[][] world, int x, int y) {
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
    private boolean horizontalNeighborChecker(TETile[][] world, int x, int y) {
        return world[x][y + 1] == Tileset.SAND || world[x][y - 1] == Tileset.SAND;
    }
    //used to check adjacent tiles
    private boolean verticalNeighborChecker(TETile[][] world, int x, int y) {
        return world[x - 1][y] == Tileset.SAND || world[x + 2][y] == Tileset.SAND;
    }
}
