package my_app.components.shared;

import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import my_app.contexts.ComponentsContext;

public class ButtonRemoverComponent extends HBox {

    Button btn = new Button("Remove component");

    public ButtonRemoverComponent(Node node) {

        config();

        getChildren().add(btn);

        btn.setOnAction(_ -> ComponentsContext.RemoveNode(node.getId()));

    }

    void config() {
        // title.setFont(Font.font(14));
        // title.setFill(Color.WHITE);

    }
}
