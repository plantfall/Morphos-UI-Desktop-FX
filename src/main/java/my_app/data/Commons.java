package my_app.data;

import java.io.File;
import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.fasterxml.jackson.databind.ObjectMapper;

import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.paint.Color;
import my_app.components.ImageComponent;
import my_app.components.TextComponent;
import my_app.components.buttonComponent.ButtonComponent;
import my_app.components.canvaComponent.CanvaComponent;
import my_app.components.inputComponents.InputComponent;

public class Commons {

    public static double CanvaWidthDefault = 800;
    public static double CanvaHeightDefault = 600;

    public static String FontWeightDefault = "normal";
    public static String ItemTextFontSizeDefault = "14";

    public static String CanvaBgColorDefault = "#d6d2e4ff";
    public static String ButtonBgColorDefault = "#664db3";
    public static String ButtonPaddingDefault = "10";
    public static String ButtonFontWeightDefault = "normal";
    public static String ButtonFontSizeDefault = "16";
    public static String FontSizeDefault = "16";
    public static String ButtonTextColorDefault = "white";
    public static String ButtonRadiusDefault = "3";
    public static String ButtonRadiusWidth = "0";

    public static String UpdateEspecificStyle(
            String currentStyle,
            String targetField,
            String value) {

        // Cria a string de estilo com o valor a ser atualizado
        String newStyle = targetField + ": " + value + ";";

        // Verifica se o estilo já contém o campo de destino
        if (currentStyle.contains(targetField)) {
            // Substitui a parte do estilo correspondente ao targetField com o novo valor
            currentStyle = currentStyle.replaceAll(
                    "(?i)" + targetField + ":\\s*[^;]+;", // Captura o campo de destino e o valor atual, ignorando
                                                          // espaços extras
                    newStyle); // Substitui com o novo valor
        } else {
            // Se não houver, adiciona o novo estilo no final
            if (!currentStyle.endsWith(" ")) { // Evita duplicação de espaços
                currentStyle += " ";
            }
            currentStyle += newStyle; // Adiciona o novo estilo ao final
        }

        // Para verificar o estilo final (opcional, apenas para depuração)
        System.out.println("Updated Style: " + currentStyle);

        return currentStyle;
    }

    public static String getValueOfSpecificField(
            String currentStyle,
            String targetField) {

        // Verifica se o campo está presente
        if (currentStyle.contains(targetField)) {
            // Expressão regular para capturar o valor do campo, tratando espaços extras e
            // valores de cor
            String regex = targetField + ":\\s*([^;]+);"; // \\s* permite espaços extras
            Pattern pattern = java.util.regex.Pattern.compile(regex);
            Matcher matcher = pattern.matcher(currentStyle);

            // Se encontrar uma correspondência, retorna o valor
            if (matcher.find()) {
                return matcher.group(1); // grupo 1 contém o valor após ":"
            }
        }

        // Se não encontrar o campo, retorna uma string vazia
        return "";
    }

    public static String ColortoHex(Color color) {
        return String.format("#%02x%02x%02x",
                (int) (color.getRed() * 255),
                (int) (color.getGreen() * 255),
                (int) (color.getBlue() * 255));
    }

    public static void WriteJsonInDisc(File file, Object obj) {

        ObjectMapper om = new ObjectMapper();

        try {
            om.writeValue(file, obj);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static CanvaComponentJson CreateCanvaComponent(File file, CanvaComponent canva) {
        ObservableList<Node> children = canva.getChildren();

        CanvaComponentJson jsonTarget = new CanvaComponentJson();

        jsonTarget.self = canva.getData();

        for (Node node : children) {

            if (node instanceof TextComponent component) {
                jsonTarget.text_componentes.add(component.getData());
            }

            if (node instanceof ButtonComponent component) {
                jsonTarget.button_componentes.add(component.getData());
            }

            if (node instanceof ImageComponent component) {
                jsonTarget.image_components.add(component.getData());
            }

            if (node instanceof InputComponent component) {
                jsonTarget.input_components.add(component.getData());
            }
        }

        return jsonTarget;
    }
}
