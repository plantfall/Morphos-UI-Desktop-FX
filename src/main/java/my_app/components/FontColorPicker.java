package my_app.components;

import javafx.beans.property.ObjectProperty;
import javafx.scene.Node;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import my_app.components.buttonComponent.ButtonComponent;
import my_app.data.Commons;

public class FontColorPicker extends HBox {

    ColorPicker colorPicker = new ColorPicker(Color.WHITE);
    Text fontColorText = new Text("Font Color:");

    public FontColorPicker(ObjectProperty<Node> selectedNode) {

        fontColorText.setFont(Font.font(14));
        fontColorText.setFill(Color.WHITE);

        setSpacing(10);

        Node node = selectedNode.get();

        if (node instanceof ButtonComponent) {

            String textFill = Commons.getValueOfSpecificField(node.getStyle(), "-fx-text-fill");

            if (textFill.isEmpty()) {
                textFill = "white";
            }

            colorPicker.setValue(Color.web(textFill));
        }

        else if (node instanceof TextField t) {
            // TextField não tem getter, tenta pegar do CSS
            String style = t.getStyle();
            Color currentColor = Color.BLACK;
            if (style != null && style.contains("-fx-text-fill:")) {
                String colorStr = style.substring(style.indexOf("-fx-text-fill:") + 14).replace(";", "").trim();
                currentColor = Color.web(colorStr);
            }
            colorPicker.setValue(currentColor);

        } else if (node instanceof TextComponent txt) {

            colorPicker.setValue((Color) txt.getFill());
        }

        colorPicker.setOnAction(e -> {
            Node n = selectedNode.get();
            String existingStyle = node.getStyle();

            Color color = colorPicker.getValue();

            if (n instanceof ButtonComponent) {

                String newStyle = Commons.UpdateEspecificStyle(existingStyle, "-fx-text-fill",
                        Commons.ColortoHex(color));

                n.setStyle(newStyle);

            } else if (n instanceof TextField) {
                n.setStyle("-fx-text-fill: " + AppearanceFactory.toRgbString(color) + ";");
            } else if (n instanceof TextComponent) {
                String newStyle = Commons.UpdateEspecificStyle(existingStyle, "-fx-fill",
                        Commons.ColortoHex(color));

                n.setStyle(newStyle);
            }

        });

        getChildren().addAll(fontColorText, colorPicker);

    }
}
