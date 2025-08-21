package my_app.components;

import javafx.beans.property.ObjectProperty;
import javafx.scene.Node;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

public class FontSizeComponent extends HBox {

    Text text = new Text("Font size:");
    TextField tf = new TextField();

    public FontSizeComponent(ObjectProperty<Node> selectedNode) {

        text.setFont(Font.font(14));
        text.setFill(Color.WHITE);

        setSpacing(10);

        loadNodeFontSizeInTextField(selectedNode.get());

        tf.textProperty().addListener((obs, old, val) -> {
            Node node = selectedNode.get();

            if (node instanceof ButtonComponent b) {
                b.setFont(new Font(Double.valueOf(val)));
            } else if (node instanceof TextField t) {
                t.setFont(new Font(Double.valueOf(val)));
            } else if (node instanceof Text txt) {
                txt.setFont(new Font(Double.valueOf(val)));
            }
        });

        getChildren().addAll(text, tf);

    }

    private void loadNodeFontSizeInTextField(Node node) {
        double size = 12; // fallback

        if (node instanceof ButtonComponent b) {
            if (b.getFont() != null)
                size = b.getFont().getSize();
        } else if (node instanceof TextField t) {
            if (t.getFont() != null)
                size = t.getFont().getSize();
        } else if (node instanceof TextComponent txt) {
            if (txt.getFont() != null)
                size = txt.getFont().getSize();
        }

        tf.setText(String.valueOf((int) size));

    }
}
