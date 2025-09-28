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

public class OnEmptyComponent extends HBox {

    private Text title = new Text("On Empty:");
    private ComboBox<String> combo = new ComboBox<>();

    public OnEmptyComponent(ColumnItens columnTarget, ObjectProperty<Node> onEmptyComponentState) {

        config();

        // 1. Popula a ComboBox com os CustomComponents disponíveis
        combo.getItems().add("None");

        for (Node node : ComponentsContext.nodes) {
            if (node instanceof CustomComponent custom) {
                combo.getItems().add(custom.getId());
            }
        }

        // ----------------------------------------------------------------
        // CORREÇÃO 1: FATORAÇÃO DA LÓGICA DE SINCRONIZAÇÃO
        // ----------------------------------------------------------------

        // Define uma função centralizada para sincronizar o ComboBox com o estado da
        // propriedade
        Runnable syncCombo = () -> {
            Node alternativeComponent = onEmptyComponentState.get();

            if (alternativeComponent == null) {
                combo.setValue("None");
            } else {
                String idToSelect = alternativeComponent.getId();

                // Remove o sufixo '_copy' para encontrar o ID original (seu fix atual)
                if (idToSelect.endsWith("_copy")) {
                    idToSelect = idToSelect.substring(0, idToSelect.lastIndexOf("_copy"));
                }

                if (combo.getItems().contains(idToSelect)) {
                    // Sincroniza o ComboBox com o ID Original
                    combo.setValue(idToSelect);
                } else {
                    // Caso o ID não esteja na lista (e.g., componente deletado), volta para "None"
                    combo.setValue("None");
                }
            }
        };

        // Aplica a sincronização para o valor INICIAL (restauração de dados)
        syncCombo.run();

        // ----------------------------------------------------------------
        // CORREÇÃO 2: CORRIGE A REFERÊNCIA NO LISTENER
        // ----------------------------------------------------------------

        // Ouve mudanças futuras na propriedade (e.g., após applyData ou outras
        // interações)
        onEmptyComponentState.addListener((_a, _b, v) -> {
            // Em vez de recriar a lógica, chamamos a função centralizada.
            // O listener deve ser disparado APÓS a mudança de 'v'.
            syncCombo.run();
        });

        // 2. Listener para seleção (Lógica do usuário para MUDAR o estado)
        combo.valueProperty().addListener((obs, oldVal, newVal) -> {
            if (newVal == null || newVal.equals("None")) {
                onEmptyComponentState.set(null);
            } else {
                // Busca o nó CustomComponent correspondente ao ID
                var op = ComponentsContext.SearchNodeByIdInNodesList(newVal);

                op.ifPresent(selectedNode -> {
                    // É crucial fazer a cópia para que cada ColumnItens tenha seu próprio
                    // CustomComponent para alterar suas propriedades de layout/estilo.
                    if (selectedNode instanceof CustomComponent c) {
                        var newComp = new CustomComponent();
                        newComp.applyData(c.getData());

                        // Manter o sufixo '_copy' no ID para que a lógica de syncCombo funcione!
                        newComp.setId(c.getId() + "_copy");

                        // O set aqui dispara o listener onEmptyComponentState.addListener
                        onEmptyComponentState.set(newComp);
                    }
                });
            }

            // 3. Força a recriação dos filhos
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