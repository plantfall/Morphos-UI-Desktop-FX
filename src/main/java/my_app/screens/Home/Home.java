package my_app.screens.Home;

import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.scene.Node;
import javafx.scene.layout.BorderPane;

public class Home extends BorderPane {
    SimpleStringProperty selectedOption = new SimpleStringProperty("");
    SimpleObjectProperty<Node> selectedNode = new SimpleObjectProperty<>();

    {
        setLeft(new LeftSide(selectedOption));

        var center = new Center(selectedOption, selectedNode);

        setCenter(center);
        setRight(new RightSide(selectedNode));

        // setStyle("-fx-background-color:red");

    }
}
