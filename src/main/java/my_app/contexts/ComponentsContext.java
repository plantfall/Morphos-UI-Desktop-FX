package my_app.contexts;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Optional;

import com.fasterxml.jackson.databind.ObjectMapper;

import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.ScaleTransition;
import javafx.animation.Timeline;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
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
import my_app.data.FlexComponentData;
import my_app.data.ImageComponentData;
import my_app.data.InnerComponentData;
import my_app.data.InputComponentData;
import my_app.data.StateJson;
import my_app.data.StateJson_v2;
import my_app.data.TextComponentData;
import my_app.data.ViewContract;
import my_app.scenes.ShowComponentScene.ShowComponentScene;
import my_app.screens.Home.Home;

public class ComponentsContext {

    private static ComponentsContext instance;

    public static SimpleStringProperty idOfComponentSelected = new SimpleStringProperty("");
    public static SimpleObjectProperty<Node> visualNodeSelected = new SimpleObjectProperty<>();

    SubItemsContext subItemsContext = SubItemsContext.getInstance();

    @Deprecated
    public ObservableList<StateJson> componentsList = FXCollections.observableList(new ArrayList<>());

    public ObservableList<Node> nodes = FXCollections.observableArrayList(new ArrayList<>());
    //

    public void loadJsonState(File file, CanvaComponent canvaComponent) {
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
                    canvaComponent.addElement(comp);
                    canvaComponent.setOnClickMethodToNode(comp, this::selectNode);
                }
            }

            // Restaura os botões
            for (ButtonComponentData data : state.button_componentes) {
                ButtonComponent comp = new ButtonComponent();

                comp.applyData(data);
                nodes.add(comp);
                subItemsContext.addItem("button", data.identification());

                if (data.in_canva()) {
                    canvaComponent.addElement(comp);
                    canvaComponent.setOnClickMethodToNode(comp, this::selectNode);
                }
            }

            // Restaura as imagens
            for (ImageComponentData data : state.image_components) {
                ImageComponent comp = new ImageComponent();

                comp.applyData(data);
                nodes.add(comp);
                subItemsContext.addItem("image", data.identification());

                if (data.in_canva()) {
                    canvaComponent.addElement(comp);
                    canvaComponent.setOnClickMethodToNode(comp, this::selectNode);
                }
            }

            // Restaura inputs
            for (InputComponentData data : state.input_components) {
                InputComponent comp = new InputComponent("");

                comp.applyData(data);
                nodes.add(comp);
                subItemsContext.addItem("input", data.identification());

                if (data.in_canva()) {
                    canvaComponent.addElement(comp);
                    canvaComponent.setOnClickMethodToNode(comp, this::selectNode);
                }
            }

            for (InnerComponentData data : state.custom_components) {
                var comp = new CustomComponent();

                comp.applyData(data);
                nodes.add(comp);

                subItemsContext.addItem("component", data.identification);

                if (data.in_canva) {
                    canvaComponent.addElement(comp);
                    canvaComponent.setOnClickMethodToNode(comp, this::selectNode);
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
        mainCanva.setOnClickMethodToNode(customComponent, this::selectNode);

        // animateOnEntry(customComponent);
    }

    public void onClickOnSubItem(String itemIdentification, String type, CanvaComponent mainCanvaComponent) {

        var canvaChildren = mainCanvaComponent.getChildren();

        var op = searchNodeById(itemIdentification);

        op.ifPresent(state -> {
            // lookin for custom component in main canva
            var target = searchNodeByIdInMainCanva(itemIdentification, canvaChildren);
            // 2. finded in main canva so, selected
            if (target != null) {
                selectNode(target);
            } else {
                // if not, just create and add in canva
                // constroi componente que também é um canva

                CustomComponent customComponent = new CustomComponent();

                // customComponent.applyData(state);
                mainCanvaComponent.addElementDragable(customComponent);
                mainCanvaComponent.setOnClickMethodToNode(customComponent, this::selectNode);
            }

        });

    }

    @Deprecated
    public Optional<StateJson> searchNodeById(String nodeId) {

        var op = componentsList.stream().filter(it -> it.self.identification.equals(nodeId))
                .findFirst();

        return op;
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

    public void selectNode(Node node) {
        visualNodeSelected.set(node);
        ComponentsContext.idOfComponentSelected.set(node.getId());

        shake(node);
        System.out.println("Selecionado: " + node);

        System.out.println("estilo do compponente selecionado: ");
        System.out.println(node.getStyle());
    }

    // achacoalhar
    void shake(Node node) {

        Timeline timeline = new Timeline(
                new KeyFrame(Duration.ZERO, new KeyValue(node.translateXProperty(), 0)),
                new KeyFrame(Duration.millis(100), new KeyValue(node.translateXProperty(), -1)),
                new KeyFrame(Duration.millis(200), new KeyValue(node.translateXProperty(), 1)),
                new KeyFrame(Duration.millis(300), new KeyValue(node.translateXProperty(), -1)),
                new KeyFrame(Duration.millis(400), new KeyValue(node.translateXProperty(), 1)),
                new KeyFrame(Duration.millis(500), new KeyValue(node.translateXProperty(), -1)),
                new KeyFrame(Duration.millis(600), new KeyValue(node.translateXProperty(), 0)));
        timeline.setCycleCount(1);
        timeline.play();
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

    public void addComponent(String type, Home home) {
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
            node = new ImageComponent(getClass().getResource("/assets/images/mago.jpg").toExternalForm());

        } else if (type.equals("Component")) {
            new ShowComponentScene(home.canva).stage.show();
            return;
        } else if (type.equals("Flex items")) {
            node = new FlexComponent();
        }

        if (node != null) {
            String nodeId = node.getId();
            subItemsContext.addItem(type.toLowerCase(), nodeId);

            canvaComponent.addElementDragable(node);
            canvaComponent.setOnClickMethodToNode(node, this::selectNode);

            animateOnEntry(node);
        }
    }

    void animateOnEntry(Node node) {
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
