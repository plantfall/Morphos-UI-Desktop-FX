package my_app.components.shared;

import javafx.scene.Node;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import my_app.components.ImageComponent;
import my_app.components.canvaComponent.CanvaComponent;
import my_app.data.Commons;

public class HeightComponent extends HBox {

    Text title = new Text("Height:");
    TextField tf = new TextField();

    public HeightComponent(Node node) {
        config();

        if (node instanceof ImageComponent imgview) {
            tf.setText(String.valueOf(imgview.getFitHeight()));
        }

        if (node instanceof CanvaComponent c) {
            tf.setText(String.valueOf(c.getPrefHeight()));
        }

        // dentro teria um NumericInputHandler
        tf.textProperty().addListener((_, _, newVal) -> {

            if (newVal.isBlank())
                return;

            try {
                double v = Double.parseDouble(newVal);

                if (node instanceof ImageComponent imgview)
                    imgview.setFitHeight(v);
            } catch (NumberFormatException err) {
                if (node instanceof CanvaComponent) {
                    setPrefHeight(Commons.CanvaHeightDefault);
                }
            }

        });

        getChildren().addAll(title, tf);
    }

    void config() {
        title.setFont(Font.font(14));
        title.setFill(Color.WHITE);
        setSpacing(10);
    }
}
