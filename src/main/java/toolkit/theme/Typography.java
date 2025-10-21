package toolkit.theme;

import javafx.scene.control.Label;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

public class Typography {
    static {
        // 🔸 Carrega as fontes personalizadas apenas uma vez (estático)
        Font.loadFont(Typography.class.getResourceAsStream("/fonts/Genshin-Impact-Font.ttf"), 10);
        Font.loadFont(Typography.class.getResourceAsStream("/fonts/Undertale.otf"), 10);
    }

    public static Text subtitle(String content) {
        Text text = new Text(content);
        text.getStyleClass().add("subtitle-typo");
        text.setStyle("-fx-font-smoothing-type: gray;"); // 🔹 Suaviza o texto
        return text;
    }

    public static Label body(String content) {
        var text = new Label(content);
        text.getStyleClass().add("body-typo");
        return text;
    }

    public static Label caption(String content) {
        var text = new Label(content);
        text.setStyle("-fx-font-smoothing-type: gray;"); // 🔹 Suaviza o texto
        text.getStyleClass().add("caption-label-typo");
        return text;
    }
}
