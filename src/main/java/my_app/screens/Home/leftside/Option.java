package my_app.screens.Home.leftside;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.ListChangeListener;
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

//--Button (OptionHeader)
//     -btn1 (subItem)

public class Option extends VBox {
    BooleanProperty expanded = new SimpleBooleanProperty(false);
    VBox subItems = new VBox();
    private String type;

    ObjectProperty<String> subItemSelected = new SimpleObjectProperty<>("");

    SubItemsContext context = SubItemsContext.getInstance();
    ComponentsContext componentsContext = ComponentsContext.getInstance();

    void handleHeaderClick() {
        expanded.set(!expanded.get());
    }

    Home home;

    public Option(String type, Home home) {
        this.type = type.toLowerCase();
        this.home = home;

        OptionHeader header = new OptionHeader(type, home, this::handleHeaderClick);

        getChildren().add(header);
        getChildren().add(subItems);

        var items = context.getItemsByType(this.type);

        items.addListener((ListChangeListener<SimpleStringProperty>) change -> {
            while (change.next()) {
                if (change.wasAdded()) {
                    System.out.println("item added id: " + items.getLast());
                    loadSubItems();
                }
            }
        });

        subItems.managedProperty().bind(expanded);
        subItems.visibleProperty().bind(expanded);

        subItems.setPadding(new Insets(5, 0, 0, 20));
        subItems.setSpacing(2);

        ComponentsContext.nodeSelected.addListener((obs, oldId, newNode) -> {

            for (Node n : subItems.getChildren()) {
                if (n instanceof HBox hbox) {
                    Label lbl = (Label) hbox.getChildren().get(0);
                    String text = lbl.getText().replace("• ", ""); // remove o bullet
                    if (text.equals(newNode.getId())) {
                        hbox.setStyle("-fx-background-color: red;");
                    } else {
                        // não sobrescreve a seleção amarela
                        if (!text.equals(subItemSelected.get())) {
                            hbox.setStyle("-fx-background-color: transparent;");
                        }
                    }
                }
            }
        });

    }

    private void loadSubItems() {
        subItems.getChildren().clear();

        ObservableList<SimpleStringProperty> itemsProperties = context.getItemsByType(type);

        for (int i = 0; i < itemsProperties.size(); i++) {
            SimpleStringProperty itemProperty = itemsProperties.get(i);
            String itemId = itemProperty.get();

            HBox subItemBox = createSubItemBox(itemId);

            subItems.getChildren().add(subItemBox);
        }
    }

    private HBox createSubItemBox(String itemId) {
        HBox subItemBox = new HBox();
        Label subLabel = new Label("• " + itemId);
        subLabel.setFont(Font.font(12));
        subLabel.setTextFill(Color.LIGHTGRAY);

        subItemBox.getChildren().add(subLabel);
        subItemBox.setPadding(new Insets(3, 5, 3, 10));

        // Checa o estado atual NA HORA DA CRIAÇÃO
        if (ComponentsContext.CurrentNodeIsSelected(itemId)) {
            subItemBox.setStyle("-fx-background-color: red;");
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
            // lookin for custom component in main canva
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
