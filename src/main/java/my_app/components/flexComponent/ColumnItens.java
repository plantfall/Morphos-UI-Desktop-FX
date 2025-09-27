package my_app.components.flexComponent;

import java.util.ArrayList;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import my_app.components.CustomComponent;
import my_app.components.LayoutPositionComponent;
import my_app.components.TextComponent;
import my_app.data.ColumnComponentData;
import my_app.data.InnerComponentData;
import my_app.data.ViewContract;

// ColumnItens.java

public class ColumnItens extends VBox implements ViewContract<ColumnComponentData> {

    // Mantemos o currentState e currentChild para a barra lateral de propriedades
    ObjectProperty<Node> currentState = new SimpleObjectProperty<>();
    // A propriedade agora armazena o ID do componente filho (ex: "Text" ou ID do
    // CustomComponent)
    SimpleStringProperty currentChild = new SimpleStringProperty("Text");

    public boolean inCanva;
    public String canvaId;

    public ColumnItens() {
        // Configuração inicial como VBox
        setSpacing(5);
        setStyle("-fx-background-color:red;");

        // ** VBox não precisa de PrefWrapLength.
        // Ele deve expandir para preencher a largura do pai. **
        setAlignment(Pos.CENTER);
        setPrefWidth(Region.USE_COMPUTED_SIZE); // Permite que o VBox se ajuste ao conteúdo/pai

        // Adiciona 3 filhos TextComponent padrão inicialmente
        var defaultComponents = new ArrayList<TextComponent>();
        for (int i = 0; i < 3; i++) {
            defaultComponents.add(new TextComponent("Item de Coluna " + i));
        }
        getChildren().addAll(defaultComponents);

        setId(String.valueOf(System.currentTimeMillis()));
        currentState.set(this);
    }

    @Override
    public void appearance(Pane father) {
        // Reduzimos as opções de aparência, já que VBox/HBox são mais simples
        father.getChildren().setAll(
                // Removido OrientationComponent e PrefLenghtComponent
                new ChildHandlerComponent(this, currentChild)
        // new VGapComponent(this), // Componente para alterar o espaçamento vertical
        // new WidthComponent(this),
        // new HeightComponent(this)
        );
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

        // Obter os dados do PRIMEIRO filho para salvar o estado que deve ser recriado
        InnerComponentData childData = new InnerComponentData();
        if (!getChildren().isEmpty()) {
            Node firstChild = getChildren().getFirst();
            if (firstChild instanceof CustomComponent custom) {
                childData = custom.getData();
            } else if (firstChild instanceof TextComponent text) {
                // Se o filho for TextComponent, você precisa de um método para obter o
                // InnerComponentData
                // ou apenas guardar o TextComponentData
                // Para manter a compatibilidade, vamos assumir que você tem uma forma de
                // serializar o estado do filho.
                // Como não temos o TextComponentData aqui, usaremos um placeholder.
            }
        }

        // Retorna o novo ColumnComponentData
        return new ColumnComponentData(
                this.getId(),
                childType,
                currentChild.get(), // childId é o ID/nome do tipo de componente atual
                childData,
                (int) getLayoutX(),
                (int) getLayoutY(),
                inCanva, canvaId);
    }

    @Override
    public void applyData(ColumnComponentData data) {
        // Limpa os filhos existentes antes de aplicar o novo estado
        getChildren().clear();

        this.setLayoutX(data.x());
        this.setLayoutY(data.y());

        this.setId(data.identification());
        currentChild.set(data.childId());

        // Se a orientação original era "column", o VBox já lida com isso.

        // Lógica de recriação dos filhos (semelhante ao ChildComponent listener)
        if (data.childId().equals("Text")) {
            // Recriar os filhos TextComponent padrão
            for (int i = 0; i < 3; i++) {
                getChildren().add(new TextComponent("Item de Coluna " + i));
            }
        } else {
            // Recriar CustomComponents
            InnerComponentData childData = data.child();
            for (int i = 0; i < 3; i++) {
                CustomComponent newCopy = new CustomComponent();
                newCopy.applyData(childData);
                newCopy.setId(System.currentTimeMillis() + "" + i); // Novo ID
                getChildren().add(newCopy);
            }
        }
    }
}