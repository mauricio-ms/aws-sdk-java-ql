import cloudformationparser.CloudFormationSymbolsTable;
import cloudformationparser.CloudFormationTemplateToSymbolsTable;
import cloudformationparser.CloudFormationTreeGeneratorTool;
import graph.*;
import javaparser.CustomJavaParserListener;
import javaparser.JavaLexer;
import javaparser.JavaParser;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.tree.ParseTreeWalker;
import services.*;
import tree.*;

import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.function.Consumer;
import java.util.stream.Stream;

public class Main {

    private static final String SRC = "/src/main/java";

    public static void main(String[] args) throws IOException {
//        for (String projectPath : List.of(
//                "/home/mauricio/development/aws-sdk-java-ql/beatstars/projects_tmp/api-inventory",
//                "/home/mauricio/development/aws-sdk-java-ql/beatstars/projects_tmp/api-album",
//                "/home/mauricio/development/aws-sdk-java-ql/beatstars/projects_tmp/api-contract"
//        )) {
        List<Project> projects = readProjects();
        Node projectTree = new Node(null, null);
        for (Project project : projects) {
//        while (!StdIn.isEmpty()) {
//            String projectPath = StdIn.readString();
            ServiceMetadata.basePackage = null;
            System.out.println(">> Analyzing project " + project.name());
            Node nodeProject = new Node(project.name(), project.type());
            projectTree.addChild(nodeProject);

            for (Path stackFilePath : project.infrastructure().stacksFilesPath()) {
                boolean isStack = stackFilePath.endsWith("stack.yaml");
                var cloudFormationSymbolTable = isStack ?
                        CloudFormationTreeGeneratorTool.parse(stackFilePath.toString()) : stackFilePath.toString();
                Node.Type type = isStack ?
                        Node.Type.CLOUD_FORMATION_STACK_SYMBOLS_TABLE :
                        Node.Type.CLOUD_FORMATION_CLIENT_SYMBOLS_TABLE;
                nodeProject.addChild(new Node(cloudFormationSymbolTable, type));
            }

            String projectFilesPath = project.path() + SRC;
            try (Stream<Path> pathStream = Files.walk(Paths.get(projectFilesPath))) {
                pathStream
                        .filter(Files::isRegularFile)
                        .forEach(path -> parse(nodeProject, path.toString()));
            }
        }

        // TODO - Receive this as input to be possible use in multiple structures of projects
        Node clientTree = new Node(null, null);
        for (Project project : projects) {
            Node nodeClient = new Node(project.name(), Node.Type.CLIENT);
            clientTree.addChild(nodeClient);
            String clientFilesPath = project.path() + "/" + project.name().replaceFirst("api-", "") + "-client" + SRC;
            try (Stream<Path> pathStream = Files.walk(Paths.get(clientFilesPath))) {
                pathStream
                        .filter(Files::isRegularFile)
                        .forEach(path -> parse(nodeClient, path.toString()));
            }
        }

        // get all cloudFormationSymbolsTable that are stack.yaml
        //  populate it in the graph
        for (Node cloudFormationSymbolsTableNode : projectTree.find(Node.Type.CLOUD_FORMATION_STACK_SYMBOLS_TABLE)) {
            CloudFormationSymbolsTable cloudFormationSymbolsTable = (CloudFormationSymbolsTable) cloudFormationSymbolsTableNode.value;
            cloudFormationSymbolsTable.populateGraph("api-" + cloudFormationSymbolsTable.getParameterValue("Service"));
        }

        // then, get all cloudFormationSymbolsTable that are stack.yaml again
        //  for each check if it has a stack associated to it
        //      if it has, so get the cloudFormationSymbolsTable and populate in the graph accordingly
        for (Node cloudFormationSymbolsTableNode : projectTree.find(Node.Type.CLOUD_FORMATION_STACK_SYMBOLS_TABLE)) {
            CloudFormationSymbolsTable cloudFormationSymbolsTable = (CloudFormationSymbolsTable) cloudFormationSymbolsTableNode.value;
            processInnerStacks(projectTree, cloudFormationSymbolsTable);
        }

//        StdOut.println("Tree");
//        tree.show();

        DependenciesSymbolTable.get()
                .forEach((source, targets) -> {
                    Node sourceNode = projectTree.find(source, Node.Type.CLASS);
                    targets.forEach(target -> {
                        Node targetNode = projectTree.find(target, Node.Type.CLASS);
                        if (targetNode == null) {
                            return;
                        }
                        if (sourceNode == null) {
                            throw new RuntimeException("Node not added in the tree: " + source);
                        }

                        Node projectSourceNode = sourceNode.parent;
                        targetNode.addChild(sourceNode);
                        while (targetNode.parent.type == Node.Type.CLASS || targetNode.parent.type == Node.Type.INTERFACE) {
                            targetNode = targetNode.parent;
                        }
                        projectSourceNode.removeChild(sourceNode);
                        projectSourceNode.addChild(targetNode);
                    });
                });

        projectTree.walk(TreeListenableFactory.project(projectTree), Node.Type.LIB);
        clientTree.walk(TreeListenableFactory.client(clientTree, projectTree), Node.Type.LIB);

        projectTree.walk(new TreeListenerDependencies(), Node.Type.LIB);

        StdOut.println("Graph");
        ServicesGraph.show();
        generateHtml();
    }

    private static List<Project> readProjects() throws IOException {
        try (Stream<Path> paths = Files.list(Path.of("/home/mauricio/development/aws-sdk-java-ql/beatstars/reduced_3"))) {
            return paths
                    .map(path -> new Project(path.toString()))
                    .toList();
        }
    }

    private static void generateHtml() throws IOException {
        Path generationDir = Paths.get(".gen");
        if (Files.notExists(generationDir)) {
            Files.createDirectory(generationDir);
        }
        Consumer<String> resourceCopier = filePath -> {
            try {
                Path targetPath = Paths.get(".gen", filePath);
                if (Files.notExists(targetPath)) {
                    Files.copy(Main.class.getClassLoader().getResourceAsStream(filePath), targetPath);
                }
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        };
        resourceCopier.accept("graph-viewer.html");
        resourceCopier.accept("avsdf-base.js");
        resourceCopier.accept("cytoscape.min.js");
        resourceCopier.accept("cytoscape-avsdf.js");
        resourceCopier.accept("layout-base.js");
        resourceCopier.accept("shim.min.js");
        Files.write(Paths.get(".gen", "graph-data.js"), ("const graphData = " + ServicesGraph.toJson() + ";").getBytes());
    }

    private static void processInnerStacks(Node tree, CloudFormationSymbolsTable cloudFormationSymbolsTable) throws IOException {
        for (var cloudFormationStack : cloudFormationSymbolsTable.getStacks()) {
            // find the CloudFormationSymbolsTable for the service
            Node projectNode = tree.find("api-" + cloudFormationStack.getService());
            if (projectNode == null) {
                continue;
            }
            Node clientStackNode = projectNode
                    .findUnique(Node.Type.CLOUD_FORMATION_CLIENT_SYMBOLS_TABLE);

            var parseTree = CloudFormationTreeGeneratorTool.getParseTree((String) clientStackNode.value);
            CloudFormationTemplateToSymbolsTable visitor = new CloudFormationTemplateToSymbolsTable((String) clientStackNode.value, cloudFormationStack.getParameters());
            visitor.visit(parseTree);
            CloudFormationSymbolsTable cloudFormationClientSymbolsTable = visitor.getCloudFormationSymbolsTable();
            cloudFormationSymbolsTable.merge(cloudFormationClientSymbolsTable);
            cloudFormationClientSymbolsTable.populateGraph("api-" + cloudFormationSymbolsTable.getParameterValue("Service"));
            processInnerStacks(tree, cloudFormationClientSymbolsTable);
        }
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
}
