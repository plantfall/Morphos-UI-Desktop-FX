package my_app.screens.Home;

import javafx.beans.binding.Bindings;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
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

    Button btnAppearence = new Button("Appearence");
    Button btnLayout = new Button("Layout");

    HBox top = new HBox(btnAppearence, btnLayout);

    Text title = new Text();

    private VBox dynamicContainer; // container que será substituído

    BooleanProperty appearenceIsSelected = new SimpleBooleanProperty(true);

    public RightSide(ObjectProperty<Node> selectedNode) {
        this.selectedNode = selectedNode;

        btnAppearence.setOnAction(ev -> appearenceIsSelected.set(true));
        btnLayout.setOnAction(ev -> appearenceIsSelected.set(false));

        getChildren().add(top);

        title.textProperty().bind(Bindings
                .createStringBinding(() -> appearenceIsSelected.get() ? "Appearence Settings" : "Layout Settings",
                        appearenceIsSelected));

        getChildren().add(title);

        // ---- Container dinâmico (será trocado conforme o node selecionado) ----
        dynamicContainer = new VBox();
        dynamicContainer.setUserData("dynamic-container");
        getChildren().add(dynamicContainer);

        var spacer = new Region();
        spacer.setPrefHeight(10);
        getChildren().add(spacer);

        // Atualiza UI quando muda de seleção

        selectedNode.addListener((obs, old, node) -> {

            // ---- Container dinâmico (será trocado conforme o node selecionado) ----
            dynamicContainer.getChildren().clear(); // limpa só o container dinâmico

            if (node instanceof CanvaComponent n) {
                n.renderRightSideContainer(dynamicContainer, appearenceIsSelected);
            }

            if (node instanceof ButtonComponent n) {
                n.renderRightSideContainer(
                        dynamicContainer, appearenceIsSelected);
            }

            if (node instanceof TextComponent n) {
                n.activeNode(selectedNode);
                n.renderRightSideContainer(dynamicContainer, appearenceIsSelected);
            }

            if (node instanceof ImageComponent n) {
                n.renderRightSideContainer(dynamicContainer, appearenceIsSelected);
            }
        });

        config();
    }

    void config() {

        setMaxHeight(Double.MAX_VALUE);
        setBackground(new Background(
                new BackgroundFill(Color.web("#17153B"), CornerRadii.EMPTY, Insets.EMPTY)));

        setPrefWidth(width);
        setMinWidth(width);
        setMaxWidth(width);

        setPadding(new Insets(20));

        title.setFill(Color.WHITE);
    }
}
