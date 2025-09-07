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

        importsMessage = "package my_app;\r\n" + //
                "\r\n" + //
                "import javafx.application.Application;\r\n" + //
                "import javafx.scene.Scene;\r\n" + //
                "import javafx.scene.control.*;\r\n" + //
                "import javafx.scene.text.*;\r\n" + //
                "import javafx.scene.image.*;\r\n" + //
                "import javafx.scene.layout.*;\r\n" + //
                "import javafx.scene.paint.Color;\r\n" + //
                "import javafx.stage.Stage;\r\n" + //
                "";

        codeMessage = "public class GeneratedUI extends Application {\r\n" + //
                "     {\r\n" + //
                "        Pane root = new Pane();\r\n" + //
                "ImageView item_0 = new ImageView();\r\n" + //
                "item_0.setLayoutX(59.99999999999997);\r\n" + //
                "item_0.setLayoutY(20.799999999999983);\r\n" + //
                "item_0.setFitWidth(100.0);\r\n" + //
                "item_0.setFitHeight(100.0);\r\n" + //
                "item_0.setPreserveRatio(true);\r\n" + //
                "item_0.setSmooth(true);\r\n" + //
                "item_0.setImage(new Image(\"file:/C:/Users/Eliezer/Documents/DEV/JAVA/JAVA-FX-PROJECTS/basic-desktop-builder/target/classes/assets/images/mago.jpg\"));\r\n"
                + //
                "root.getChildren().add(item_0);\r\n" + //
                "        Text item_1 = new Text(\"Im new here\");\r\n" + //
                "        item_1.setLayoutX(62.39999999999995);\r\n" + //
                "        item_1.setLayoutY(155.2);\r\n" + //
                "        item_1.setFont(Font.font(\"System\", FontWeight.NORMAL, 16.0));\r\n" + //
                "        item_1.setFill(Color.web(\"0x000000ff\"));\r\n" + //
                "        root.getChildren().add(item_1);\r\n" + //
                "}\r\n" + //
                "\r\n" + //
                "\r\n" + //
                "                }\r\n" + //
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
