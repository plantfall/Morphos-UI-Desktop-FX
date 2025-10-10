package my_app.data;

// ComponentFactory.java (NOVA CLASSE)
import javafx.scene.Node;
import my_app.components.CustomComponent;
import my_app.components.TextComponent;
import my_app.components.buttonComponent.ButtonComponent;
import my_app.components.columnComponent.ColumnComponent;
import my_app.components.inputComponents.InputComponent;

public class ComponentFactory {

    /**
     * Cria um novo componente JavaFX (Node) a partir do objeto de dados.
     * 
     * @param data O ComponentData contendo o estado.
     * @return O Node (Componente JavaFX) totalmente configurado.
     */
    public static Node createNodeFromData(ComponentData data) {
        if (data == null) {
            return null;
        }

        Node component = null;

        // O switch baseado no campo 'type' (que deve existir em todos os ComponentData)
        switch (data.type()) {
            case "text":
                // 1. Cria a instância
                component = new TextComponent("");
                // 2. Aplica os dados (assumindo que TextComponent implementa
                // applyData<TextComponentData>)
                ((ViewContract<TextComponentData>) component).applyData((TextComponentData) data);
                break;

            case "component":
                component = new CustomComponent();
                // Assumindo que o applyData do CustomComponent recebe CustomComponentData
                ((ViewContract<CustomComponentData>) component).applyData((CustomComponentData) data);
                break;

            case "button":
                component = new ButtonComponent();
                ((ViewContract<ButtonComponentData>) component).applyData((ButtonComponentData) data);
                break;

            case "column items":
                // Em casos de contêineres, evite recursão desnecessária no Factory.
                // O ColumnComponent aplica os dados e recria os filhos internamente.
                component = new ColumnComponent();
                ((ViewContract<ColumnComponentData>) component).applyData((ColumnComponentData) data);
                break;

            case "input":
                // Em casos de contêineres, evite recursão desnecessária no Factory.
                // O ColumnComponent aplica os dados e recria os filhos internamente.
                component = new InputComponent();
                ((ViewContract<InputComponentData>) component).applyData((InputComponentData) data);
                break;

            // Adicionar outros tipos de componentes (Input, Image, etc.)
            default:
                System.err.println("Tipo de componente desconhecido: " + data.type());
                return null;
        }

        return component;
    }
}