package my_app.components;

import javafx.geometry.Insets;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import my_app.components.buttonComponent.ButtonComponent;
import my_app.contexts.ComponentsContext;
import my_app.data.ButtonComponentData;
import my_app.data.StateJson;
import my_app.data.ImageComponentData;
import my_app.data.TextComponentData;
import my_app.data.ViewContract;

public class CustomComponent extends Pane implements ViewContract<StateJson> {

    private StateJson data;

    ComponentsContext componentsContext = ComponentsContext.getInstance();

    public CustomComponent() {

    }

    @Override
    public void appearance(Pane father) {
        father.getChildren().setAll(new Text("Empty"));
    }

    @Override
    public void settings(Pane father) {
        father.getChildren().setAll(new Text("Empty"));
    }

    @Override
    public StateJson getData() {

        this.data.self.x = (int) this.getLayoutX();
        this.data.self.y = (int) this.getLayoutY();

        return this.data;
    }

    @Override
    public void applyData(StateJson data) {

        this.data = data;

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
