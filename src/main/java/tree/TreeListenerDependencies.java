package tree;

import graph.MethodCallNodeValue;
import graph.Node;
import services.ServicesGraph;
import services.ServicesSymbolTable;

import java.util.function.Consumer;

public class TreeListenerDependencies implements Consumer<Node> {

    private Integer currentServiceId;

    @Override
    public void accept(Node node) {
        if (node.type == null) {
            return;
        }

        switch (node.type) {
            case API -> currentServiceId = ServicesSymbolTable.getId((String) node.id);
            case METHOD_CALL -> {
                MethodCallNodeValue methodCallNodeValue = (MethodCallNodeValue) node.value;
                String caller = methodCallNodeValue.caller;
                Integer clientServiceId = ServicesSymbolTable.getId(caller.substring(0, 1).toUpperCase() + caller.substring(1) + ":"
                        + methodCallNodeValue.call);
                if (clientServiceId != null) {
                    ServicesGraph.addEdge(currentServiceId, clientServiceId);
                }
            }
        }
    }
}
