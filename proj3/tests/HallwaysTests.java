import core.AutograderBuddy;
import core.Hallways;
import core.Rooms;
import edu.princeton.cs.algs4.StdDraw;
import org.junit.jupiter.api.Test;
import tileengine.TERenderer;
import tileengine.TETile;

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
}

