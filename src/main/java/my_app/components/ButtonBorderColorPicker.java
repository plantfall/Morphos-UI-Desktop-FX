package my_app.components;

import javafx.beans.property.ObjectProperty;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.TextField;
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

public class ButtonBorderColorPicker extends HBox {

    ColorPicker colorPicker = new ColorPicker(Color.WHITE);
    Text fontColorText = new Text("Border Color:");

    public ButtonBorderColorPicker(ObjectProperty<Node> selectedNode) {

        config();

        ButtonComponent node = (ButtonComponent) selectedNode.get();

        Color borderColor = getBorderColor(node);
        colorPicker.setValue(borderColor);

        colorPicker.setOnAction(e -> {
            Color c = colorPicker.getValue();
            BorderStroke stroke = new BorderStroke(
                    c,
                    BorderStrokeStyle.SOLID,
                    new CornerRadii(getRadius(node)),
                    new BorderWidths(getBorderWidth(node)));

            node.setBorder(new Border(stroke));
        });

        getChildren().addAll(fontColorText, colorPicker);

    }

    void config() {
        fontColorText.setFont(Font.font(14));
        fontColorText.setFill(Color.WHITE);

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
}
