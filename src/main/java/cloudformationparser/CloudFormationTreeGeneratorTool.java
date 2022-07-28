package cloudformationparser;

import graph.StdIn;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;

import java.io.FileInputStream;
import java.io.IOException;

public class CloudFormationTreeGeneratorTool {

    public static void main(String[] args) {
//        while (!StdIn.isEmpty()) {
//            String projectPath = StdIn.readString();
//            parse(projectPath + "/infrastructure/stack.yaml");
//        }
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
            var cloudFormationTreeGeneratorVisitor = new CloudFormationTreeGenerator();
            cloudFormationTreeGeneratorVisitor.visit(tree);
            System.out.println(cloudFormationTreeGeneratorVisitor);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}