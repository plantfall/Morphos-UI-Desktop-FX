package my_app.screens.Home;

import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.BorderPane;
import my_app.components.CanvaComponent;

public class Home extends BorderPane {
    SimpleStringProperty selectedOption = new SimpleStringProperty("");

    SimpleObjectProperty<Node> visualNodeSelected = new SimpleObjectProperty<>();

    public CanvaComponent canva;

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

    {
        setLeft(new LeftSide(selectedOption));

        ScrollPane editor = new ScrollPane();

        this.canva = new CanvaComponent(selectedOption, currentNode -> selectNode(currentNode));
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
