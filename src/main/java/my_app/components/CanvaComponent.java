package my_app.components;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.TextField;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderStroke;
import javafx.scene.layout.BorderStrokeStyle;
import javafx.scene.layout.BorderWidths;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;

public class CanvaComponent extends Pane {

    private final ObjectProperty<Node> selectedNode;

    public CanvaComponent(SimpleStringProperty optionSelected, ObjectProperty<Node> selectedNode) {
        this.selectedNode = selectedNode;

        setBorder(new Border(
                new BorderStroke(
                        Color.BLACK,
                        BorderStrokeStyle.SOLID,
                        null,
                        new BorderWidths(1))));

        setHeight(600);
        setMaxHeight(600);

        setPrefWidth(700); // largura padrão
        setMaxWidth(Double.MAX_VALUE); // pode expandir

        optionSelected.addListener((obs, old, v) -> {
            if (v == null || v.isBlank())
                return;

            Node node = null;
            var content = "Im new here";

            if (v.equalsIgnoreCase("button")) {
                node = new ButtonComponent(content);
            } else if (v.equalsIgnoreCase("input")) {
                node = new TextField(content);
            } else if (v.equalsIgnoreCase("text")) {
                node = new TextComponent(content);
            }

            if (node != null) {
                addElementDragable(node);
                selectNode(node);
            }

            optionSelected.set("");
        });

        setOnMouseClicked(e -> {
            if (e.getTarget() == this) { // só dispara se clicou no fundo do Canva
                selectedNode.set(this);
                System.out.println("Canva selecionado");
            }
        });

    }

    private void addElementDragable(Node node) {
        // posição inicial centralizada
        double relX = 0.5;
        double relY = 0.5;

        node.setLayoutX(getWidth() * relX);
        node.setLayoutY(getHeight() * relY);

        // clique = seleciona
        node.setOnMouseClicked(e -> selectNode(node));

        enableDrag(node, relX, relY);

        node.setId(String.valueOf(System.currentTimeMillis()));

        getChildren().add(node);
    }

    private void enableDrag(Node node, double relX, double relY) {
        final double[] offsetX = new double[1];
        final double[] offsetY = new double[1];

        node.setOnMousePressed(e -> {
            offsetX[0] = e.getSceneX() - node.getLayoutX();
            offsetY[0] = e.getSceneY() - node.getLayoutY();
        });

        node.setOnMouseDragged(e -> {
            double x = e.getSceneX() - offsetX[0];
            double y = e.getSceneY() - offsetY[0];

            node.setLayoutX(x);
            node.setLayoutY(y);

            // salva posição relativa
            node.getProperties().put("relX", x / getWidth());
            node.getProperties().put("relY", y / getHeight());
        });

        // quando o canva for redimensionado, reposiciona proporcionalmente
        widthProperty().addListener((obs, oldW, newW) -> updateRelativePosition(node));
        heightProperty().addListener((obs, oldH, newH) -> updateRelativePosition(node));
    }

    private void updateRelativePosition(Node node) {
        Object relX = node.getProperties().get("relX");
        Object relY = node.getProperties().get("relY");

        if (relX instanceof Double && relY instanceof Double) {
            node.setLayoutX((Double) relX * getWidth());
            node.setLayoutY((Double) relY * getHeight());
        }
    }

    private void selectNode(Node node) {
        selectedNode.set(node);
        System.out.println("Selecionado: " + node);
    }

    public void renderRightSideContainer(Pane father) {
        // father.getChildren().
        // Cria controles de Background
        VBox bgControls = new VBox(10);
        bgControls.setUserData("bgControls");

        Text title = new Text("Background Settings");

        // Color Picker
        ColorPicker bgColorPicker = new ColorPicker(Color.WHITE);
        bgColorPicker.setOnAction(e -> {
            Color c = bgColorPicker.getValue();
            setBackground(new Background(new BackgroundFill(c, CornerRadii.EMPTY, Insets.EMPTY)));
        });

        // Botão para escolher imagem do sistema
        Button chooseImgBtn = new Button("Escolher Imagem...");
        chooseImgBtn.setOnAction(e -> {
            javafx.stage.FileChooser fc = new javafx.stage.FileChooser();
            fc.getExtensionFilters().addAll(
                    new javafx.stage.FileChooser.ExtensionFilter("Imagens", "*.png", "*.jpg", "*.jpeg"));
            var file = fc.showOpenDialog(null);
            if (file != null) {
                setStyle("-fx-background-image: url('" + file.toURI().toString() + "'); " +
                        "-fx-background-size: cover; -fx-background-position: center;");
            }
        });

        // Campo para URL
        TextField urlField = new TextField();
        urlField.setPromptText("Cole a URL da imagem");
        Button applyUrl = new Button("Aplicar URL");
        applyUrl.setOnAction(e -> {
            String url = urlField.getText();
            if (url != null && !url.isBlank()) {
                setStyle("-fx-background-image: url('" + url + "'); " +
                        "-fx-background-size: cover; -fx-background-position: center;");
            }
        });

        bgControls.getChildren().addAll(title, bgColorPicker, chooseImgBtn, urlField, applyUrl);

        getChildren().add(bgControls);
    }

}
