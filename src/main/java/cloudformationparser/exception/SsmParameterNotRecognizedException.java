package cloudformationparser.exception;

public class SsmParameterNotRecognizedException extends Exception {

    public SsmParameterNotRecognizedException(String parameter) {
        super("SSM Parameter not recognized '" + parameter + "'.");
    }
}
