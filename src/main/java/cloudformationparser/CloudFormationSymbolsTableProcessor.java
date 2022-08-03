package cloudformationparser;

import graph.Graph;
import services.ServicesGraph;
import services.ServicesSymbolTable;

import java.util.Map;

public class CloudFormationSymbolsTableProcessor {

    private final Map<String, CloudFormationTemplateToSymbolsTable.AbstractResource> sqsQueuesTable;

    private final Map<String, CloudFormationTemplateToSymbolsTable.AbstractResource> snsTopicsTable;

    public CloudFormationSymbolsTableProcessor(Map<String, CloudFormationTemplateToSymbolsTable.AbstractResource> sqsQueuesTable, Map<String, CloudFormationTemplateToSymbolsTable.AbstractResource> snsTopicsTable) {
        this.sqsQueuesTable = sqsQueuesTable;
        this.snsTopicsTable = snsTopicsTable;
    }

    public void process() {
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
