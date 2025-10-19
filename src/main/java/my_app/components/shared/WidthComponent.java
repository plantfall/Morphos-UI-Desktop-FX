package my_app.components.shared;

import javafx.scene.Node;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import my_app.components.canvaComponent.CanvaComponent;
import my_app.components.imageComponent.ImageComponent;
import my_app.data.Commons;

public class WidthComponent extends HBox {

    Text title = new Text("Width:");
    TextField tf = new TextField();

    public WidthComponent(Node node) {
        config();

        if (node instanceof ImageComponent imgview) {
            tf.setText(String.valueOf(imgview.getFitWidth()));
        }

        if (node instanceof CanvaComponent c) {
            tf.setText(String.valueOf(c.getPrefWidth()));
        }

        tf.textProperty().addListener((_, _, newVal) -> {
            if (newVal.isBlank())
                return;

            try {
                double v = Double.parseDouble(newVal);
                if (node instanceof ImageComponent imgview) {
                    imgview.setFitWidth(v);
                } else if (node instanceof CanvaComponent c) {
                    c.setPrefWidth(v);
                }

            } catch (NumberFormatException err) {
                System.out.println(err.getMessage());
                if (node instanceof CanvaComponent) {
                    setPrefWidth(Commons.CanvaWidthDefault);
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
