package yaml;

public class CustomYamlParserListener extends YamlParserBaseListener {

    @Override
    public void enterKey(YamlParser.KeyContext ctx) {
        System.out.println("enterKey: " + ctx.getText());
    }
}
