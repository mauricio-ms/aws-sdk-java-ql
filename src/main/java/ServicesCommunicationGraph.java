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
