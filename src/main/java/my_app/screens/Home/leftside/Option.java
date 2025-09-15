package my_app.screens.Home.leftside;

import java.util.function.Consumer;

import javafx.application.Platform;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.ListChangeListener;
import javafx.collections.MapChangeListener;
import javafx.collections.ObservableList;
import javafx.collections.WeakListChangeListener;
import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import my_app.contexts.SubItemsContext;
import my_app.screens.Home.Home.HandleClickSubItem;

//--Button
//     -btn1

public class Option extends VBox {
    BooleanProperty expanded = new SimpleBooleanProperty(false);
    VBox subItems = new VBox();
    private String type;

    ObjectProperty<String> subItemSelected = new SimpleObjectProperty<>("");

    SubItemsContext context = SubItemsContext.getInstance();

    void handleHeaderClick() {
        expanded.set(!expanded.get());
    }

    public Option(String type,
            Consumer<String> callbackClickOnBtnAdd,
            HandleClickSubItem callbackClickSubItem) {
        this.type = type.toLowerCase();

        OptionHeader header = new OptionHeader(type, callbackClickOnBtnAdd, this::handleHeaderClick);

        getChildren().add(header);
        getChildren().add(subItems);

        var items = context.getItemsByType(this.type);

        items.addListener((ListChangeListener<SimpleStringProperty>) change -> {
            while (change.next()) {
                if (change.wasAdded() || change.wasRemoved()) {
                    loadSubItems(callbackClickSubItem);
                }
            }
        });

        expanded.addListener((obs, old, value) -> {
            if (value) {
                loadSubItems(callbackClickSubItem);
            } else {
                subItems.getChildren().clear();
            }
        });

        subItems.setPadding(new Insets(5, 0, 0, 20));
        subItems.setSpacing(2);
    }

    private void loadSubItems(HandleClickSubItem callbackClickSubItem) {
        subItems.getChildren().clear();

        ObservableList<SimpleStringProperty> itemsProperties = context.getItemsByType(type);

        for (SimpleStringProperty itemProperty : itemsProperties) {
            String itemId = itemProperty.get();

            HBox subItemBox = createSubItemBox(itemId, callbackClickSubItem);
            subItems.getChildren().add(subItemBox);
        }
    }

    private HBox createSubItemBox(String itemId, HandleClickSubItem callbackClickSubItem) {
        HBox subItemBox = new HBox();
        Label subLabel = new Label("• " + itemId);
        subLabel.setFont(Font.font(12));
        subLabel.setTextFill(Color.LIGHTGRAY);

        subItemBox.getChildren().add(subLabel);
        subItemBox.setPadding(new Insets(3, 5, 3, 10));

        //

        // 🔹 Listener para atualizar estilo quando subItemSelected mudar
        subItemSelected.addListener((obs, oldVal, newVal) -> {
            if (itemId.equals(newVal)) {
                subItemBox.setStyle("-fx-background-color: yellow;");
            } else {
                subItemBox.setStyle("-fx-background-color: transparent;");
            }
        });

        subItemBox.setOnMouseClicked(e -> {
            subItemSelected.set(itemId); // marca este como selecionado
            callbackClickSubItem.onClick(itemId, this.type);
        });

        subItemBox.setOnMouseEntered(e -> {
            if (!itemId.equals(subItemSelected.get())) {
                subItemBox.setStyle("-fx-background-color: #2D2A6E;");
            }
        });

        subItemBox.setOnMouseExited(e -> {
            if (!itemId.equals(subItemSelected.get())) {
                subItemBox.setStyle("-fx-background-color: transparent;");
            }
        });
        //

        return subItemBox;
    }
}
