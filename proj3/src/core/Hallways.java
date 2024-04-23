package core;

import edu.princeton.cs.algs4.Graph;
import edu.princeton.cs.algs4.QuickFindUF;
import edu.princeton.cs.algs4.WeightedQuickUnionUF;
import org.w3c.dom.Node;
import tileengine.TETile;
import tileengine.Tileset;

import java.util.*;

import java.util.*;

public class Hallways extends Rooms {
    private static final int HALLWAYSWIDTH = 1;
    public static final HashMap<Integer, Integer> DKNEARESTPATH = new HashMap<>(); //tracking hallways with shortest paths

    //using quickFind to connect rooms
    public void roomConnecting(HashMap<Integer, ArrayList<List<Integer>>> rooms) {
        WeightedQuickUnionUF weightedUF = new WeightedQuickUnionUF(rooms.size());
        Set<Integer> roomsAsKeys = rooms.keySet();
        Integer[] roomKeysArray = roomsAsKeys.toArray(new Integer[0]);

        for (int f = 0; f < roomKeysArray.length; f++) {
            for (int s = 0; s < roomKeysArray.length; s++) {
                int roomOfFirstList = roomKeysArray[f];
                int roomOfSecondList = roomKeysArray[s];
                if (!weightedUF.connected(roomOfFirstList, roomOfSecondList)) {
                    weightedUF.union(roomOfFirstList, roomOfSecondList); //adding method to check if should connect?
                }
            }
        }

        /* for (int roomOfFirstList : rooms.keySet()) {
            for (int roomOfSecondList : rooms.keySet()) {
                if (roomOfFirstList != roomOfSecondList) {
                    if (!weightedUF.connected(roomOfFirstList, roomOfSecondList)) {
                        weightedUF.union(roomOfFirstList, roomOfSecondList); //adding method to check if should connect?
                    }
                }
            }
        } */
    }
    //Dijkstra's Graph Instantialization
    // Dist[]        --> Reflects series of shortest distances
    // nodes         --> Nodes / Rooms
    //adjacentList   --> Represents graph where each element corresponds to vtx in graph
    //shortestDistance        --> Used to keep track of nodes whose shortest distance has been finalized
    //priority queue --> Efficient retrieval of the element with shortest distance
    public class Graph {
        private int Dist[];
        private int nodes;
        private List<List<Node>> adjacentList;
        private Set<Integer> shortestDistance ;
        private PriorityQueue<Node> priorityqueue;

        public Graph(int nodes) {
            this.nodes = nodes;
            this.adjacentList = new ArrayList<>(nodes);
            this.shortestDistance  = new HashSet<>();
            this.priorityqueue = new PriorityQueue<>();
        }

        //Edges are HallWays
        public void edgeCases(int startNode, int endPoint, int distance) {
            adjacentList.get(startNode).add(new Node(endPoint, distance));
        }

        //Creating the Node Class to use (Rooms)
        public class Node implements Comparable<Node> {
            public int node;
            public int distance;

            public Node(int node, int distance) {
                this.node = node;
                this.distance = distance;
            }
            //In compareTo we compare weights, so for example if 3,4 are there the priority queue would choose either
            //one based off what we want
            @Override
            public int compareTo(Node other) {
                return Integer.compare(this.distance, other.distance);
            }
        }
        //DK Helper Cases to check neighbors
        private void neighborsChecker(int currvetx) {
            int edgeDistance = -1;
            int newDistance = -1;

            //Verticies of all the neighbors
            for (int m = 0; m < adjacentList.get(currvetx).size(); m++) {
                Node vert = adjacentList.get(currvetx).get(m);
                if (!shortestDistance .contains(vert.node)) {
                    edgeDistance = vert.distance;
                    newDistance = Dist[currvetx] + edgeDistance;
                    if (newDistance < Dist[vert.node])
                        Dist[vert.node] = newDistance;
                    priorityqueue.add(new Node(vert.node, Dist[vert.node]));
                }
            }
        }

        //Dijkstra's algorithm to find shortestpath
        //Adjacency list representation of the connected edges by declaring list class
        //Intialize a list for every node
        public HashMap<Integer, Integer> shortestPathFinder(int sourceNode) {
            Dist = new int[nodes];
            for (int m = 0; m < CORNERS.size(); m++) {
                adjacentList.add(new ArrayList<>());
                Dist[m] = Integer.MAX_VALUE;
            }
            priorityqueue.add(new Node(sourceNode, 0));
            while (!priorityqueue.isEmpty()) {
                int mindist = priorityqueue.remove().node;
                if (shortestDistance.contains(mindist)) {
                    continue;
                }
                shortestDistance.add(mindist);
                neighborsChecker(mindist);
            }
            return DKNEARESTPATH;
        }
    }

    //path making btwn 2 rooms
    //roomACorner: using CORNERS in rooms

    public static boolean horizontalHallwayChecker(List<Integer> from, List<Integer> to) {
        return Math.abs(from.get(0) - to.get(0)) > Math.abs(from.get(1) - to.get(1));
    }

    public static void singleHallwayGenerator(TETile[][] world, ArrayList<List<Integer>> roomACorners, ArrayList<List<Integer>> roomBCorners) {
        Random random = new Random();
        int roomARandom = random.nextInt(roomACorners.size());
        int roomBRandom = random.nextInt(roomACorners.size());
        List<Integer> fromCoordinates = roomACorners.get(roomARandom);
        List<Integer> toCoordinates = roomBCorners.get(roomBRandom);
        //individual coordinates of to and from corners
        int positionPeachX = fromCoordinates.get(0);
        int positionPeachY = fromCoordinates.get(1);
        int positionDaisyX = toCoordinates.get(0);
        int positionDaisyY = toCoordinates.get(1);
        //tile placement and updated depending direction field
        while((!horizontalHallwayChecker(fromCoordinates, toCoordinates) && positionPeachY != positionDaisyY) || (horizontalHallwayChecker(fromCoordinates, toCoordinates) && positionPeachX != positionDaisyX)) {
            world[positionPeachX][positionPeachY] = Tileset.FLOOR;
            if (horizontalHallwayChecker(fromCoordinates, toCoordinates)) {
                //figuring out the direction of the next tile
                int pointer = Integer.compare(positionDaisyX,positionPeachX );
                positionPeachX += pointer;
            } else if (!horizontalHallwayChecker(fromCoordinates, toCoordinates)) {
                int pointer = Integer.compare(positionDaisyY,positionPeachY );
                positionPeachY += pointer;
            }
        }
        world[positionDaisyX][positionDaisyY] = Tileset.FLOOR;
    }

    //using shortestPathGenerator to generate hallways
    public static void hallwayCreation (TETile[][] world, HashMap<Integer, ArrayList<List<Integer>>> rooms) {
        for (int roomOfFirstList : rooms.keySet()) {
            for (int roomOfSecondList : rooms.keySet()) {
                if (roomOfFirstList < roomOfSecondList) {
                    if (DKNEARESTPATH.containsKey(roomOfSecondList)) {
                        ArrayList<List<Integer>> roomACorners = rooms.get(roomOfFirstList);
                        ArrayList<List<Integer>> roomBCorners = rooms.get(roomOfSecondList);
                        singleHallwayGenerator(world, roomACorners, roomBCorners);
                    }
                }
            }
        }
//    public boolean roomOverlapChecker(TETile[][] world) {
//            int choosenX = COORDINATES.getLast().get(0);
//            int choosenY = COORDINATES.getLast().get(1);
//            int roomWidth = DIMENSIONS.getLast().get(0);
//            int roomHeight = DIMENSIONS.getLast().get(1);
//            if (!roomOutBoundsChecker(world)) { //room is not out of bounds
//                for (int a = choosenX - 3; a < choosenX + roomWidth + 3; a++) {
//                    for (int b = choosenY - 3; b < choosenY + roomHeight + 3; b++) {
//                        if (world[a][b] == Tileset.FLOOR) {
//                            return true; //overlap
//                        }
//                    }
//                }
//                return false;
//            }
//            return true;
//        }
    }
}