package my_app.screens.Home;

import java.util.Map;
import java.util.function.Consumer;

import javafx.beans.property.ObjectProperty;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ColorPicker;
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

        combo.getItems().addAll(FONT_WEIGHT_MAP.keySet());
        combo.setValue("normal"); // valor inicial

        // Listener: String -> FontWeight
        combo.valueProperty().addListener((obs, old, v) -> {
            Node node = selectedNode.get();
            FontWeight fw = FONT_WEIGHT_MAP.getOrDefault(v.toLowerCase(), FontWeight.NORMAL);

            if (node instanceof Button b) {
                b.setFont(Font.font(b.getFont().getFamily(), fw, b.getFont().getSize()));
            } else if (node instanceof TextField t) {
                t.setFont(Font.font(t.getFont().getFamily(), fw, t.getFont().getSize()));
            } else if (node instanceof Text txt) {
                txt.setFont(Font.font(txt.getFont().getFamily(), fw, txt.getFont().getSize()));
            }
        });

        getChildren().add(fontWeightItem);

        var fontColorText = new Text("Font Color");
        var colorPicker = new ColorPicker(Color.WHITE);

        var fontColorItem = new HBox(fontColorText, colorPicker);
        fontColorItem.setSpacing(10);

        colorPicker.setOnAction(e -> {
            Node node = selectedNode.get();
            Color color = colorPicker.getValue();

            if (node instanceof Button b) {
                b.setTextFill(color);
            } else if (node instanceof TextField t) {
                t.setStyle("-fx-text-fill: " + toRgbString(color) + ";");
            } else if (node instanceof Text txt) {
                txt.setFill(color);
            }
        });

        getChildren().add(fontColorItem);

        // Atualiza UI quando muda de seleção
        selectedNode.addListener((obs, old, node) -> {
            if (node instanceof Button b) {
                tf.setText(b.getText());
                combo.setValue(getFontWeightName(FontWeight.findByName(b.getFont().getStyle())));
                colorPicker.setValue((Color) b.getTextFill());
            } else if (node instanceof TextField t) {
                tf.setText(t.getText());
                combo.setValue(getFontWeightName(FontWeight.findByName(t.getFont().getStyle())));
                colorPicker.setValue(extractColorFromStyle(t.getStyle()));
            } else if (node instanceof Text txt) {
                tf.setText(txt.getText());
                combo.setValue(getFontWeightName(FontWeight.findByName(txt.getFont().getStyle())));
                colorPicker.setValue((Color) txt.getFill());
            } else {
                tf.setText("");
            }
        });
    }

    private static Color extractColorFromStyle(String style) {
        if (style != null && style.contains("-fx-text-fill")) {
            try {
                String rgb = style.substring(style.indexOf("#")).trim(); // pega o hex
                return Color.web(rgb);
            } catch (Exception ignored) {
            }
        }
        return Color.BLACK; // fallback
    }

    // Map centralizado para conversão
    private static final Map<String, FontWeight> FONT_WEIGHT_MAP = Map.of(
            "normal", FontWeight.NORMAL,
            "bold", FontWeight.BOLD,
            "thin", FontWeight.THIN,
            "light", FontWeight.LIGHT,
            "medium", FontWeight.MEDIUM,
            "black", FontWeight.BLACK);

    private static String getFontWeightName(FontWeight fw) {
        return FONT_WEIGHT_MAP.entrySet().stream()
                .filter(e -> e.getValue() == fw)
                .map(Map.Entry::getKey)
                .findFirst()
                .orElse("normal");
    }

    // Função auxiliar para converter Color -> CSS rgb()
    private String toRgbString(Color c) {
        return "rgb("
                + (int) (c.getRed() * 255) + ","
                + (int) (c.getGreen() * 255) + ","
                + (int) (c.getBlue() * 255) + ")";
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
