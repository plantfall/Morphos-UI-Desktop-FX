package my_app.data;

import javafx.beans.property.ObjectProperty;
import javafx.scene.Node;
import my_app.components.VisibilityRowComponent;
import my_app.components.buttonComponent.ButtonComponent;
import java.util.ArrayList;
import java.util.List;

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

}
