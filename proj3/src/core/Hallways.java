package core;

import java.util.Random;

//Need to make some adjustments but quickFind will
public class Hallways {
    public class QuickFindUF {
        private int[] id;

   public QuickFind (int size){
       id = new int[size];
       for( int i = 0; i< size; i++)
           id[i]=-1;

       /* Returns true if nodes/vertices P1 and P2 are connected. */
       public boolean roomsConnected(Room p1, Room p2) {
           return find(p1) == find(p2);
       }

       /* Returns the root of the set P point belongs to. Path-compression is employed
          allowing for fast search-time.
        */
       public int find(int p) {
           if (p < 0) {
               throw new IllegalArgumentException();
           }
           if (p >= id.length) {
               throw new IllegalArgumentException();
           }
           int res = p;
           if (id[res] < 0) {
               return res;
           }
           id[res] = find(id[res]);
           return parent(res);
       }

       /* Connects two items P1 and P2 together by connecting their respective
          sets. P1 and P2 can be any element, and a union-by-size heuristic is
          used. If the sizes of the sets are equal, tie break by connecting P1's
          root to P2's root.

        */
       public void union(int p1, int p2) {
           int itemOne = sizeOf(p1);
           int itemTwo = sizeOf(p2);

           if (!connected(p1, p2) && sizeOf(p1) == sizeOf(p2)) {
               id[find(p2)] += id[find(p1)];
               id[find(p1)] = find(p2);
           }
           if (sizeOf(p1) > sizeOf(p2)) {
               id[find(p1)] += id[find(p2)];
               id[find(p2)] = find(p1);
           }
           if (sizeOf(p1) < sizeOf(p2)) {
               id[find(p2)] += id[find(p1)];
               id[find(p1)] = find(p2);
           }
       }
   }

