package my_app.screens.Home;

import java.util.List;

import javafx.beans.property.SimpleStringProperty;
import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderStroke;
import javafx.scene.layout.BorderStrokeStyle;
import javafx.scene.layout.BorderWidths;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;

public class LeftSide extends VBox {

    List<String> items = List.of("Text", "Button", "Input", "Image");

    public LeftSide(SimpleStringProperty optionSelected) {
        // Faz com que o LeftSide ocupe a altura toda
        setMaxHeight(Double.MAX_VALUE);

        // Borda apenas no lado direito
        setBorder(new Border(
                new BorderStroke(
                        Color.GRAY, // cor da borda
                        BorderStrokeStyle.SOLID, // estilo
                        null, // sem cantos arredondados
                        new BorderWidths(0, 1, 0, 0) // top, right, bottom, left
                )));

        // Espaçamento horizontal entre conteúdo e borda
        setPadding(new Insets(0, 10, 0, 10)); // top, right, bottom, left

        var title = new Text("Visual Elements");
        getChildren().add(title);

        var spacer = new Region();
        spacer.setMaxHeight(10);
        spacer.setPrefHeight(10);

        getChildren().add(spacer);

        items.forEach(it -> {

            var label = new Label(it);
            label.setPadding(new Insets(5));
            label.setOnMouseEntered(e -> label.setStyle("-fx-background-color: lightblue;"));
            label.setOnMouseExited(e -> label.setStyle("-fx-background-color: transparent;"));
            label.setOnMouseClicked(ev -> optionSelected.set(it));

            getChildren().add(label);
        });

    }
}
