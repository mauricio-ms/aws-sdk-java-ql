import graph.*;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.tree.ParseTreeWalker;

import javaparser.CustomJavaParserListener;
import javaparser.JavaLexer;
import javaparser.JavaParser;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.stream.Stream;

public class Main {

    private static final String SRC = "/src/main/java";

    public static void main(String[] args) throws IOException {
        Node tree = new Node(null, null);
        while (!StdIn.isEmpty()) {
            String projectPath = StdIn.readString();
            String[] projectParts = projectPath.split("/");
            String project = projectParts[projectParts.length - 1];
            Node nodeProject = new Node(project, Node.Type.PROJECT);
            tree.addChild(nodeProject);
            System.out.println("Analyzing project " + project);
            String projectFilesPath = projectPath + SRC;
            try (Stream<Path> pathStream = Files.walk(Paths.get(projectFilesPath))) {
                pathStream
                        .filter(Files::isRegularFile)
                        .forEach(path -> parse(nodeProject, path.toString()));
            }
        }

//        graph.Node nodeProject = new graph.Node("api-audiotag", graph.Node.Type.PROJECT);
//        tree.addChild(nodeProject);
//        parse(nodeProject, "/home/mauricio/development/aws-sdk-java-ql/projects_tmp/api-audiotag/src/main/java/com/beatstars/audiotag/data/model/Language.java");

        StdOut.println("Tree");
        tree.show();
        tree.walk(new TreeListener());

        System.out.println("graph.Graph");
        ServicesCommunicationGraph.show();
//        System.out.println(graph.ServicesCommunicationGraph.toJson());
    }

    private static void parse(Node nodeProject, String filePath) {
        try {
//            System.out.println("\t" + filePath);
            var input = new FileInputStream(filePath);
            var chars = CharStreams.fromStream(input);
            var lexer = new JavaLexer(chars);
            var tokens = new CommonTokenStream(lexer);
            var parser = new JavaParser(tokens);
            parser.setBuildParseTree(true);
            var tree = parser.compilationUnit().getRuleContext();
            var listener = new CustomJavaParserListener(nodeProject);
            ParseTreeWalker.DEFAULT.walk(listener, tree);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
