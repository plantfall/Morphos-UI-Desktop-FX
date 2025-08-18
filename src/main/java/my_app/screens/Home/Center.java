package my_app.screens.Home;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.layout.StackPane;
import my_app.components.CanvaComponent;

public class Center extends StackPane {
    public Center(SimpleStringProperty selectedOption, ObjectProperty<Node> selectedNode) {

        // espaçamento horizontal (20px à esquerda e à direita) e 40px no topo
        setPadding(new Insets(0, 20, 0, 20));

        CanvaComponent canva = new CanvaComponent(selectedOption, selectedNode);

        // margem no topo só do Canva
        StackPane.setMargin(canva, new Insets(-50, 0, 0, 0));

        getChildren().add(canva);
    }
}
