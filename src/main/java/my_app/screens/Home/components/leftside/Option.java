package my_app.screens.Home.components.leftside;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import my_app.components.canvaComponent.CanvaComponent;
import my_app.contexts.ComponentsContext;
import my_app.contexts.SubItemsContext;
import my_app.screens.Home.Home;
import toolkit.Component;

//--Button (OptionHeader)
//     -btn1 (subItem)

public class Option extends VBox {
    BooleanProperty expanded = new SimpleBooleanProperty(false);
    VBox subItemsContainer = new VBox();
    String type;

    OptionHeader header;

    SubItemsContext context = SubItemsContext.getInstance();

    Home home;

    public Option(String type, Home home) {
        this.type = type.toLowerCase().trim();
        this.home = home;

        header = new OptionHeader(type, home, expanded);

        getChildren().add(header);
        getChildren().add(subItemsContainer);

        System.out.println(this.type);

        loadSubItems();

        ComponentsContext.leftItemsStateRefreshed.addListener((_, _, _) -> {
            loadSubItems();
        });

        subItemsContainer.managedProperty().bind(expanded);
        subItemsContainer.visibleProperty().bind(expanded);

        subItemsContainer.setPadding(new Insets(5, 0, 0, 20));
        subItemsContainer.setSpacing(2);
    }

    private void loadSubItems() {
        subItemsContainer.getChildren().clear();

        ObservableList<Node> nodes = ComponentsContext.getItemsByType(type);

        for (int i = 0; i < nodes.size(); i++) {
            String itemId = nodes.get(i).getId();

            HBox subItemBox = createSubItemBox(itemId);

            subItemsContainer.getChildren().add(subItemBox);
        }
    }

    @Component
    private HBox createSubItemBox(String itemId) {
        HBox subItemBox = new HBox();
        Label subLabel = new Label("• " + itemId);
        subItemBox.setId(itemId);

        subLabel.setFont(Font.font(12));
        subLabel.setTextFill(Color.LIGHTGRAY);

        subItemBox.getChildren().add(subLabel);
        subItemBox.setPadding(new Insets(3, 5, 3, 10));

        // Estilo inicial:
        updateSubItemStyle(subItemBox, itemId);

        // Adiciona um listener para que, se o nó for selecionado/deselecionado, o
        // estilo mude
        ComponentsContext.nodeSelected.addListener((_, _, _) -> {
            updateSubItemStyle(subItemBox, itemId);
        });

        subItemBox.setOnMouseClicked(_ -> onClickOnSubItem(itemId, this.type, home.canva));

        subItemBox.setOnMouseEntered(_ -> {
            if (!ComponentsContext.CurrentNodeIsSelected(itemId)) {
                subItemBox.setStyle("-fx-background-color: #2D2A6E;");
            }
        });

        subItemBox.setOnMouseExited(_ -> {
            if (!ComponentsContext.CurrentNodeIsSelected(itemId)) {
                subItemBox.setStyle("-fx-background-color: transparent;");
            }
        });

        return subItemBox;
    }

    // Método auxiliar para aplicar/remover o estilo de seleção
    private void updateSubItemStyle(HBox subItemBox, String itemId) {
        if (ComponentsContext.nodeSelected.get() != null && ComponentsContext.CurrentNodeIsSelected(itemId)) {
            subItemBox.setStyle("-fx-background-color: red;");
            expanded.set(true); // Opcional: Expande o menu se o nó for selecionado
        } else {
            subItemBox.setStyle("-fx-background-color: transparent;");
        }
    }

    void onClickOnSubItem(String itemIdentification, String type,
            CanvaComponent mainCanvaComponent) {

        var canvaChildren = mainCanvaComponent.getChildren();

        var op = ComponentsContext.SearchNodeById(itemIdentification);

        op.ifPresent(_ -> {
            var target = ComponentsContext.SearchNodeByIdInMainCanva(itemIdentification, canvaChildren);
            // 2. finded in main canva so, selected
            if (target != null) {
                ComponentsContext.SelectNode(target);
                CanvaComponent.Shake(target);
            } else {
                // if not, just add in canva
                mainCanvaComponent.addElementDragable(op.get(), false);
            }
        });

    }

}
