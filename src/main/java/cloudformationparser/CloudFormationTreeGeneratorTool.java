package cloudformationparser;

import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import services.ServicesSymbolTable;

import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

public class CloudFormationTreeGeneratorTool {

    public static void main(String[] args) {
//        while (!StdIn.isEmpty()) {
//            String projectPath = StdIn.readString();
//            parse(projectPath);
//        }
        ServicesSymbolTable.setCurrent("api-audiotag");
        String filePath = "/home/mauricio/development/aws-sdk-java-ql/projects_tmp/api-audiotag/infrastructure/stack.yaml";
        var cloudFormationSymbolsTable = parse(filePath);
        cloudFormationSymbolsTable.populateGraph();

//        JSONObject jsonObject = new GraphToJson(ServicesGraph.get()).build();
//        System.out.println(jsonObject);
    }

    public static CloudFormationSymbolsTable parse(String stackFilePath) {
        try {
            System.out.println("Parsing " + stackFilePath);
            var input = new FileInputStream(stackFilePath);
            var chars = CharStreams.fromStream(input);
            var lexer = new YamlLexer(chars);
            var tokens = new CommonTokenStream(lexer);
            var parser = new YamlParser(tokens);
            parser.setBuildParseTree(true);
            var tree = parser.file().getRuleContext();
            var cloudFormationTemplateToSymbolsTable = new CloudFormationTemplateToSymbolsTable(stackFilePath);
            cloudFormationTemplateToSymbolsTable.visit(tree);
            return cloudFormationTemplateToSymbolsTable.getCloudFormationSymbolsTable();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}