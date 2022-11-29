public class UnionFind {

    /* TODO: Add instance variables here. */
    int[] arr;

    /* Creates a UnionFind data structure holding N items. Initially, all
       items are in disjoint sets. */
    public UnionFind(int N) {
        // TODO: YOUR CODE HERE
        arr = new int[N];
        for (int i = 0; i < N; i++) {
            arr[i]--;
        }
    }

    /* Returns the size of the set V belongs to. */
    public int sizeOf(int v) {
        // TODO: YOUR CODE HERE
        prove(v);
        int root = find(v);
        return -1 * arr[root];
    }

    private void prove(int v) {
        if (v < 0 || v > arr.length) {
            throw new IllegalArgumentException();
        }
    }

    /* Returns the parent of V. If V is the root of a tree, returns the
       negative size of the tree for which V is the root. */
    public int parent(int v) {
        // TODO: YOUR CODE HERE
        prove(v);
        return arr[v];
    }

    /* Returns true if nodes V1 and V2 are connected. */
    public boolean connected(int v1, int v2) {
        // TODO: YOUR CODE HERE
        prove(v1);
        prove(v2);
        return find(v1) == find(v2);
    }

    /* Returns the root of the set V belongs to. Path-compression is employed
       allowing for fast search-time. If invalid items are passed into this
       function, throw an IllegalArgumentException. */
    public int find(int v) {
        // TODO: YOUR CODE HERE
        prove(v);
        int root = v;
        while (parent(root) >= 0) {
            root = parent(root);
        }
        int currParent;
        while (v != root) {
            currParent = parent(v);
            arr[v] = root;
            v = currParent;
        }
        return root;
    }

    /* Connects two items V1 and V2 together by connecting their respective
       sets. V1 and V2 can be any element, and a union-by-size heuristic is
       used. If the sizes of the sets are equal, tie break by connecting V1's
       root to V2's root. Union-ing a item with itself or items that are
       already connected should not change the structure. */
    public void union(int v1, int v2) {
        // TODO: YOUR CODE HERE
        prove(v1);
        prove(v2);
        int root1 = find(v1);
        int root2 = find(v2);
        int size1 = sizeOf(v1);
        int size2 = sizeOf(v2);
        if (root1 == root2) {
            return;
        }
        if (size1 > size2) {
            arr[root1] -= sizeOf(root2);
            arr[root2] = root1;
        } else {
            arr[root2] -= sizeOf(root1);
            arr[root1] = root2;
        }
    }
}
