package toolkit.declarative_components;

import java.util.function.Consumer;

import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.TextAlignment;
import toolkit.declarative_components.modifiers.TextModifier;
import toolkit.declarative_components.modifiers.TextStyles;
import toolkit.theme.ThemeStyler;

public class Label_ extends Label {
    public Label_(String value) {
        super(value);
        setPadding(Insets.EMPTY);
        FXNodeContext.add(this);
    }

    public Label_(String value, Consumer<InnerModifier> withModifier) {
        super(value);
        setPadding(Insets.EMPTY);
        FXNodeContext.add(this);
        withModifier.accept(new InnerModifier(this));
    }

    public InnerModifier modifier() {
        return new InnerModifier(this);
    }

    public static class InnerModifier extends TextModifier<Label> {
        public InnerModifier(Label node) {
            super(node);
        }

        public InnerModifier alignment(TextAlignment alignment) {
            node.setTextAlignment(alignment);
            return this;
        }

        @Override
        public InnerModifier fontSize(double fontSize) {
            node.setStyle("-fx-font-size: " + fontSize + "px;");
            return this;
        }

        @Override
        public InnerModifier font(Font font) {
            node.setFont(font);
            return this;
        }

        @Override
        public InnerStyles styles() {
            return new InnerStyles(this);
        }

        public static class InnerStyles extends TextStyles<Label> {
            public InnerStyles(InnerModifier modifier) {
                super(modifier);
            }

            @Override
            public InnerStyles color(Color color) {
                modifier.getNode().setTextFill(color);
                return this;
            }

            @Override
            public InnerStyles fontWeight(String weight) {
                String currentStyle = modifier.getNode().getStyle();
                modifier.getNode().setStyle(
                        (currentStyle != null ? currentStyle : "") + "-fx-font-weight: " + weight + ";");
                return this;
            }
        }
    }
}
