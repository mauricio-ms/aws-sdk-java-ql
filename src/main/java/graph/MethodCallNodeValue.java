package graph;

import java.util.Objects;

public class MethodCallNodeValue {

    public final String caller;

    public final String call;

    public MethodCallNodeValue(String caller, String call) {
        this.caller = caller;
        this.call = call;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        MethodCallNodeValue that = (MethodCallNodeValue) o;

        if (!Objects.equals(caller, that.caller)) return false;
        return Objects.equals(call, that.call);
    }

    @Override
    public int hashCode() {
        int result = caller != null ? caller.hashCode() : 0;
        result = 31 * result + (call != null ? call.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return caller + "." + call;
    }
}
