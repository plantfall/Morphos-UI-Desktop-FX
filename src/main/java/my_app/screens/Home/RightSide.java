package my_app.screens.Home;

import java.util.function.Consumer;

import javafx.beans.property.ObjectProperty;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
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
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;

public class RightSide extends VBox {

    final ObjectProperty<Node> selectedNode;

    public RightSide(ObjectProperty<Node> selectedNode) {
        this.selectedNode = selectedNode;

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

        var fontSizeItem = itemRow("Font size", "12",
                (v) -> {
                    Node node = selectedNode.get();
                    if (node instanceof Button b) {
                        b.setFont(new Font(Double.valueOf(v)));
                    } else if (node instanceof TextField t) {
                        t.setFont(new Font(Double.valueOf(v)));
                    } else if (node instanceof Text txt) {
                        txt.setFont(new Font(Double.valueOf(v)));
                    }
                });

        getChildren().add(fontSizeItem);

        // ---------------------------------------------------------------
        var text = new Text("Font Weight");
        var combo = new ComboBox<String>();

        var fontWeightItem = new HBox(text, combo);
        fontWeightItem.setSpacing(10);

        combo.getItems().addAll("normal", "bold", "thin", "light", "medium", "black");
        combo.setValue("normal"); // valor inicial

        combo.valueProperty().addListener((obs, old, v) -> {
            Node node = selectedNode.get();
            FontWeight fw = switch (v.toLowerCase()) {
                case "bold" -> FontWeight.BOLD;
                case "thin" -> FontWeight.THIN;
                case "light" -> FontWeight.LIGHT;
                case "medium" -> FontWeight.MEDIUM;
                case "black" -> FontWeight.BLACK;
                default -> FontWeight.NORMAL;
            };

            if (node instanceof Button b) {
                b.setFont(Font.font(b.getFont().getFamily(), fw, b.getFont().getSize()));
            } else if (node instanceof TextField t) {
                t.setFont(Font.font(t.getFont().getFamily(), fw, t.getFont().getSize()));
            } else if (node instanceof Text txt) {
                txt.setFont(Font.font(txt.getFont().getFamily(), fw, txt.getFont().getSize()));
            }
        });

        getChildren().add(fontWeightItem);

        var fontColorItem = itemRow("Font color", "#fff",
                (v) -> {
                });

        getChildren().add(fontColorItem);

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

    HBox itemRow(String name, String defaultValue, Consumer<String> callback) {
        var text = new Text(name);
        var tf = new TextField(defaultValue);

        tf.textProperty().addListener((obs, old, val) -> {
            callback.accept(val);
        });

        var container = new HBox(text, tf);
        return container;
    }
}
