package core;

import java.util.Random;


public class Hallways {
    public class QuickFindUF {
        private int[] id;

        public QuickFindUF(int size) {
            id = new int[size];
            for (int i = 0; i < size; i++) {
                id[i] = i;
            }
        }

        /* Returns the size of the set P belongs to. */
        public int sizeOf(int p) {
            return Math.abs(id[find(p)]);
        }

        /* Returns true if nodes/vertices P1 and P2 are connected. */
        public boolean roomsConnected(int p1, int p2) {
            return find(p1) == find(p2);
        }

        /* Returns the root of the set P point belongs to. Path-compression is employed
           allowing for fast search-time.
         */
        public int find(int p) {
            if (p < 0) {
                throw new IllegalArgumentException("Out of bounds dawg");
            }
            if (p >= id.length) {
                throw new IllegalArgumentException("Out of Bounds dawg");
            }
            if (id[p] < 0) {
                return p;
            }
            id[p] = find(id[p]);
            return id[p];
        }

        /* Connects two items P1 and P2 together by connecting their respective
           sets. P1 and P2 can be any element, and a union-by-size heuristic is
           used. If the sizes of the sets are equal, tie break by connecting P1's
           root to P2's root.
         */
        public void union(int p1, int p2) {
            int setOne = sizeOf(p1);
            int setTwo = sizeOf(p2);

            if (!roomsConnected(p1, p2) && setOne == setTwo) {
                id[find(p2)] += id[find(p1)];
                id[find(p1)] = find(p2);
            }
            if (setOne > setTwo) {
                id[find(p1)] += id[find(p2)];
                id[find(p2)] = find(p1);
            }
            if (setOne < setTwo) {
                id[find(p2)] += id[find(p1)];
                id[find(p1)] = find(p2);
            }
        }
    }
}




