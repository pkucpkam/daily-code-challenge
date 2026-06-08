import java.util.*;

class Solution {
    class Node {
        String parent;
        double weight;

        Node(String parent, double weight) {
            this.parent = parent;
            this.weight = weight;
        }
    }

    private Map<String, Node> parentMap;

    public double[] calcEquation(List<List<String>> equations, double[] values, List<List<String>> queries) {
        parentMap = new HashMap<>();

        // 1. Build Union-Find
        for (int i = 0; i < equations.size(); i++) {
            String u = equations.get(i).get(0);
            String v = equations.get(i).get(1);
            union(u, v, values[i]);
        }

        // 2. Process queries
        double[] result = new double[queries.size()];
        for (int i = 0; i < queries.size(); i++) {
            String c = queries.get(i).get(0);
            String d = queries.get(i).get(1);

            if (!parentMap.containsKey(c) || !parentMap.containsKey(d)) {
                result[i] = -1.0;
            } else {
                Node nodeC = find(c);
                Node nodeD = find(d);

                if (!nodeC.parent.equals(nodeD.parent)) {
                    result[i] = -1.0;
                } else {
                    // c / rootC = weightC, d / rootD = weightD
                    // Since rootC == rootD, c / d = weightC / weightD
                    result[i] = nodeC.weight / nodeD.weight;
                }
            }
        }

        return result;
    }

    private Node find(String nodeId) {
        if (!parentMap.containsKey(nodeId)) {
            parentMap.put(nodeId, new Node(nodeId, 1.0));
        }

        Node node = parentMap.get(nodeId);
        if (!node.parent.equals(nodeId)) {
            Node root = find(node.parent);
            // Path Compression
            node.parent = root.parent;
            node.weight = node.weight * root.weight;
        }
        return node;
    }

    private void union(String u, String v, double value) {
        Node nodeU = find(u);
        Node nodeV = find(v);

        String rootU = nodeU.parent;
        String rootV = nodeV.parent;

        if (!rootU.equals(rootV)) {
            Node rootNodeU = parentMap.get(rootU);
            rootNodeU.parent = rootV;
            // Calculate new weight for rootU -> rootV link
            rootNodeU.weight = value * nodeV.weight / nodeU.weight;
        }
    }
}