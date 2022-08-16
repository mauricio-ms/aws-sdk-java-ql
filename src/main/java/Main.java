import cloudformationparser.*;
import graph.*;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.tree.ParseTreeWalker;

import javaparser.CustomJavaParserListener;
import javaparser.JavaLexer;
import javaparser.JavaParser;
import services.ServicesGraph;
import services.ServicesSymbolTable;

import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Stream;

public class Main {

    private static final String SRC = "/src/main/java";

    public static void main(String[] args) throws IOException {
        Node tree = new Node(null, null);
        while (!StdIn.isEmpty()) {
//        for (String projectPath : List.of(
//                "/home/mauricio/development/aws-sdk-java-ql/projects_tmp/api-audio",
//                "/home/mauricio/development/aws-sdk-java-ql/projects_tmp/api-track"
//        )) {
            String projectPath = StdIn.readString();
            String[] projectParts = projectPath.split("/");
            String project = projectParts[projectParts.length - 1];
            ServicesSymbolTable.setCurrent(project);
            Node nodeProject = new Node(project, Node.Type.PROJECT);
            tree.addChild(nodeProject);

            for (Path stackFilePath : getStacksFilesPath(projectPath)) {
                boolean isStack = stackFilePath.endsWith("stack.yaml");
                var cloudFormationSymbolTable = isStack ?
                        CloudFormationTreeGeneratorTool.parse(stackFilePath.toString()) : stackFilePath.toString();
                Node.Type type = isStack ?
                        Node.Type.CLOUD_FORMATION_STACK_SYMBOLS_TABLE :
                        Node.Type.CLOUD_FORMATION_CLIENT_SYMBOLS_TABLE;
                nodeProject.addChild(new Node(cloudFormationSymbolTable, type));
            }

            System.out.println("Analyzing project " + project);
            String projectFilesPath = projectPath + SRC;
            try (Stream<Path> pathStream = Files.walk(Paths.get(projectFilesPath))) {
                pathStream
                        .filter(Files::isRegularFile)
                        .forEach(path -> parse(nodeProject, path.toString()));
            }
        }

        // get all cloudFormationSymbolsTable that are stack.yaml
        //  populate it in the graph
        for (Node cloudFormationSymbolsTableNode : tree.find(Node.Type.CLOUD_FORMATION_STACK_SYMBOLS_TABLE)) {
            CloudFormationSymbolsTable cloudFormationSymbolsTable = (CloudFormationSymbolsTable) cloudFormationSymbolsTableNode.value;
            cloudFormationSymbolsTable.populateGraph();
        }

        // then, get all cloudFormationSymbolsTable that are stack.yaml again
        //  for each check if it has a stack associated to it
        //      if it has, so get the cloudFormationSymbolsTable and populate in the graph accordingly
        for (Node cloudFormationSymbolsTableNode : tree.find(Node.Type.CLOUD_FORMATION_STACK_SYMBOLS_TABLE)) {
            CloudFormationSymbolsTable cloudFormationSymbolsTable = (CloudFormationSymbolsTable) cloudFormationSymbolsTableNode.value;
            for (var cloudFormationStack : cloudFormationSymbolsTable.getStacks()) {
                System.out.println(cloudFormationStack.getService());
                System.out.println(cloudFormationStack.getParameters());
                // find the CloudFormationSymbolsTable for the service
                Node projectNode = tree.find("api-" + cloudFormationStack.getService(), Node.Type.PROJECT);
                if (projectNode == null) {
                    continue;
                }
                Node clientStackNode = projectNode
                        .findUnique(Node.Type.CLOUD_FORMATION_CLIENT_SYMBOLS_TABLE);

                var input = new FileInputStream((String) clientStackNode.value);
                var chars = CharStreams.fromStream(input);
                var lexer = new YamlLexer(chars);
                var tokens = new CommonTokenStream(lexer);
                var parser = new YamlParser(tokens);
                parser.setBuildParseTree(true);
                var parseTree = parser.file().getRuleContext();

                CloudFormationTemplateToSymbolsTable cloudFormationTemplateToSymbolsTable = new CloudFormationTemplateToSymbolsTable("", cloudFormationStack.getParameters());
                cloudFormationTemplateToSymbolsTable.visit(parseTree);
                cloudFormationTemplateToSymbolsTable.getCloudFormationSymbolsTable().populateGraph();
            }
        }

//        graph.Node nodeProject = new graph.Node("api-audiotag", graph.Node.Type.PROJECT);
//        tree.addChild(nodeProject);
//        parse(nodeProject, "/home/mauricio/development/aws-sdk-java-ql/projects_tmp/api-audiotag/src/main/java/com/beatstars/audiotag/data/model/Language.java");

        StdOut.println("Tree");
        tree.show();
        tree.walk(new TreeListener(tree));

        System.out.println("graph.Graph");
        ServicesGraph.show();
        System.out.println(ServicesGraph.toJson());
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

    private static List<Path> getStacksFilesPath(String projectPath) {
        try (Stream<Path> stackFilesStream = Files.list(Path.of(projectPath, "infrastructure"))) {
            List<Path> stackFilesPath = stackFilesStream
                    .filter(p -> !p.getFileName().toString().startsWith(".") && p.getFileName().toString().endsWith(".yaml"))
                    .toList();
            if (stackFilesPath.isEmpty()) {
                throw new RuntimeException("Stack file not found for project '" + projectPath + "'.");
            } else if (stackFilesPath.size() > 2) {
                throw new RuntimeException("infrastructure folder with unexpected content for project '" + projectPath + "'.");
            }
            return stackFilesPath;
        } catch (IOException e) {
            throw new RuntimeException("Error reading the infrastructure folder for project" + projectPath + "': ", e);
        }
    }
}
