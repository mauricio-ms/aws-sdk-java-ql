package services;

import graph.Node;

public class Project {

    private final String projectPath;

    private final Infrastructure infrastructure;

    public Project(String projectPath) {
        this.projectPath = projectPath;
        infrastructure = new Infrastructure(projectPath);
    }

    public String name() {
        String[] projectParts = projectPath.split("/");
        return projectParts[projectParts.length - 1];
    }

    public String path() {
        return projectPath;
    }

    public boolean isApi() {
        return type() == Node.Type.API;
    }

    public Node.Type type() {
        return infrastructure.hasStackYaml() ? Node.Type.API : Node.Type.LIB;
    }

    public Infrastructure infrastructure() {
        return infrastructure;
    }
}
