import software.amazon.awssdk.services.ssm.SsmClient;
import software.amazon.awssdk.services.ssm.model.GetParameterRequest;
import software.amazon.awssdk.services.ssm.model.SsmException;

public final class GetAwsParameter {

    private GetAwsParameter() {
    }

    public static String get(String name) {
        try (SsmClient ssmClient = SsmClient.builder().build()) {
            try {
                GetParameterRequest getRequest = GetParameterRequest.builder()
                        .name(name)
                        .build();
                return ssmClient.getParameter(getRequest).parameter().value();
            } catch (SsmException e) {
                throw new RuntimeException("Error getting the aws parameter '" + name + "': ", e);
            }
        }
    }
}
