package my_app.components;

import javafx.beans.property.ObjectProperty;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.TextField;
import javafx.scene.layout.Background;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderStroke;
import javafx.scene.layout.BorderStrokeStyle;
import javafx.scene.layout.BorderWidths;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

public class ButtonBorderWidth extends HBox {

    ColorPicker colorPicker = new ColorPicker(Color.WHITE);
    Text title = new Text("Border width:");
    TextField tf = new TextField();

    public ButtonBorderWidth(ObjectProperty<Node> selectedNode) {

        config();

        ButtonComponent node = (ButtonComponent) selectedNode.get();
        Color borderColor = getBorderColor(node);
        ColorPicker borderColorPicker = new ColorPicker(borderColor);
        double borderWidth = getBorderWidth(node);

        // // inicializa borderWidth do Button no texfield
        tf.setText(String.valueOf(borderWidth));

        tf.textProperty().addListener((obs, old, newVal) -> {
            if (!newVal.isBlank()) {
                try {
                    double v = Double.parseDouble(newVal);
                    BorderStroke stroke = new BorderStroke(
                            borderColorPicker.getValue(),
                            BorderStrokeStyle.SOLID,
                            new CornerRadii(getRadius(node)),
                            new BorderWidths(v));

                    node.setBorder(new Border(stroke));
                } catch (NumberFormatException ignored) {
                }
            }
        });

        colorPicker.setOnAction(e -> {
            Color c = colorPicker.getValue();
            updateBackground(node, c, getRadius(node));
        });

        getChildren().addAll(title, tf);

    }

    void config() {
        title.setFont(Font.font(14));
        title.setFill(Color.WHITE);
        setSpacing(10);
    }

    private Color getBorderColor(Button b) {
        Color borderColor = Color.BLACK;
        if (b.getBorder() != null && !b.getBorder().getStrokes().isEmpty()) {
            Paint strokePaint = b.getBorder().getStrokes().get(0).getTopStroke();
            if (strokePaint instanceof Color c)
                borderColor = c;
        }
        return borderColor;
    }

    private double getBorderWidth(Button b) {
        if (b.getBorder() != null && !b.getBorder().getStrokes().isEmpty()) {
            return b.getBorder().getStrokes().get(0).getWidths().getTop();
        }
        return 0;
    }

    // Se mudar só a cor → mantém o radius.
    // Se mudar só o radius → mantém a cor.
    // Se mudar os dois → funciona direitinho.
    // Função utilitária para recriar o background do botão
    private void updateBackground(Button b, Color color, double radius) {
        b.setBackground(new javafx.scene.layout.Background(
                new javafx.scene.layout.BackgroundFill(
                        color != null ? color : Color.TRANSPARENT,
                        new CornerRadii(radius),
                        Insets.EMPTY)));
    }

    private double getRadius(Button b) {
        if (b.getBorder() != null && !b.getBorder().getStrokes().isEmpty()) {
            return b.getBorder().getStrokes().get(0).getRadii().getTopLeftHorizontalRadius();
        }
        return 0;
    }

}
