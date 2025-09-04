package my_app.components;

import javafx.beans.property.ObjectProperty;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ColorPicker;
import javafx.scene.layout.Background;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

public class ButtonBgColorPicker extends HBox {

    ColorPicker colorPicker = new ColorPicker(Color.WHITE);
    Text fontColorText = new Text("Background color:");

    public ButtonBgColorPicker(ObjectProperty<Node> selectedNode) {

        config();

        ButtonComponent node = (ButtonComponent) selectedNode.get();

        // garante que o botão tenha um Background inicial
        if (node.getBackground() == null || node.getBackground().getFills().isEmpty()) {
            node.setBackground(new Background(
                    new javafx.scene.layout.BackgroundFill(
                            Color.WHITE,
                            new CornerRadii(getRadius(node)), // mantém o radius atual
                            Insets.EMPTY)));
        }

        // inicializa com a cor de fundo atual do botão
        Background bg = node.getBackground();
        if (bg != null && !bg.getFills().isEmpty()) {
            Paint fill = bg.getFills().get(0).getFill();
            if (fill instanceof Color c) {
                colorPicker.setValue(c);
            }
        }

        colorPicker.setOnAction(e -> {
            Color c = colorPicker.getValue();
            updateBackground(node, c, getRadius(node));
        });

        getChildren().addAll(fontColorText, colorPicker);

    }

    void config() {
        fontColorText.setFont(Font.font(14));
        fontColorText.setFill(Color.WHITE);

        setSpacing(10);

    }

    // Se mudar só a cor → mantém o radius.
    // Se mudar só o radius → mantém a cor.
    // Se mudar os dois → funciona direitinho.
    // Função utilitária para recriar o background do botão
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
