package my_app.components.columnComponent;

import java.util.ArrayList;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import my_app.components.LayoutPositionComponent;
import my_app.components.TextComponent;
import my_app.components.shared.ChildHandlerComponent;
import my_app.components.shared.ItemsAmountPreviewComponent;
import my_app.components.shared.OnEmptyComponent;
import my_app.contexts.ComponentsContext;
import my_app.data.ColumnComponentData;
import my_app.data.Commons;
import my_app.data.ComponentData;
import my_app.data.ComponentFactory;
import my_app.data.ViewContract;

// ColumnItens.java

public class ColumnItens extends VBox implements ViewContract<ColumnComponentData> {

    // Mantemos o currentState e currentChild para a barra lateral de propriedades
    ObjectProperty<Node> currentState = new SimpleObjectProperty<>();
    // A propriedade agora armazena o ID do componente filho (ex: "Text" ou ID do
    // CustomComponent)
    SimpleStringProperty currentChild = new SimpleStringProperty("Text");
    public SimpleIntegerProperty childrenAmountState = new SimpleIntegerProperty(3);

    // NOVO: Propriedade para armazenar o Componente a ser exibido quando a lista
    // está vazia
    ObjectProperty<Node> onEmptyComponentState = new SimpleObjectProperty<>();

    public ColumnItens() {
        // Configuração inicial como VBox
        setSpacing(5);
        setStyle("-fx-background-color:red;");

        setAlignment(Pos.CENTER);
        setPrefWidth(Region.USE_COMPUTED_SIZE); // Permite que o VBox se ajuste ao conteúdo/pai

        // Adiciona 3 filhos TextComponent padrão inicialmente
        var defaultComponents = new ArrayList<TextComponent>();
        for (int i = 0; i < childrenAmountState.get(); i++) {
            defaultComponents.add(new TextComponent("Item de Coluna " + i));
        }
        getChildren().addAll(defaultComponents);

        setId(String.valueOf(System.currentTimeMillis()));
        currentState.set(this);
    }

    // -------------------------------------------------------------------
    // NOVO MÉTODO: Lógica Centralizada para Recriar os Filhos
    // -------------------------------------------------------------------

    public void recreateChildren() {
        int amount = childrenAmountState.get();

        // 1. Limpa todos os filhos existentes
        getChildren().clear();

        if (amount == 0) {
            // SE A QUANTIDADE FOR ZERO, exibe o componente de placeholder
            Node emptyNode = onEmptyComponentState.get();
            if (emptyNode != null) {
                // Remove o nó de seu pai anterior (se existir) para evitar Parent Exception
                if (emptyNode.getParent() != null) {
                    ((Pane) emptyNode.getParent()).getChildren().remove(emptyNode);
                }
                getChildren().add(emptyNode);
            }
            return; // Encerra a função, pois o placeholder foi adicionado
        }

        // 2. SE A QUANTIDADE FOR MAIOR QUE ZERO...
        String currentChildId = currentChild.get();

        if (currentChildId.equals("Text")) {
            // Recriação de TextComponent (O estado padrão é criado aqui, sem dados salvos)
            for (int i = 0; i < amount; i++) {
                getChildren().add(new TextComponent("Item de Coluna " + i));
            }
        } else {
            // Recriação de CustomComponents (DEEP COPY)
            var op = ComponentsContext.SearchNodeByIdInNodesList(currentChildId);

            op.ifPresent(existingNode -> {
                // ** Universalização: Usamos ViewContract e a Fábrica **
                if (existingNode instanceof ViewContract existingView) {

                    ComponentData originalData = (ComponentData) existingView.getData(); // Pega os dados originais

                    var copies = new ArrayList<Node>();
                    for (int i = 0; i < amount; i++) {
                        // Criamos uma nova cópia do nó a partir dos dados originais
                        Node newCopy = ComponentFactory.createNodeFromData(originalData);

                        // Aplicamos o ID da cópia
                        newCopy.setId(System.currentTimeMillis() + "_copy" + i);
                        copies.add(newCopy);
                    }
                    getChildren().addAll(copies);
                }
            });
        }
    }

    @Override
    public void appearance(Pane father) {
        father.getChildren().setAll(
                new ChildHandlerComponent(this, currentChild),
                new ItemsAmountPreviewComponent(this),
                new OnEmptyComponent(this, onEmptyComponentState));
    }

    @Override
    public void settings(Pane father) {
        father.getChildren().setAll(
                new LayoutPositionComponent(currentState));
    }

    @Override
    public ColumnComponentData getData() {
        // O tipo do componente filho atual
        String childType = currentChild.get().equals("Text") ? "text_component" : "custom_component";

        // NOVO: Usamos ComponentData como tipo de retorno
        ComponentData childData = null;
        if (!getChildren().isEmpty()) {
            Node firstChild = getChildren().getFirst();

            // ** Refatoração: Usamos a interface ViewContract **
            if (firstChild instanceof ViewContract viewContract) {
                // Chamamos getData() no componente para obter o ComponentData específico
                childData = (ComponentData) viewContract.getData();
            }
        }

        ComponentData alternativeChildData = null;
        Node emptyNode = onEmptyComponentState.get();
        if (emptyNode instanceof ViewContract viewContract) {
            // Chamamos getData() no placeholder para obter o ComponentData específico
            alternativeChildData = (ComponentData) viewContract.getData();
        }

        var location = Commons.NodeInCanva(this);

        // Retorna o novo ColumnComponentData
        return new ColumnComponentData(
                // NOVO: Adicione o tipo aqui
                "column items",
                this.getId(),
                childType,
                currentChild.get(),
                childData,
                alternativeChildData, // Agora aceita qualquer ComponentData
                (int) getLayoutX(),
                (int) getLayoutY(),
                location.inCanva(),
                location.fatherId(),
                childrenAmountState.get());
    }

    @Override
    public void applyData(ColumnComponentData data) {
        // Limpa os filhos existentes antes de aplicar o novo estado
        getChildren().clear();

        this.setLayoutX(data.x());
        this.setLayoutY(data.y());

        this.setId(data.identification());
        currentChild.set(data.childId());
        childrenAmountState.set(data.pref_child_amount_for_preview());

        // 2. Restaurar o componente alternativo (On Empty)
        if (data.alternative_child() != null) {
            // Recriamos o nó (Node) a partir dos dados (ComponentData)
            Node emptyCopy = ComponentFactory.createNodeFromData(data.alternative_child());

            // É essencial que o ComponentFactory também lide com a aplicação dos dados
            // no nó criado.
            onEmptyComponentState.set(emptyCopy);
        } else {
            onEmptyComponentState.set(null);
        }

        // 3. Chamar a lógica centralizada (permanece igual)
        recreateChildren();

    }
}