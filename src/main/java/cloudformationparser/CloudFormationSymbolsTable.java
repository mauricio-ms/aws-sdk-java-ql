package cloudformationparser;

import cloudformationparser.exception.SsmParameterNotRecognizedException;
import services.ServicesGraph;
import services.ServicesSymbolTable;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Stream;

public class CloudFormationSymbolsTable {

    enum ResourceType {
        CLOUD_FORMATION_STACK("AWS::CloudFormation::Stack"),
        SQS_QUEUE("AWS::SQS::Queue"),
        SNS_TOPIC("AWS::SNS::Topic"),
        SSM_PARAMETER("AWS::SSM::Parameter");

        private final String value;

        ResourceType(String value) {
            this.value = value;
        }

        public static ResourceType get(String value) {
            return Stream.of(values())
                    .filter(v -> v.value.equals(value))
                    .findFirst()
                    .orElse(null);
        }
    }

    public static class AbstractResource {
        private final Map<String, Object> parameters;

        AbstractResource(Map<String, Object> parameters) {
            this.parameters = parameters;
        }

        public Object get(String key) {
            return parameters.get(key);
        }

        public Map<String, Object> parameters() {
            return parameters;
        }

        @Override
        public String toString() {
            return parameters.toString();
        }
    }

    public static class CloudFormationStack {
        private final String service;

        private final String name;

        private final Map<String, Map<String, Object>> parameters;

        CloudFormationStack(Map<String, Object> properties) {
            String templateUrl = (String) properties.get("TemplateURL");
            if (templateUrl == null) {
                throw new RuntimeException("A CloudFormationStack object cannot be built without the 'TemplateURL' parameter");
            }
            String[] templateUrlParts = templateUrl.split("/");
            service = templateUrlParts[templateUrlParts.length - 3];
            name = templateUrlParts[templateUrlParts.length - 1];
            parameters = (Map<String, Map<String, Object>>) properties.get("Parameters");
        }

        public String getService() {
            return service;
        }

        public Map<String, Map<String, Object>> getParameters() {
            return parameters;
        }
    }

    public static class SsmParameter {
        private final String name;

        private final String parameterKey;
        private final String resourceKey;
        private final String resourceAttributeValue;

        SsmParameter(Map<String, Object> properties) throws SsmParameterNotRecognizedException {
            name = (String) properties.get("Name");
            Map.Entry<String, String> value = (Map.Entry<String, String>) properties.get("Value");
            if (value == null) {
                throw new SsmParameterNotRecognizedException(name);
            }
            String[] nameParts = name.split("/");
            parameterKey = nameParts[nameParts.length - 1];
            resourceKey = value.getKey();
            resourceAttributeValue = value.getValue();
        }

        public String getParameterKey() {
            return parameterKey;
        }

        public String getResourceKey() {
            return resourceKey;
        }

        public String getResourceAttributeValue() {
            return resourceAttributeValue;
        }

        @Override
        public String toString() {
            return name + " -> " + resourceKey + "." + resourceAttributeValue;
        }
    }

    private final String name;

    private final Map<String, Map<String, Object>> parametersTable;

    private final Map<String, CloudFormationStack> stacksTable;

    private final Map<String, AbstractResource> sqsQueuesTable;

    private final Map<String, AbstractResource> snsTopicsTable;

    private final Map<String, SsmParameter> ssmParametersTable;

    public CloudFormationSymbolsTable(String name) {
        this.name = name;
        parametersTable = new HashMap<>();
        stacksTable = new HashMap<>();
        sqsQueuesTable = new HashMap<>();
        snsTopicsTable = new HashMap<>();
        ssmParametersTable = new HashMap<>();
    }

    public void addParameter(String key, Map<String, Object> value) {
        if (parametersTable.containsKey(key)) {
            parametersTable.get(key).putAll(value);
        } else {
            parametersTable.put(key, value);
        }
    }

    public boolean containsParameter(String key) {
        return parametersTable.containsKey(key);
    }

    public Map<String, Object> getParameter(String key) {
        return parametersTable.get(key);
    }

    public void addStack(String key, CloudFormationStack cloudFormationStack) {
        stacksTable.put(key, cloudFormationStack);
    }

    public List<CloudFormationStack> getStacks() {
        return stacksTable.values().stream().toList();
    }

    public void addSqsQueue(String key, AbstractResource resource) {
        sqsQueuesTable.put(key, resource);
    }

    public void addSnsTopic(String key, AbstractResource resource) {
        snsTopicsTable.put(key, resource);
    }

    public void addSsmParameter(String key, SsmParameter ssmParameter) {
        ssmParametersTable.put(key, ssmParameter);
    }

    public SsmParameter getSsmParameter(String key) {
        return ssmParametersTable.get(key);
    }

    public Object findSsmParameterValue(SsmParameter ssmParameter) {
        Function<Map<String, AbstractResource>, Object> finder = parameters -> {
            AbstractResource value = parameters.get(ssmParameter.getResourceKey());
            if (value != null) {
                return value.get(ssmParameter.getResourceAttributeValue());
            } else {
                return null;
            }
        };

        return Optional.ofNullable(finder.apply(sqsQueuesTable))
                .orElseGet(() -> finder.apply(snsTopicsTable));
    }

    public void populateGraph() {
        for (var sqsQueueEntry : sqsQueuesTable.entrySet()) {
            ServicesSymbolTable.add((String) sqsQueueEntry.getValue().parameters().get("QueueName"));
        }

        for (var snsTopicEntry : snsTopicsTable.entrySet()) {
            ServicesSymbolTable.add((String) snsTopicEntry.getValue().parameters().get("TopicName"));
        }

        Integer currentServiceKey = ServicesSymbolTable.getKey(ServicesSymbolTable.current());
        for (var sqsQueueEntry : sqsQueuesTable.entrySet()) {
            ServicesGraph.addEdge(currentServiceKey, ServicesSymbolTable.getKey((String) sqsQueueEntry.getValue().parameters().get("QueueName")));
        }

        for (var snsTopicEntry : snsTopicsTable.entrySet()) {
            ServicesGraph.addEdge(currentServiceKey, ServicesSymbolTable.getKey((String) snsTopicEntry.getValue().parameters().get("TopicName")));
        }
    }
}
