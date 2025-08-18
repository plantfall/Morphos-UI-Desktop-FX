package my_app.components;

import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.TextField;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderStroke;
import javafx.scene.layout.BorderStrokeStyle;
import javafx.scene.layout.BorderWidths;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.text.Text;

public class ButtonComponent extends Button {

    public ButtonComponent() {
    }

    public ButtonComponent(String content) {
        super(content);
    }

    public void renderRightSideContainer(Pane father) {
        VBox btnControls = new VBox(10);
        btnControls.setUserData("bgControls");

        Text title = new Text("Button Settings");

        // 🔹 Background Color
        ColorPicker bgPicker = new ColorPicker(Color.LIGHTGRAY);
        bgPicker.setOnAction(e -> {
            Color c = bgPicker.getValue();
            updateBackground(this, c, getRadius(this)); // usa radius atual
        });

        // --- PADDING ---
        Insets pad = getPadding();
        double padValue = pad != null ? pad.getTop() : 0;
        TextField padField = new TextField(String.valueOf(padValue));
        padField.textProperty().addListener((o, oldVal, newVal) -> {
            if (!newVal.isBlank()) {
                try {
                    double v = Double.parseDouble(newVal);
                    setPadding(new Insets(v));
                } catch (NumberFormatException ignored) {
                }
            }
        });

        // --- BORDER COLOR ---
        Color borderColor = Color.BLACK; // valor default
        if (getBorder() != null && !getBorder().getStrokes().isEmpty()) {
            Paint strokePaint = getBorder().getStrokes().get(0).getTopStroke();
            if (strokePaint instanceof Color c) {
                borderColor = c;
            }
        }
        ColorPicker borderColorPicker = new ColorPicker(borderColor);
        borderColorPicker.setOnAction(e -> {
            Color c = borderColorPicker.getValue();
            BorderStroke stroke = new BorderStroke(
                    c,
                    BorderStrokeStyle.SOLID,
                    new CornerRadii(getRadius(this)),
                    new BorderWidths(getBorderWidth(this)));
            setBorder(new Border(stroke));
        });

        // --- BORDER WIDTH ---
        double borderWidth = getBorderWidth(this);
        TextField borderWidthField = new TextField(String.valueOf(borderWidth));
        borderWidthField.textProperty().addListener((o, oldVal, newVal) -> {
            if (!newVal.isBlank()) {
                try {
                    double v = Double.parseDouble(newVal);
                    BorderStroke stroke = new BorderStroke(
                            borderColorPicker.getValue(),
                            BorderStrokeStyle.SOLID,
                            new CornerRadii(getRadius(this)),
                            new BorderWidths(v));
                    setBorder(new Border(stroke));
                } catch (NumberFormatException ignored) {
                }
            }
        });

        // --- BORDER RADIUS ---
        double borderRadius = getRadius(this);
        TextField borderRadiusField = new TextField(String.valueOf(borderRadius));
        borderRadiusField.textProperty().addListener((o, oldVal, newVal) -> {
            if (!newVal.isBlank()) {
                try {
                    double v = Double.parseDouble(newVal);

                    // border
                    BorderStroke stroke = new BorderStroke(
                            borderColorPicker.getValue(),
                            BorderStrokeStyle.SOLID,
                            new CornerRadii(v),
                            new BorderWidths(getBorderWidth(this)));
                    setBorder(new Border(stroke));

                    // background (reaproveita cor atual)
                    Color currentBg = Color.TRANSPARENT;
                    if (getBackground() != null && !getBackground().getFills().isEmpty()) {
                        Paint fill = getBackground().getFills().get(0).getFill();
                        if (fill instanceof Color c) {
                            currentBg = c;
                        }
                    }
                    updateBackground(this, currentBg, v);

                } catch (NumberFormatException ignored) {
                }
            }
        });

        padField.textProperty().addListener((o, oldVal, newVal) -> {
            if (!newVal.isBlank()) {
                var padding = new Insets(Double.valueOf(newVal));
                setPadding(padding);
            }

        });

        btnControls.getChildren().addAll(
                title,
                new HBox(new Text("Background:"), bgPicker),
                new HBox(new Text("Padding:"), padField),
                new HBox(new Text("Border Color:"), borderColorPicker),
                new HBox(new Text("Border Width:"), borderWidthField),
                new HBox(new Text("Border Radius:"), borderRadiusField));

        father.getChildren().add(btnControls);
    }

    private double getBorderWidth(Button b) {
        if (b.getBorder() != null && !b.getBorder().getStrokes().isEmpty()) {
            return b.getBorder().getStrokes().get(0).getWidths().getTop();
        }
        return 0;
    }

    private double getRadius(Button b) {
        if (b.getBorder() != null && !b.getBorder().getStrokes().isEmpty()) {
            return b.getBorder().getStrokes().get(0).getRadii().getTopLeftHorizontalRadius();
        }
        return 0;
    }

    // Se mudar só a cor → mantém o radius.
    // Se mudar só o radius → mantém a cor.
    // Se mudar os dois → funciona direitinho.
    // Função utilitária para recriar o background do botão
    private void updateBackground(Button b, Color color, double radius) {
        BackgroundFill fill = new BackgroundFill(
                color != null ? color : Color.TRANSPARENT,
                new CornerRadii(radius),
                Insets.EMPTY);
        b.setBackground(new Background(fill));
    }

}
