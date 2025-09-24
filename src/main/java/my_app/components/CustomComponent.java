package my_app.components;

import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import my_app.components.buttonComponent.ButtonComponent;
import my_app.components.canvaComponent.HeightComponent;
import my_app.components.canvaComponent.WidthComponent;
import my_app.components.inputComponents.InputComponent;
import my_app.contexts.ComponentsContext;
import my_app.data.ButtonComponentData;
import my_app.data.CanvaComponentData;
import my_app.data.Commons;
import my_app.data.StateJson;
import my_app.data.ImageComponentData;
import my_app.data.TextComponentData;
import my_app.data.ViewContract;

public class CustomComponent extends Pane implements ViewContract<StateJson> {

    StateJson currentState;
    ComponentsContext componentsContext = ComponentsContext.getInstance();

    public CustomComponent() {

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
        father.getChildren().setAll(new Text("Empty"));
    }

    @Override
    public StateJson getData() {

        String canvastyle = this.getStyle();

        Insets padding = this.getPadding();
        int paddingTop = (int) padding.getTop();
        int paddingRight = (int) padding.getRight();
        int paddingBottom = (int) padding.getBottom();
        int paddingLeft = (int) padding.getLeft();

        double width = this.getPrefWidth();
        double height = this.getPrefHeight();

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

        // this.data.self.x = (int) this.getLayoutX();
        // this.data.self.y = (int) this.getLayoutY();

        currentState.self = new CanvaComponentData(
                paddingTop, paddingRight, paddingBottom, paddingLeft, width, height, bgType,
                bgContent, this.getId(),
                (int) this.getLayoutX(),
                (int) this.getLayoutY());

        for (Node node : getChildren()) {

            if (node instanceof TextComponent component) {
                currentState.text_componentes.add(component.getData());
            }

            if (node instanceof ButtonComponent component) {
                currentState.button_componentes.add(component.getData());
            }

            if (node instanceof ImageComponent component) {
                currentState.image_components.add(component.getData());
            }

            if (node instanceof InputComponent component) {
                currentState.input_components.add(component.getData());
            }

            if (node instanceof CustomComponent component) {
                currentState.custom_components.add(component.getData());
            }
        }

        return currentState;
    }

    @Override
    public void applyData(StateJson data) {

        this.currentState = data;

        var self = data.self;
        this.setId(self.identification);

        this.setLayoutX(data.self.x);
        this.setLayoutY(data.self.y);

        // Aplicando as informações extraídas ao CanvaComponent
        this.setPrefWidth(self.width);
        this.setPrefHeight(self.height);

        // Ajustando o padding
        this.setPadding(
                new Insets(self.padding_top, self.padding_right, self.padding_bottom, self.padding_left));

        var bgType = self.bg_type;
        var bgContent = self.bgContent;
        // Definindo o fundo com base no tipo
        if (bgType.equals("color")) {
            this.setStyle("-fx-background-color:%s;".formatted(
                    bgContent));
        } else if (bgType.equals("image")) {
            // Para imagem, você pode fazer algo como isso:
            this.setStyle("-fx-background-image: url('" + bgContent + "');" +
                    "-fx-background-size: cover; -fx-background-position: center;");
        }

        for (ButtonComponentData data_ : data.button_componentes) {
            var node = new ButtonComponent(data_.text());
            node.applyData(data_);
            node.setOnMouseClicked((e) -> componentsContext.selectNode(node));
            getChildren().add(node);
        }

        for (TextComponentData data_ : data.text_componentes) {
            var node = new TextComponent(data_.text());
            node.applyData(data_);
            node.setOnMouseClicked((e) -> componentsContext.selectNode(node));
            getChildren().add(node);
        }

        for (ImageComponentData data_ : data.image_components) {
            var node = new ImageComponent(data_.url());
            node.applyData(data_);
            node.setOnMouseClicked((e) -> componentsContext.selectNode(node));
            getChildren().add(node);
        }
    }
}
