package services;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

public final class ServicesSymbolTable {

    private static Integer counter = 0;

    private static final List<String> resourceNames = new ArrayList<>();

    private static final Map<String, Resource> table = new HashMap<>();

    public static class Resource {

        private final Integer id;

        private final String color;

        public static Resource service(Integer id) {
            return new Resource(id, "#b3b3b3");
        }

        public static Resource client(Integer id) {
            return new Resource(id, "#6699ff");
        }

        public static Resource snsTopic(Integer id) {
            return new Resource(id, "#ffa366");
        }

        public static Resource sqsQueue(Integer id) {
            return new Resource(id, "#b3ff66");
        }

        public static Resource nonResolved(Integer id) {
            return new Resource(id, "#ff6666");
        }

        private Resource(Integer id, String color) {
            this.id = id;
            this.color = color;
        }

        public String getColor() {
            return color;
        }
    }

    private ServicesSymbolTable() {
    }

    public static Integer getCounter() {
        return counter;
    }

    public static Integer add(String resourceName, Function<Integer, Resource> resourceCreator) {
        if (table.containsKey(resourceName)) {
            return getId(resourceName);
        }
        table.put(resourceName, resourceCreator.apply(counter));
        resourceNames.add(resourceName);
        return counter++;
    }

    public static Resource get(String resourceName) {
        return table.get(resourceName);
    }

    public static Integer getId(String resourceName) {
        Resource resource = table.get(resourceName);
        return resource != null ? resource.id : null;
    }

    public static String getName(Integer id) {
        return resourceNames.get(id);
    }

    public static Integer[] ids() {
        return table
                .values()
                .stream()
                .map(service -> service.id)
                .toArray(Integer[]::new);
    }
}
