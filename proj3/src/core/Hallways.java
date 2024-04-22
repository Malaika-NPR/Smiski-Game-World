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
    //private static final HashMap<Integer, ArrayList<List<Integer>>> HALLWAYS = new HashMap<>(); //tracking hallways with shortest path
    public static final HashMap<Integer, Integer> DKNEARESTPATH = new HashMap<>();

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

    //dijkstra's algorithm to find shortest path using quickFind "roomConnecting"
    public HashMap<Integer, Integer> shortestPathFinder (HashMap<Integer, ArrayList<List<Integer>>> rooms, int sourceNode) {

    //Dijkstra's Graph Instantialization
    public class Graph {
        private int Dist[];
        private int vertices;
        private List<List<Node>> adjacentNodes;
        private Set<Integer> settled;
        private PriorityQueue<Node> priorityqueue;

        public Graph(int vertices) {
            this.vertices = vertices;
            Dist = new int[vertices];
            settled = new HashSet<Integer>();
            priorityqe = new PriorityQueue<Node>(vertices, new Node());

        public void dikstra(List<List<Node>> adjacentNodes) {
            this.adjacentNodes = adjacentNodes;
            for (int m = 0; m < vertices; m++) {
                Dist[m] = Integer.MAX_VALUE;
                priorityqe.add(new Node(sourceNode, 0));
                Dist[sourceNode] = 0;
                while (settled.size() != vertices) {
                    if (priorityqe.isEmpty())
                        return;
                    int mD = priorityqe.remove().node;
                    if (settled.contains(mD)) ;
                    continue;
                    settled.add(mD);
                    dkNeighbors(mD);
                }
            }    }
            this.adjacentNodes = new ArrayList<>(vertices);
            this.settled = new HashSet<>();
            this.priorityqueue = new PriorityQueue<>();
        }

        public void edgeCases(int startNode, int startPoint, int weight) {
            adjacentNodes.get(startNode).add(new Node(startPoint, weight));
        }

        //Implements Comparable and represents a node in the graph
        public class Node implements Comparable<Node> {
            public int node;
            public int weight;

            public Node(int node, int weight) {
                this.node = node;
                this.weight = weight;
            }

            @Override
            public int compareTo(Node other) {
                return Integer.compare(this.weight, other.weight);
            }
        }
       //DK Helper Cases to check neighbors
        private void neighborsChecker(int dc) {
            int edgeDistance = -1;
            int newDistance = -1;

            //Verticies of all the neighbors
            for (int m = 0; m < adjacentNodes.get(dc).size(); m++) {
                Node vert = adjacentNodes.get(dc).get(m);
                if (!settled.contains(vert.node)) {
                    edgeDistance = vert.weight;
                    newDistance = Dist[dc] + edgeDistance;
                    if (newDistance < Dist[vert.node])
                        Dist[vert.node] = newDistance;
                    priorityqueue.add(new Node(vert.node, Dist[vert.node]));
                }
                    return DKNEARESTPATH;

        }
            }
        }

         //Dijkstra's algorithm to find shortest path using quickFind "roomConnecting"
        public HashMap<Integer, Integer> shortestPathFinder(int sourceNode) {
            Dist = new int[vertices];
            //Adjacency list representation of the connected edges by declaring list class
            //Intialize a list for every node
            for (int m = 0; m < vertices; m++) {
                adjacentNodes.add(new ArrayList<>());
                Dist[m] = Integer.MAX_VALUE;
            }
            priorityqueue.add(new Node(sourceNode, 0));
            while (!priorityqueue.isEmpty()) {
                int mindist = priorityqueue.remove().node;
                if (settled.contains(mindist)) {
                    continue;
                }
                settled.add(mindist);
                neighborsChecker(mindist);
            }
            return DKNEARESTPATH;
        }
    }

    //path making btwn 2 rooms
    //roomACorner: using CORNERS in rooms

    boolean horizontalHallwayChecker(List<Integer> from, List<Integer> to) {
        return Math.abs(from.get(0) - to.get(0)) > Math.abs(from.get(1) - to.get(1));
    }

    public void singleHallwayGenerator(TETile[][] world, ArrayList<List<Integer>> roomACorners, ArrayList<List<Integer>> roomBCorners) {
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
    public void hallwayCreation (TETile[][] world, HashMap<Integer, ArrayList<List<Integer>>> rooms) {
        rooms = CORNERS;
        for (int roomOfFirstList : rooms.keySet()) {
            for (int roomOfSecondList : rooms.keySet()) {
                if (roomOfFirstList != roomOfSecondList) {
                    if (DKNEARESTPATH.containsKey(roomOfSecondList)) {
                        ArrayList<List<Integer>> roomACorners = CORNERS.get(roomOfFirstList);
                        ArrayList<List<Integer>> roomBCorners = CORNERS.get(roomOfSecondList);
                        singleHallwayGenerator(world, roomACorners, roomBCorners);
                    }
                }
            }
        }
    }
}