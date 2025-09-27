package my_app.contexts;

import java.io.File;
import java.util.ArrayList;
import java.util.Optional;

import com.fasterxml.jackson.databind.ObjectMapper;

import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.ScaleTransition;
import javafx.animation.Timeline;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.util.Duration;
import my_app.components.CustomComponent;
import my_app.components.ImageComponent;
import my_app.components.TextComponent;
import my_app.components.buttonComponent.ButtonComponent;
import my_app.components.canvaComponent.CanvaComponent;
import my_app.components.flexComponent.FlexComponent;
import my_app.components.inputComponents.InputComponent;
import my_app.data.ButtonComponentData;
import my_app.data.Commons;
import my_app.data.ImageComponentData;
import my_app.data.InnerComponentData;
import my_app.data.InputComponentData;
import my_app.data.StateJson;
import my_app.data.StateJson_v2;
import my_app.data.TextComponentData;
import my_app.scenes.ShowComponentScene.ShowComponentScene;
import my_app.screens.Home.Home;

public class ComponentsContext {

    private static ComponentsContext instance;

    public static SimpleObjectProperty<Node> nodeSelected = new SimpleObjectProperty<>();

    static SubItemsContext subItemsContext = SubItemsContext.getInstance();

    public static ObservableList<Node> nodes = FXCollections.observableArrayList(new ArrayList<>());
    //

    public static boolean CurrentNodeIsSelected(String nodeId) {
        return nodeSelected.get().getId().equals(nodeId);
    }

    public static void loadJsonState(File file, CanvaComponent canvaComponent) {
        ObjectMapper om = new ObjectMapper();
        canvaComponent.getChildren().clear();

        if (!file.exists() || file.length() == 0)
            return;

        try {
            var state = om.readValue(file, StateJson_v2.class);
            canvaComponent.applyData(state.canva);

            for (TextComponentData data : state.text_componentes) {
                TextComponent comp = new TextComponent(data.text());
                comp.applyData(data);
                nodes.add(comp);

                subItemsContext.addItem("text", data.identification());

                if (data.in_canva()) {
                    canvaComponent.addElementDragable(comp, false);
                }
            }

            // Restaura os botões
            for (ButtonComponentData data : state.button_componentes) {
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

            for (InnerComponentData data : state.custom_components) {
                var comp = new CustomComponent();

                comp.applyData(data);
                nodes.add(comp);

                subItemsContext.addItem("component", data.identification);

                if (data.in_canva) {
                    canvaComponent.addElementDragable(comp, false);

                }
            }

            // for (FlexComponentData data : state.flex_componentes) {
            // var comp = new FlexComponent();

            // canvaComponent.addElementDragable(comp, this::selectNode);

            // comp.applyData(data);

            // // como o custom component já existe no canva, posso adicionar ele aqui se
            // // pertencer tbm ao flex component
            // var target = searchNodeByIdInMainCanva(
            // data.childId(), canvaComponent.getChildren());

            // if (target != null) {
            // comp.getChildren().clear();
            // comp.getChildren().add(target);

            // }

            // // subItemsContext.addItem("component", data.self.identification);
            // }

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    //

    public void addCustomComponent(Node customComponent, CanvaComponent mainCanva) {

        String nodeId = customComponent.getId();
        subItemsContext.addItem("component", nodeId);

        // mainCanva.addElementDragable(customComponent);

        // animateOnEntry(customComponent);
    }

    public void onClickOnSubItem(String itemIdentification, String type, CanvaComponent mainCanvaComponent) {

        var canvaChildren = mainCanvaComponent.getChildren();

        var op = searchNodeByIdInNodesList(itemIdentification);

        op.ifPresent(state -> {
            // lookin for custom component in main canva
            var target = searchNodeByIdInMainCanva(itemIdentification, canvaChildren);
            // 2. finded in main canva so, selected
            if (target != null) {
                SelectNode(target);
            } else {
                // if not, just add in canva
                mainCanvaComponent.addElementDragable(op.get(), false);
            }
        });

    }

    public static void AddComponent(String type, Home home) {
        SubItemsContext subItemsContext = SubItemsContext.getInstance();

        CanvaComponent canvaComponent = home.canva;

        if (type == null || type.isBlank())
            return;

        Node node = null;
        var content = "Im new here";

        if (type.equals("Button")) {
            node = new ButtonComponent(content);
        } else if (type.equals("Input")) {
            node = new InputComponent(content);

        } else if (type.equals("Text")) {
            node = new TextComponent(content);

        } else if (type.equals("Image")) {
            node = new ImageComponent(ComponentsContext.class.getResource("/assets/images/mago.jpg").toExternalForm());

        } else if (type.equals("Component")) {
            new ShowComponentScene(home.canva).stage.show();
            return;
        } else if (type.equals("Flex items")) {
            node = new FlexComponent();
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

    public Optional<Node> searchNodeByIdInNodesList(String nodeId) {
        var op = nodes.stream().filter(it -> it.getId().equals(nodeId))
                .findFirst();

        return op;
    }

    public Node searchNodeByIdInMainCanva(String nodeId, ObservableList<Node> canvaChildren) {
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

    // @Deprecated
    // public void loadJsonCustomComponents(File file) {
    // ObjectMapper om = new ObjectMapper();

    // if (!file.exists())
    // return;

    // if (file.exists() && file.length() == 0)
    // return;

    // try {
    // StateJson[] componentsArray = om.readValue(file, StateJson[].class);

    // var list = new ArrayList<>(Arrays.asList(componentsArray));

    // list.forEach(it -> {
    // componentsList.add(it);
    // subItemsContext.addItem("component", it.self.identification);

    // System.out.println("aqui: " + it.self.identification);
    // });

    // } catch (Exception e) {
    // e.printStackTrace();
    // }
    // }

    public void saveStateInJsonFile(File file, CanvaComponent mainCanvaComponent) {
        StateJson data = Commons.CreateStateData(mainCanvaComponent);
        Commons.WriteJsonInDisc(file, data);
    }

    static void animateOnEntry(Node node) {
        ScaleTransition st = new ScaleTransition(Duration.millis(400), node);
        st.setFromX(0.5);
        st.setFromY(0.5);
        st.setToX(1);
        st.setToY(1);

        st.play();
    }

    //

    public static ComponentsContext getInstance() {
        if (instance == null) {
            instance = new ComponentsContext();
        }
        return instance;
    }
}
