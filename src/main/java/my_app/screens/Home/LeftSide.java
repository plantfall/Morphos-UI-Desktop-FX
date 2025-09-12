package my_app.screens.Home;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.kordamp.ikonli.antdesignicons.AntDesignIconsOutlined;
import org.kordamp.ikonli.javafx.FontIcon;

import javafx.application.Platform;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.collections.WeakListChangeListener;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.Button;
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
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import my_app.App;
import my_app.contexts.SubItemsContext;
import my_app.screens.Home.Home.HandleClickSubItem;

public class LeftSide extends VBox {

    Text title = new Text("Visual Elements");
    List<String> titles = List.of("Text", "Button", "Input", "Image", "Component");
    IntegerProperty indexSelecionado = new SimpleIntegerProperty(-1);

    List<ItemColumn> nodes = new ArrayList<>();

    SimpleStringProperty optionSelected;

    public LeftSide(
            SimpleStringProperty optionSelected,
            HandleClickSubItem callbackClickSubItem) {
        this.optionSelected = optionSelected;

        config();

        getChildren().add(title);

        var spacer = new Region();
        spacer.setMaxHeight(10);
        spacer.setPrefHeight(10);

        getChildren().add(spacer);

        titles.forEach(title -> {
            nodes.add(new ItemColumn(title, callbackClickSubItem, () -> {
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
        private WeakListChangeListener<SimpleStringProperty> weakListChangeListener;
        private ListChangeListener<SimpleStringProperty> listChangeListener;

        HandleClickSubItem callbackClickSubItem;

        public ItemColumn(String name,
                HandleClickSubItem callbackClickSubItem,
                Runnable function) {
            this.type = name.toLowerCase();

            this.callbackClickSubItem = callbackClickSubItem;
            // Row principal
            Row mainRow = new Row(name, () -> {
                function.run();
                expanded.set(!expanded.get());
            });

            getChildren().add(mainRow);
            getChildren().add(subItems);

            // Cria o listener
            listChangeListener = (ListChangeListener.Change<? extends SimpleStringProperty> change) -> {
                if (expanded.get()) {
                    Platform.runLater(this::loadSubItems);
                }
            };
            weakListChangeListener = new WeakListChangeListener<>(listChangeListener);

            expanded.addListener((obs, old, value) -> {
                SubItemsContext context = SubItemsContext.getInstance();
                // ObservableList<SimpleStringProperty> items = context.getItemsByType(type);

                var items = context.getItemsByType(type);

                if (value) {
                    // Adiciona o listener quando expandido
                    items.addListener(weakListChangeListener);
                    loadSubItems();
                } else {
                    // Remove o listener quando recolhido
                    items.removeListener(weakListChangeListener);
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

            for (SimpleStringProperty itemProperty : itemsProperties) {
                String itemName = itemProperty.get();

                HBox subItemBox = createSubItemBox(itemName);
                subItems.getChildren().add(subItemBox);
            }
        }

        private HBox createSubItemBox(String itemName) {
            HBox subItemBox = new HBox();
            Label subLabel = new Label("• " + itemName);
            subLabel.setFont(Font.font(12));
            subLabel.setTextFill(Color.LIGHTGRAY);

            subItemBox.getChildren().add(subLabel);
            subItemBox.setPadding(new Insets(3, 5, 3, 10));

            subItemBox.setOnMouseClicked(e -> {
                System.out.println("Selected: " + itemName);
                // Lógica para deixar o componente selecionado no Canva

                this.callbackClickSubItem.onClick(itemName);

            });

            subItemBox.setOnMouseEntered(e -> subItemBox.setStyle("-fx-background-color: #2D2A6E;"));

            subItemBox.setOnMouseExited(e -> subItemBox.setStyle("-fx-background-color: transparent;"));

            return subItemBox;
        }
    }

    class Row extends HBox {
        BooleanProperty onHover = new SimpleBooleanProperty(false);
        Button btnAdd = new Button();

        public Row(String name, Runnable function) {
            var label = new Label(name);
            label.setFont(Font.font(18));
            label.setStyle("-fx-text-fill: #F8FAFC;");

            label.setFont(App.FONT_BOLD);

            getChildren().add(label);
            getChildren().add(btnAdd);

            btnAdd.managedProperty().bind(onHover);
            btnAdd.visibleProperty().bind(onHover);

            btnAdd.setOnAction(ev -> {
                optionSelected.set(name);
            });

            var icon = FontIcon.of(
                    AntDesignIconsOutlined.PLUS_CIRCLE,
                    12,
                    Color.BLACK);
            onHover.set(true);

            btnAdd.setGraphic(icon);

            setOnMouseClicked(ev -> {
                // limpa todos
                nodes.forEach(n -> n.setStyle("-fx-background-color: transparent;"));

                // pinta o atual imediatamente
                setStyle("-fx-background-color:#3B38A0;");

                // notifica a lógica externa
                function.run();

                onHover.set(true);

            });

            setOnMouseEntered(e -> {
                if (indexSelecionado.get() != nodes.indexOf(this)) {
                    onHover.set(true);

                    setStyle("-fx-background-color: lightblue;");
                }
            });

            setOnMouseExited(e -> {
                if (indexSelecionado.get() != nodes.indexOf(this)) {
                    onHover.set(false);
                    setStyle("-fx-background-color: transparent;");
                }
            });

            setPadding(new Insets(5));
        }
    }

}
