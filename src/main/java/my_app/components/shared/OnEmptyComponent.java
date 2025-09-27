package my_app.components.shared;

import javafx.beans.property.ObjectProperty;
import javafx.scene.Node;
import javafx.scene.control.ComboBox;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import my_app.components.CustomComponent;
import my_app.components.columnComponent.ColumnItens;
import my_app.contexts.ComponentsContext;

// OnEmptyComponent.java

public class OnEmptyComponent extends HBox {

    private final ColumnItens columnTarget;

    private Text title = new Text("On Empty:");
    private ComboBox<String> combo = new ComboBox<>();

    // O construtor recebe a referência do ColumnItens (para acesso aos filhos)
    // e o ObjectProperty (para armazenar o nó selecionado)
    public OnEmptyComponent(ColumnItens columnTarget, ObjectProperty<Node> onEmptyComponentState) {
        this.columnTarget = columnTarget;
        config();

        // 1. Popula a ComboBox com os CustomComponents disponíveis
        // Nota: O nó de "Placeholder" deve ser um componente que já existe no seu
        // sistema (CustomComponent/TextComponent, etc.)

        // Adiciona um valor para "Nenhum" (opcional)
        combo.getItems().add("None");

        // Adiciona todos os CustomComponents disponíveis na lista global
        // Isso assume que todos os CustomComponents salvos no ComponentsContext.nodes
        // são candidatos.
        for (Node node : ComponentsContext.nodes) {
            if (node instanceof CustomComponent custom) {
                // Adiciona o ID do componente (ou um nome amigável)
                combo.getItems().add(custom.getId());
            }
        }

        combo.setValue("None"); // Valor inicial

        // 2. Listener para seleção
        combo.valueProperty().addListener((obs, oldVal, newVal) -> {
            if (newVal.equals("None")) {
                onEmptyComponentState.set(null);
            } else {
                // Busca o nó CustomComponent correspondente ao ID
                var op = ComponentsContext.SearchNodeByIdInNodesList(newVal);

                op.ifPresent(selectedNode -> {
                    // copia o nó para armazenar

                    if (selectedNode instanceof CustomComponent c) {
                        var newComp = new CustomComponent();
                        newComp.applyData(c.getData());
                        newComp.setId(c.getId() + "_copy");

                        onEmptyComponentState.set(newComp);
                    }

                });
            }

            // 3. Força a recriação dos filhos para mostrar o placeholder imediatamente, se
            // amount for 0
            if (columnTarget.childrenAmountState.get() == 0) {
                columnTarget.recreateChildren();
            }
        });

        getChildren().addAll(title, combo);
    }

    private void config() {
        title.setFont(Font.font(14));
        title.setFill(Color.WHITE);
        setSpacing(10);
    }
}