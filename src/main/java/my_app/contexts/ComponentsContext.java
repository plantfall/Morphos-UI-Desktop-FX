package my_app.contexts;

import java.io.File;
import java.util.ArrayList;
import java.util.Optional;

import com.fasterxml.jackson.databind.ObjectMapper;

import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Node;
import my_app.components.CustomComponent;
import my_app.components.ImageComponent;
import my_app.components.TextComponent;
import my_app.components.buttonComponent.ButtonComponent;
import my_app.components.canvaComponent.CanvaComponent;
import my_app.components.columnComponent.ColumnItens;
import my_app.components.inputComponents.InputComponent;
import my_app.data.ButtonComponentData;
import my_app.data.ColumnComponentData;
import my_app.data.Commons;
import my_app.data.CustomComponentData;
import my_app.data.ImageComponentData;
import my_app.data.InputComponentData;
import my_app.data.StateJson_v2;
import my_app.data.TextComponentData;
import my_app.scenes.ShowComponentScene.ShowComponentScene;
import my_app.screens.Home.Home;

public class ComponentsContext {

    private static ComponentsContext instance;

    public static SimpleObjectProperty<Node> nodeSelected = new SimpleObjectProperty<>();

    static SubItemsContext subItemsContext = SubItemsContext.getInstance();

    public static ObservableList<Node> nodes = FXCollections.observableArrayList(new ArrayList<>());

    public static boolean CurrentNodeIsSelected(String nodeId) {
        return nodeSelected.get().getId().equals(nodeId);
    }

    public static void loadJsonState(File file, CanvaComponent canvaComponent) {
        ObjectMapper om = new ObjectMapper();
        canvaComponent.getChildren().clear();
        nodes.clear();
        subItemsContext.clearAllItems();

        if (!file.exists() || file.length() == 0) {
            return;
        }

        try {
            var state = om.readValue(file, StateJson_v2.class);
            canvaComponent.applyData(state.canva);

            for (TextComponentData data : state.text_components) {
                TextComponent comp = new TextComponent(data.text());
                comp.applyData(data);
                nodes.add(comp);

                subItemsContext.addItem("text", data.identification());

                if (data.in_canva()) {
                    canvaComponent.addElementDragable(comp, false);
                }
            }

            // Restaura os botões
            for (ButtonComponentData data : state.button_components) {
                ButtonComponent comp = new ButtonComponent();

                comp.applyData(data);
                nodes.add(comp);
                subItemsContext.addItem("button", data.identification());

                if (data.in_canva()) {
                    canvaComponent.addElementDragable(comp, false);
                }
            }

            // Restaura as imagens
            for (ImageComponentData data : state.image_components) {
                ImageComponent comp = new ImageComponent();

                comp.applyData(data);
                nodes.add(comp);
                subItemsContext.addItem("image", data.identification());

                if (data.in_canva()) {
                    canvaComponent.addElementDragable(comp, false);
                }
            }

            // Restaura inputs
            for (InputComponentData data : state.input_components) {
                InputComponent comp = new InputComponent("");

                comp.applyData(data);
                nodes.add(comp);
                subItemsContext.addItem("input", data.identification());

                if (data.in_canva()) {
                    canvaComponent.addElementDragable(comp, false);

                }
            }

            for (CustomComponentData data : state.custom_components) {
                var comp = new CustomComponent();

                comp.applyData(data);
                nodes.add(comp);

                subItemsContext.addItem("component", data.identification);

                if (data.in_canva) {
                    canvaComponent.addElementDragable(comp, false);

                }
            }

            for (ColumnComponentData data : state.column_components) {
                var comp = new ColumnItens();

                comp.applyData(data);
                nodes.add(comp);

                subItemsContext.addItem("column items", data.identification());

                if (data.in_canva()) {
                    canvaComponent.addElementDragable(comp, false);
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public static void AddComponent(String type, Home home) {
        SubItemsContext subItemsContext = SubItemsContext.getInstance();

        if (type == null || type.isBlank()) {
            return;
        }

        Node node = null;
        var content = "Im new here";

        if (type.equalsIgnoreCase("Button")) {
            node = new ButtonComponent(content);
        } else if (type.equalsIgnoreCase("Input")) {
            node = new InputComponent(content);

        } else if (type.equalsIgnoreCase("Text")) {
            node = new TextComponent(content);

        } else if (type.equalsIgnoreCase("Image")) {
            node = new ImageComponent(ComponentsContext.class.getResource("/assets/images/mago.jpg").toExternalForm());

        } else if (type.equalsIgnoreCase("Component")) {
            new ShowComponentScene(home.canva).stage.show();
            return;
        } else if (type.equalsIgnoreCase("Column items")) {
            node = new ColumnItens();
        }

        if (node != null) {

            String nodeId = node.getId();
            nodes.add(node);

            // 1. Adiciona o nó ao Canva.
            // Isso executa canvaComponent.addElementDragable(node, true);
            // QUE POR SUA VEZ CHAMA SelectNode(node);
            home.canva.addElementDragable(node, true);

            // AGORA o nó está SELECIONADO (estado vermelho = verdadeiro) no
            // ComponentsContext.
            // 2. ADICIONA O ITEM À SIDEBAR (subItemsContext).
            // Isso DISPARA o listener no Option e chama loadSubItems().
            // Como o nó já está selecionado, loadSubItems() criará o item com a cor
            // vermelha.
            subItemsContext.addItem(type.toLowerCase(), nodeId);

        }
    }

    public void addCustomComponent(Node customComponent, CanvaComponent mainCanva) {
        String nodeId = customComponent.getId();
        nodes.add(customComponent); // Adiciona à lista mestre
        subItemsContext.addItem("component", nodeId);
    }

    public static Optional<Node> SearchNodeByIdInNodesList(String nodeId) {
        var op = nodes.stream().filter(it -> it.getId().equals(nodeId))
                .findFirst();

        return op;
    }

    public static Node SearchNodeByIdInMainCanva(String nodeId, ObservableList<Node> canvaChildren) {
        // lookin for custom component in main canva
        var target = canvaChildren.stream()
                .filter(n -> nodeId.equals(n.getId()))
                .findFirst()
                .orElse(null);

        return target;
    }

    public static void SelectNode(Node node) {
        nodeSelected.set(node);
        System.out.println("Selecionado: " + node);
    }

    public static void SaveStateInJsonFile_v2(File file, CanvaComponent mainCanvaComponent) {
        try {
            // Gera o StateJson_v2 a partir dos Nodes e do CanvaComponent
            StateJson_v2 data = CreateStateData(mainCanvaComponent);

            // Usa Commons para escrever os dados no disco
            Commons.WriteJsonInDisc(file, data);

            System.out.println("Estado salvo com sucesso no arquivo: " + file.getAbsolutePath());
        } catch (Exception e) {
            System.err.println("Erro ao salvar o estado no arquivo JSON.");
            e.printStackTrace();
        }
    }

    static StateJson_v2 CreateStateData(CanvaComponent canva) {
        StateJson_v2 jsonTarget = new StateJson_v2();

        // 1. Salva as propriedades do CanvaComponent
        jsonTarget.canva = canva.getData();

        // 2. Itera sobre a lista de todos os nós (nodes)
        for (Node node : nodes) {

            if (node instanceof TextComponent component) {
                // O .getData() deve retornar um TextComponentData que inclui a flag 'in_canva'
                jsonTarget.text_components.add(component.getData());
            }

            if (node instanceof ButtonComponent component) {
                jsonTarget.button_components.add(component.getData());
            }

            if (node instanceof ImageComponent component) {
                jsonTarget.image_components.add(component.getData());
            }

            if (node instanceof InputComponent component) {
                jsonTarget.input_components.add(component.getData());
            }

            // Se o FlexComponent for uma composição de outros nós, ele deve serializar seus
            // filhos internamente.
            // CustomComponent, se for salvo como InnerComponentData.
            // Verifique se o getData() dele é compatível com InnerComponentData.
            // **Atenção:** Se ele for uma instância que contém outros componentes,
            // sua lógica de getData() deve ser recursiva (salvar seus filhos).
            if (node instanceof CustomComponent component) {
                // Supondo que getData() retorne InnerComponentData ou StateJson_v2 completo
                jsonTarget.custom_components.add(component.getData());
            }

            if (node instanceof ColumnItens component) {
                jsonTarget.column_components.add(component.getData());
            }

        }

        return jsonTarget;
    }

    public static ComponentsContext getInstance() {
        if (instance == null) {
            instance = new ComponentsContext();
        }
        return instance;
    }
}
