package my_app.components.buttonComponent;

import javafx.beans.property.ObjectProperty;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import my_app.data.Commons;

public class ButtonPaddingComponent extends HBox {

    Text title = new Text("Paddings:");
    TextField tf = new TextField();

    public ButtonPaddingComponent(ObjectProperty<Node> selectedNode) {

        config();

        ButtonComponent node = (ButtonComponent) selectedNode.get();

        String currentPadding = Commons.getValueOfSpecificField(node.getStyle(), "-fx-padding");

        if (currentPadding.isEmpty()) {
            currentPadding = Commons.ButtonPaddingDefault;
        }

        System.out.println("currentPadding: " + currentPadding);

        tf.setText(currentPadding);

        tf.textProperty().addListener((o, old, newVal) -> {
            String existingStyle = node.getStyle();

            if (!newVal.isBlank()) {
                try {
                    String newStyle = Commons.UpdateEspecificStyle(existingStyle, "-fx-padding",
                            newVal.trim());

                    // Aplica o estilo com a modificação
                    node.setStyle(newStyle);
                } catch (NumberFormatException ignored) {
                }
            }
            if (newVal.trim().isEmpty()) {
                System.out.println(newVal.isEmpty());

                String newStyle = Commons.UpdateEspecificStyle(existingStyle, "-fx-padding",
                        "0");

                // Aplica o estilo com a modificação
                node.setStyle(newStyle);
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
