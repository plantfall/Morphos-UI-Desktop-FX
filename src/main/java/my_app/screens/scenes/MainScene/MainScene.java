package my_app.screens.scenes.MainScene;

import java.io.File;
import java.io.FileWriter;

import org.luaj.vm2.Globals;
import org.luaj.vm2.LuaTable;
import org.luaj.vm2.LuaValue;
import org.luaj.vm2.Varargs;
import org.luaj.vm2.lib.jse.JsePlatform;

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
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import my_app.components.CanvaComponent;
import my_app.components.ImageComponent;
import my_app.components.TextComponent;
import my_app.components.buttonComponent.ButtonComponent;
import my_app.components.inputComponents.InputComponent;
import my_app.data.Commons;
import my_app.screens.Home.Home;
import my_app.screens.ShowCode.ShowCode;

public class MainScene extends Scene {

    static Home home = new Home();
    static Stage stage = new Stage();

    // Scene mainScene = new Scene(mainView, 1200, 650);
    public MainScene() {
        super(createRoot(), 1200, 650);

        try {
            loadStateFromLua(new File("state.lua"), home.canva);
        } catch (Exception e) {

        }

    }

    private static VBox createRoot() {
        Menu menu = new Menu("Options");
        MenuItem itemSalvar = new MenuItem("Salvar");
        MenuItem itemCarregar = new MenuItem("Carregar");
        MenuItem itemShowCode = new MenuItem("Show code");
        menu.getItems().addAll(itemSalvar, itemCarregar, itemShowCode);

        MenuBar menuBar = new MenuBar(menu);

        VBox mainView = new VBox(menuBar, home);

        HBox.setHgrow(mainView.getChildren().get(1), Priority.ALWAYS);
        VBox.setVgrow(mainView.getChildren().get(1), Priority.ALWAYS);

        itemSalvar.setOnAction(ev -> {
            // generateJavaCode(home.canvaChildren());
            saveStateToFile(new File("state.lua"), home.canva);
        });

        itemCarregar.setOnAction(ev -> {
            // generateJavaCode(home.canvaChildren());
            loadStateFromLua(new File("state.lua"), home.canva);
        });

        itemShowCode.setOnAction(ev -> {
            reviewJavaCode(home.canvaChildren());
        });

        stage.setOnCloseRequest(ev -> saveStateToFile(new File("state.lua"), home.canva));

        return mainView;
    }

    private static void saveStateToFile(File file, CanvaComponent canva) {
        ObservableList<Node> children = canva.getChildren();

        try (FileWriter writer = new FileWriter(file)) {
            writer.write("canva = {\n");

            // Escrevendo os text_componentes
            writer.write("  self = {\n");

            String canvastyle = canva.getStyle();

            Insets cpadding = canva.getPadding();
            double cpaddingTop = cpadding.getTop();
            double cpaddingRight = cpadding.getRight();
            double cpaddingBottom = cpadding.getBottom();
            double cpaddingLeft = cpadding.getLeft();

            double cwidth = canva.getPrefWidth();
            double cheight = canva.getPrefHeight();

            /*
             * setStyle("-fx-background-image: url('" + url + "'); " +
             * "-fx-background-size: cover; -fx-background-position: center;");
             */
            String bgType = "";
            String bgContent = "";
            if (Commons.getValueOfSpecificField(canvastyle, "-fx-background-image").isEmpty()) {
                bgContent = Commons.getValueOfSpecificField(canvastyle, "-fx-background-color");
                bgType = "color";
            } else {
                var bgImage = Commons.getValueOfSpecificField(canvastyle, "-fx-background-image");// url('" + url +
                // "');

                var right = bgImage.split("(")[1];
                var left = right.split(")")[0];

                bgContent = left;
                bgType = "image";
            }

            writer.write(String.format(
                    "      width = %.2f,\n" +
                            "      height = %.2f,\n" +
                            "      padding_top = %.2f,\n" +
                            "      padding_right = %.2f,\n" +
                            "      padding_bottom = %.2f,\n" +
                            "      padding_left = %.2f,\n" +
                            "      bg_type = \"%s\",\n" +
                            "      bg_content = \"%s\"\n",
                    cwidth, cheight, cpaddingTop, cpaddingRight, cpaddingBottom, cpaddingLeft, bgType, bgContent));

            // fim do self
            writer.write("  },\n");

            // Escrevendo os text_componentes
            writer.write("  text_componentes = {\n");
            int textCount = 1;
            for (Node node : children) {
                String style = node.getStyle();

                if (node instanceof TextComponent textNode) {
                    String key = "text_" + textCount++;
                    String text = textNode.getText();
                    String fontWeight = Commons.getValueOfSpecificField(style, "-fx-font-weight");
                    double x = textNode.getLayoutX();
                    double y = textNode.getLayoutY();

                    String fontSize = Commons.getValueOfSpecificField(style, "-fx-font-size");
                    String textFill = Commons.getValueOfSpecificField(style, "-fx-fill");

                    writer.write(String.format(
                            "    %s = {\n" +
                                    "      text = \"%s\",\n" +
                                    "      layout_x = %.2f,\n" +
                                    "      layout_y = %.2f,\n" +
                                    "      font_size = %s,\n" +
                                    "      color = \"%s\",\n" +
                                    "      font_weight = \"%s\"\n" +
                                    "    },\n",
                            key, text.replace("\"", "\\\""), x, y, fontSize, textFill, fontWeight));
                }
            }
            // fim do textcomponent
            writer.write("  },\n");

            // Escrevendo os button_componentes
            writer.write("  button_componentes = {\n");
            int buttonCount = 1;
            for (Node node : children) {
                String style = node.getStyle();

                if (node instanceof Button component) {
                    String key = "btn_" + buttonCount++;
                    String text = component.getText();
                    Font font = component.getFont();
                    Background bg = component.getBackground();
                    Insets padding = component.getPadding();

                    // Extraindo informações sobre o estilo do botão
                    String fontSize = Commons.getValueOfSpecificField(style, "-fx-font-size");
                    String fontWeight = Commons.getValueOfSpecificField(style, "-fx-font-weight");
                    String textFill = Commons.getValueOfSpecificField(style, "-fx-text-fill");
                    String borderWidth = Commons.getValueOfSpecificField(style, "-fx-border-width");
                    String bgColor = Commons.getValueOfSpecificField(style, "-fx-background-color");
                    String paddingTop = String.valueOf(padding.getTop());
                    String paddingRight = String.valueOf(padding.getRight());
                    String paddingBottom = String.valueOf(padding.getBottom());
                    String paddingLeft = String.valueOf(padding.getLeft());
                    String borderRadius = Commons.getValueOfSpecificField(style, "-fx-border-radius");
                    String bgRadius = Commons.getValueOfSpecificField(style, "-fx-background-radius");

                    writer.write(String.format(
                            "    %s = {\n" +
                                    "      text = \"%s\",\n" +
                                    "      layout_x = %.2f,\n" +
                                    "      layout_y = %.2f,\n" +
                                    "      font_size = %s,\n" +
                                    "      color = \"%s\",\n" +
                                    "      font_weight = \"%s\",\n" +
                                    "      bg_color = \"%s\",\n" +
                                    "      padding_top = %s,\n" +
                                    "      padding_right = %s,\n" +
                                    "      padding_bottom = %s,\n" +
                                    "      padding_left = %s,\n" +
                                    "      border_width = %s,\n" +
                                    "      border_radius = %s,\n" +
                                    "      bg_radius = %s\n" +
                                    "    },\n",
                            key, text.replace("\"", "\\\""), component.getLayoutX(), component.getLayoutY(), fontSize,
                            textFill, fontWeight, bgColor, paddingTop, paddingRight, paddingBottom, paddingLeft,
                            borderWidth, borderRadius, bgRadius));
                }

            }
            // fechou button_components
            writer.write("  },\n");

            writer.write("  image_components = {\n");
            int imgCount = 1;
            for (Node node : children) {
                String style = node.getStyle();

                if (node instanceof ImageComponent component) {
                    String key = "img_" + imgCount++;

                    Image img = component.getImage();

                    String url = (img != null && img.getUrl() != null) ? img.getUrl() : "";
                    double width = component.getFitWidth();
                    double height = component.getFitHeight();

                    double x = component.getLayoutX();
                    double y = component.getLayoutY();

                    boolean preserveRatio = component.isPreserveRatio();

                    writer.write(String.format(
                            "    %s = {\n" +
                                    "      uri = \"%s\",\n" +
                                    "      layout_x = %.2f,\n" +
                                    "      layout_y = %.2f,\n" +
                                    "      width = %s,\n" +
                                    "      height = %s,\n" +
                                    "      preserve_ratio = \"%s\"\n" +
                                    "    },\n",
                            key, url.replace("\"", "\\\""), x, y, width, height, preserveRatio));
                }
            }
            // fechou image_components
            writer.write("  },\n");

            writer.write("  input_components = {\n");
            int inputCount = 1;
            for (Node node : children) {
                String style = node.getStyle();

                if (node instanceof InputComponent component) {
                    String key = "input_" + inputCount++;

                    String text = component.getText();
                    String fontWeight = Commons.getValueOfSpecificField(style, "-fx-font-weight");
                    double x = component.getLayoutX();
                    double y = component.getLayoutY();

                    String fontSize = Commons.getValueOfSpecificField(style, "-fx-font-size");
                    String color = Commons.getValueOfSpecificField(style, "-fx-text-fill");

                    writer.write(String.format(
                            "    %s = {\n" +
                                    "      text = \"%s\",\n" +
                                    "      layout_x = %.2f,\n" +
                                    "      layout_y = %.2f,\n" +
                                    "      font_size = %s,\n" +
                                    "      color = \"%s\",\n" +
                                    "      font_weight = \"%s\"\n" +
                                    "    },\n",
                            key, text.replace("\"", "\\\""), x, y, fontSize, color, fontWeight));
                }
            }
            // fechou input_components
            writer.write("  }\n");

            // fim do canva
            writer.write("}\n");

            System.out.println(writer.toString());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void loadStateFromLua(File file, CanvaComponent canvaComp) {
        Globals globals = JsePlatform.standardGlobals();
        LuaValue chunk = globals.loadfile(file.getAbsolutePath());
        chunk.call();

        LuaValue canva = globals.get("canva");

        var children = canvaComp.getChildren();
        children.clear();

        // --- Extraindo as informações de self ---
        LuaValue self = canva.get("self");
        if (self.istable()) {
            LuaTable selfTable = (LuaTable) self;

            // Extraindo largura e altura
            double width = selfTable.get("width").todouble();
            double height = selfTable.get("height").todouble();
            double paddingTop = selfTable.get("padding_top").todouble();
            double paddingRight = selfTable.get("padding_right").todouble();
            double paddingBottom = selfTable.get("padding_bottom").todouble();
            double paddingLeft = selfTable.get("padding_left").todouble();
            String bgType = selfTable.get("bg_type").toString();
            String bgContent = selfTable.get("bg_content").toString();

            // Aplicando as informações extraídas ao CanvaComponent
            canvaComp.setPrefWidth(width);
            canvaComp.setPrefHeight(height);

            // Ajustando o padding
            canvaComp.setPadding(new Insets(paddingTop, paddingRight, paddingBottom, paddingLeft));

            // Definindo o fundo com base no tipo
            if (bgType.equals("color")) {
                canvaComp.setStyle("-fx-background-color:%s;".formatted(
                        bgContent));
            } else if (bgType.equals("image")) {
                // Para imagem, você pode fazer algo como isso:
                canvaComp.setStyle("-fx-background-image: url('" + bgContent + "');" +
                        "-fx-background-size: cover; -fx-background-position: center;");
            }

        }

        // --- Textos ---
        LuaValue textComponents = canva.get("text_componentes");
        if (textComponents.istable()) {
            LuaTable table = (LuaTable) textComponents;
            LuaValue k = LuaValue.NIL;
            while (true) {
                Varargs n = table.next(k);
                if ((k = n.arg1()).isnil())
                    break;
                LuaValue comp = n.arg(2);

                String text = comp.get("text").optjstring("");
                double x = comp.get("layout_x").todouble();
                double y = comp.get("layout_y").todouble();
                String fontSize = comp.get("font_size").toString();
                String colorHex = comp.get("color").toString();
                String fontWeight = comp.get("font_weight").toString();

                TextComponent node = new TextComponent(text);

                node.setStyle("-fx-fill:%s;-fx-font-size:%s;-fx-font-weight:%s;"
                        .formatted(colorHex, fontSize, fontWeight));

                canvaComp.addElementDragable(node, current -> home.selectNode(current));

                node.setLayoutX(x);
                node.setLayoutY(y);
            }
        }

        // --- Imagens ---
        LuaValue imgComponents = canva.get("image_components");
        if (imgComponents.istable()) {
            LuaTable table = (LuaTable) imgComponents;
            LuaValue k = LuaValue.NIL;
            while (true) {
                Varargs n = table.next(k);
                if ((k = n.arg1()).isnil())
                    break;
                LuaValue comp = n.arg(2);

                String url = comp.get("uri").optjstring("");
                double x = comp.get("layout_x").todouble();
                double y = comp.get("layout_y").todouble();
                double width = comp.get("width").todouble();
                double height = comp.get("height").todouble();
                String preserveRatio = comp.get("preserve_ratio").toString();

                if (!url.isBlank()) {
                    ImageComponent node = new ImageComponent(url);

                    canvaComp.addElementDragable(node, current -> home.selectNode(current));

                    node.setPreserveRatio(preserveRatio.equals("true"));

                    node.setLayoutX(x);
                    node.setLayoutY(y);

                    node.setFitHeight(height);
                    node.setFitWidth(width);
                }
            }
        }

        // --- Botões ---
        LuaValue btnComponents = canva.get("button_componentes");
        if (btnComponents.istable()) {
            LuaTable table = (LuaTable) btnComponents;
            LuaValue k = LuaValue.NIL;
            while (true) {
                Varargs n = table.next(k);
                if ((k = n.arg1()).isnil())
                    break;
                LuaValue comp = n.arg(2);

                String text = comp.get("text").optjstring("Button");
                double x = comp.get("layout_x").todouble();
                double y = comp.get("layout_y").todouble();
                String bg_color = comp.get("bg_color").toString();
                String fontSize = comp.get("font_size").toString();
                String textColor = comp.get("color").toString();
                String fontWeight = comp.get("font_weight").toString();
                String bg_radius = comp.get("bg_radius").toString();

                String padding_top = comp.get("padding_top").toString();
                String padding_right = comp.get("padding_right").toString();
                String padding_bottom = comp.get("padding_bottom").toString();
                String padding_left = comp.get("padding_left").toString();
                String padding = padding_top + " " + padding_right + " " + padding_bottom + " " + padding_left;

                String border_width = comp.get("border_width").toString();

                ButtonComponent node = new ButtonComponent(text);

                node.setStyle(
                        "-fx-background-color:%s;-fx-padding:%s;-fx-font-weight:%s;-fx-background-radius:%s;-fx-border-radius:%s;-fx-text-fill:%s;-fx-font-size: %s;-fx-border-width: %s;"
                                .formatted(
                                        bg_color,
                                        padding,
                                        fontWeight,
                                        bg_radius,
                                        bg_radius,
                                        textColor,
                                        fontSize,
                                        border_width));

                canvaComp.addElementDragable(node, current -> home.selectNode(current));

                node.setLayoutX(x);
                node.setLayoutY(y);

            }
        }

        // --- inputs ---
        LuaValue inputComponents = canva.get("input_components");
        if (inputComponents.istable()) {
            LuaTable table = (LuaTable) inputComponents;
            LuaValue k = LuaValue.NIL;
            while (true) {
                Varargs n = table.next(k);
                if ((k = n.arg1()).isnil())
                    break;
                LuaValue comp = n.arg(2);

                String text = comp.get("text").optjstring("");
                double x = comp.get("layout_x").todouble();
                double y = comp.get("layout_y").todouble();
                String fontSize = comp.get("font_size").toString();
                String colorHex = comp.get("color").toString();
                String fontWeight = comp.get("font_weight").toString();

                var node = new InputComponent(text);

                node.setStyle("-fx-text-fill:%s;-fx-font-size:%s;-fx-font-weight:%s;"
                        .formatted(colorHex, fontSize, fontWeight));

                canvaComp.addElementDragable(node, current -> home.selectNode(current));

                node.setLayoutX(x);
                node.setLayoutY(y);
            }
        }
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
                        .replace("#tF", textFill);

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

}
