package my_app.data;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.scene.Node;
import java.util.HashMap;
import java.util.Map;

public class NodeVisibilityManager {

    public static class VisibilityConfig {
        public String mode = "always"; // "always" ou "when"
        public String dataName = null; // se "when", qual DataItem controla
        public String expectedValue = null; // "True" ou "False"
    }

    private final Map<Node, BooleanProperty> visibilityMap = new HashMap<>();
    private final Map<Node, VisibilityConfig> configMap = new HashMap<>();

    private static final NodeVisibilityManager INSTANCE = new NodeVisibilityManager();

    public static NodeVisibilityManager getInstance() {
        return INSTANCE;
    }

    public BooleanProperty registerNode(Node node) {
        BooleanProperty prop = new SimpleBooleanProperty(true);
        prop.addListener((obs, oldV, newV) -> {
            node.setVisible(newV);
            node.setManaged(newV);
        });
        visibilityMap.put(node, prop);
        configMap.putIfAbsent(node, new VisibilityConfig());
        return prop;
    }

    public VisibilityConfig getConfig(Node node) {
        return configMap.get(node);
    }

    public BooleanProperty getVisibilityProperty(Node node) {
        return visibilityMap.get(node);
    }
}
