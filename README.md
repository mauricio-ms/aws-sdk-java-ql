ls -d ~/development/aws-sdk-java-ql/projects_tmp/* | java -cp target/java-ast-generator-1.0-SNAPSHOT-jar-with-dependencies.jar src/main/java/Main.java

# Generating the Java Lexer and Parser
antlr4 -Dlanguage=Java JavaLexer.g4
antlr4 -Dlanguage=Java JavaParser.g4

# Generating the Yaml Lexer and Parser
cd src/main/java/cloudformationparser
antlr4 -no-listener -visitor -package cloudformationparser Yaml.g4