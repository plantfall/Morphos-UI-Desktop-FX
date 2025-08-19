package my_app.components;

import java.util.function.Consumer;

import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

public class ItemRowComponent extends HBox {
    public ItemRowComponent(String name, String defaultValue, Consumer<String> callback) {
        var text = new Text(name);
        var tf = new TextField(defaultValue);

        tf.textProperty().addListener((obs, old, val) -> {
            callback.accept(val);
        });

        text.setFont(Font.font(14));
        text.setFill(Color.WHITE);

        getChildren().addAll(text, tf);
    }

}
