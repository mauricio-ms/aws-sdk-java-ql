package services;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.stream.Stream;

public class Infrastructure {

    private final List<Path> stacksFilesPath;

    public Infrastructure(String projectPath) {
        stacksFilesPath = getStacksFilesPath(projectPath);
    }

    private static List<Path> getStacksFilesPath(String projectPath) {
        Path infrastructurePath = Path.of(projectPath, "infrastructure");
        if (Files.notExists(infrastructurePath)) {
            return List.of();
        }
        try (Stream<Path> stackFilesStream = Files.list(infrastructurePath)) {
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

    public List<Path> getStacksFilesPath() {
        return stacksFilesPath;
    }

    public boolean hasStackYaml() {
        return stacksFilesPath
                .stream()
                .anyMatch(p -> p.endsWith("stack.yaml"));
    }
}
