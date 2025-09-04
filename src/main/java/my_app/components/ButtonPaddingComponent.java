package my_app.components;

import javafx.beans.property.ObjectProperty;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

public class ButtonPaddingComponent extends HBox {

    Text title = new Text("Paddings:");
    TextField tf = new TextField();

    public ButtonPaddingComponent(ObjectProperty<Node> selectedNode) {

        config();

        ButtonComponent node = (ButtonComponent) selectedNode.get();

        Insets pad = node.getPadding();
        double padValue = pad != null ? pad.getTop() : 0;

        tf.setText(String.valueOf(padValue));

        tf.textProperty().addListener((o, old, newVal) -> {
            if (!newVal.isBlank()) {
                try {
                    double v = Double.parseDouble(newVal);

                    // Atualize apenas o padding sem afetar o restante do estilo
                    node.setPadding(new Insets(v));

                    // Se o botão tiver um estilo CSS que modifica padding, preserve o restante do
                    // estilo
                    String currentStyle = node.getStyle();
                    node.setStyle(currentStyle); // Preserva o estilo CSS atual
                } catch (NumberFormatException ignored) {
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
