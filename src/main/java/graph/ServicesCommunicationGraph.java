package graph;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public final class ServicesCommunicationGraph {

    private static final Graph graph = new Graph(1_000);

    private static Integer serviceId = 0;

    private static final List<String> services = new ArrayList<>();

    private static final Map<String, Integer> servicesId = new HashMap<>();

    private ServicesCommunicationGraph() {
    }

    public static void addService(String service) {
        servicesId.put(service, serviceId);
        services.add(service);
        serviceId++;
    }

    public static void add(String from, String to) {
        graph.addEdge(getServiceId(from), getServiceId(to));
    }

    private static Integer getServiceId(String service) {
        Integer id = servicesId.get(service);
        if (id != null) {
            return id;
        }

        addService(service);
        return servicesId.get(service);
    }

    public static JSONObject toJson() {
        JSONArray nodes = new JSONArray();
        JSONArray edges = new JSONArray();
        int edgesCounter = 0;
        for (int i = 0; i < serviceId; i++) {
            JSONObject node = new JSONObject();
            JSONObject nodeData = new JSONObject();
            nodeData.put("id", String.valueOf(i));
            nodeData.put("name", services.get(i));
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
        JSONObject elements = new JSONObject();
        elements.put("nodes", nodes);
        elements.put("edges", edges);
        jsonObject.put("elements", elements);
        return jsonObject;
    }

    public static void show() {
        String s = graph.v() + " vertices, " + graph.e() + " edges\n";
        for (int i = 0; i < serviceId; i++) {
            s += services.get(i) + ": ";
            for (int w : graph.adj(i)) {
                s += services.get(w) + " ";
            }
            s += "\n";
        }
        System.out.println(s);
    }
}
