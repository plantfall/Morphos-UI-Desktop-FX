package my_app.screens.Home;

import java.util.List;

import javafx.beans.property.SimpleStringProperty;
import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderStroke;
import javafx.scene.layout.BorderStrokeStyle;
import javafx.scene.layout.BorderWidths;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

public class LeftSide extends VBox {

    List<String> items = List.of("Text", "Button", "Input", "Image");

    public LeftSide(SimpleStringProperty optionSelected) {
        // Faz com que o LeftSide ocupe a altura toda
        setMaxHeight(Double.MAX_VALUE);

        setBackground(new Background(
                new BackgroundFill(Color.web("#17153B"), CornerRadii.EMPTY, Insets.EMPTY)));

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

        title.setFont(Font.font(19));
        title.setFill(Color.web("#BCCCDC"));

        getChildren().add(title);

        var spacer = new Region();
        spacer.setMaxHeight(10);
        spacer.setPrefHeight(10);

        getChildren().add(spacer);

        items.forEach(it -> {

            var label = new Label(it);
            label.setFont(Font.font(18));
            label.setStyle("-fx-text-fill: #F8FAFC;");

            var itemContainer = new HBox(label);

            itemContainer.setOnMouseEntered(e -> itemContainer.setStyle("-fx-background-color: lightblue;"));
            itemContainer.setOnMouseExited(e -> itemContainer.setStyle("-fx-background-color: transparent;"));
            itemContainer.setOnMouseClicked(ev -> optionSelected.set(it));

            itemContainer.setPadding(new Insets(5));

            getChildren().add(itemContainer);
        });

    }
}
