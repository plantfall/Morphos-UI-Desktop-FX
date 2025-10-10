package my_app.screens.Home.components.leftside;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.ObservableList;
import javafx.scene.layout.HBox;
import my_app.components.canvaComponent.CanvaComponent;
import my_app.contexts.ComponentsContext;
import my_app.contexts.SubItemsContext;
import my_app.screens.Home.Home;

public class LeftSideController {

    // : Estados aqui
    // Propriedade para rastrear o ID do subitem (componente) atualmente
    // selecionado/clicado.
    private final StringProperty selectedSubItemId = new SimpleStringProperty(null);

    // Propriedade para rastrear a referência da HBox do subitem que está
    // selecionado no LeftSide
    private final ObjectProperty<HBox> selectedSubItemBox = new SimpleObjectProperty<>(null);

    private final SubItemsContext subItemsContext = SubItemsContext.getInstance();

    private final StringProperty headerExpanded = new SimpleStringProperty(null);

    // : Métodos aqui

    public void handleHeaderClick(String type, Home home) {
        // optionHeader.setStyle("-fx-background-color:#3B38A0;");
    }

    /**
     * Retorna a propriedade do ID do subitem selecionado.
     * Pode ser usado para ligar a lógica de estilo do subitem na UI.
     */
    public StringProperty selectedSubItemIdProperty() {
        return selectedSubItemId;
    }

    /**
     * Retorna a lista observável de IDs de componentes de um determinado tipo.
     * Usado por Option para carregar seus subitens.
     */
    public ObservableList<SimpleStringProperty> getItemsByType(String type) {
        return subItemsContext.getItemsByType(type);
    }

    /**
     * Lógica central de manipulação de clique em um subitem (componente existente).
     * 1. Tenta selecionar o nó no Canva se ele já estiver lá.
     * 2. Se não estiver, adiciona-o ao Canva.
     */
    public void onClickOnSubItem(
            String itemIdentification,
            CanvaComponent mainCanvaComponent,
            HBox clickedSubItemBox // Referência visual do item clicado
    ) {

        // 1. Busca pelo nó original
        var op = ComponentsContext.SearchNodeByIdInNodesList(itemIdentification);

        op.ifPresent(state -> {
            var canvaChildren = mainCanvaComponent.getChildren();
            var target = ComponentsContext.SearchNodeByIdInMainCanva(itemIdentification, canvaChildren);

            if (target != null) {
                // 2. Nó encontrado no Canva, então apenas o seleciona
                ComponentsContext.SelectNode(target);
            } else {
                // 3. Se não, adiciona-o ao Canva para que possa ser arrastado
                mainCanvaComponent.addElementDragable(op.get(), false);
            }
        });

        // 4. Gerencia o estado de seleção visual no LeftSide
        updateSelectedSubItemState(itemIdentification, clickedSubItemBox);
    }

    /**
     * Lógica para o botão de adicionar novo componente (botão no OptionHeader).
     */
    public void handleAddComponent(String type, Home home) {
        // A função ComponentsContext.AddComponent já cria o nó, adiciona-o à lista
        // (onde o LeftSide o verá) e, se o componente for visível, o adiciona ao Canva.
        ComponentsContext.AddComponent(type, home);
    }

    /**
     * Atualiza o estado de qual HBox de subitem está selecionado visualmente.
     */
    private void updateSelectedSubItemState(String newId, HBox newSubItemBox) {
        // Remove o estilo do item anterior, se houver
        HBox previousBox = selectedSubItemBox.get();
        if (previousBox != null) {
            // Garante que o item anterior volte ao seu estado normal (transparente)
            if (!previousBox.getId().equals(newId)) {
                previousBox.setStyle("-fx-background-color: transparent;");
            }
        }

        // Aplica o novo estilo e salva a referência
        newSubItemBox.setStyle("-fx-background-color: red;");
        selectedSubItemBox.set(newSubItemBox);
        selectedSubItemId.set(newId);
    }
}