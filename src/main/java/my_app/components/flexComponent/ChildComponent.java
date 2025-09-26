package my_app.components.flexComponent;

import javafx.beans.property.SimpleStringProperty;
import javafx.scene.control.ComboBox;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import my_app.components.CustomComponent;
import my_app.contexts.ComponentsContext;
import my_app.contexts.SubItemsContext;

public class ChildComponent extends HBox {
    Text title = new Text("Child component:");

    String[] orientationList = { "Row", "Column" };

    SubItemsContext context = SubItemsContext.getInstance();
    ComponentsContext componentsContext = ComponentsContext.getInstance();

    public ChildComponent(FlowPane flowpane, SimpleStringProperty currentChild) {
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

            // aqui vou alterar o child de TextComponent -> componente clicado
            // if(newVal.equals(""))

            var op = componentsContext.searchNodeById(newVal);

            op.ifPresent(state -> {
                // reconstruimos o node
                CustomComponent customComponent = new CustomComponent();
                // customComponent.applyData(state);

                // agor vou adiiconar como filho
                flowpane.getChildren().clear();
                flowpane.getChildren().add(customComponent);
            });

        });

        getChildren().addAll(title, combo);
    }

    void config() {
        title.setFont(Font.font(14));
        title.setFill(javafx.scene.paint.Color.WHITE);
        setSpacing(10);
    }
}
