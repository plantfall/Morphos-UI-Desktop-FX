package my_app.screens.Home.leftside;

import java.util.ArrayList;
import java.util.List;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.geometry.Insets;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderStroke;
import javafx.scene.layout.BorderStrokeStyle;
import javafx.scene.layout.BorderWidths;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import my_app.App;

public class LeftSide extends VBox {

        Text title = new Text("Visual Elements");
        List<String> optionsText = List.of("Text", "Button", "Input", "Image", "Component", "Flex items");
        IntegerProperty indexSelecionado = new SimpleIntegerProperty(-1);

        List<Option> options = new ArrayList<>();

        public LeftSide() {

                config();

                getChildren().add(title);

                var spacer = new Region();
                spacer.setMaxHeight(10);
                spacer.setPrefHeight(10);

                getChildren().add(spacer);

                optionsText.forEach(
                                title -> options.add(new Option(title)));

                getChildren().addAll(options);

        }

        void config() {
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
                setPadding(new Insets(10, 10, 0, 10)); // top, right, bottom, left

                title.setFont(App.FONT_BOLD);
                title.setFill(Color.web("#BCCCDC"));
        }

}
