package my_app.components;

import javafx.geometry.Insets;
import javafx.scene.layout.Pane;
import my_app.components.buttonComponent.ButtonComponent;
import my_app.data.ButtonComponentData;
import my_app.data.CanvaComponentJson;
import my_app.data.ImageComponentData;
import my_app.data.TextComponentData;

public class CustomComponent extends Pane {
    public CustomComponent() {

    }

    public void applyConfig(CanvaComponentJson data) {
        var self = data.self;
        this.setId(self.identification());

        // Aplicando as informações extraídas ao CanvaComponent
        this.setPrefWidth(self.width());
        this.setPrefHeight(self.height());

        this.setId(self.identification());

        // Ajustando o padding
        this.setPadding(
                new Insets(self.padding_top(), self.padding_right(), self.padding_bottom(), self.padding_left()));

        var bgType = self.bg_type();
        var bgContent = self.bgContent();
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
            getChildren().add(node);
        }

        for (TextComponentData data_ : data.text_componentes) {
            var node = new TextComponent(data_.text());
            node.applyData(data_);
            getChildren().add(node);
        }

        for (ImageComponentData data_ : data.image_components) {
            var node = new ImageComponent(data_.url());
            node.applyData(data_);
            getChildren().add(node);
        }
    }
}
