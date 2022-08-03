package graph;

import org.json.JSONArray;
import org.json.JSONObject;
import services.ServicesSymbolTable;

public class GraphToJson {

    private final Graph graph;

    public GraphToJson(Graph graph) {
        this.graph = graph;
    }

    public JSONObject build() {
        JSONArray nodes = new JSONArray();
        JSONArray edges = new JSONArray();
        int edgesCounter = 0;
        Integer[] servicesKeys = ServicesSymbolTable.keys();
        for (int i = 0; i < servicesKeys.length; i++) {
            JSONObject node = new JSONObject();
            JSONObject nodeData = new JSONObject();
            Integer serviceKey = servicesKeys[i];
            nodeData.put("id", String.valueOf(serviceKey));
            nodeData.put("name", ServicesSymbolTable.getName(serviceKey));
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
