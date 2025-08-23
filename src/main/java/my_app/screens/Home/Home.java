package my_app.screens.Home;

import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.scene.Node;
import javafx.scene.layout.BorderPane;

public class Home extends BorderPane {
    SimpleStringProperty selectedOption = new SimpleStringProperty("");

    SimpleObjectProperty<Node> visualNodeSelected = new SimpleObjectProperty<>();

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

        var center = new Center(selectedOption, currentNode -> selectNode(currentNode));

        setCenter(center);
        setRight(new RightSide(visualNodeSelected));

        // setStyle("-fx-background-color:red");

    }
}
