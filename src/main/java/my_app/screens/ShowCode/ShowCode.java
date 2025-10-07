package my_app.screens.ShowCode;

import java.util.function.Consumer;

import javafx.animation.ScaleTransition;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;
import my_app.components.ImageComponent;
import my_app.components.TextComponent;
import my_app.components.inputComponents.InputComponent;

public class ShowCode {
    private Stage stage;

    // por enquanto só os node do canva
    // mas adiante expandir para os componentes gerais também pra
    // ver como vou encaix-alos no codigo gerado
    public ShowCode(ObservableList<Node> nodesInCanva) {
        stage = new Stage();
        stage.setTitle("Nova Janela");

        String importsContent = createImports();
        String codeContent = createRestOfCode(nodesInCanva);

        VBox importsColumnContent = columnItem(importsContent, "Imports");

        VBox.setMargin(importsColumnContent, new Insets(0, 0, 20, 0));

        VBox codeColumnContent = columnItem(codeContent, "Code content");

        VBox root = new VBox(importsColumnContent, codeColumnContent);
        root.setSpacing(10);
        root.setStyle("-fx-padding: 20; -fx-alignment: center; -fx-background-color:#39375B");

        Scene scene = new Scene(root, 500, 550);
        stage.setScene(scene);
    }

    private String createImports() {
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

    private String createRestOfCode(ObservableList<Node> nodesInCanva) {
        // codigo da classe

        StringBuilder code = new StringBuilder();
        code
                .append("public class Screen extends Pane {\n\t");

        handleComponentsCreationBellowClass(nodesInCanva, str -> code.append(str));

        code.append("\n{\n");
        // restante aqui da implementação

        code.append("}\n");
        code.append("}");

        System.out.println("codigo");

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

    VBox columnItem(String content, String title) {
        Text titleText = new Text(title);
        VBox.setMargin(titleText, new Insets(0, 0, 10, 0));

        // TextArea permite seleção e cópia
        TextArea textArea = new TextArea(content);
        textArea.setEditable(false); // não deixa editar
        textArea.setWrapText(true); // quebra de linha automática

        // ScrollPane não é necessário, o TextArea já tem barra de rolagem embutida
        textArea.setPrefHeight(200);

        titleText.setStyle("-fx-fill:white;-fx-font-size:18px;");

        textArea.setStyle(
                "-fx-control-inner-background:#1E1E1E;" + // fundo estilo editor
                        "-fx-font-family:Consolas, monospace;" + // fonte de código
                        "-fx-highlight-fill:#264F78;" + // cor do highlight
                        "-fx-highlight-text-fill:white;" +
                        "-fx-text-fill:white;" // cor do texto
        );

        VBox column = new VBox(titleText, textArea);
        column.setSpacing(5);

        ScaleTransition st = new ScaleTransition(Duration.millis(600));
        st.setNode(textArea);

        st.setFromX(0.7);
        st.setFromY(0.7);

        st.setToX(1);
        st.setToY(1);

        st.setAutoReverse(true);
        st.setCycleCount(1);

        st.play();

        return column;
    }

    public void abrir() {
        stage.show();
    }
}
