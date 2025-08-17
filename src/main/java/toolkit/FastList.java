package toolkit;

import java.util.Collection;
import java.util.function.BiConsumer;
import java.util.function.Function;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;

public class FastList<T> {
    private final ObservableList<T> items = FXCollections.observableArrayList();

    private final ListView<T> listView = new ListView<>();

    private FastList(Collection<T> data,
            Function<T, Node> renderFunction,
            BiConsumer<T, Integer> onClick) {
        items.addAll(data);
        listView.setItems(items);
        // removing default blue focus
        listView.setFocusTraversable(false);
        // remove zebra striping
        listView.setStyle(
                "-fx-control-inner-background: white; " +
                        "-fx-control-inner-background-alt: white;");

        // 1) Fundo externo transparente
        listView.setBackground(new javafx.scene.layout.Background(
                new javafx.scene.layout.BackgroundFill(
                        javafx.scene.paint.Color.TRANSPARENT, null, null)));

        listView.setCellFactory((ListView<T> param) -> new ListCell<>() {
            {
                setOnMouseClicked(ev -> {
                    if (!isEmpty() && getItem() != null && onClick != null) {
                        onClick.accept(getItem(), getIndex());
                    }
                });
            }

            @Override
            protected void updateItem(T item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || item == null) {
                    setGraphic(null);
                } else {
                    setGraphic(renderFunction.apply(item));
                    setStyle("-fx-background-color: transparent;"); // fundo sempre transparente
                }
            }

        });
    }

    public static <T> FastList<T> of(Collection<T> data,
            Function<T, Node> renderFunction, BiConsumer<T, Integer> onClick) {
        return new FastList<>(data, renderFunction, onClick);
    }

    public ListView<T> getView() {
        return listView;
    }

    public void removeItem(T item) {
        items.remove(item);
    }

    public static <T> Builder<T> items(Collection<T> data) {
        return new Builder<>(data);
    }

    public static class Builder<T> {

        Collection<T> data;
        Function<T, Node> renderFunction;
        BiConsumer<T, Integer> onClick;

        public Builder(Collection<T> data) {
            this.data = data;
        }

        public Builder<T> render(Function<T, Node> renderFunction) {
            this.renderFunction = renderFunction;
            return this;
        }

        public Builder<T> onClick(BiConsumer<T, Integer> onClick) {
            this.onClick = onClick;
            return this;
        }

        public FastList<T> use() {
            return new FastList<>(data, renderFunction, onClick);
        }

    }

}
