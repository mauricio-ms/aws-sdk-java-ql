package services;

import java.util.*;

public final class ServicesSymbolTable {

    private static Integer counter = 0;

    private static String current;

    private static final List<String> names = new ArrayList<>();

    private static final Map<String, Integer> table = new HashMap<>();

    private ServicesSymbolTable() {
    }

    public static Integer getCounter() {
        return counter;
    }

    public static String current() {
        return current;
    }

    public static void setCurrent(String service) {
        current = service;
        add(service);
    }

    public static Integer add(String service) {
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

    public static void print() {
        System.out.println(
                "==== Names ====\n" + names + "\n\n" +
                        "==== Table ====\n" + table
        );
    }
}
