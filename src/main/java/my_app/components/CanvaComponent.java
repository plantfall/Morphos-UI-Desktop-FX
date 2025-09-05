package my_app.components;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
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
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import my_app.components.buttonComponent.ButtonComponent;
import my_app.data.ViewContract;
import my_app.screens.Home.Home.VisualNodeCallback;

public class CanvaComponent extends Pane implements ViewContract {

    ObjectProperty<Node> currentState = new SimpleObjectProperty<>();

    public CanvaComponent(SimpleStringProperty optionSelected, VisualNodeCallback callback) {

        setBorder(new Border(
                new BorderStroke(
                        Color.BLACK,
                        BorderStrokeStyle.SOLID,
                        null,
                        new BorderWidths(1))));

        setMaxHeight(Double.MAX_VALUE);
        setMaxWidth(Double.MAX_VALUE);

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
            } else if (v.equalsIgnoreCase("image")) {
                node = new ImageComponent(getClass().getResource("/assets/images/mago.jpg").toExternalForm());
            }

            if (node != null) {
                addElementDragable(node, callback);
                callback.set(node);
            }

            optionSelected.set("");
        });

        setOnMouseClicked(e -> {
            if (e.getTarget() == this) { // só dispara se clicou no fundo do Canva
                callback.set(this);
                System.out.println("Canva selecionado");
            }
        });

    }

    private void addElementDragable(Node node, VisualNodeCallback callback) {
        // posição inicial centralizada
        double relX = 0.5;
        double relY = 0.5;

        node.setLayoutX(getWidth() * relX);
        node.setLayoutY(getHeight() * relY);

        // clique = seleciona
        node.setOnMouseClicked(e -> callback.set(node));

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

    @Override
    public void appearance(Pane father) {

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

        father.getChildren().setAll(title, bgColorPicker, chooseImgBtn, urlField, applyUrl);

    }

    @Override
    public void settings(Pane father) {

    }

}
