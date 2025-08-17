package my_app.screens.Home;

import javafx.beans.property.ObjectProperty;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderStroke;
import javafx.scene.layout.BorderStrokeStyle;
import javafx.scene.layout.BorderWidths;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;

public class RightSide extends VBox {

    public RightSide(ObjectProperty<Node> selectedNode) {
        setMaxHeight(Double.MAX_VALUE);
        setBackground(new Background(
                new BackgroundFill(Color.web("#4F4646"), CornerRadii.EMPTY, Insets.EMPTY)));

        setPadding(new Insets(20));

        Button btnAppearence = new Button("Appearence");
        Button btnLayout = new Button("Layout");

        HBox top = new HBox(btnAppearence, btnLayout);
        getChildren().add(top);

        var spacer = new Region();
        spacer.setPrefHeight(10);
        getChildren().add(spacer);

        setBorder(new Border(
                new BorderStroke(
                        Color.BLACK,
                        BorderStrokeStyle.SOLID,
                        null,
                        new BorderWidths(1))));

        var tf = new TextField();
        tf.setPromptText("Change text...");

        // Se digitar, muda o conteúdo do node selecionado
        tf.textProperty().addListener((obs, old, val) -> {
            Node node = selectedNode.get();
            if (node instanceof Button b) {
                b.setText(val);
            } else if (node instanceof TextField t) {
                t.setText(val);
            } else if (node instanceof Text txt) {
                txt.setText(val);
            }
        });

        getChildren().add(tf);

        var aps = new Text("Appearence Settings");
        getChildren().add(aps);

        getChildren().add(itemRow("Font size", "12"));
        getChildren().add(itemRow("Font Weight", "normal"));
        getChildren().add(itemRow("Font color", "#fff"));

        // Atualiza UI quando muda de seleção
        selectedNode.addListener((obs, old, node) -> {
            if (node instanceof Button b) {
                tf.setText(b.getText());
            } else if (node instanceof TextField t) {
                tf.setText(t.getText());
            } else if (node instanceof Text txt) {
                tf.setText(txt.getText());
            } else {
                tf.setText("");
            }
        });
    }

    HBox itemRow(String name, String defaultValue) {
        var text = new Text(name);
        var tf = new TextField(defaultValue);
        var container = new HBox(text, tf);
        return container;
    }
}
