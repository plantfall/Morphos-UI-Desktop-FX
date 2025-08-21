package my_app.components;

import static my_app.components.AppearanceFactory.FONT_WEIGHT_MAP;

import java.util.Map;
import java.util.stream.Collectors;

import javafx.beans.property.ObjectProperty;
import javafx.scene.Node;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;

public class FontWeightComponent extends HBox {

    public FontWeightComponent(ObjectProperty<Node> selectedNode) {
        var text = new Text("Font Weight");
        var combo = new ComboBox<String>();

        text.setFont(Font.font(14));
        text.setFill(Color.WHITE);

        setSpacing(10);

        // na renderização atualiza combobox

        // Carregar valores possíveis no ComboBox
        combo.getItems().addAll(FONT_WEIGHT_MAP.keySet());

        // 🔹 Inicializa o ComboBox com base no selectedNode
        Node node = selectedNode.get();

        Font font = null;

        if (node instanceof ButtonComponent b) {
            font = b.getFont();
        } else if (node instanceof TextField t) {
            font = t.getFont();
        } else if (node instanceof TextComponent txt) {
            font = txt.getFont();
        }

        if (font != null) {
            // usa o próprio FontWeight da fonte
            FontWeight fw = FontWeight.findByName(font.getStyle().toUpperCase());
            // procura chave no mapa invertido
            String selectedKey = FONT_WEIGHT_TO_KEY.getOrDefault(fw, "normal");
            combo.setValue(selectedKey);
        }

        // clica no combo atualiza textComponent

        // Listener: String -> FontWeight
        combo.valueProperty().addListener((obs, old, v) -> {
            Node n = selectedNode.get();
            FontWeight fw = FONT_WEIGHT_MAP.getOrDefault(v.toLowerCase(), FontWeight.NORMAL);

            if (n instanceof ButtonComponent b) {
                b.setFont(Font.font(b.getFont().getFamily(), fw, b.getFont().getSize()));
            } else if (n instanceof TextField t) {
                t.setFont(Font.font(t.getFont().getFamily(), fw, t.getFont().getSize()));
            } else if (n instanceof TextComponent txt) {
                txt.setFont(Font.font(txt.getFont().getFamily(), fw, txt.getFont().getSize()));
            }
        });

        getChildren().addAll(text, combo);

    }

    // Cria mapa invertido só uma vez (pode até colocar em AppearanceFactory)
    private static final Map<FontWeight, String> FONT_WEIGHT_TO_KEY = FONT_WEIGHT_MAP.entrySet().stream()
            .collect(Collectors.toMap(Map.Entry::getValue, Map.Entry::getKey));
}
