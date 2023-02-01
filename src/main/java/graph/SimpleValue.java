package graph;

public final class SimpleValue extends Evaluable {

    SimpleValue(String id) {
        super(id);
    }

    @Override
    public String evaluate() {
        return id;
    }

    @Override
    public String toString() {
        return "SimpleValue{" +
                "id='" + id + '\'' +
                '}';
    }
}
