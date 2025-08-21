package my_app.components;

import javafx.beans.property.ObjectProperty;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.control.Button;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderStroke;
import javafx.scene.layout.BorderStrokeStyle;
import javafx.scene.layout.BorderWidths;
import javafx.scene.layout.CornerRadii;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import my_app.components.ItemRowComponent;

public class AppearanceFactory {

    public static List<Node> renderComponentes(ButtonComponent optionalButton,
            ObjectProperty<Node> selectedNode, String... items) {
        List<Node> controls = new ArrayList<>();

        for (String item : items) {
            switch (item) {

                case "node-value-field" -> {
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

                    controls.add(tf);
                }

                case "bg-picker" -> {
                    ColorPicker bgPicker = new ColorPicker(Color.LIGHTGRAY);
                    bgPicker.setOnAction(e -> {
                        Color c = bgPicker.getValue();
                        updateBackground(optionalButton, c, getRadius(optionalButton));
                    });

                    var text = new Text("Background:");
                    text.setFont(Font.font(14));
                    text.setFill(Color.WHITE);

                    controls.add(new HBox(text, bgPicker));
                }

                case "padding-field" -> {
                    Insets pad = optionalButton.getPadding();
                    double padValue = pad != null ? pad.getTop() : 0;
                    var paddingItem = new ItemRowComponent("Padding:", String.valueOf(padValue), newVal -> {
                        if (!newVal.isBlank()) {
                            try {
                                double v = Double.parseDouble(newVal);
                                optionalButton.setPadding(new Insets(v));
                            } catch (NumberFormatException ignored) {
                            }
                        }
                    });
                    controls.add(paddingItem);
                }

                case "border-color-picker" -> {
                    Color borderColor = getBorderColor(optionalButton);
                    ColorPicker borderColorPicker = new ColorPicker(borderColor);
                    borderColorPicker.setOnAction(e -> {
                        Color c = borderColorPicker.getValue();
                        BorderStroke stroke = new BorderStroke(
                                c,
                                BorderStrokeStyle.SOLID,
                                new CornerRadii(getRadius(optionalButton)),
                                new BorderWidths(getBorderWidth(optionalButton)));
                        optionalButton.setBorder(new Border(stroke));
                    });

                    var text = new Text("Border Color:");
                    text.setFont(Font.font(14));
                    text.setFill(Color.WHITE);

                    controls.add(new HBox(text, borderColorPicker));
                }

                case "border-width-field" -> {
                    Color borderColor = getBorderColor(optionalButton);
                    ColorPicker borderColorPicker = new ColorPicker(borderColor);
                    double borderWidth = getBorderWidth(optionalButton);

                    var borderWidthItem = new ItemRowComponent("Border Width:", String.valueOf(borderWidth), newVal -> {
                        if (!newVal.isBlank()) {
                            try {
                                double v = Double.parseDouble(newVal);
                                BorderStroke stroke = new BorderStroke(
                                        borderColorPicker.getValue(),
                                        BorderStrokeStyle.SOLID,
                                        new CornerRadii(getRadius(optionalButton)),
                                        new BorderWidths(v));
                                optionalButton.setBorder(new Border(stroke));
                            } catch (NumberFormatException ignored) {
                            }
                        }
                    });

                    controls.add(borderWidthItem);
                }

                case "border-radius-field" -> {
                    Color borderColor = getBorderColor(optionalButton);
                    ColorPicker borderColorPicker = new ColorPicker(borderColor);

                    double borderRadius = getRadius(optionalButton);
                    var borderRadiusItem = new ItemRowComponent("Border Radius:", String.valueOf(borderRadius),
                            newVal -> {
                                if (!newVal.isBlank()) {
                                    try {
                                        double v = Double.parseDouble(newVal);

                                        // border
                                        BorderStroke stroke = new BorderStroke(
                                                borderColorPicker.getValue(),
                                                BorderStrokeStyle.SOLID,
                                                new CornerRadii(v),
                                                new BorderWidths(getBorderWidth(optionalButton)));
                                        optionalButton.setBorder(new Border(stroke));

                                        var bg = optionalButton.getBackground();
                                        // background (reaproveita cor atual)
                                        Color currentBg = Color.TRANSPARENT;
                                        if (optionalButton.getBackground() != null && !bg.getFills().isEmpty()) {
                                            Paint fill = bg.getFills().get(0).getFill();
                                            if (fill instanceof Color c) {
                                                currentBg = c;
                                            }
                                        }
                                        updateBackground(optionalButton, currentBg, v);

                                    } catch (NumberFormatException ignored) {
                                    }
                                }
                            });

                    controls.add(borderRadiusItem);
                }

                case "font-weight-field" -> {
                    var text = new Text("Font Weight");
                    var combo = new ComboBox<String>();

                    text.setFont(Font.font(14));
                    text.setFill(Color.WHITE);

                    var fontWeightItem = new HBox(text, combo);
                    fontWeightItem.setSpacing(10);

                    combo.getItems().addAll(FONT_WEIGHT_MAP.keySet());
                    combo.setValue("normal"); // valor inicial

                    // Listener: String -> FontWeight
                    combo.valueProperty().addListener((obs, old, v) -> {
                        Node node = selectedNode.get();
                        FontWeight fw = FONT_WEIGHT_MAP.getOrDefault(v.toLowerCase(), FontWeight.NORMAL);

                        if (node instanceof ButtonComponent b) {
                            b.setFont(Font.font(b.getFont().getFamily(), fw, b.getFont().getSize()));
                        } else if (node instanceof TextField t) {
                            t.setFont(Font.font(t.getFont().getFamily(), fw, t.getFont().getSize()));
                        } else if (node instanceof Text txt) {
                            txt.setFont(Font.font(txt.getFont().getFamily(), fw, txt.getFont().getSize()));
                        }
                    });

                    controls.add(fontWeightItem);
                }

                case "font-color-field" -> {

                    var colorPicker = new ColorPicker(Color.WHITE);

                    var fontColorText = new Text("Border Color:");
                    fontColorText.setFont(Font.font(14));
                    fontColorText.setFill(Color.WHITE);

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

                    controls.add(fontColorItem);
                }

                case "font-color-size" -> {
                    var fontSizeItem = new ItemRowComponent("Font size", "12",
                            (newFontSize) -> {
                                Node node = selectedNode.get();
                                loadNodeFontSize(node, newFontSize);
                            });

                    controls.add(fontSizeItem);
                }

                case "visibility-row-component" -> {
                    controls.add(new VisibilityRowComponent(selectedNode));
                }
            }
        }

        return controls;
    }

    private static void loadNodeFontSize(Node node, String v) {

        if (node instanceof ButtonComponent b) {
            b.setFont(new Font(Double.valueOf(v)));
        } else if (node instanceof TextField t) {
            t.setFont(new Font(Double.valueOf(v)));
        } else if (node instanceof Text txt) {
            txt.setFont(new Font(Double.valueOf(v)));
        }

    }

    // Função auxiliar para converter Color -> CSS rgb()
    private static String toRgbString(Color c) {
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

    private static Color getBorderColor(Button b) {
        Color borderColor = Color.BLACK;
        if (b.getBorder() != null && !b.getBorder().getStrokes().isEmpty()) {
            Paint strokePaint = b.getBorder().getStrokes().get(0).getTopStroke();
            if (strokePaint instanceof Color c)
                borderColor = c;
        }
        return borderColor;
    }

    // Se mudar só a cor → mantém o radius.
    // Se mudar só o radius → mantém a cor.
    // Se mudar os dois → funciona direitinho.
    // Função utilitária para recriar o background do botão
    private static void updateBackground(Button b, Color color, double radius) {
        b.setBackground(new javafx.scene.layout.Background(
                new javafx.scene.layout.BackgroundFill(
                        color != null ? color : Color.TRANSPARENT,
                        new CornerRadii(radius),
                        Insets.EMPTY)));
    }

    private static double getBorderWidth(Button b) {
        if (b.getBorder() != null && !b.getBorder().getStrokes().isEmpty()) {
            return b.getBorder().getStrokes().get(0).getWidths().getTop();
        }
        return 0;
    }

    private static double getRadius(Button b) {
        if (b.getBorder() != null && !b.getBorder().getStrokes().isEmpty()) {
            return b.getBorder().getStrokes().get(0).getRadii().getTopLeftHorizontalRadius();
        }
        return 0;
    }
}
