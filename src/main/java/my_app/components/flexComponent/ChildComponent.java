package my_app.components.flexComponent;

import javafx.beans.property.SimpleStringProperty;
import javafx.geometry.Orientation;
import javafx.scene.control.ComboBox;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import my_app.contexts.SubItemsContext;

public class ChildComponent extends HBox {
    Text title = new Text("Child component:");

    String[] orientationList = { "Row", "Column" };

    SubItemsContext context = SubItemsContext.getInstance();

    public ChildComponent(FlowPane selectedNode, SimpleStringProperty currentChild) {
        var combo = new ComboBox<String>();

        config();

        // combo.getItems().add(currentChild.get());

        // adiciono o Text
        if (currentChild.get().equals("Text")) {
            combo.getItems().add("Text");
        }

        // adiciono os demais custom components depois
        var items = context.getItemsByType("component");
        for (var item : items) {
            combo.getItems().add(item.get());
        }

        combo.setValue(currentChild.get());

        combo.valueProperty().addListener((obs, old, newVal) -> {
            currentChild.set(newVal);

            // if(newVal.equals(""))

        });

        getChildren().addAll(title, combo);
    }

    void config() {
        title.setFont(Font.font(14));
        title.setFill(javafx.scene.paint.Color.WHITE);
        setSpacing(10);
    }
}
