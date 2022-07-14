package graph;

public class MethodCallNodeValue {

    public final String caller;

    public final String call;

    public MethodCallNodeValue(String caller, String call) {
        this.caller = caller;
        this.call = call;
    }

    @Override
    public String toString() {
        return caller + "." + call;
    }
}
