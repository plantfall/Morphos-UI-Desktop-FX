package my_app.screens.Home;

import java.util.function.Consumer;

import javafx.beans.property.ObjectProperty;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderStroke;
import javafx.scene.layout.BorderStrokeStyle;
import javafx.scene.layout.BorderWidths;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import my_app.components.ButtonComponent;
import my_app.components.CanvaComponent;
import my_app.components.TextComponent;

public class RightSide extends VBox {

    final ObjectProperty<Node> selectedNode;
    private VBox dynamicContainer; // container que será substituído

    public RightSide(ObjectProperty<Node> selectedNode) {
        this.selectedNode = selectedNode;

        setMaxHeight(Double.MAX_VALUE);
        setBackground(new Background(
                new BackgroundFill(Color.web("#4F4646"), CornerRadii.EMPTY, Insets.EMPTY)));

        setPrefWidth(250);
        setMinWidth(250);
        setMaxWidth(250);

        setPadding(new Insets(20));

        Button btnAppearence = new Button("Appearence");
        Button btnLayout = new Button("Layout");

        HBox top = new HBox(btnAppearence, btnLayout);
        getChildren().add(top);

        var aps = new Text("Appearence Settings");
        getChildren().add(aps);

        // ---- Container dinâmico (será trocado conforme o node selecionado) ----
        dynamicContainer = new VBox();
        dynamicContainer.setUserData("dynamic-container");
        getChildren().add(dynamicContainer);

        var spacer = new Region();
        spacer.setPrefHeight(10);
        getChildren().add(spacer);

        setBorder(new Border(
                new BorderStroke(
                        Color.BLACK,
                        BorderStrokeStyle.SOLID,
                        null,
                        new BorderWidths(1))));

        // Atualiza UI quando muda de seleção

        selectedNode.addListener((obs, old, node) -> {
            // limpa controles antigos
            // getChildren().removeIf(n -> n.getUserData() != null &&
            // n.getUserData().equals("bgControls"));

            // ---- Container dinâmico (será trocado conforme o node selecionado) ----
            dynamicContainer.getChildren().clear(); // limpa só o container dinâmico

            if (node instanceof CanvaComponent n) {
                n.renderRightSideContainer(dynamicContainer);
            }

            if (node instanceof ButtonComponent n) {
                n.renderRightSideContainer(dynamicContainer);
            }

            if (node instanceof TextComponent n) {
                n.activeNode(selectedNode);
                n.renderRightSideContainer(dynamicContainer);
            }
        });

    }

    HBox itemRow(String name, String defaultValue, Consumer<String> callback) {
        var text = new Text(name);
        var tf = new TextField(defaultValue);

        tf.textProperty().addListener((obs, old, val) -> {
            callback.accept(val);
        });

        var container = new HBox(text, tf);
        return container;
    }
}
