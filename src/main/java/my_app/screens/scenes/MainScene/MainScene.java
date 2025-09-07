package my_app.screens.scenes.MainScene;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;

import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import my_app.data.Commons;
import my_app.screens.Home.Home;
import my_app.screens.ShowCode.ShowCode;

public class MainScene extends Scene {

    // Scene mainScene = new Scene(mainView, 1200, 650);
    public MainScene() {
        super(createRoot(), 1200, 650);
    }

    private static VBox createRoot() {
        Menu menu = new Menu("Options");
        MenuItem itemSalvar = new MenuItem("Salvar");
        MenuItem itemShowCode = new MenuItem("Show code");
        menu.getItems().addAll(itemSalvar, itemShowCode);

        MenuBar menuBar = new MenuBar(menu);

        Home home = new Home();

        VBox mainView = new VBox(menuBar, home);

        HBox.setHgrow(mainView.getChildren().get(1), Priority.ALWAYS);
        VBox.setVgrow(mainView.getChildren().get(1), Priority.ALWAYS);

        itemSalvar.setOnAction(ev -> {
            generateJavaCode(home.canvaChildren());
        });

        itemShowCode.setOnAction(ev -> {
            reviewJavaCode(home.canvaChildren());
        });

        return mainView;
    }

    private static void reviewJavaCode(ObservableList<Node> children) {

        // imports
        StringBuilder imports = new StringBuilder();
        imports.append("import javafx.scene.Scene;\n")
                .append("import javafx.scene.control.*;\n")
                .append("import javafx.scene.text.*;\n")
                .append("import javafx.scene.image.*;\n")
                .append("import javafx.scene.layout.*;\n")
                .append("import javafx.scene.paint.Color;\n")
                .append("import javafx.stage.Stage;");

        // codigo da classe

        StringBuilder code = new StringBuilder();
        code
                .append("public class Screen{\n")
                .append("\t\t{\n")
                .append("\t\t\tPane root = new Pane();\n\n");

        for (int i = 0; i < children.size(); i++) {
            Node node = children.get(i);
            String style = node.getStyle();

            if (node instanceof Button) {
                var component = (Button) node;

                String text = component.getText();
                Font font = component.getFont();
                Background bg = component.getBackground();

                Insets padding = component.getPadding();

                // Extraindo informações sobre a fonte
                String fontFamily = font.getFamily();

                String fontSize = Commons.getValueOfSpecificField(style, "-fx-font-size");
                String fontWeight = Commons.getValueOfSpecificField(style, "-fx-font-weight");
                String textFill = Commons.getValueOfSpecificField(style, "-fx-text-fill");
                String borderWidth = Commons.getValueOfSpecificField(style, "-fx-border-width");

                // fundo
                double borderRadius = 0;
                Color fill = Color.WHEAT;

                if (!bg.getFills().isEmpty()) {
                    BackgroundFill fill_ = bg.getFills().get(0);
                    fill = (Color) fill_.getFill();
                    borderRadius = fill_.getRadii().getTopLeftHorizontalRadius();
                }

                String nodeCode = "\t\t\tButton item_#d = new Button(\"#content\");\n" +
                        "\t\t\titem_#d.setLayoutX(#lx);\n" +
                        "\t\t\titem_#d.setLayoutY(#ly);\n" +
                        "\t\t\titem_#d.setStyle(\"-fx-background-color: #hex; -fx-background-radius:  #radius; -fx-border-width: #borderWidth; -fx-padding: #paddingT  #paddingR  #paddingB  #paddingL; -fx-font-family: '#fontFamily'; -fx-font-size: #fontSize; -fx-font-weight: #fW; -fx-text-fill: #tF;\");\n"
                        +
                        "\t\t\troot.getChildren().add(item_#d);\n\n";

                nodeCode = nodeCode
                        .replace("#content", text)
                        .replace("#d", String.valueOf(i))
                        .replace("#lx", String.valueOf(component.getLayoutX()))
                        .replace("#ly", String.valueOf(component.getLayoutY()))
                        .replace("#hex", String.valueOf(Commons.ColortoHex(fill)))
                        .replace("#radius", String.valueOf(borderRadius))
                        .replace("#borderWidth", String.valueOf(borderWidth))
                        .replace("#paddingT", String.valueOf(padding.getTop()))
                        .replace("#paddingR", String.valueOf(padding.getRight()))
                        .replace("#paddingB", String.valueOf(padding.getBottom()))
                        .replace("#paddingL", String.valueOf(padding.getLeft()))
                        .replace("#fontFamily", fontFamily)
                        .replace("#fontSize", String.valueOf(fontSize))
                        .replace("#fW", fontWeight)
                        .replace("#tF", textFill)

                ;

                code.append(nodeCode);
            }

            if (node instanceof Text) {
                var component = (Text) node;

                double fontSize = component.getFont().getSize();
                String fontFamily = component.getFont().getFamily();
                FontWeight weight = FontWeight.findByName(component.getFont().getStyle()); // cuidado: pode vir
                                                                                           // "Regular", "Bold", etc.
                Color fill = (Color) component.getFill();

                String nodeCode = "\t\t\tText item_#d = new Text(\"#content\");\n" +
                        "\t\t\titem_#d.setLayoutX(#lx);\n" +
                        "\t\t\titem_#d.setLayoutY(#ly);\n" +
                        "\t\t\titem_#d.setFont(Font.font(\"#family\", FontWeight.#weight, #size));\n" + //
                        "\t\t\titem_#d.setFill(Color.web(\"#hex\"));\n" +
                        "\t\t\troot.getChildren().add(item_#d);\n\n";

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
                Image img = component.getImage();

                String url = (img != null && img.getUrl() != null) ? img.getUrl() : "";
                double fw = component.getFitWidth();
                double fh = component.getFitHeight();

                String nodeCode = """
                        ImageView item_#d = new ImageView();
                        item_#d.setLayoutX(#lx);
                        item_#d.setLayoutY(#ly);
                        item_#d.setFitWidth(#fw);
                        item_#d.setFitHeight(#fh);
                        item_#d.setPreserveRatio(%s);
                        item_#d.setSmooth(%s);
                        %s
                        root.getChildren().add(item_#d)\n\n;
                        """.formatted(
                        component.isPreserveRatio(),
                        component.isSmooth(),
                        url.isEmpty() ? "" : "item_" + i + ".setImage(new Image(\"" + url + "\"));");

                nodeCode = nodeCode
                        .replace("#d", String.valueOf(i))
                        .replace("#lx", String.valueOf(component.getLayoutX()))
                        .replace("#ly", String.valueOf(component.getLayoutY()))
                        .replace("#fw", String.valueOf(fw))
                        .replace("#fh", String.valueOf(fh));

                code.append(nodeCode);
            }

            // Você pode expandir para outros componentes...
        }

        code.append("\t\t}\n");
        code.append("\t}");
        System.out.println("codigo gerado...\nImports:\n");

        System.out.println(imports);

        System.out.println("codigo");

        System.out.println(code.toString());

        new ShowCode(imports.toString(), code.toString()).abrir();

    }

    private static void generateJavaCode(ObservableList<Node> children) {
        StringBuilder code = new StringBuilder();
        code.append("package my_app;\n\nimport javafx.application.Application;\n")
                .append("import javafx.scene.Scene;\n")
                .append("import javafx.scene.control.*;\n")
                .append("import javafx.scene.text.*;\n")
                .append("import javafx.scene.image.*;\n")
                .append("import javafx.scene.layout.*;\n")
                .append("import javafx.scene.paint.Color;\n")
                .append("import javafx.stage.Stage;\n\n")
                .append("public class GeneratedUI extends Application {\n")
                .append("    @Override\n")
                .append("    public void start(Stage stage) {\n")
                .append("        Pane root = new Pane();\n");

        for (int i = 0; i < children.size(); i++) {
            Node node = children.get(i);
            String style = node.getStyle();

            if (node instanceof Button) {
                var component = (Button) node;

                String text = component.getText();
                Font font = component.getFont();
                Background bg = component.getBackground();

                Insets padding = component.getPadding();

                // Extraindo informações sobre a fonte
                String fontFamily = font.getFamily();

                String fontSize = Commons.getValueOfSpecificField(style, "-fx-font-size");
                String fontWeight = Commons.getValueOfSpecificField(style, "-fx-font-weight");
                String textFill = Commons.getValueOfSpecificField(style, "-fx-text-fill");
                String borderWidth = Commons.getValueOfSpecificField(style, "-fx-border-width");

                // fundo
                double borderRadius = 0;
                Color fill = Color.WHEAT;

                if (!bg.getFills().isEmpty()) {
                    BackgroundFill fill_ = bg.getFills().get(0);
                    fill = (Color) fill_.getFill();
                    borderRadius = fill_.getRadii().getTopLeftHorizontalRadius();
                }

                String nodeCode = "        Button item_#d = new Button(\"#content\");\n" +
                        "        item_#d.setLayoutX(#lx);\n" +
                        "        item_#d.setLayoutY(#ly);\n" +
                        "        item_#d.setStyle(\"-fx-background-color: #hex; -fx-background-radius:  #radius; -fx-border-width: #borderWidth; -fx-padding: #paddingT  #paddingR  #paddingB  #paddingL; -fx-font-family: '#fontFamily'; -fx-font-size: #fontSize; -fx-font-weight: #fW; -fx-text-fill: #tF;\");\n"
                        +
                        "        root.getChildren().add(item_#d);\n";

                nodeCode = nodeCode
                        .replace("#content", text)
                        .replace("#d", String.valueOf(i))
                        .replace("#lx", String.valueOf(component.getLayoutX()))
                        .replace("#ly", String.valueOf(component.getLayoutY()))
                        .replace("#hex", String.valueOf(Commons.ColortoHex(fill)))
                        .replace("#radius", String.valueOf(borderRadius))
                        .replace("#borderWidth", String.valueOf(borderWidth))
                        .replace("#paddingT", String.valueOf(padding.getTop()))
                        .replace("#paddingR", String.valueOf(padding.getRight()))
                        .replace("#paddingB", String.valueOf(padding.getBottom()))
                        .replace("#paddingL", String.valueOf(padding.getLeft()))
                        .replace("#fontFamily", fontFamily)
                        .replace("#fontSize", String.valueOf(fontSize))
                        .replace("#fW", fontWeight)
                        .replace("#tF", textFill)

                ;

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
                Image img = component.getImage();

                String url = (img != null && img.getUrl() != null) ? img.getUrl() : "";
                double fw = component.getFitWidth();
                double fh = component.getFitHeight();

                String nodeCode = """
                        ImageView item_#d = new ImageView();
                        item_#d.setLayoutX(#lx);
                        item_#d.setLayoutY(#ly);
                        item_#d.setFitWidth(#fw);
                        item_#d.setFitHeight(#fh);
                        item_#d.setPreserveRatio(%s);
                        item_#d.setSmooth(%s);
                        %s
                        root.getChildren().add(item_#d);
                        """.formatted(
                        component.isPreserveRatio(),
                        component.isSmooth(),
                        url.isEmpty() ? "" : "item_" + i + ".setImage(new Image(\"" + url + "\"));");

                nodeCode = nodeCode
                        .replace("#d", String.valueOf(i))
                        .replace("#lx", String.valueOf(component.getLayoutX()))
                        .replace("#ly", String.valueOf(component.getLayoutY()))
                        .replace("#fw", String.valueOf(fw))
                        .replace("#fh", String.valueOf(fh));

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

}
