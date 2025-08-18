package my_app.components;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.ObjectProperty;
import javafx.scene.Node;
import javafx.scene.control.ComboBox;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import my_app.data.DataStore;
import my_app.data.NodeVisibilityManager;

public class VisibilityRowComponent extends VBox {

    private final ComboBox<String> visibleSelect = new ComboBox<>();
    private final VBox booleanOptionsContainer = new VBox(5);

    public VisibilityRowComponent(ObjectProperty<Node> selectedNode) {
        super(5);

        Text visibleText = new Text("Visible");
        visibleSelect.getItems().addAll("always", "when");
        visibleSelect.setValue("always"); // valor inicial garantido

        HBox visibleRow = new HBox(10, visibleText, visibleSelect);
        getChildren().addAll(visibleRow, booleanOptionsContainer);

        Runnable buildBooleanOptions = () -> {
            booleanOptionsContainer.getChildren().clear();
            Node node = selectedNode.get();
            if (node == null)
                return;

            NodeVisibilityManager.VisibilityConfig cfg = NodeVisibilityManager.getInstance().getConfig(node);
            BooleanProperty nodeVisibility = NodeVisibilityManager.getInstance().registerNode(node);

            for (DataStore.DataItem item : DataStore.getInstance().getDataList()) {
                if (!"boolean".equals(item.type))
                    continue;

                ToggleGroup tg = new ToggleGroup();
                RadioButton rbTrue = new RadioButton(item.name + " is True");
                RadioButton rbFalse = new RadioButton(item.name + " is False");
                rbTrue.setToggleGroup(tg);
                rbFalse.setToggleGroup(tg);

                if (cfg.dataName != null && cfg.dataName.equals(item.name)) {
                    if ("True".equals(cfg.expectedValue))
                        tg.selectToggle(rbTrue);
                    else
                        tg.selectToggle(rbFalse);
                }

                tg.selectedToggleProperty().addListener((obs, oldSel, newSel) -> {
                    if (newSel != null) {
                        RadioButton rb = (RadioButton) newSel;
                        cfg.mode = "when";
                        cfg.dataName = item.name;
                        cfg.expectedValue = rb.getText().endsWith("True") ? "True" : "False";
                        nodeVisibility.set(cfg.expectedValue.equals(item.value.get()));
                    }
                });

                item.value.addListener((obs, oldV, newV) -> {
                    if ("when".equals(cfg.mode) && item.name.equals(cfg.dataName)) {
                        nodeVisibility.set(cfg.expectedValue.equals(newV));
                    }
                });

                booleanOptionsContainer.getChildren().addAll(rbTrue, rbFalse);
            }
        };

        // listener do combo
        visibleSelect.setOnAction(e -> {
            Node node = selectedNode.get();
            if (node == null)
                return;

            NodeVisibilityManager.VisibilityConfig cfg = NodeVisibilityManager.getInstance().getConfig(node);
            BooleanProperty nodeVisibility = NodeVisibilityManager.getInstance().registerNode(node);

            cfg.mode = visibleSelect.getValue();
            if ("always".equals(cfg.mode)) {
                nodeVisibility.set(true);
                booleanOptionsContainer.getChildren().clear();
                cfg.dataName = null;
                cfg.expectedValue = null;
            } else {
                buildBooleanOptions.run();
            }
        });

        // listener do selectedNode
        selectedNode.addListener((obs, oldNode, newNode) -> {
            booleanOptionsContainer.getChildren().clear();
            if (newNode == null)
                return;

            NodeVisibilityManager.VisibilityConfig cfg = NodeVisibilityManager.getInstance().getConfig(newNode);
            visibleSelect.setValue(cfg.mode != null ? cfg.mode : "always");

            if ("when".equals(visibleSelect.getValue()))
                buildBooleanOptions.run();
        });

        Node node = selectedNode.get();
        if (node != null) {
            // registra o node
            NodeVisibilityManager.getInstance().registerNode(node);

            // agora pega a config, que nunca será null
            NodeVisibilityManager.VisibilityConfig cfg = NodeVisibilityManager.getInstance().getConfig(node);

            visibleSelect.setValue(cfg.mode != null ? cfg.mode : "always");
            if ("when".equals(visibleSelect.getValue()))
                buildBooleanOptions.run();
        }

    }
}
