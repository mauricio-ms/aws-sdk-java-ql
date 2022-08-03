package cloudformationparser;

import graph.Graph;
import services.ServicesSymbolTable;

import java.util.Map;

public class CloudFormationGraphGenerator {

    private final Map<String, CloudFormationTemplateToSymbolsTable.AbstractResource> sqsQueuesTable;

    private final Map<String, CloudFormationTemplateToSymbolsTable.AbstractResource> snsTopicsTable;

    public CloudFormationGraphGenerator(Map<String, CloudFormationTemplateToSymbolsTable.AbstractResource> sqsQueuesTable, Map<String, CloudFormationTemplateToSymbolsTable.AbstractResource> snsTopicsTable) {
        this.sqsQueuesTable = sqsQueuesTable;
        this.snsTopicsTable = snsTopicsTable;
    }

    public Graph generate() {
        Graph graph = new Graph(1_000);
        Integer currentServiceKey = ServicesSymbolTable.getKey(ServicesSymbolTable.current());
        for (var sqsQueueEntry : sqsQueuesTable.entrySet()) {
            graph.addEdge(currentServiceKey, ServicesSymbolTable.getKey((String) sqsQueueEntry.getValue().parameters().get("QueueName")));
        }

        for (var snsTopicEntry : snsTopicsTable.entrySet()) {
            graph.addEdge(currentServiceKey, ServicesSymbolTable.getKey((String) snsTopicEntry.getValue().parameters().get("TopicName")));
        }

        return graph;
    }
}
