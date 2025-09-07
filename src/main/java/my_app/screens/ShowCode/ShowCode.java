package my_app.screens.ShowCode;

import javafx.animation.ScaleTransition;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.TextArea;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;

public class ShowCode {
    private Stage stage;

    public ShowCode(String importsMessage, String codeMessage) {
        stage = new Stage();
        stage.setTitle("Nova Janela");

        importsMessage = "import javafx.scene.Scene;\r\n" + //
                "import javafx.scene.control.*;\r\n" + //
                "import javafx.scene.text.*;\r\n" + //
                "import javafx.scene.image.*;\r\n" + //
                "import javafx.scene.layout.*;\r\n" + //
                "import javafx.scene.paint.Color;\r\n" + //
                "import javafx.stage.Stage;";

        codeMessage = "public class Screen{\r\n" + //
                "                {\r\n" + //
                "                        Pane root = new Pane();\r\n" + //
                "\r\n" + //
                "                        Button item_0 = new Button(\"Im new here\");\r\n" + //
                "                        item_0.setLayoutX(28.80000000000004);\r\n" + //
                "                        item_0.setLayoutY(24.799999999999983);\r\n" + //
                "                        item_0.setStyle(\"-fx-background-color: #664db3; -fx-background-radius:  3.0; -fx-border-width: 0; -fx-padding: 10.0  10.0  10.0  10.0; -fx-font-family: 'System'; -fx-font-size: 16; -fx-font-weight: normal; -fx-text-fill: white;\");\r\n"
                + //
                "                        root.getChildren().add(item_0);\r\n" + //
                "\r\n" + //
                "                        Text item_1 = new Text(\"Im new here\");\r\n" + //
                "                        item_1.setLayoutX(39.19999999999996);\r\n" + //
                "                        item_1.setLayoutY(106.39999999999998);\r\n" + //
                "                        item_1.setFont(Font.font(\"System\", FontWeight.NORMAL, 16.0));\r\n" + //
                "                        item_1.setFill(Color.web(\"0x000000ff\"));\r\n" + //
                "                        root.getChildren().add(item_1);\r\n" + //
                "\r\n" + //
                "                }\r\n" + //
                "        }\r\n" + //
                "\r\n" + //
                "\r\n" + //
                "";

        VBox importsColumnContent = columnItem(importsMessage, "Imports");

        VBox.setMargin(importsColumnContent, new Insets(0, 0, 20, 0));

        VBox codeColumnContent = columnItem(codeMessage, "Code content");

        VBox root = new VBox(importsColumnContent, codeColumnContent);
        root.setSpacing(10);
        root.setStyle("-fx-padding: 20; -fx-alignment: center; -fx-background-color:#39375B");

        Scene scene = new Scene(root, 500, 550);
        stage.setScene(scene);
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
