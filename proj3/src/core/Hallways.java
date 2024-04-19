package core;
<<<<<<< HEAD

import java.util.Random;


public class Hallways {
    private static final int HALLWAYSWIDTH = 1;

    public class QuickFindUF {
        private int[] id;

        public QuickFindUF(int size) {
            id = new int[size];
            for (int i = 0; i < size; i++) {
                id[i] = i;
            }
        }

        /* Returns the size of the set P belongs to. */
        public int sizeOf(int room) {
            return Math.abs(id[find(room)]);
        }

        /* Returns true if nodes/vertices room1 and room2 are connected. */
        public boolean roomsConnected(int room1, int room2) {
            return find(room1) == find(room2);
        }

        /* Returns the id of the set room belongs to. Path-compression is employed
           allowing for fast search-time.
         */
        public int find(int room) {
            if (room < 0) {
                throw new IllegalArgumentException("Out of bounds dawg");
            }
            if (room >= id.length) {
                throw new IllegalArgumentException("Out of Bounds dawg");
            }
            if (id[room] < 0) {
                return room;
            }
            id[room] = find(id[room]);
            return id[room];
        }

        /* Connects two items room1 and room2 together by connecting their respective
           sets. room1 and room2 can be any element, and a union-by-size heuristic is
           used. If the sizes of the sets are equal, tie break by connecting room1's
           root to room2's root.
         */
        public void union(int room1, int room2) {
            int setOne = sizeOf(room1);
            int setTwo = sizeOf(room2);

            if (!roomsConnected(room1, room2) && setOne == setTwo) {
                id[find(room2)] += id[find(room1)];
                id[find(room1)] = find(room2);
            }
            if (setOne > setTwo) {
                id[find(room1)] += id[find(room2)];
                id[find(room2)] = find(room1);
            }
            if (setOne < setTwo) {
                id[find(room2)] += id[find(room1)];
                id[find(room1)] = find(room2);
            }
        }
    }
}




=======
>>>>>>> 6ad6c8a190b2851d7f51c67af7d51e6788c159f0
