package services;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public final class ServicesSymbolTable {

    private static Integer counter = 0;

    private static final List<String> names = new ArrayList<>();

    private static final Map<String, Integer> table = new HashMap<>();

    private ServicesSymbolTable() {
    }

    public static Integer getCounter() {
        return counter;
    }

    public static Integer add(String service) {
        if (table.containsKey(service)) {
            return getKey(service);
        }
        table.put(service, counter);
        names.add(service);
        return counter++;
    }

    public static Integer getKey(String service) {
        return table.get(service);
    }

    public static String getName(Integer key) {
        return names.get(key);
    }

    public static Integer[] keys() {
        return table.values().toArray(Integer[]::new);
    }
}
