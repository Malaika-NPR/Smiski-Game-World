import core.AutograderBuddy;
import core.Hallways;
import core.Rooms;
import core.OtherHallways;
import edu.princeton.cs.algs4.StdDraw;
import org.junit.jupiter.api.Test;
import tileengine.TERenderer;
import tileengine.TETile;

import javax.print.attribute.standard.MediaSize;
import java.util.ArrayList;
import java.util.List;

import static core.OtherHallways.*;

public class HallwaysTests {
    private static final int BOARDWIDTH = 80;
    private static final int BOARDHEIGHT = 35;
    Rooms rooms = new Rooms();
    TETile[][] world = new TETile[BOARDWIDTH][BOARDHEIGHT];

    @Test
    public void basicTest() {
        Rooms.placeRandomRooms(world);
        Hallways hallways = new Hallways();
        Hallways.hallwayCreation(world, Rooms.CORNERS);
    }

    @Test
    public void randomListTest() {
        Rooms.placeRandomRooms(world);
        //System.out.println(Rooms.CORNERS.keySet());
        System.out.println(randomizedSetToList(Rooms.CORNERS.keySet()));
    }

    @Test
    public void randomConnectionTest() {
        Rooms.placeRandomRooms(world);
        System.out.println(randomConnection());
    }

    @Test
    public void pathTrajectoryTest() {
        Rooms.placeRandomRooms(world);
        System.out.println("Dimensions Below");
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
        System.out.println(OtherHallways.pathTrajectory(unionList.get(0), unionList.get(1)));
    }

    @Test
    public void horizontalNeighborCheck() {
        Rooms.placeRandomRooms(world);
        //OtherHallways.horizontalNeighborChecker();
    }
}