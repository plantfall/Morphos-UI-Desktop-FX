package my_app.components.columnComponent;

import javafx.scene.control.TextField;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

public class PrefLenghtComponent extends HBox {
    Text title = new Text("Pref Length:");
    TextField tf = new TextField();

    public PrefLenghtComponent(FlowPane selectedNode) {

        config();

        // Inicializa o TextField com o valor atual de border-width
        tf.setText(String.valueOf(selectedNode.getPrefWrapLength()));

        tf.textProperty().addListener((obs, old, newVal) -> {
            if (!newVal.isBlank()) {
                try {
                    // Valida se o valor inserido possui uma unidade de medida
                    if (!newVal.matches("\\d+(px|em|pt)?")) { // Permite valores numéricos seguidos de px, em ou pt
                        return; // Se não for válido, ignora a alteração
                    }

                    selectedNode.setPrefWrapLength(Double.parseDouble(newVal));
                } catch (NumberFormatException ignored) {
                }
            }
        });

        getChildren().addAll(title, tf);
    }

    void config() {
        title.setFont(Font.font(14));
        title.setFill(javafx.scene.paint.Color.WHITE);
        setSpacing(10);
    }
}
