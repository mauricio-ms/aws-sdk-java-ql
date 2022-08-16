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

import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
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
        for (String projectPath : List.of(
//                "/home/mauricio/development/aws-sdk-java-ql/projects_tmp/api-wallet",
//                "/home/mauricio/development/aws-sdk-java-ql/projects_tmp/api-distribution",
                "/home/mauricio/development/aws-sdk-java-ql/projects_tmp/api-track",
                "/home/mauricio/development/aws-sdk-java-ql/projects_tmp/api-inventory"
        )) {
//        while (!StdIn.isEmpty()) {
//            String projectPath = StdIn.readString();
            String[] projectParts = projectPath.split("/");
            String project = projectParts[projectParts.length - 1];
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
            cloudFormationSymbolsTable.populateGraph("api-" + cloudFormationSymbolsTable.getParameterValue("Service"));
        }

        // then, get all cloudFormationSymbolsTable that are stack.yaml again
        //  for each check if it has a stack associated to it
        //      if it has, so get the cloudFormationSymbolsTable and populate in the graph accordingly
        for (Node cloudFormationSymbolsTableNode : tree.find(Node.Type.CLOUD_FORMATION_STACK_SYMBOLS_TABLE)) {
            CloudFormationSymbolsTable cloudFormationSymbolsTable = (CloudFormationSymbolsTable) cloudFormationSymbolsTableNode.value;
            for (var cloudFormationStack : cloudFormationSymbolsTable.getStacks()) {
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

                CloudFormationTemplateToSymbolsTable cloudFormationTemplateToSymbolsTable = new CloudFormationTemplateToSymbolsTable((String) clientStackNode.value, cloudFormationStack.getParameters());
                cloudFormationTemplateToSymbolsTable.visit(parseTree);
                cloudFormationTemplateToSymbolsTable.getCloudFormationSymbolsTable().populateGraph("api-" + cloudFormationSymbolsTable.getParameterValue("Service"));
            }
        }

        StdOut.println("Tree");
        tree.show();
        tree.walk(new TreeListener(tree));

        StdOut.println("Graph");
        ServicesGraph.show();
        Files.write(Paths.get(Main.class.getClassLoader().getResource("graph-data.js").getPath()),
                ("const graphData = " + ServicesGraph.toJson() + ";").getBytes());
    }

    private static void parse(Node nodeProject, String filePath) {
        try {
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
