package cloudformationparser;

import graph.GraphToJson;
import graph.StdIn;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import org.json.JSONObject;
import services.ServicesGraph;
import services.ServicesSymbolTable;

import java.io.FileInputStream;
import java.io.IOException;

public class CloudFormationTreeGeneratorTool {

    public static void main(String[] args) {
        while (!StdIn.isEmpty()) {
            String projectPath = StdIn.readString();
            String[] projectParts = projectPath.split("/");
            String project = projectParts[projectParts.length - 1];
            ServicesSymbolTable.setCurrent(project);
            parse(projectPath + "/infrastructure/stack.yaml");
        }
//        ServicesSymbolTable.setCurrent("api-audiotag");
//        String filePath = "/home/mauricio/development/aws-sdk-java-ql/java-ast-generator/src/main/java/cloudformationparser/stack.yaml";
//        parse(filePath);

        JSONObject jsonObject = new GraphToJson(ServicesGraph.get()).build();
        System.out.println(jsonObject);
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
//            System.out.println(cloudFormationTreeGeneratorVisitor);

            new CloudFormationSymbolsTableProcessor(
                    cloudFormationTreeGeneratorVisitor.getSqsQueuesTable(),
                    cloudFormationTreeGeneratorVisitor.getSnsTopicsTable())
                    .process();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}