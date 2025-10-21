package my_app.screens.Home.components;

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
import my_app.contexts.ComponentsContext;
import my_app.contexts.ComponentsContext.SelectedComponent;
import my_app.data.ViewContract;

public class RightSide extends VBox {

    final double width = 250;
    // 1. ALTERADO: Tipo da propriedade é agora SelectedComponent
    final ObjectProperty<SelectedComponent> selectedComponentProperty;

    Button btnAppearence = new Button("Appearence");
    Button btnLayout = new Button("Layout");
    HBox top = new HBox(btnAppearence, btnLayout);
    HBox topWrapper = new HBox(top); // wrapper só para não se esticar

    Text title = new Text();

    private VBox dynamicContainer; // container que será substituído

    BooleanProperty appearenceIsSelected = new SimpleBooleanProperty(true);

    public RightSide(ComponentsContext componentsContext) {
        // 1. ALTERADO: Atribui a propriedade com o tipo correto
        ObjectProperty<SelectedComponent> selectedCompProp = componentsContext.nodeSelected;

        this.selectedComponentProperty = selectedCompProp; // Renomeado para clareza

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

        // mount
        mount();

        // Atualiza UI quando muda de seleção

        appearenceIsSelected.addListener((obs, old, node) -> mount());
        // NodeWrapper

        // quando muda o node
        appearenceIsSelected.addListener((obs, old, node) -> mount());

        // 2. ALTERADO: Listener agora recebe SelectedComponent
        selectedComponentProperty.addListener((obs, oldComp, newComp) -> {
            // Extrai o Node do SelectedComponent. Será null se a seleção for limpa.
            Node newNode = (newComp != null) ? newComp.node() : null;

            if (newNode instanceof ViewContract renderable) {
                NodeWrapper nw = new NodeWrapper(renderable);
                nw.renderRightSideContainer(dynamicContainer, appearenceIsSelected);
            } else {
                // Se newNode for null (desseleção) ou não for ViewContract
                dynamicContainer.getChildren().setAll(new Text("No component selected or configuration available."));
            }
        });

        config();
    }

    void mount() {
        SelectedComponent currentSelectedComp = selectedComponentProperty.get();
        Node currentNode = (currentSelectedComp != null) ? currentSelectedComp.node() : null;

        if (currentNode instanceof ViewContract renderable) {
            NodeWrapper nw = new NodeWrapper(renderable);
            nw.renderRightSideContainer(dynamicContainer, appearenceIsSelected);
        } else {
            // Garante que o container esteja limpo se nada estiver selecionado ao montar
            dynamicContainer.getChildren().setAll(new Text("Select a component to view settings."));
        }
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
                new BackgroundFill(Color.web("#1E1F23"), CornerRadii.EMPTY, Insets.EMPTY)));

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
