package graph.exception;

public final class SsmParameterNotResolvedException extends Exception {

    private final String parameter;

    public SsmParameterNotResolvedException(String parameter) {
        super(parameter + " not resolved.");
        this.parameter = parameter;
    }

    public String getParameter() {
        return parameter;
    }
}
