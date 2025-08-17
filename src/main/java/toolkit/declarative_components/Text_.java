package toolkit.declarative_components;

import java.util.function.Consumer;

import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import toolkit.declarative_components.modifiers.TextModifier;
import toolkit.declarative_components.modifiers.TextStyles;

public class Text_ extends Text {
    public Text_(String value) {
        super(value);
        FXNodeContext.add(this);
    }

    public Text_(String value, Consumer<InnerModifier> withModifier) {
        this(value);
        withModifier.accept(new InnerModifier(this));
    }

    public InnerModifier modifier() {
        return new InnerModifier(this);
    }

    public static class InnerModifier extends TextModifier<Text_> {
        public InnerModifier(Text_ node) {
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

        public InnerModifier maxWidth(double maxWidth) {
            node.setWrappingWidth(maxWidth);
            return this;
        }

        @Override
        public InnerStyles styles() {
            return new InnerStyles(this);
        }

        public static class InnerStyles extends TextStyles<Text_> {
            public InnerStyles(InnerModifier modifier) {
                super(modifier);
            }

            @Override
            public InnerStyles color(Color color) {
                modifier.getNode().setFill(color);
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
