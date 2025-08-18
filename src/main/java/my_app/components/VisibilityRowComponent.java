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

    public VisibilityRowComponent(ObjectProperty<Node> selectedNode) {
        super(5); // spacing

        // ==================== VISIBILITY ROW ====================
        Text visibleText = new Text("Visible");
        ComboBox<String> visibleSelect = new ComboBox<>();
        visibleSelect.getItems().addAll("always", "when");

        VBox visibilityContainer = new VBox(5);
        HBox visibleRow = new HBox(10, visibleText, visibleSelect);
        visibilityContainer.getChildren().add(visibleRow);

        VBox booleanOptionsContainer = new VBox(5);
        visibilityContainer.getChildren().add(booleanOptionsContainer);

        // Atualiza quando o selectedNode muda
        selectedNode.addListener((obsNode, oldNode, node) -> {
            if (node == null)
                return;

            BooleanProperty nodeVisibility = NodeVisibilityManager.getInstance().registerNode(node);
            NodeVisibilityManager.VisibilityConfig cfg = NodeVisibilityManager.getInstance().getConfig(node);

            // valor inicial do combo
            visibleSelect.setValue(cfg.mode != null ? cfg.mode : "always");
            booleanOptionsContainer.getChildren().clear();

            // Função para reconstruir opções booleanas
            Runnable buildBooleanOptions = () -> {
                booleanOptionsContainer.getChildren().clear();
                for (DataStore.DataItem item : DataStore.getInstance().getDataList()) {
                    if ("boolean".equals(item.type)) {
                        ToggleGroup tg = new ToggleGroup();
                        RadioButton rbTrue = new RadioButton(item.name + " is True");
                        RadioButton rbFalse = new RadioButton(item.name + " is False");
                        rbTrue.setToggleGroup(tg);
                        rbFalse.setToggleGroup(tg);

                        // Seleção automática
                        if (cfg.dataName != null && cfg.dataName.equals(item.name)) {
                            if ("True".equals(cfg.expectedValue))
                                tg.selectToggle(rbTrue);
                            else
                                tg.selectToggle(rbFalse);
                        }

                        // Listener de seleção
                        tg.selectedToggleProperty().addListener((obsSel, oldSel, newSel) -> {
                            if (newSel != null) {
                                RadioButton rb = (RadioButton) newSel;
                                cfg.mode = "when";
                                cfg.dataName = item.name;
                                cfg.expectedValue = rb.getText().endsWith("True") ? "True" : "False";
                                nodeVisibility.set(cfg.expectedValue.equals(item.value.get()));
                            }
                        });

                        // Listener de alteração do DataItem
                        item.value.addListener((obsV, oldV, newV) -> {
                            if ("when".equals(cfg.mode) && item.name.equals(cfg.dataName)) {
                                nodeVisibility.set(cfg.expectedValue.equals(newV));
                            }
                        });

                        booleanOptionsContainer.getChildren().addAll(rbTrue, rbFalse);
                    }
                }
            };

            // Listener do combo
            visibleSelect.setOnAction(e -> {
                cfg.mode = visibleSelect.getValue();
                if ("always".equals(cfg.mode)) {
                    nodeVisibility.set(true);
                    booleanOptionsContainer.getChildren().clear();
                    cfg.dataName = null;
                    cfg.expectedValue = null;
                } else if ("when".equals(cfg.mode)) {
                    buildBooleanOptions.run();
                }
            });

            // Inicializa caso "when"
            if ("when".equals(cfg.mode))
                buildBooleanOptions.run();
        });

        getChildren().add(visibilityContainer);
        // ==================== FIM VISIBILITY ROW ====================
    }
}
