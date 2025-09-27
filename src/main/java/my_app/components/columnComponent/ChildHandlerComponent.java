package my_app.components.columnComponent;

import java.util.ArrayList;

import javafx.beans.property.SimpleStringProperty;
import javafx.scene.Node;
import javafx.scene.control.ComboBox;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import my_app.components.CustomComponent;
import my_app.components.TextComponent;
import my_app.contexts.ComponentsContext;
import my_app.contexts.SubItemsContext;
import my_app.data.InnerComponentData;

public class ChildHandlerComponent extends HBox {
    Text title = new Text("Child component:");

    String[] orientationList = { "Row", "Column" };

    SubItemsContext context = SubItemsContext.getInstance();
    ComponentsContext componentsContext = ComponentsContext.getInstance();

    public ChildHandlerComponent(ColumnItens nodeTarget, SimpleStringProperty currentChild) {
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
            // 1. Atualiza a propriedade do ColumnItens (flowpane é agora ColumnItens)
            currentChild.set(newVal);

            // ** CHAMAMOS O MÉTODO CENTRALIZADO NO ColumnItens **
            // Isso garantirá que o número correto de filhos (childrenAmountState) seja
            // usado.

            nodeTarget.recreateChildren();
        });

        getChildren().addAll(title, combo);
    }

    void config() {
        title.setFont(Font.font(14));
        title.setFill(javafx.scene.paint.Color.WHITE);
        setSpacing(10);
    }
}
