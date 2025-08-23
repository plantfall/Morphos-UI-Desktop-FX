package my_app.screens.Home;

import java.util.ArrayList;
import java.util.List;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
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

    Text title = new Text("Visual Elements");
    List<String> titles = List.of("Text", "Button", "Input", "Image");
    IntegerProperty indexSelecionado = new SimpleIntegerProperty(-1);

    List<Item> nodes = new ArrayList<>();

    public LeftSide(SimpleStringProperty optionSelected) {
        config();

        getChildren().add(title);

        var spacer = new Region();
        spacer.setMaxHeight(10);
        spacer.setPrefHeight(10);

        getChildren().add(spacer);

        titles.forEach(title -> {
            nodes.add(new Item(title, () -> {
                optionSelected.set(title);
                indexSelecionado.set(titles.indexOf(title));
            }));
        });

        getChildren().addAll(nodes);

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
        setPadding(new Insets(0, 10, 0, 10)); // top, right, bottom, left

        title.setFont(Font.font(19));
        title.setFill(Color.web("#BCCCDC"));
    }

    class Item extends HBox {
        public Item(String name, Runnable function) {
            var label = new Label(name);
            label.setFont(Font.font(18));
            label.setStyle("-fx-text-fill: #F8FAFC;");

            getChildren().add(label);

            setOnMouseClicked(ev -> {
                // limpa todos
                nodes.forEach(n -> n.setStyle("-fx-background-color: transparent;"));

                // pinta o atual imediatamente
                setStyle("-fx-background-color:#3B38A0;");

                // notifica a lógica externa
                function.run();
            });

            setOnMouseEntered(e -> {
                if (indexSelecionado.get() != nodes.indexOf(this)) {
                    setStyle("-fx-background-color: lightblue;");
                }
            });

            setOnMouseExited(e -> {
                if (indexSelecionado.get() != nodes.indexOf(this)) {
                    setStyle("-fx-background-color: transparent;");
                }
            });

            setPadding(new Insets(5));
        }
    }

}
