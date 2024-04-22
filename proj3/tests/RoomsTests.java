import core.Rooms;
import core.SeedHandler;
import org.junit.Test;
import tileengine.TETile;

import java.util.ArrayList;
import java.util.List;

//import static com.google.common.truth.Truth.assertThat;
import static core.Rooms.*;

public class RoomsTests {
    private static final int BOARDWIDTH = 80;
    private static final int BOARDHEIGHT = 35;
    Rooms rooms = new Rooms();
    TETile[][] world = new TETile[BOARDWIDTH][BOARDHEIGHT];

    @Test
    public void randomRoomDimensionTest() {
        rooms.randomRoomDimensions();
        System.out.println(DIMENSIONS);
    }

    @Test
    public void randomRoomCoordiantesTest() {
        rooms.randomRoomCoordinates();
        System.out.println(COORDINATES);
    }

    @Test
    public void roomOverlapCheckerTest() {
        ArrayList<List<Integer>> dimensions = new ArrayList<>();
        ArrayList<List<Integer>> coordinates = new ArrayList<>();

        boolean check = rooms.roomOverlapChecker(world);

        System.out.println(check);
    }

    @Test
    public void placeRandomRoomsTest() {
        placeRandomRooms(world);
    }

    @Test
    public void bottomWallGenerationTest() {
        placeRandomRooms(world);
        wallGeneration(world);
    }
    
    @Test
    public void seedParserTest() {
        String inputSeed = "N123456789S";
        long outputSeed = SeedHandler.seedParser(inputSeed);
        System.out.println(outputSeed);
    }
}
