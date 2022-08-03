package services;

import graph.Graph;

public final class ServicesGraph {

    private static final Graph graph = new Graph(1_000);

    private ServicesGraph() {
    }

    public static void addEdge(int sourceServiceKey, int targetServiceKey) {
        graph.addEdge(sourceServiceKey, targetServiceKey);
    }

    public static Graph get() {
        return graph;
    }
}
