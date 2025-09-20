package my_app.components.flexComponent;

import javafx.geometry.Orientation;
import javafx.scene.control.ComboBox;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

public class OrientationComponent extends HBox {
    Text title = new Text("Direction:");

    String[] orientationList = { "Row", "Column" };

    public OrientationComponent(FlowPane selectedNode) {
        var combo = new ComboBox<String>();

        config();

        // opções do ComboBox
        combo.getItems().addAll(orientationList);

        // 🔹 inicializa com base na orientação atual
        if (selectedNode.getOrientation() == Orientation.HORIZONTAL) {
            combo.setValue("Row");
        } else {
            combo.setValue("Column");
        }

        // 🔹 listener para mudar a orientação
        combo.valueProperty().addListener((obs, old, newVal) -> {
            if ("Row".equals(newVal)) {
                selectedNode.setOrientation(Orientation.HORIZONTAL);
            } else {
                selectedNode.setOrientation(Orientation.VERTICAL);
            }
        });

        getChildren().addAll(title, combo);
    }

    void config() {
        title.setFont(Font.font(14));
        title.setFill(javafx.scene.paint.Color.WHITE);
        setSpacing(10);
    }
}
