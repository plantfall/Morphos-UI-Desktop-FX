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

public class ButtonBorderRadius extends HBox {

    ColorPicker colorPicker = new ColorPicker(Color.WHITE);
    Text title = new Text("Border radius:");
    TextField tf = new TextField();

    public ButtonBorderRadius(ObjectProperty<Node> selectedNode) {

        config();

        ButtonComponent node = (ButtonComponent) selectedNode.get();
        Color borderColor = getBorderColor(node);
        ColorPicker borderColorPicker = new ColorPicker(borderColor);
        double borderRadius = getRadius(node);

        // Inicializa borderRadius do Button no TextField
        tf.setText(String.valueOf(borderRadius));

        tf.textProperty().addListener((obs, old, newVal) -> {
            if (!newVal.isBlank()) {
                try {
                    double v = Double.parseDouble(newVal);

                    // Atualiza a borda
                    BorderStroke stroke = new BorderStroke(
                            borderColorPicker.getValue(),
                            BorderStrokeStyle.SOLID,
                            new CornerRadii(v),
                            new BorderWidths(getBorderWidth(node)));
                    node.setBorder(new Border(stroke));

                    // Atualiza o fundo, reaproveitando a cor atual
                    var bg = node.getBackground();
                    Color currentBg = Color.TRANSPARENT;
                    if (node.getBackground() != null && !bg.getFills().isEmpty()) {
                        Paint fill = bg.getFills().get(0).getFill();
                        if (fill instanceof Color c) {
                            currentBg = c;
                        }
                    }
                    updateBackground(node, currentBg, v);

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

    // Função para atualizar tanto o background quanto a borda
    private void updateBackground(Button b, Color color, double radius) {
        // Preserva o padding atual
        Insets currentPadding = b.getPadding();

        // Atualiza o estilo (background)
        String existingStyle = b.getStyle();
        String colorStyle = "-fx-background-color: " + String.format("#%02x%02x%02x",
                (int) (color.getRed() * 255),
                (int) (color.getGreen() * 255),
                (int) (color.getBlue() * 255));
        String radiusStyle = "-fx-background-radius: " + radius + ";";

        // Se já houver a cor de fundo no estilo, substitui
        if (existingStyle.contains("-fx-background-color")) {
            existingStyle = existingStyle.replaceFirst("(-fx-background-color: [^;]+);", colorStyle + ";");
        } else {
            existingStyle += " " + colorStyle + ";";
        }

        // Se já houver o raio de borda no estilo, substitui
        if (existingStyle.contains("-fx-background-radius")) {
            existingStyle = existingStyle.replaceFirst("(-fx-background-radius: [^;]+);", radiusStyle);
        } else {
            existingStyle += " " + radiusStyle;
        }

        // Aplique o estilo com a cor e o raio
        b.setStyle(existingStyle);

        // Restaure o padding após alterar o estilo
        b.setPadding(currentPadding);
    }

    private double getRadius(Button b) {
        if (b.getBorder() != null && !b.getBorder().getStrokes().isEmpty()) {
            return b.getBorder().getStrokes().get(0).getRadii().getTopLeftHorizontalRadius();
        }
        return 0;
    }
}
