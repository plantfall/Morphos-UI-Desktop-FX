package my_app.components.flexComponent;

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

    public ChildHandlerComponent(Pane nodeTarget, SimpleStringProperty currentChild) {
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

            // 2. Limpa o ColumnItens
            nodeTarget.getChildren().clear(); // flowpane é a instância de ColumnItens (VBox)

            if (newVal.equals("Text")) {
                // Recriando filhos TextComponent padrão (exemplo: 3 itens)
                var defaultTexts = new ArrayList<Node>();
                for (int i = 0; i < 3; i++) {
                    defaultTexts.add(new TextComponent("Item " + i));
                }
                nodeTarget.getChildren().addAll(defaultTexts);

            } else {
                // 3. Busca a instância original do CustomComponent pelo ID (newVal)
                var op = ComponentsContext.SearchNodeByIdInNodesList(newVal);

                op.ifPresent(existingNode -> {
                    if (existingNode instanceof CustomComponent custom) {
                        InnerComponentData data = custom.getData();

                        // 4. Cria MÚLTIPLAS cópias (Deep Copy) - removendo o binding complexo
                        var copies = new ArrayList<Node>();
                        for (int i = 0; i < 3; i++) { // 3 cópias
                            CustomComponent newCopy = new CustomComponent();
                            newCopy.applyData(data);

                            // Definir o prefWidth/prefHeight do filho para que o VBox o gerencie
                            // newCopy.setPrefWidth(VBox.USE_COMPUTED_SIZE);
                            // Se o CustomComponent tiver tamanho fixo, ele o manterá.

                            newCopy.setId(System.currentTimeMillis() + "" + i); // NOVO ID
                            copies.add(newCopy);
                        }
                        nodeTarget.getChildren().addAll(copies);
                    }
                });
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
