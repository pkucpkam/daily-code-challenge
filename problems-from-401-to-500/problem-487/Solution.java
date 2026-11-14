class Solution {
    public int[] findRedundantConnection(int[][] edges) {
        int n = edges.length;
        int[] parent = new int[n + 1];
        int[] rank = new int[n + 1];

        for (int i = 1; i <= n; i++) {
            parent[i] = i;
            rank[i] = 1;
        }

        for (int[] edge : edges) {
            int u = edge[0];
            int v = edge[1];

            if (!union(parent, rank, u, v)) {
                return edge; // Cạnh tạo chu trình
            }
        }

        return new int[0];
    }

    private int find(int[] parent, int i) {
        if (parent[i] != i) {
            parent[i] = find(parent, parent[i]); // Nén đường đi (Path compression)
        }
        return parent[i];
    }

    private boolean union(int[] parent, int[] rank, int u, int v) {
        int rootU = find(parent, u);
        int rootV = find(parent, v);

        if (rootU == rootV) {
            return false; // Đã cùng thuộc một tập hợp
        }

        if (rank[rootU] < rank[rootV]) {
            parent[rootU] = rootV;
        } else if (rank[rootU] > rank[rootV]) {
            parent[rootV] = rootU;
        } else {
            parent[rootV] = rootU;
            rank[rootU]++;
        }

        return true;
    }
}
