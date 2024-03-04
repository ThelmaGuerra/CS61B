import java.util.Arrays;

public class UnionFind {
    /**
     * DO NOT DELETE OR MODIFY THIS, OTHERWISE THE TESTS WILL NOT PASS.
     * You can assume that we are only working with non-negative integers as the items
     * in our disjoint sets.
     */
    private int[] data;

    /* Creates a UnionFind data structure holding N items. Initially, all
       items are in disjoint sets. */
    public UnionFind(int N) {
        // TODO: YOUR CODE HERE
        data = new int[N];
        for(int i = 0; i < N; i++) {
            data[i] = -1;
        }
    }

    /* Returns the size of the set V belongs to. */
    public int sizeOf(int v) {
        // TODO: YOUR CODE HERE
        int root = find(v);
        return - data[root];
    }

    /* Returns the parent of V. If V is the root of a tree, returns the
       negative size of the tree for which V is the root. */
    public int parent(int v) {
        // TODO: YOUR CODE HERE
        return data[v];
    }

    /* Returns true if nodes/vertices V1 and V2 are connected. */
    public boolean connected(int v1, int v2) {
        // TODO: YOUR CODE HERE
        return find(v1) == find(v2);
    }

    /* Returns the root of the set V belongs to. Path-compression is employed
       allowing for fast search-time. If invalid items are passed into this
       function, throw an IllegalArgumentException. */
    public int find(int v) {
        // TODO: YOUR CODE HERE
        if(v < 0) {
            throw new IllegalArgumentException("Argument does not allow negative numbers.");
        }
        if(v >= data.length) {
            throw new IllegalArgumentException("Argument only accepts numbers that are part of the disjoint sets created on the initialization of the UnionFind data structure.");
        }


        if(data[v] >= 0) {
            return data[v] = find(data[v]);
        } else {
            return v;
        }
    }

    /* Connects two items V1 and V2 together by connecting their respective
       sets. V1 and V2 can be any element, and a union-by-size heuristic is
       used. If the sizes of the sets are equal, tie break by connecting V1's
       root to V2's root. Union-ing a item with itself or items that are
       already connected should not change the structure. */
    public void union(int v1, int v2) {
        // TODO: YOUR CODE HERE
        if(v1 < 0 || v2 < 0) {
            throw new IllegalArgumentException("Argument does not allow negative numbers.");
        }

        if(v1 >= data.length || v2 >= data.length) {
            throw new IllegalArgumentException("Argument only accepts numbers that are part of the disjoint sets created on the initialization of the UnionFind data structure.");
        }

        if (v1 == v2 || connected(v1, v2)) return;

        int root1 = find(v1);
        int root2 = find(v2);

        if(data[root1] < data[root2]) {
            // root 1 tem mais elementos, entao a root 2 tem que virar child do root 1.
            // root 1 tambem tem que ser incrementado com o size do root 2.

            data[root1] += data[root2];
            data[root2] = root1;
        } else if (data[root2] <= data[root1]) {
            // root 2 tem mais elementos, entao a root 1 tem que virar child do root 2.
            // root 2 tambem tem que ser incrementado com o size do root 1.

            data[root2] += data[root1];
            data[root1] = root2;
        }
    }

    /**
     * DO NOT DELETE OR MODIFY THIS, OTHERWISE THE TESTS WILL NOT PASS.
     */
    public int[] returnData() {
        return data;
    }

    public static void main(String[] args) {

        // 🚨 FOLLOW THE TRAIL TO SEE PATH COMPRESSION IN ACTION:

        UnionFind uf = new UnionFind(10);
        System.out.println(Arrays.toString(uf.returnData()));


        System.out.println("----------------------");

        uf.union(0, 1);
        System.out.println(Arrays.toString(uf.returnData()));


        uf.union(2, 0);
        System.out.println(Arrays.toString(uf.returnData()));



        uf.union(3, 4);
        System.out.println(Arrays.toString(uf.returnData()));

        uf.union(3, 0);
        System.out.println(Arrays.toString(uf.returnData()));



        uf.union(9, 8);

        uf.union(7, 8);

        System.out.println(Arrays.toString(uf.returnData()));


        uf.union(9, 0);

        System.out.println(Arrays.toString(uf.returnData()));

        uf.union(9, 6);

        System.out.println(Arrays.toString(uf.returnData()));

        uf.union(3, 5);

        System.out.println(Arrays.toString(uf.returnData()));

        System.out.println(uf.connected(7, 9));

        System.out.println(Arrays.toString(uf.returnData()));
    }
}