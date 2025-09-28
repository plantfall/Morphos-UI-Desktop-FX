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

    // Remova os campos não utilizados se possível
    // String[] orientationList = { "Row", "Column" };

    SubItemsContext context = SubItemsContext.getInstance();
    // ComponentsContext componentsContext = ComponentsContext.getInstance(); // Não
    // está sendo usado

    public ChildHandlerComponent(ColumnItens nodeTarget, SimpleStringProperty currentChild) {
        var combo = new ComboBox<String>();

        config();

        // 1. **Adiciona o item "Text" padrão**
        // A string "Text" (com 'T' maiúsculo) é usada como o ID/nome para o componente
        // Text
        // simples, conforme sua lógica em ColumnItens.
        combo.getItems().add("Text");

        // 2. **Adiciona todos os Custom/Named Components**
        // Iteramos sobre todos os grupos de dados (Map<Tipo, Lista<IDs>>)
        for (var entry : context.getAllData().entrySet()) {
            String componentType = entry.getKey(); // Ex: "button", "input", "component", "column items"

            // O tipo "text" (minúsculo) é a lista de TextComponents com IDs reais.
            // Para evitar duplicidade e confusão com o item padrão "Text" (Maiúsculo),
            // podemos ignorar o grupo "text" (minúsculo) ou decidir qual usar.
            // Pelo seu JSON, os filhos de ColumnItens parecem ser Componentes Customizados
            // (que você chama de "component" ou "custom" na sidebar) E o Text Component
            // Padrão.

            // Vamos focar nos componentes Customizados (que geralmente são contêineres ou
            // designs complexos que o usuário criou para reutilizar).

            // Se você quer apenas **Custom Components** e o **Text Padrão**, mantenha a
            // lógica original,
            // mas talvez mudando o nome para maior clareza.
            // O que você realmente quer são os componentes que o usuário pode
            // **reutilizar**
            // como filhos. Assumimos que são os "component" (CustomComponent) e "column
            // items".

            // Se você quer *TODOS* os itens da sidebar, você faria:
            for (SimpleStringProperty idProperty : entry.getValue()) {
                // Adiciona o ID/nome de identificação do componente
                combo.getItems().add(idProperty.get());
            }
        }

        // **Alternativa Recomendada (Mais específica ao contexto de Coluna):**
        // Geralmente, uma coluna só pode ter componentes que são reutilizáveis (Custom
        // Componentes),
        // outras colunas, ou componentes simples (Text, Button, etc.).
        // Se você deseja listar todos os IDs **e tipos**, a lista de IDs de todos os
        // Custom Components
        // (como "1759102023348", "1759102056980") está espalhada pelo dataMap.

        // Para trazer **todos os IDs** que foram adicionados à sidebar:

        combo.getItems().clear(); // Limpa itens anteriores
        combo.getItems().add("Text"); // Adiciona o tipo padrão

        // Itera sobre todos os valores em todos os grupos (listas de IDs)
        context.getAllData().values().forEach(idList -> {
            for (SimpleStringProperty idProperty : idList) {
                String id = idProperty.get();
                // Verifica se o ID já não é o valor atual para evitar duplicidade
                if (!id.equals(currentChild.get())) {
                    combo.getItems().add(id);
                }
            }
        });

        // Garante que o item atual, mesmo que não esteja em 'nodes' ainda, esteja na
        // lista
        if (!combo.getItems().contains(currentChild.get())) {
            combo.getItems().add(currentChild.get());
        }

        // Mantém o valor selecionado
        combo.setValue(currentChild.get());

        combo.valueProperty().addListener((obs, old, newVal) -> {
            // ... (restante do código de listener permanece o mesmo)
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