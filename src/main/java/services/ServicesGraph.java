package services;

import graph.Graph;
import org.json.JSONArray;
import org.json.JSONObject;

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

    public static void show() {
        String s = graph.v() + " vertices, " + graph.e() + " edges\n";
        for (int i = 0; i < ServicesSymbolTable.getCounter(); i++) {
            s += ServicesSymbolTable.getName(i) + ": ";
            for (int w : graph.adj(i)) {
                s += ServicesSymbolTable.getName(w) + " ";
            }
            s += "\n";
        }
        System.out.println(s);
    }

    public static JSONObject toJson() {
        JSONArray nodes = new JSONArray();
        JSONArray edges = new JSONArray();
        int edgesCounter = 0;
        for (int i = 0; i < ServicesSymbolTable.ids().length; i++) {
            JSONObject node = new JSONObject();
            JSONObject nodeData = new JSONObject();
            nodeData.put("id", String.valueOf(i));
            String serviceName = ServicesSymbolTable.getName(i);
            nodeData.put("name", serviceName);
            nodeData.put("color", ServicesSymbolTable.get(serviceName).getColor());
            node.put("data", nodeData);
            nodes.put(i, node);
            for (int w : graph.adj(i)) {
                JSONObject edge = new JSONObject();
                JSONObject edgeData = new JSONObject();
                edgeData.put("source", String.valueOf(i));
                edgeData.put("target", String.valueOf(w));
                edge.put("data", edgeData);
                edges.put(edgesCounter++, edge);
            }
        }

        JSONObject jsonObject = new JSONObject();
        jsonObject.put("nodes", nodes);
        jsonObject.put("edges", edges);
        return jsonObject;
    }
}
