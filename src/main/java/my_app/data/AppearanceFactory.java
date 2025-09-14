package my_app.data;

import javafx.beans.property.ObjectProperty;
import javafx.scene.Node;
import javafx.scene.text.FontWeight;
import my_app.components.VisibilityRowComponent;
import my_app.components.buttonComponent.ButtonComponent;
import javafx.scene.paint.Color;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class AppearanceFactory {

    public static List<Node> renderComponentes(ButtonComponent optionalButton,
            ObjectProperty<Node> selectedNode, String... items) {
        List<Node> controls = new ArrayList<>();

        for (String item : items) {
            switch (item) {

                case "visibility-row-component" -> {
                    controls.add(new VisibilityRowComponent(selectedNode));
                }
            }
        }

        return controls;
    }

    // Função auxiliar para converter Color -> CSS rgb()
    public static String toRgbString(Color c) {
        return "rgb("
                + (int) (c.getRed() * 255) + ","
                + (int) (c.getGreen() * 255) + ","
                + (int) (c.getBlue() * 255) + ")";
    }

    // Map centralizado para conversão
    public static final Map<String, FontWeight> FONT_WEIGHT_MAP = Map.of(
            "normal", FontWeight.NORMAL,
            "bold", FontWeight.BOLD,
            "thin", FontWeight.THIN,
            "light", FontWeight.LIGHT,
            "medium", FontWeight.MEDIUM,
            "black", FontWeight.BLACK);

}
