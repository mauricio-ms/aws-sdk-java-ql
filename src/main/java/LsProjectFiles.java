import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.stream.Stream;

public class LsProjectFiles {

    private static final String SRC = "/src/main/java";

    public static void main(String[] args) throws IOException {
        while (!StdIn.isEmpty()) {
            String projectPath = StdIn.readString();
            StdOut.println(projectPath);
            String projectFilesPath = projectPath + SRC;
            try (Stream<Path> pathStream = Files.walk(Paths.get(projectFilesPath))) {
                pathStream
                        .filter(Files::isRegularFile)
                        .forEach(StdOut::println);
            }
        }
    }
}
