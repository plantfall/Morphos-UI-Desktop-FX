package my_app.screens.Home;

import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.BorderPane;
import my_app.App;
import my_app.components.CustomComponent;
import my_app.components.canvaComponent.CanvaComponent;
import my_app.data.Commons;

public class Home extends BorderPane {
    SimpleStringProperty selectedOption = new SimpleStringProperty("");

    SimpleObjectProperty<Node> visualNodeSelected = new SimpleObjectProperty<>();

    public CanvaComponent canva;

    @FunctionalInterface
    public interface HandleClickSubItem {
        public void onClick(String itemIdentification, String type);
    }

    void onClickOnSubItem(String itemIdentification, String type) {

        if (type.equalsIgnoreCase("component")) {
            // new ShowComponentScene().stage.show();
            var op = App.ComponentsList.stream().filter(it -> it.self.identification().equals(itemIdentification))
                    .findFirst();

            if (op.isPresent()) {
                // vou buscar o custom component no canva
                Node target = canvaChildren()
                        .stream()
                        .filter(n -> itemIdentification.equals(n.getId()))
                        .findFirst()
                        .orElse(null);

                // 2. Se achou, seleciona
                if (target != null) {
                    selectNode(target);
                }

                else {
                    // senao achou, cria e adiciona

                    var cc = new CustomComponent();
                    canva.addElementDragable(cc, currentNode -> selectNode(currentNode));
                    cc.applyConfig(op.get());
                    // node = new CustomComponent(op.get());
                }

            }

            return;
        }
        //
        Node target = canvaChildren()
                .stream()
                .filter(n -> itemIdentification.equals(n.getId()))
                .findFirst()
                .orElse(null);

        // 2. Se achou, seleciona
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
        setLeft(new LeftSide(selectedOption, this::onClickOnSubItem));

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
