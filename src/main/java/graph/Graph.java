package graph;

public class Graph {

    private final int v;

    private int e;

    private final Integer[][] adj;

    public Graph(int v) {
        this.v = v;
        adj = new Integer[v][v];
    }

    public int v() {
        return v;
    }

    public int e() {
        return e;
    }

    public void addEdge(int v, int w) {
        addEdge(v, w, 0);
    }

    public void addEdge(int v, int w, int defaultValue) {
        if (adj[v][w] == null) {
            adj[v][w] = defaultValue;
        } else {
            adj[v][w]++;
        }
        e++;
    }

    public Integer[] adj(int v) {
        return adj[v];
    }

    @Override
    public String toString() {
        String s = v + " vertices, " + e + " edges\n";
        for (int i = 0; i < v; i++) {
            s += i + ": ";
            Integer[] w = adj(i);
            for (int j = 0; j < w.length; j++) {
                if (w[j] != null) {
                    s += j + " ";
                }
            }
            s += "\n";
        }
        return s;
    }

    public static void main(String[] args) {
        Graph graph = new Graph(13);
        graph.addEdge(0, 5);
        graph.addEdge(4, 3);
        graph.addEdge(0, 1);
        graph.addEdge(9, 12);
        graph.addEdge(6, 4);
        graph.addEdge(5, 4);
        graph.addEdge(0, 2);
        graph.addEdge(11, 12);
        graph.addEdge(9, 10);
        graph.addEdge(0, 6);
        graph.addEdge(7, 8);
        graph.addEdge(9, 11);
        graph.addEdge(5, 3);
        System.out.println(graph);
    }
}
