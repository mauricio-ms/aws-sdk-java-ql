package cloudformationparser;

import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;

import java.io.FileInputStream;
import java.io.IOException;

public class CloudFormationTreeGeneratorTool {

    public static void main(String[] args) {
        try {
//            String filePath = "/home/mauricio/development/aws-sdk-java-ql/projects_tmp/api-audiotag/infrastructure/stack.yaml";
            String filePath = "/home/mauricio/development/aws-sdk-java-ql/java-ast-generator/src/main/java/cloudformationparser/stack.yaml";
            var input = new FileInputStream(filePath);
            var chars = CharStreams.fromStream(input);
            var lexer = new YamlLexer(chars);
            var tokens = new CommonTokenStream(lexer);
            var parser = new YamlParser(tokens);
            parser.setBuildParseTree(true);
            var tree = parser.file().getRuleContext();
//            var cloudFormationTreeGenerator = new CloudFormationTreeGenerator(new Node("api-audiotag", Node.Type.PROJECT));
            var cloudFormationTreeGeneratorVisitor = new CloudFormationTreeGenerator();
            cloudFormationTreeGeneratorVisitor.visit(tree);
//            ParseTreeWalker.DEFAULT.walk(cloudFormationTreeGeneratorVisitor, tree);
//            System.out.println(cloudFormationTreeGenerator);
            System.out.println(cloudFormationTreeGeneratorVisitor);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}