package cloudformationparser;

import graph.Graph;
import graph.GraphToJson;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import org.json.JSONObject;
import services.ServicesSymbolTable;

import java.io.FileInputStream;
import java.io.IOException;

public class CloudFormationTreeGeneratorTool {

    public static void main(String[] args) {
//        while (!StdIn.isEmpty()) {
//            String projectPath = StdIn.readString();
//            parse(projectPath + "/infrastructure/stack.yaml");
//        }
        ServicesSymbolTable.setCurrent("api-audio");
        String filePath = "/home/mauricio/development/aws-sdk-java-ql/java-ast-generator/src/main/java/cloudformationparser/stack.yaml";
        parse(filePath);
    }

    private static void parse(String filePath) {
        try {
            System.out.println("Parsing " + filePath);
            var input = new FileInputStream(filePath);
            var chars = CharStreams.fromStream(input);
            var lexer = new YamlLexer(chars);
            var tokens = new CommonTokenStream(lexer);
            var parser = new YamlParser(tokens);
            parser.setBuildParseTree(true);
            var tree = parser.file().getRuleContext();
            var cloudFormationTreeGeneratorVisitor = new CloudFormationTemplateToSymbolsTable();
            cloudFormationTreeGeneratorVisitor.visit(tree);
            System.out.println(cloudFormationTreeGeneratorVisitor);

            for (var sqsQueueEntry : cloudFormationTreeGeneratorVisitor.getSqsQueuesTable().entrySet()) {
                ServicesSymbolTable.add((String) sqsQueueEntry.getValue().parameters().get("QueueName"));
            }

            for (var snsTopicEntry : cloudFormationTreeGeneratorVisitor.getSnsTopicsTable().entrySet()) {
                ServicesSymbolTable.add((String) snsTopicEntry.getValue().parameters().get("TopicName"));
            }

            ServicesSymbolTable.print();

            Graph cloudFormationGraph = new CloudFormationGraphGenerator(
                    cloudFormationTreeGeneratorVisitor.getSqsQueuesTable(),
                    cloudFormationTreeGeneratorVisitor.getSnsTopicsTable())
                    .generate();
            JSONObject jsonObject = new GraphToJson(cloudFormationGraph).build();
            System.out.println(jsonObject);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}