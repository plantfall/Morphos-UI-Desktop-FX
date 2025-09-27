package my_app.components.shared;

import javafx.beans.property.ObjectProperty;
import javafx.scene.Node;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import my_app.data.Commons;

public class FontSizeComponent extends HBox {

    Text title = new Text("Font size:");
    TextField tf = new TextField();

    public FontSizeComponent(ObjectProperty<Node> selectedNode) {

        Node node = selectedNode.get();

        config();

        loadNodeFontSizeInTextField(node);

        tf.textProperty().addListener((obs, old, newVal) -> {

            String existingStyle = node.getStyle();

            try {
                String newStyle = Commons.UpdateEspecificStyle(existingStyle, "-fx-font-size", newVal.trim());
                node.setStyle(newStyle);
            } catch (NumberFormatException err) {
            }

        });

        getChildren().addAll(title, tf);

    }

    private void loadNodeFontSizeInTextField(Node node) {

        String currentFontSize = Commons.getValueOfSpecificField(node.getStyle(), "-fx-font-size");

        if (currentFontSize.isEmpty()) {
            currentFontSize = Commons.ItemTextFontSizeDefault;
        }

        tf.setText(currentFontSize);

    }

    void config() {
        title.setFont(Font.font(14));
        title.setFill(Color.WHITE);
        setSpacing(10);
    }
}
