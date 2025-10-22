package my_app.components.shared;

import javafx.scene.control.Button;

public class UiComponents {
    public static Button ButtonPrimary(String text) {
        var b = new Button(text);
        b.getStyleClass().addAll("button-primary", "text-primary-color");
        return b;
    }

    public static Button ButtonSecondary(String text) {
        var b = new Button(text);
        b.getStyleClass().addAll("button-secondary", "text-primary-color");
        return b;
    }
}
