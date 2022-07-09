import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.tree.ParseTreeWalker;

import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.stream.Stream;

public class Main {

    private static final String SRC = "/src/main/java";

    public static void main(String[] args) throws IOException {
        while (!StdIn.isEmpty()) {
            String projectPath = StdIn.readString();
            String[] projectParts = projectPath.split("/");
            String project = projectParts[projectParts.length - 1];
            System.out.println("Analyzing project " + project);
            String projectFilesPath = projectPath + SRC;
            try (Stream<Path> pathStream = Files.walk(Paths.get(projectFilesPath))) {
                pathStream
                        .filter(Files::isRegularFile)
                        .forEach(path -> parse(project, path.toString()));
            }
        }
//        parse("api-contract", "/home/mauricio/development/beatstars/api-contract/src/main/java/com/beatstars/contract/service/ContractAttachmentServiceImpl.java");
        ServicesCommunicationGraph.show();
    }

    private static void parse(String project, String filePath) {
        try {
            System.out.println("\t" + filePath);
            var input = new FileInputStream(filePath);
            var chars = CharStreams.fromStream(input);
            var lexer = new JavaLexer(chars);
            var tokens = new CommonTokenStream(lexer);
            var parser = new JavaParser(tokens);
            parser.setBuildParseTree(true);
            var tree = parser.compilationUnit().getRuleContext();
            var listener = new CustomJavaParserListener(project);
            ParseTreeWalker.DEFAULT.walk(listener, tree);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
