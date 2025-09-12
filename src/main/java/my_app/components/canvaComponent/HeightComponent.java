package my_app.components.canvaComponent;

import javafx.beans.property.ObjectProperty;
import javafx.scene.Node;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import my_app.data.Commons;

public class HeightComponent extends HBox {
    Text title = new Text("Height:");
    TextField tf = new TextField();

    public HeightComponent(ObjectProperty<Node> selectedNode) {

        Node node = selectedNode.get();

        config();

        loadNodeWidthInTextField(node);

        tf.textProperty().addListener((obs, old, newVal) -> {
            try {
                if (node instanceof CanvaComponent c) {
                    c.setPrefHeight(Double.parseDouble(newVal));
                }

            } catch (NumberFormatException err) {
                setPrefHeight(Commons.CanvaWidthDefault);
            }

        });

        getChildren().addAll(title, tf);

    }

    private void loadNodeWidthInTextField(Node node) {

        if (node instanceof CanvaComponent c) {
            double height = c.getPrefHeight();
            tf.setText(String.valueOf(height));
        }
    }

    void config() {
        title.setFont(Font.font(14));
        title.setFill(javafx.scene.paint.Color.WHITE);
        setSpacing(10);
    }
}
