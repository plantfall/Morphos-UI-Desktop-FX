package my_app.screens.Home.components.leftside;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
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

    SubItemsContext context = SubItemsContext.getInstance();

    void handleHeaderClick() {
        expanded.set(!expanded.get());
    }

    Home home;

    public Option(String type, Home home) {
        this.type = type.toLowerCase().trim();
        this.home = home;

        OptionHeader header = new OptionHeader(type, home, this::handleHeaderClick);

        getChildren().add(header);
        getChildren().add(subItemsContainer);

        System.out.println(this.type);

        SubItemsContext.leftItemsStateRefreshed.addListener((a, b, val) -> {
            loadSubItems();
        });

        subItemsContainer.managedProperty().bind(expanded);
        subItemsContainer.visibleProperty().bind(expanded);

        subItemsContainer.setPadding(new Insets(5, 0, 0, 20));
        subItemsContainer.setSpacing(2);
    }

    private void loadSubItems() {
        subItemsContainer.getChildren().clear();

        ObservableList<SimpleStringProperty> itemsProperties = context.getItemsByType(type);

        for (int i = 0; i < itemsProperties.size(); i++) {
            SimpleStringProperty itemProperty = itemsProperties.get(i);
            String itemId = itemProperty.get();

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

        // Checa o estado atual NA HORA DA CRIAÇÃO
        if (ComponentsContext.CurrentNodeIsSelected(itemId)) {
            subItemBox.setStyle("-fx-background-color: red;");
            expanded.set(true);
        }

        subItemBox.setOnMouseClicked(e -> {
            onClickOnSubItem(itemId, this.type, home.canva);
        });

        subItemBox.setOnMouseEntered(e -> {
            if (!ComponentsContext.CurrentNodeIsSelected(itemId)) {
                subItemBox.setStyle("-fx-background-color: #2D2A6E;");
            }
        });

        subItemBox.setOnMouseExited(e -> {
            if (!ComponentsContext.CurrentNodeIsSelected(itemId)) {
                subItemBox.setStyle("-fx-background-color: transparent;");
            }
        });

        return subItemBox;
    }

    void onClickOnSubItem(String itemIdentification, String type,
            CanvaComponent mainCanvaComponent) {

        var canvaChildren = mainCanvaComponent.getChildren();

        var op = ComponentsContext.SearchNodeByIdInNodesList(itemIdentification);

        op.ifPresent(state -> {
            var target = ComponentsContext.SearchNodeByIdInMainCanva(itemIdentification, canvaChildren);
            // 2. finded in main canva so, selected
            if (target != null) {
                ComponentsContext.SelectNode(target);
            } else {
                // if not, just add in canva
                mainCanvaComponent.addElementDragable(op.get(), false);
            }
        });

    }

}
