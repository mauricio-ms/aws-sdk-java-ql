package yamlparser;

import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.tree.ParseTreeWalker;

import java.io.FileInputStream;
import java.io.IOException;

public class YamlMain {

    public static void main(String[] args) {
        try {
            String filePath = "/home/mauricio/development/aws-sdk-java-ql/projects_tmp/api-audiotag/infrastructure/stack.yaml";
            var input = new FileInputStream(filePath);
            var chars = CharStreams.fromStream(input);
            var lexer = new YamlLexer(chars);
            var tokens = new CommonTokenStream(lexer);
            var parser = new YamlParser(tokens);
            parser.setBuildParseTree(true);
            var tree = parser.file().getRuleContext();
            var listener = new CustomYamlParserListener();
            ParseTreeWalker.DEFAULT.walk(listener, tree);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}