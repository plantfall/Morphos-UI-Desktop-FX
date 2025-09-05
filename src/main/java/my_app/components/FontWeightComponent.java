package my_app.components;

import java.util.List;
import javafx.beans.property.ObjectProperty;
import javafx.scene.Node;
import javafx.scene.control.ComboBox;
import javafx.scene.layout.HBox;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import my_app.data.Commons;

public class FontWeightComponent extends HBox {
    Text title = new Text("Font Weight:");

    public FontWeightComponent(ObjectProperty<Node> selectedNode) {

        var combo = new ComboBox<String>();
        Node node = selectedNode.get();

        config();

        // na renderização atualiza combobox

        // Carregar valores possíveis no ComboBox
        combo.getItems().addAll(fontWeightList);

        // 🔹 Inicializa o ComboBox com base no no selecionado

        String currentFontWeight = Commons.getValueOfSpecificField(node.getStyle(), "-fx-font-weight");

        if (currentFontWeight.isEmpty()) {
            currentFontWeight = fontWeightList.get(0);
        }

        combo.setValue(currentFontWeight);

        // clica no combo atualiza textComponent

        // Listener: String -> FontWeight
        combo.valueProperty().addListener((obs, old, newVal) -> {

            String existingStyle = node.getStyle();

            String newStyle = Commons.UpdateEspecificStyle(existingStyle, "-fx-font-weight", newVal);

            node.setStyle(newStyle);

        });

        getChildren().addAll(title, combo);

    }

    List<String> fontWeightList = List.of(
            "normal",
            "bold",
            "thin",
            "light",
            "medium",
            "black");

    void config() {
        title.setFont(Font.font(14));
        title.setFill(javafx.scene.paint.Color.WHITE);
        setSpacing(10);
    }
}
