package my_app;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javafx.application.Application;
import javafx.collections.ObservableList;
import javafx.geometry.Rectangle2D;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Screen;
import javafx.stage.Stage;
import my_app.screens.Home.Home;
import my_app.screens.scenes.DataScene.DataScene;

public class App extends Application {

    Stage primaryStage;

    public static Font FONT_REGULAR;
    public static Font FONT_MEDIUM;
    public static Font FONT_SEMIBOLD;
    public static Font FONT_BOLD;

    @Override
    public void init() {
        FONT_REGULAR = Font.loadFont(getClass().getResourceAsStream("/fonts/Nunito-Regular.ttf"), 14);
        FONT_MEDIUM = Font.loadFont(getClass().getResourceAsStream("/fonts/Nunito-Medium.ttf"), 14);
        FONT_SEMIBOLD = Font.loadFont(getClass().getResourceAsStream("/fonts/Nunito-SemiBold.ttf"), 16);
        FONT_BOLD = Font.loadFont(getClass().getResourceAsStream("/fonts/Nunito-Bold.ttf"), 14);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        this.primaryStage = primaryStage;

        definirJanelaFromArquivo();

        Menu menu = new Menu("File");
        MenuItem itemSalvar = new MenuItem("Salvar");

        menu.getItems().add(itemSalvar);

        MenuBar menuBar = new MenuBar(menu);

        Home home = new Home();

        VBox mainView = new VBox(menuBar, home);

        HBox.setHgrow(mainView.getChildren().get(1), Priority.ALWAYS);
        VBox.setVgrow(mainView.getChildren().get(1), Priority.ALWAYS);

        Scene mainScene = new Scene(mainView, 1200, 650);

        // Cria a DataScene passando a referência da mainScene e do primaryStage
        DataScene dataScene = new DataScene(primaryStage, mainScene);

        // Botão muda para DataScene
        // componentData.setOnAction(e -> primaryStage.setScene(dataScene));

        itemSalvar.setOnAction(ev -> {
            generateJavaCode(home.canvaChildren());
        });

        setup(mainScene); // seta ícone, título etc.
        this.primaryStage.show();
    }

    private void generateJavaCode(ObservableList<Node> children) {
        StringBuilder code = new StringBuilder();
        code.append("package my_app;\n\nimport javafx.application.Application;\n")
                .append("import javafx.scene.Scene;\n")
                .append("import javafx.scene.control.*;\n")
                .append("import javafx.scene.text.*;\n")
                .append("import javafx.scene.image.ImageView;\n")
                .append("import javafx.scene.layout.*;\n")
                .append("import javafx.scene.paint.Color;\n")
                .append("import javafx.stage.Stage;\n\n")
                .append("public class GeneratedUI extends Application {\n")
                .append("    @Override\n")
                .append("    public void start(Stage stage) {\n")
                .append("        Pane root = new Pane();\n");

        for (int i = 0; i < children.size(); i++) {
            Node node = children.get(i);
            if (node instanceof Button) {
                var component = (Button) node;

                String nodeCode = "        Button item_#d = new Button(\"#content\");\n" +
                        "        item_#d.setLayoutX(#lx);\n" +
                        "        item_#d.setLayoutY(#ly);\n" +
                        "        root.getChildren().add(item_#d);\n";

                nodeCode = nodeCode
                        .replace("#content", component.getText())
                        .replace("#d", String.valueOf(i))
                        .replace("#lx", String.valueOf(component.getLayoutX()))
                        .replace("#ly", String.valueOf(component.getLayoutY()));

                code.append(nodeCode);
            }

            if (node instanceof Text) {
                var component = (Text) node;

                double fontSize = component.getFont().getSize();
                String fontFamily = component.getFont().getFamily();
                FontWeight weight = FontWeight.findByName(component.getFont().getStyle()); // cuidado: pode vir
                                                                                           // "Regular", "Bold", etc.
                Color fill = (Color) component.getFill();

                String nodeCode = "        Text item_#d = new Text(\"#content\");\n" +
                        "        item_#d.setLayoutX(#lx);\n" +
                        "        item_#d.setLayoutY(#ly);\n" +
                        "        item_#d.setFont(Font.font(\"#family\", FontWeight.#weight, #size));\r\n" + //
                        "        item_#d.setFill(Color.web(\"#hex\"));\r\n" +
                        "        root.getChildren().add(item_#d);\n";

                nodeCode = nodeCode
                        .replace("#content", component.getText())
                        .replace("#d", String.valueOf(i))
                        .replace("#lx", String.valueOf(component.getLayoutX()))
                        .replace("#ly", String.valueOf(component.getLayoutY()))
                        .replace("#family", String.valueOf(fontFamily))
                        .replace("#weight", String.valueOf(weight))
                        .replace("#size", String.valueOf(fontSize))
                        .replace("#hex", String.valueOf(fill))

                ;

                code.append(nodeCode);
            }

            if (node instanceof ImageView) {
                var component = (ImageView) node;

                String nodeCode = "        ImageView item_#d = new ImageView();\n" +
                        "        item_#d.setLayoutX(#lx);\n" +
                        "        item_#d.setLayoutY(#ly);\n" +
                        "        root.getChildren().add(item_#d);\n";

                nodeCode = nodeCode
                        .replace("#d", String.valueOf(i))
                        .replace("#lx", String.valueOf(component.getLayoutX()))
                        .replace("#ly", String.valueOf(component.getLayoutY()));

                code.append(nodeCode);
            }

            // Você pode expandir para outros componentes...
        }

        code.append("        stage.setScene(new Scene(root, 800, 600));\n")
                .append("        stage.show();\n")
                .append("    }\n\n")
                .append("    public static void main(String[] args) {\n")
                .append("        launch(args);\n")
                .append("    }\n")
                .append("}\n");

        File codigoFonte = new File(
                "C:\\Users\\Eliezer\\Documents\\DEV\\JAVA\\JAVA-FX-PROJECTS\\basic-desktop-builder\\src\\main\\java\\my_app\\GeneratedUI.java");

        try (var bw = new BufferedWriter(new FileWriter(codigoFonte))) {
            bw.write(code.toString());
            System.out.println("salvou");
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private void definirJanelaFromArquivo() {
        // Lê configuração do arquivo
        Map<String, Double> config = lerConfiguracao(
                "C:\\Users\\Eliezer\\Documents\\DEV\\JAVA\\JAVA-FX-PROJECTS\\basic-desktop-builder\\gui_config.txt");

        int monitorIndex = config.get("monitor").intValue();
        double x = config.get("x");
        double y = config.get("y");
        double width = config.get("width");
        double height = config.get("height");

        // Seleciona monitor e aplica posição/tamanho
        if (monitorIndex >= 0 && monitorIndex < Screen.getScreens().size()) {
            Screen screen = Screen.getScreens().get(monitorIndex);
            Rectangle2D bounds = screen.getVisualBounds();

            primaryStage.setX(bounds.getMinX() + x);
            primaryStage.setY(bounds.getMinY() + y);
            primaryStage.setWidth(width);
            primaryStage.setHeight(height);
        }
    }

    private Map<String, Double> lerConfiguracao(String caminhoArquivo) {
        Map<String, Double> config = new HashMap<>();
        try (BufferedReader br = new BufferedReader(new FileReader(caminhoArquivo))) {
            String line;
            while ((line = br.readLine()) != null) {
                if (line.contains("=")) {
                    String[] parts = line.split("=");
                    String key = parts[0].trim();
                    double value = Double.parseDouble(parts[1].trim());
                    config.put(key, value);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return config;
    }

    void setup(Scene scene) {
        // icon on window
        primaryStage.getIcons().add(new Image(
                getClass().getResourceAsStream("/assets/app_ico_window_32_32.png")));

        // styles
        scene.getStylesheets().add(
                getClass().getResource("/global_styles.css")
                        .toExternalForm());

        this.primaryStage.setTitle("Basic Desktop Builder");
        this.primaryStage.setScene(scene);
    }

    public static void main(String[] args) {
        launch(args);
    }
}
