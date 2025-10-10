package my_app.components.shared;

import java.util.HashSet;
import java.util.Set;

import javafx.beans.property.SimpleStringProperty;
import javafx.scene.control.ComboBox;
import javafx.scene.layout.HBox;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import my_app.components.columnComponent.ColumnComponent;
import my_app.contexts.SubItemsContext;

public class ChildHandlerComponent extends HBox {

    Text title = new Text("Child component:");
    ComboBox<String> combo = new ComboBox<>();

    SubItemsContext context = SubItemsContext.getInstance();

    public ChildHandlerComponent(
            String title,
            ColumnComponent nodeTarget,
            SimpleStringProperty currentNodeId) {

        this.title.setText(title);

        config();

        // Usamos Set para evitar duplicados
        Set<String> uniqueItems = new HashSet<>();
        uniqueItems.add("None"); // adiciona o item padrão

        // Itera sobre os GRUPOS de componentes
        for (var entry : context.getAllData().entrySet()) {
            String componentType = entry.getKey();

            // Filtro 2: ignora ColumnItens
            if (componentType.equals("column items")) {
                continue;
            }

            // Itera sobre os IDs desse grupo
            for (SimpleStringProperty idProperty : entry.getValue()) {
                String id = idProperty.get();

                // Filtro 1: ignora o próprio nodeTarget
                if (id.equals(nodeTarget.getId())) {
                    continue;
                }

                uniqueItems.add(id); // garante unicidade
            }
        }

        // Garante que o item atual também esteja na lista
        if (currentNodeId.get() != null && !currentNodeId.get().isEmpty()) {
            uniqueItems.add(currentNodeId.get());
        }

        // Adiciona todos ao combo de uma vez
        combo.getItems().setAll(uniqueItems);

        // Mantém o valor selecionado
        combo.setValue(currentNodeId.get());

        combo.valueProperty().addListener((obs, old, newVal) -> {
            if (newVal != null && !newVal.equals(old)) {
                currentNodeId.set(newVal);
                nodeTarget.recreateChildren();
            }
        });

        getChildren().addAll(this.title, combo);
    }

    void config() {
        title.setFont(Font.font(14));
        title.setFill(javafx.scene.paint.Color.WHITE);
        setSpacing(10);
    }
}
