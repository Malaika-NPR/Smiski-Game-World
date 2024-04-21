package core;

import edu.princeton.cs.algs4.Graph;
import edu.princeton.cs.algs4.QuickFindUF;
import edu.princeton.cs.algs4.WeightedQuickUnionUF;
import org.w3c.dom.Node;

import java.util.*;


public class Hallways {
    private static final int HALLWAYSWIDTH = 1;
    private static final HashMap<Integer, ArrayList<List<Integer>>> HALLWAYS = new HashMap<>(); //tracking hallways with shortest path
    public static final HashMap<Integer, Integer> DKNEARESTPATH = new HashMap<>();

    //using quickFind to connect rooms
    public void roomConnecting(HashMap<Integer, ArrayList<List<Integer>>> rooms) {
        WeightedQuickUnionUF weightedUF = new WeightedQuickUnionUF (rooms.size());

        for (int roomOfFirstList : rooms.keySet()) {
            for (int roomOfSecondList : rooms.keySet()) {
                if (roomOfFirstList != roomOfSecondList) {
                    if (!weightedUF.connected(roomOfFirstList, roomOfSecondList)) {
                        weightedUF.union(roomOfFirstList, roomOfSecondList); //adding method to check if should connect?
                    }
                }
            }
        }
    }

    //dijkstra's algorithm to find shortest path using quickFind "roomConnecting"
    public HashMap<Integer, Integer> shortestPathFinder (HashMap<Integer, ArrayList<List<Integer>>> rooms, int sourceNode) {
        private int Dist[];
        private Set<Integer> settled;
        private PriorityQueue<Node> priorityqe;
        private int vertices;
        List<List<Node>> adjacentNodes;

        public sleepo(int vertices) {
            this.vertices = vertices;
            Dist = new int[vertices];
            settled = new HashSet<Integer>();
            priorityqe = new PriorityQueue<Node>(vertices, new Node());
        }
        public void dikstra(List<List<Node>> adjacentNodes) {
            this.adjacentNodes = adjacentNodes;
            for (int m = 0; m < vertices; m++) {
                Dist[m] = Integer.MAX_VALUE;
                priorityqe.add(new Node(sourceNode,0));
                Dist[sourceNode] = 0;
            while (settled.size() != vertices) {
                if (priorityqe.isEmpty())
                    return ;
                int mD = priorityqe.remove().node;
                if (settled.contains(mD));
                continue;
                settled.add(mD);
                dkNeighbors(mD);

        // Helper Method to process all the neighbors
        private void dkNeighbors(int mD) {
                int edgeDistance = -1;
                int newDistance = -1;
                for (int m = 0; m < adjacentNodes.get(mD).size(); m++){
                    Node nv = adjacentNodes.get(mD).get(m);
                    if (!settled.contains(nv.node)) {

                    }

                }

        }
        return DKNEARESTPATH;
    }

    //path making btwn 2 rooms
    public void generatePaths() {

    }

    public void

    //adding generated path to world
    public void floorToHallways() {

    }

    //using shortestPathGenerator to generate hallways
    public void hallwayCreation (HashMap<Integer, ArrayList<List<Integer>>> rooms, HashMap<Integer, Integer> shortDistance) {
        for (int roomOfFirstList : rooms.keySet()) {
            for (int roomOfSecondList : rooms.keySet()) {
                if (roomOfFirstList != roomOfSecondList) {
                    if (shortestPathFinder(roomOfFirstList, roomOfSecondList)) {
                        if (DKNEARESTPATH.containsKey(roomOfSecondList)) {
                            //method to generate paths
                            //method to add path to hallway map
                        }
                    }
                }
            }
        }
    }
}

