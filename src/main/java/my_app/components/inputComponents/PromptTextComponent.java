package my_app.components.inputComponents;

import javafx.beans.property.ObjectProperty;
import javafx.scene.Node;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import my_app.components.buttonComponent.ButtonComponent;

public class PromptTextComponent extends HBox {

    Text text = new Text("Placeholder:");
    TextField tf = new TextField();

    public PromptTextComponent(ObjectProperty<Node> selectedNode) {

        text.setFont(Font.font(14));
        text.setFill(Color.WHITE);

        setSpacing(10);

        var node = (InputComponent) selectedNode.get();

        tf.setText(node.getPromptText());

        tf.textProperty().addListener((obs, old, val) -> {
            if (!val.trim().isEmpty()) {
                node.setPromptText(val);
            }
        });

        getChildren().addAll(text, tf);

    }
}
