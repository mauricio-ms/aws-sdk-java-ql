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
ls -d ~/development/aws-sdk-java-ql/projects_tmp/* | <command>
