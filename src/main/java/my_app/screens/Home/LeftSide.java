package my_app.screens.Home;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.ObservableList;
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
import my_app.App;
import my_app.contexts.SubItemsContext;

public class LeftSide extends VBox {

    Text title = new Text("Visual Elements");
    List<String> titles = List.of("Text", "Button", "Input", "Image", "Component");
    IntegerProperty indexSelecionado = new SimpleIntegerProperty(-1);

    List<ItemColumn> nodes = new ArrayList<>();

    public LeftSide(SimpleStringProperty optionSelected) {
        config();

        getChildren().add(title);

        var spacer = new Region();
        spacer.setMaxHeight(10);
        spacer.setPrefHeight(10);

        getChildren().add(spacer);

        titles.forEach(title -> {
            nodes.add(new ItemColumn(title, () -> {
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
        setPadding(new Insets(10, 10, 0, 10)); // top, right, bottom, left

        title.setFont(App.FONT_BOLD);
        title.setFill(Color.web("#BCCCDC"));
    }

    class ItemColumn extends VBox {
        BooleanProperty expanded = new SimpleBooleanProperty(false);
        VBox subItems = new VBox();
        private String type;

        public ItemColumn(String name, Runnable function) {
            this.type = name.toLowerCase();

            // Row principal
            Row mainRow = new Row(name, () -> {
                function.run();
                expanded.set(!expanded.get());
            });

            getChildren().add(mainRow);
            getChildren().add(subItems);

            expanded.addListener((obs, old, value) -> {
                if (value) {
                    loadSubItems();
                } else {
                    subItems.getChildren().clear();
                }
            });

            subItems.setPadding(new Insets(5, 0, 0, 20));
            subItems.setSpacing(2);
        }

        private void loadSubItems() {
            subItems.getChildren().clear();

            SubItemsContext context = SubItemsContext.getInstance();
            ObservableList<SimpleStringProperty> itemsProperties = context.getItemsByType(type);

            // Converte SimpleStringProperty para String
            List<String> items = itemsProperties.stream()
                    .map(SimpleStringProperty::get)
                    .collect(Collectors.toList());

            for (String itemName : items) {
                // Cria HBox simples para subitens
                HBox subItemBox = new HBox();
                Label subLabel = new Label("• " + itemName);
                subLabel.setFont(Font.font(12));
                subLabel.setTextFill(Color.LIGHTGRAY);

                subItemBox.getChildren().add(subLabel);
                subItemBox.setPadding(new Insets(3, 5, 3, 10));

                subItemBox.setOnMouseClicked(e -> {
                    System.out.println("Selected: " + itemName);
                    // Lógica para adicionar ao canvas
                });

                subItemBox.setOnMouseEntered(e -> subItemBox.setStyle("-fx-background-color: #2D2A6E;"));

                subItemBox.setOnMouseExited(e -> subItemBox.setStyle("-fx-background-color: transparent;"));

                subItems.getChildren().add(subItemBox);
            }
        }
    }

    class Row extends HBox {
        public Row(String name, Runnable function) {
            var label = new Label(name);
            label.setFont(Font.font(18));
            label.setStyle("-fx-text-fill: #F8FAFC;");

            label.setFont(App.FONT_BOLD);

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
