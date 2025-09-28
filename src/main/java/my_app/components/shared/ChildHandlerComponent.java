package my_app.components.shared;

import javafx.beans.property.SimpleStringProperty;
import javafx.scene.control.ComboBox;
import javafx.scene.layout.HBox;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import my_app.components.columnComponent.ColumnItens;
import my_app.contexts.ComponentsContext;
import my_app.contexts.SubItemsContext;

public class ChildHandlerComponent extends HBox {
    Text title = new Text("Child component:");

    SubItemsContext context = SubItemsContext.getInstance();

    public ChildHandlerComponent(ColumnItens nodeTarget, SimpleStringProperty currentChild) {
        var combo = new ComboBox<String>();

        config();

        combo.getItems().clear();
        combo.getItems().add("Text"); // Adiciona o tipo padrão

        // Itera sobre os GRUPOS de componentes (Ex: "text", "button", "column items")
        for (var entry : context.getAllData().entrySet()) {
            String componentType = entry.getKey();

            // FILTRO 2 (OPCIONAL, mas RECOMENDADO): Impede que qualquer ColumnItens seja
            // filho.
            // Se o tipo for "column items", pula o grupo inteiro.
            if (componentType.equals("column items")) {
                continue;
            }

            // Itera sobre todos os IDs dentro desse grupo
            for (SimpleStringProperty idProperty : entry.getValue()) {
                String id = idProperty.get();

                // FILTRO 1: Impede que a própria ColumnItens seja adicionada à lista
                if (id.equals(nodeTarget.getId())) {
                    continue;
                }

                combo.getItems().add(id);
            }
        }

        // Garante que o item atualmente selecionado esteja na lista (mesmo que seja o
        // Text Padrão)
        if (!combo.getItems().contains(currentChild.get())) {
            combo.getItems().add(currentChild.get());
        }

        // Mantém o valor selecionado
        combo.setValue(currentChild.get());

        combo.valueProperty().addListener((obs, old, newVal) -> {
            if (newVal != null && !newVal.equals(old)) {
                currentChild.set(newVal);
                nodeTarget.recreateChildren();
            }
        });

        getChildren().addAll(title, combo);
    }

    void config() {
        title.setFont(Font.font(14));
        title.setFill(javafx.scene.paint.Color.WHITE);
        setSpacing(10);
    }
}