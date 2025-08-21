package my_app.components;

import javafx.beans.property.ObjectProperty;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.ColorPicker;
import javafx.scene.text.FontWeight;
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

public class AppearanceFactory {

    public static List<Node> renderComponentes(ButtonComponent optionalButton,
            ObjectProperty<Node> selectedNode, String... items) {
        List<Node> controls = new ArrayList<>();

        for (String item : items) {
            switch (item) {

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
