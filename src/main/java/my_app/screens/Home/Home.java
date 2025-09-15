package my_app.screens.Home;

import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.BorderPane;
import my_app.App;
import my_app.components.CustomComponent;
import my_app.components.ImageComponent;
import my_app.components.TextComponent;
import my_app.components.buttonComponent.ButtonComponent;
import my_app.components.canvaComponent.CanvaComponent;
import my_app.components.inputComponents.InputComponent;
import my_app.contexts.SubItemsContext;
import my_app.scenes.ShowComponentScene.ShowComponentScene;
import my_app.screens.Home.leftside.LeftSide;

public class Home extends BorderPane {
    SimpleStringProperty selectedOption = new SimpleStringProperty("");

    SimpleObjectProperty<Node> visualNodeSelected = new SimpleObjectProperty<>();

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
        }

        if (node != null) {
            canva.addElementDragable(node, s -> {
            });
            // callback.set(node);
        }

        // criar o node com o id
        // e o subitem com o mesmo id
        var nodeId = node.getId();
        context.addItem(type.toLowerCase(), nodeId);
        // optionSelected.set("");

        // adiciona subitem na lista

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
        System.out.println("Selecionado: " + node);

        System.out.println("estilo do compponente selecionado: ");
        System.out.println(node.getStyle());

    }

    public Home(boolean openComponentScene) {
        setLeft(new LeftSide(this::onClickAdd, this::onClickOnSubItem));

        ScrollPane editor = new ScrollPane();

        this.canva = new CanvaComponent(selectedOption, currentNode -> selectNode(currentNode));

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
