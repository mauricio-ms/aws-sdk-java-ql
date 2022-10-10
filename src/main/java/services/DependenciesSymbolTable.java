package services;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public final class DependenciesSymbolTable {

    private static final Map<String, List<String>> dependencies = new HashMap<>();

    public static void add(String source, String target) {
        if (dependencies.containsKey(source)) {
            dependencies.get(source).add(target);
        } else {
            List<String> targets = new ArrayList<>();
            targets.add(target);
            dependencies.put(source, targets);
        }
    }

    // TODO MUTABLE DATA BEING RETURNED
    public static Map<String, List<String>> get() {
        return dependencies;
    }
}
