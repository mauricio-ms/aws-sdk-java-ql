package graph;

import java.util.List;
import java.util.Map;

public class ConstructorNodeValue {

    private final Map<String, Evaluable> parameters;

    private final List<String> superParameters;

    public ConstructorNodeValue(Map<String, Evaluable> parameters, List<String> superParameters) {
        this.parameters = parameters;
        this.superParameters = superParameters;
    }

    public Evaluable get(String parameterName) {
        return parameters.get(parameterName);
    }

    @Override
    public String toString() {
        return "ConstructorNodeValue{" +
                "parameters=" + parameters +
                ", superParameters=" + superParameters +
                '}';
    }
}