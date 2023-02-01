# Generating the Java Lexer and Parser
antlr4 -Dlanguage=Java JavaLexer.g4
antlr4 -Dlanguage=Java JavaParser.g4

# Generating the Yaml Lexer and Parser
cd src/main/java/cloudformationparser
antlr4 -no-listener -visitor -package cloudformationparser Yaml.g4

# Inspecting the tree
mkdir temp
cp Yaml.g4 temp/
antlr4 Yaml.g4 && javac Yaml*.java && grun Yaml file -gui <yaml-file>

# Compile
mvn package

# Run ServicesGraph Generator
java -cp target/java-ast-generator-1.0-SNAPSHOT-jar-with-dependencies.jar src/main/java/Main.java

# Run the CloudFormationGraph Generator
java -cp target/java-ast-generator-1.0-SNAPSHOT-jar-with-dependencies.jar src/main/java/cloudformationparser/CloudFormationTreeGeneratorTool.java

# Pass projects via pipe
ls -d ~/development/aws-sdk-java-ql/beatstars/projects_tmp/* | <command>

# To Do
[] - If a message is sent to SQS or SNS, but the code is not reachable,
this can be reported but should not be considered in the communication graph
[] - Add value to the edge to identify if the communication is defined in Stack but never used (i.e., edge with value 0)
    If the edge not exists it can be reported that resource was created manually
[] - Check who uses the AsyncClient to use as example to make the system detect it

# Ideas
- Check cycles between messaging endpoints analyzing all the code paths
- From a given resource (for example, a specific HTTP request), answers with the possible paths that the request can follow (another HTTP requests or notification messages via SQS/SNS)
