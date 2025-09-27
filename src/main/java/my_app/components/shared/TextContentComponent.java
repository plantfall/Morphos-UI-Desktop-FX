package my_app.components.shared;

import javafx.beans.property.ObjectProperty;
import javafx.scene.Node;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import my_app.components.TextComponent;
import my_app.components.buttonComponent.ButtonComponent;

public class TextContentComponent extends HBox {

    Text text = new Text("Text content:");
    TextField tf = new TextField();

    public TextContentComponent(ObjectProperty<Node> selectedNode) {

        text.setFont(Font.font(14));
        text.setFill(Color.WHITE);

        setSpacing(10);

        Node node = selectedNode.get();

        if (node instanceof ButtonComponent b) {
            this.tf.setText(b.getText());
        } else if (node instanceof TextField t) {
            this.tf.setText(t.getText());
        } else if (node instanceof TextComponent txt) {
            this.tf.setText(txt.getText());
        }

        tf.textProperty().addListener((obs, old, val) -> {
            Node n = selectedNode.get();

            if (n instanceof ButtonComponent b) {
                b.setText(val);
            } else if (n instanceof TextField t) {
                t.setText(val);
            } else if (n instanceof TextComponent txt) {
                txt.setText(val);
            }
        });

        getChildren().addAll(text, tf);

    }
}
