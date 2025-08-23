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
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import my_app.App;
import my_app.components.NodeWrapper;
import my_app.data.ViewContract;

public class RightSide extends VBox {

    final double width = 250;
    final ObjectProperty<Node> selectedNode;

    Button btnAppearence = new Button("Appearence");
    Button btnLayout = new Button("Layout");

    HBox top = new HBox(btnAppearence, btnLayout);
    HBox topWrapper = new HBox(top); // wrapper só para não se esticar

    Text title = new Text();

    private VBox dynamicContainer; // container que será substituído

    BooleanProperty appearenceIsSelected = new SimpleBooleanProperty(true);

    public RightSide(ObjectProperty<Node> selectedNode) {
        this.selectedNode = selectedNode;

        btnAppearence.setOnAction(ev -> appearenceIsSelected.set(true));
        btnLayout.setOnAction(ev -> appearenceIsSelected.set(false));

        getChildren().add(topWrapper);

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

        appearenceIsSelected.addListener((obs, old, node) -> {
            Node currentNode = selectedNode.get();
            if (currentNode instanceof ViewContract renderable) {
                NodeWrapper nw = new NodeWrapper(renderable);
                nw.renderRightSideContainer(dynamicContainer, appearenceIsSelected);
            } else {
                dynamicContainer.getChildren().setAll(new Text("Nenhuma configuração disponível"));
            }
        });
        // NodeWrapper

        selectedNode.addListener((obs, oldNode, newNode) -> {
            if (newNode instanceof ViewContract renderable) {
                NodeWrapper nw = new NodeWrapper(renderable);
                nw.renderRightSideContainer(dynamicContainer, appearenceIsSelected);
            } else {
                dynamicContainer.getChildren().setAll(new Text("Nenhuma configuração disponível"));
            }
        });

        config();
    }

    void config() {

        top.setSpacing(0);

        HBox.setHgrow(top, Priority.NEVER);
        top.setMaxWidth(Region.USE_COMPUTED_SIZE); // largura baseada nos filhos

        top.setStyle("-fx-background-color:#003161;  -fx-background-radius: 7px");

        appearenceIsSelected.addListener((obs, old, newVal) -> {
            if (newVal) {
                top.setStyle("-fx-background-color:#003161;  -fx-background-radius: 7px");
            } else {
                top.setStyle("-fx-background-color:#3B38A0;  -fx-background-radius: 7px");
            }
        });

        setMaxHeight(Double.MAX_VALUE);
        setBackground(new Background(
                new BackgroundFill(Color.web("#232C33"), CornerRadii.EMPTY, Insets.EMPTY)));

        setPrefWidth(width);
        setMinWidth(width);
        setMaxWidth(width);

        setPadding(new Insets(20));

        title.setFill(Color.WHITE);

        btnAppearence.setId("btnAppearence");
        btnLayout.setId("btnLayout");

        btnAppearence.setFont(App.FONT_BOLD);
        btnLayout.setFont(App.FONT_BOLD);
    }
}
