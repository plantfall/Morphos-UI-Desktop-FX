package my_app.components;

import static my_app.components.AppearanceFactory.FONT_WEIGHT_MAP;

import javafx.beans.property.ObjectProperty;
import javafx.scene.Node;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;

public class FontColorPicker extends HBox {

    public FontColorPicker(ObjectProperty<Node> selectedNode) {
        var text = new Text("Font Weight");
        var combo = new ComboBox<String>();

        text.setFont(Font.font(14));
        text.setFill(Color.WHITE);

        setSpacing(10);

        combo.getItems().addAll(FONT_WEIGHT_MAP.keySet());
        combo.setValue("normal"); // valor inicial

        // Listener: String -> FontWeight
        combo.valueProperty().addListener((obs, old, v) -> {
            Node node = selectedNode.get();
            FontWeight fw = FONT_WEIGHT_MAP.getOrDefault(v.toLowerCase(), FontWeight.NORMAL);

            if (node instanceof ButtonComponent b) {
                b.setFont(Font.font(b.getFont().getFamily(), fw, b.getFont().getSize()));
            } else if (node instanceof TextField t) {
                t.setFont(Font.font(t.getFont().getFamily(), fw, t.getFont().getSize()));
            } else if (node instanceof Text txt) {
                txt.setFont(Font.font(txt.getFont().getFamily(), fw, txt.getFont().getSize()));
            }
        });

        getChildren().addAll(text, combo);

    }
}
