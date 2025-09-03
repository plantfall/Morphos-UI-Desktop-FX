package my_app.screens.Home;

import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.layout.BorderPane;
import my_app.components.CanvaComponent;

public class Home extends BorderPane {
    SimpleStringProperty selectedOption = new SimpleStringProperty("");

    SimpleObjectProperty<Node> visualNodeSelected = new SimpleObjectProperty<>();

    private CanvaComponent canva;

    @FunctionalInterface
    public interface VisualNodeCallback {
        public void set(Node n);
    }

    private void selectNode(Node node) {
        visualNodeSelected.set(node);
        System.out.println("Selecionado: " + node);
    }

    {
        setLeft(new LeftSide(selectedOption));

        this.canva = new CanvaComponent(selectedOption, currentNode -> selectNode(currentNode));

        setCenter(this.canva);
        setRight(new RightSide(visualNodeSelected));

        // setStyle("-fx-background-color:red");

    }

    public ObservableList<Node> canvaChildren() {
        return canva.getChildren();
    }
}
