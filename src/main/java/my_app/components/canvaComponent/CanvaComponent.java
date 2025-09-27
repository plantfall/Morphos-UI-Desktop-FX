package my_app.components.canvaComponent;

import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.ScaleTransition;
import javafx.animation.Timeline;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.TextField;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderStroke;
import javafx.scene.layout.BorderStrokeStyle;
import javafx.scene.layout.BorderWidths;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.util.Duration;
import my_app.contexts.ComponentsContext;
import my_app.data.CanvaProps;
import my_app.data.Commons;
import my_app.data.ViewContract;
import my_app.screens.Home.Home.VisualNodeCallback;

public class CanvaComponent extends Pane implements ViewContract<CanvaProps> {

    public CanvaComponent() {

        config();

        setOnMouseClicked(e -> {
            if (e.getTarget() == this) { // só dispara se clicou no fundo do Canva
                ComponentsContext.nodeSelected.set(this);

                System.out.println("Canva selecionado");
            }
        });

    }

    @Deprecated
    public void addElementDragable(Node node, VisualNodeCallback callback) {
        // posição inicial centralizada
        double relX = 0.5;
        double relY = 0.5;

        node.setLayoutX((getWidth() - node.prefWidth(-1)) * relX);
        node.setLayoutY((getHeight() - node.prefHeight(-1)) * relY);

        // clique = seleciona
        node.setOnMouseClicked(e -> callback.set(node));

        enableDrag(node, relX, relY);

        getChildren().add(node);

    }

    public void addElementDragable(Node node, boolean putInCenter) {
        // posição inicial centralizada
        double relX = 0.5;
        double relY = 0.5;

        if (putInCenter) {
            node.setLayoutX((getWidth() - node.prefWidth(-1)) * relX);
            node.setLayoutY((getHeight() - node.prefHeight(-1)) * relY);
        }

        enableDrag(node, relX, relY);

        node.setOnMouseClicked(e -> {
            ComponentsContext.SelectNode(node);
            Shake(node);
        });

        ComponentsContext.SelectNode(node);
        AnimateOnEntry(node);

        getChildren().add(node);
    }

    static void AnimateOnEntry(Node node) {
        ScaleTransition st = new ScaleTransition(Duration.millis(400), node);
        st.setFromX(0.5);
        st.setFromY(0.5);
        st.setToX(1);
        st.setToY(1);

        st.play();
    }

    // achacoalhar
    static void Shake(Node node) {

        Timeline timeline = new Timeline(
                new KeyFrame(Duration.ZERO, new KeyValue(node.translateXProperty(), 0)),
                new KeyFrame(Duration.millis(100), new KeyValue(node.translateXProperty(), -1)),
                new KeyFrame(Duration.millis(200), new KeyValue(node.translateXProperty(), 1)),
                new KeyFrame(Duration.millis(300), new KeyValue(node.translateXProperty(), -1)),
                new KeyFrame(Duration.millis(400), new KeyValue(node.translateXProperty(), 1)),
                new KeyFrame(Duration.millis(500), new KeyValue(node.translateXProperty(), -1)),
                new KeyFrame(Duration.millis(600), new KeyValue(node.translateXProperty(), 0)));
        timeline.setCycleCount(1);
        timeline.play();
    }

    public void setOnClickMethodToNode(Node node, VisualNodeCallback callback) {
        // clique = seleciona
        node.setOnMouseClicked(e -> callback.set(node));
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

            // pega as dimensões reais do node
            var bounds = node.getBoundsInLocal();
            double nodeWidth = bounds.getWidth();
            double nodeHeight = bounds.getHeight();

            // clamp para garantir que fique 100% dentro do canva
            x = Math.max(0, Math.min(x, getWidth() - nodeWidth));
            y = Math.max(0, Math.min(y, getHeight() - nodeHeight));

            node.setLayoutX(x);
            node.setLayoutY(y);

            node.getProperties().put("relX", x / (getWidth() - nodeWidth));
            node.getProperties().put("relY", y / (getHeight() - nodeHeight));
        });

        // quando o canva for redimensionado, reposiciona proporcionalmente
        widthProperty().addListener((obs, oldW, newW) -> updateRelativePosition(node));
        heightProperty().addListener((obs, oldH, newH) -> updateRelativePosition(node));
    }

    private void updateRelativePosition(Node node) {
        Object relX = node.getProperties().get("relX");
        Object relY = node.getProperties().get("relY");

        if (relX instanceof Double && relY instanceof Double) {
            var bounds = node.getBoundsInLocal();
            double nodeWidth = bounds.getWidth();
            double nodeHeight = bounds.getHeight();

            node.setLayoutX((Double) relX * (getWidth() - nodeWidth));
            node.setLayoutY((Double) relY * (getHeight() - nodeHeight));

        }
    }

    @Override
    public void appearance(Pane father) {

        // Color Picker
        ColorPicker bgColorPicker = new ColorPicker(
                Color.web(
                        Commons.getValueOfSpecificField(getStyle(), "-fx-background-color")));
        bgColorPicker.setOnAction(e -> {
            Color c = bgColorPicker.getValue();

            setStyle("-fx-background-color:%s;".formatted(
                    Commons.ColortoHex(c)));
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

        father.getChildren().setAll(bgColorPicker, chooseImgBtn, urlField,
                applyUrl,
                new WidthComponent(this),
                new HeightComponent(this));

    }

    @Override
    public void settings(Pane father) {
        father.getChildren().clear();
    }

    void config() {
        setBorder(new Border(
                new BorderStroke(
                        Color.BLACK,
                        BorderStrokeStyle.SOLID,
                        null,
                        new BorderWidths(1))));

        // setPrefHeight(Double.MAX_VALUE);
        // setMaxWidth(Double.MAX_VALUE);

        setPrefSize(Commons.CanvaWidthDefault, Commons.CanvaHeightDefault); // tamanho inicial padrão

        setPadding(new Insets(0));

        setStyle("-fx-background-color:%s;".formatted(Commons.CanvaBgColorDefault));

        setId(String.valueOf(System.currentTimeMillis()));

        ComponentsContext.nodeSelected.set(this);
    }

    @Override
    public CanvaProps getData() {

        String canvastyle = this.getStyle();

        Insets padding = this.getPadding();
        int paddingTop = (int) padding.getTop();
        int paddingRight = (int) padding.getRight();
        int paddingBottom = (int) padding.getBottom();
        int paddingLeft = (int) padding.getLeft();

        double width = this.getPrefWidth();
        double height = this.getPrefHeight();

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

        return new CanvaProps(
                paddingTop, paddingRight, paddingBottom, paddingLeft, width, height, bgType,
                bgContent, this.getId(), 0, 0);
    }

    @Override
    public void applyData(CanvaProps data) {

        // Aplicando as informações extraídas ao CanvaComponent
        setPrefWidth(data.width);
        setPrefHeight(data.height);

        setId(data.identification);

        // Ajustando o padding
        setPadding(
                new Insets(data.padding_top, data.padding_right, data.padding_bottom, data.padding_left));

        var bgType = data.bg_type;
        var bgContent = data.bgContent;
        // Definindo o fundo com base no tipo
        if (bgType.equals("color")) {
            setStyle("-fx-background-color:%s;".formatted(
                    bgContent));
        } else if (bgType.equals("image")) {
            // Para imagem, você pode fazer algo como isso:
            setStyle("-fx-background-image: url('" + bgContent + "');" +
                    "-fx-background-size: cover; -fx-background-position: center;");
        }
    }

    public void addElement(Node comp) {
        getChildren().add(comp);
    }
}
