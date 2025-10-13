package my_app.screens.ShowCode;

import java.util.Locale;
import java.util.function.Consumer;

import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.control.Button;
import my_app.components.ImageComponent;
import my_app.components.TextComponent;
import my_app.components.canvaComponent.CanvaComponent;
import my_app.components.inputComponents.InputComponent;

public class ShowCodeController {
    public String createImports() {
        // imports
        StringBuilder imports = new StringBuilder();
        imports.append("import javafx.scene.Scene;\n")
                .append("import javafx.scene.control.*;\n")
                .append("import javafx.scene.text.*;\n")
                .append("import javafx.scene.image.*;\n")
                .append("import javafx.scene.layout.*;\n")
                .append("import javafx.scene.paint.Color;\n")
                .append("import javafx.stage.Stage;");

        System.out.println("codigo gerado...\nImports:\n");
        System.out.println(imports.toString());

        return imports.toString();
    }

    public String createRestOfCode(
            CanvaComponent canvaComponent) {

        ObservableList<Node> nodesInCanva = canvaComponent.getChildren();
        // codigo da classe

        StringBuilder code = new StringBuilder();
        code
                .append("public class Screen extends Pane {\n\t");

        handleComponentsCreationBellowClass(nodesInCanva, str -> code.append(str));

        code.append("\n\t{\n");
        // restante aqui da implementação

        handleComponentsInsideGetChildren(nodesInCanva, str -> code.append(str));

        code.append("\t\tsetup();\n");
        code.append("\t\tstyles();\n");

        code.append("\t}\n\n");

        // setup(){
        code.append("\tvoid setup(){\n\t\t");

        String config = "this.setPrefSize(%.0f, %.0f);\n\t".formatted(
                canvaComponent.getPrefWidth(),
                canvaComponent.getPrefHeight());
        code.append(config);

        handleComponentsInsideSetupMethod(nodesInCanva, str -> code.append(str));
        code.append("\t}\n\n");
        // }

        // styles(){
        code.append("\tvoid styles(){\n\t\t");
        handleComponentsInsideStylesMethod(nodesInCanva, str -> code.append(str));
        code.append("\t}\n");
        // }

        code.append("}");

        System.out.println(code.toString());
        return code.toString();
    }

    private void handleComponentsCreationBellowClass(ObservableList<Node> nodesInCanva,
            Consumer<StringBuilder> callBackCodeStr) {

        StringBuilder sb = new StringBuilder();

        int textCount = 0;
        int btnCount = 0;
        int imgCount = 0;
        int inputCount = 0;

        for (int i = 0; i < nodesInCanva.size(); i++) {
            Node node = nodesInCanva.get(i);

            if (node instanceof TextComponent) {
                var component = (TextComponent) node;

                textCount++;

                String textText = component.getText();

                String textCreation = "Text text%d = new Text(\"%s\");\n\t".formatted(textCount, textText);
                sb.append(textCreation);
            }

            if (node instanceof Button) {
                var component = (Button) node;

                btnCount++;

                String btnText = component.getText();

                String btnCreation = "Button button%d = new Button(\"%s\");\n\t".formatted(btnCount, btnText);
                sb.append(btnCreation);
            }

            if (node instanceof ImageComponent) {
                var component = (ImageComponent) node;

                imgCount++;

                // String btnCreation = "ImageView imgV%d = new
                // ImageView(\"%s\");\n\t".formatted(imgCount, btnText);
                // sb.append(btnCreation);

                String imgViewCreation = "ImageView imgV%d = new ImageView();\n\t".formatted(imgCount);

                sb.append(imgViewCreation);
            }

            if (node instanceof InputComponent) {
                var component = (InputComponent) node;

                inputCount++;

                String textText = component.getText();

                String textCreation = "TextField input%d = new TextField(\"%s\");\n\t".formatted(inputCount, textText);
                sb.append(textCreation);
            }

            // Você pode expandir para outros componentes...
        }

        callBackCodeStr.accept(sb);
    }

    private void handleComponentsInsideGetChildren(ObservableList<Node> nodesInCanva,
            Consumer<StringBuilder> callBackCodeStr) {

        StringBuilder sb = new StringBuilder();

        int textCount = 0;
        int btnCount = 0;
        int imgCount = 0;
        int inputCount = 0;

        sb.append("\t\tgetChildren().addAll(\n\t\t");

        for (int i = 0; i < nodesInCanva.size(); i++) {
            Node node = nodesInCanva.get(i);

            if (node instanceof TextComponent) {
                textCount++;
                String compName = "text%d,\n\t\t".formatted(textCount);
                sb.append(compName);
            }

            if (node instanceof Button) {
                btnCount++;
                String compName = "button%d,\n\t\t".formatted(btnCount);
                sb.append(compName);
            }

            if (node instanceof ImageComponent) {
                imgCount++;
                String compName = "imgV%d,\n\t\t".formatted(imgCount);
                // sb.append(compName);
            }

            if (node instanceof InputComponent) {
                inputCount++;
                String compName = "input%d,\n\t\t".formatted(inputCount);
                sb.append(compName);
            }
        }

        sb.append(");\n");

        callBackCodeStr.accept(sb);
    }

    private void handleComponentsInsideSetupMethod(ObservableList<Node> nodesInCanva,
            Consumer<StringBuilder> callBackCodeStr) {

        StringBuilder sb = new StringBuilder();

        int textCount = 0;
        int btnCount = 0;
        int imgCount = 0;
        int inputCount = 0;

        for (int i = 0; i < nodesInCanva.size(); i++) {
            Node node = nodesInCanva.get(i);

            if (node instanceof TextComponent) {
                textCount++;

                String setX = String.format(Locale.US, "text%d.setLayoutX(%f);\n\t", textCount, node.getLayoutX());
                String setY = String.format(Locale.US, "text%d.setLayoutY(%f);\n\t", textCount, node.getLayoutY());

                sb.append(setX);
                sb.append(setY);
            }

            if (node instanceof Button) {
                btnCount++;

                String setX = String.format(Locale.US, "button%d.setLayoutX(%f);\n\t", btnCount, node.getLayoutX());
                String setY = String.format(Locale.US, "button%d.setLayoutY(%f);\n\t", btnCount, node.getLayoutY());

                sb.append(setX);
                sb.append(setY);
            }

            if (node instanceof ImageComponent) {
                var component = (ImageComponent) node;

                imgCount++;

                String setX = String.format(Locale.US, "imgV%d.setLayoutX(%f);\n\t", imgCount, node.getLayoutX());
                String setY = String.format(Locale.US, "imgV%d.setLayoutY(%f);\n\t", imgCount, node.getLayoutY());

                // sb.append(setX);
                // sb.append(setY);

                // String btnCreation = "ImageView imgV%d = new
                // ImageView(\"%s\");\n\t".formatted(imgCount, btnText);
                // sb.append(btnCreation);

                String imgViewCreation = "ImageView imgV%d = new ImageView();\n\t".formatted(imgCount);

                // sb.append(imgViewCreation);
            }

            if (node instanceof InputComponent) {
                inputCount++;

                String setX = String.format(Locale.US, "input%d.setLayoutX(%f);\n\t", inputCount, node.getLayoutX());
                String setY = String.format(Locale.US, "input%d.setLayoutY(%f);\n\t", inputCount, node.getLayoutY());

                sb.append(setX);
                sb.append(setY);

            }

            // Você pode expandir para outros componentes...
        }

        callBackCodeStr.accept(sb);
    }

    private void handleComponentsInsideStylesMethod(ObservableList<Node> nodesInCanva,
            Consumer<StringBuilder> callBackCodeStr) {

        StringBuilder sb = new StringBuilder();

        int textCount = 0;
        int btnCount = 0;
        int imgCount = 0;
        int inputCount = 0;

        for (int i = 0; i < nodesInCanva.size(); i++) {
            Node node = nodesInCanva.get(i);

            if (node instanceof TextComponent) {
                var component = (TextComponent) node;

                textCount++;

                String setStyle = "text%d.setStyle(\"%s\");\n\t\t".formatted(textCount, component.getStyle());

                sb.append(setStyle);
            }

            if (node instanceof Button) {
                var component = (Button) node;

                btnCount++;

                String setStyle = "button%d.setStyle(\"%s\");\n\t\t".formatted(btnCount, component.getStyle());

                sb.append(setStyle);
            }

            if (node instanceof ImageComponent) {
                var component = (ImageComponent) node;

                imgCount++;
                String setStyle = "imgV%d.setStyle(\"%s\");\n\t\t".formatted(imgCount, component.getStyle());

                sb.append(setStyle);
            }

            if (node instanceof InputComponent) {
                var component = (InputComponent) node;

                inputCount++;

                String setStyle = "input%d.setStyle(\"%s\");\n\t\t".formatted(inputCount, component.getStyle());

                sb.append(setStyle);
            }

        }

        callBackCodeStr.accept(sb);
    }

}
