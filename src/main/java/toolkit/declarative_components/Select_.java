package toolkit.declarative_components;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.TextAlignment;

import java.util.List;
import java.util.function.Consumer;

public class Select_<T> extends ComboBox<T> {

    public Select_() {
        super();
        FXNodeContext.add(this);
    }

    public Select_(List<T> items) {
        super(FXCollections.observableArrayList(items));

        this.value(items.get(0));
        FXNodeContext.add(this);
    }

    /** Define os itens usando uma lista */
    public Select_<T> items(List<T> values) {
        ObservableList<T> obs = FXCollections.observableArrayList(values);
        setItems(obs);
        this.value(values.get(0));
        return this;
    }

    /** Define item inicial selecionado */
    public Select_<T> value(T value) {
        setValue(value);
        return this;
    }

    /** Define ação quando mudar seleção */
    public Select_<T> onChange(Consumer<T> action) {
        setOnAction(e -> action.accept(getValue()));
        return this;
    }

    /** Define largura */
    public Select_<T> width(double w) {
        setPrefWidth(w);
        return this;
    }

    public InnerModifier<T> modifier() {
        return new InnerModifier<T>(this);
    }

    public static class InnerModifier<T> {
        private final Select_<T> node;

        public InnerModifier(Select_<T> node) {
            this.node = node;
        }

        public InnerModifier<T> marginTop(double margin) {
            VBox.setMargin(node, new Insets(margin, 0, 0, 0));
            return this;
        }

        public InnerModifier<T> fontSize(double fontSize) {
            node.setStyle("-fx-font-size: " + fontSize + "px;");
            return this;
        }

        // public InnerStyles styles() {
        // return new InnerStyles(this);
        // }

        // public static class InnerStyles {
        // private final InnerModifier mod;

        // public InnerStyles(InnerModifier modifier) {
        // this.mod = modifier;
        // }

        // public InnerStyles color(Color color) {
        // mod.node.setTextFill(color);
        // return this;
        // }
        // }

    }
}
