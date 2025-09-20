package my_app.screens.Home;

import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.ScaleTransition;
import javafx.animation.Timeline;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.BorderPane;
import javafx.util.Duration;
import my_app.App;
import my_app.components.CustomComponent;
import my_app.components.ImageComponent;
import my_app.components.TextComponent;
import my_app.components.buttonComponent.ButtonComponent;
import my_app.components.canvaComponent.CanvaComponent;
import my_app.components.flexComponent.FlexComponent;
import my_app.components.inputComponents.InputComponent;
import my_app.contexts.SubItemsContext;
import my_app.scenes.ShowComponentScene.ShowComponentScene;
import my_app.screens.Home.leftside.LeftSide;

public class Home extends BorderPane {
    SimpleObjectProperty<Node> visualNodeSelected = new SimpleObjectProperty<>();

    public static SimpleStringProperty idOfComponentSelected = new SimpleStringProperty("");

    public CanvaComponent canva;

    @FunctionalInterface
    public interface HandleClickSubItem {
        public void onClick(String itemIdentification, String type);
    }

    void onClickAdd(String type) {
        SubItemsContext context = SubItemsContext.getInstance();

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
            new ShowComponentScene().stage.show();
            return;
        } else if (type.equals("Flex items")) {
            node = new FlexComponent();
        }

        if (node != null) {
            canva.addElementDragable(node);
            canva.setOnClickMethodToNode(node, this::selectNode);
            // callback.set(node);

            // criar o node com o id
            // e o subitem com o mesmo id
            var nodeId = node.getId();
            context.addItem(type.toLowerCase(), nodeId);
            idOfComponentSelected.set(nodeId);
            visualNodeSelected.set(node);

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

    void onClickOnSubItem(String itemIdentification, String type) {

        if (type.equalsIgnoreCase("component")) {
            // new ShowComponentScene().stage.show();
            var op = App.ComponentsList.stream().filter(it -> it.self.identification.equals(itemIdentification))
                    .findFirst();

            if (op.isPresent()) {
                // lookin for custom component in canva
                Node target = canvaChildren()
                        .stream()
                        .filter(n -> itemIdentification.equals(n.getId()))
                        .findFirst()
                        .orElse(null);
                // 2. finded so, selected
                if (target != null) {
                    selectNode(target);
                }

                else {
                    // if not, just create and add in canva
                    var cc = new CustomComponent();
                    canva.addElementDragable(cc, currentNode -> selectNode(currentNode));
                    cc.applyData(op.get());
                }
            }

            return;
        }
        // basics components (text, image...)
        Node target = canvaChildren()
                .stream()
                .filter(n -> itemIdentification.equals(n.getId()))
                .findFirst()
                .orElse(null);

        //
        if (target != null) {
            selectNode(target);
        }
    }

    @FunctionalInterface
    public interface VisualNodeCallback {
        public void set(Node n);
    }

    public void selectNode(Node node) {
        visualNodeSelected.set(node);
        idOfComponentSelected.set(node.getId());

        shake(node);
        System.out.println("Selecionado: " + node);

        System.out.println("estilo do compponente selecionado: ");
        System.out.println(node.getStyle());
    }

    public Home(boolean openComponentScene) {
        setLeft(new LeftSide(this::onClickAdd, this::onClickOnSubItem));

        ScrollPane editor = new ScrollPane();

        this.canva = new CanvaComponent();

        if (openComponentScene) {
            canva.setPrefSize(370, 250);
        }
        // scrollPane mostra o canva com barras se for maior que a janela
        editor.setContent(canva);
        editor.setFitToWidth(false);
        editor.setFitToHeight(false);

        // setCenter(this.canva);
        setCenter(editor);
        setRight(new RightSide(visualNodeSelected));

        // setStyle("-fx-background-color:red");

    }

    public ObservableList<Node> canvaChildren() {
        return canva.getChildren();
    }
}
