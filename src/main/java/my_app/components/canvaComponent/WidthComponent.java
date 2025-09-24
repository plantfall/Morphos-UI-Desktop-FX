package my_app.components.canvaComponent;

import javafx.scene.Node;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import my_app.data.Commons;

public class WidthComponent extends HBox {
    Text title = new Text("Width:");
    TextField tf = new TextField();

    public WidthComponent(Node selectedNode) {

        Node node = selectedNode;

        config();

        loadNodeWidthInTextField(node);

        tf.textProperty().addListener((obs, old, newVal) -> {
            try {
                if (node instanceof CanvaComponent c) {
                    c.setPrefWidth(Double.parseDouble(newVal));
                }

            } catch (NumberFormatException err) {
                setPrefWidth(Commons.CanvaWidthDefault);
            }

        });

        getChildren().addAll(title, tf);
    }

    private void loadNodeWidthInTextField(Node node) {

        if (node instanceof CanvaComponent c) {
            double width = c.getPrefWidth();
            tf.setText(String.valueOf(width));
        }
    }

    void config() {
        title.setFont(Font.font(14));
        title.setFill(javafx.scene.paint.Color.WHITE);
        setSpacing(10);
    }
}
