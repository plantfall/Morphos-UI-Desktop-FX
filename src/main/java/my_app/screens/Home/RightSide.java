package my_app.screens.Home;

import java.util.function.Consumer;

import javafx.beans.binding.Bindings;
import javafx.beans.binding.ObjectBinding;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleBooleanProperty;
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
import my_app.components.ImageComponent;
import my_app.components.TextComponent;

public class RightSide extends VBox {

    final double width = 250;
    final ObjectProperty<Node> selectedNode;
    private VBox dynamicContainer; // container que será substituído

    BooleanProperty appearenceIsSelected = new SimpleBooleanProperty(true);

    public RightSide(ObjectProperty<Node> selectedNode) {
        this.selectedNode = selectedNode;

        setMaxHeight(Double.MAX_VALUE);
        setBackground(new Background(
                new BackgroundFill(Color.web("#4F4646"), CornerRadii.EMPTY, Insets.EMPTY)));

        setPrefWidth(width);
        setMinWidth(width);
        setMaxWidth(width);

        setPadding(new Insets(20));

        Button btnAppearence = new Button("Appearence");
        Button btnLayout = new Button("Layout");

        btnAppearence.setOnAction(ev -> appearenceIsSelected.set(true));
        btnLayout.setOnAction(ev -> appearenceIsSelected.set(false));

        HBox top = new HBox(btnAppearence, btnLayout);
        getChildren().add(top);

        var aps = new Text();
        aps.textProperty().bind(Bindings
                .createStringBinding(() -> appearenceIsSelected.get() ? "Appearence Settings" : "Layout Settings",
                        appearenceIsSelected));

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

            if (node instanceof ImageComponent n) {
                n.renderRightSideContainer(dynamicContainer, appearenceIsSelected);
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
