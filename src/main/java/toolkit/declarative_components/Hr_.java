package toolkit.declarative_components;

import java.util.function.Consumer;

import javafx.geometry.Insets;
import javafx.scene.control.Separator;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;

public class Hr_ extends Separator {
    public Hr_() {
        super();
        FXNodeContext.add(this);
        setLineColor(Color.BLACK);
    }

    public Hr_(Consumer<InnerModifier> content) {
        FXNodeContext.add(this);
        content.accept(new InnerModifier(this));
        setLineColor(Color.BLACK);
    }

    private void setLineColor(javafx.scene.paint.Color color) {
        // Converter Color para CSS (ex: #RRGGBB)
        String hex = String.format("#%02X%02X%02X",
                (int) (color.getRed() * 255),
                (int) (color.getGreen() * 255),
                (int) (color.getBlue() * 255));

        this.setStyle("-fx-background-color: " + hex + ";");
    }

    public InnerModifier modifier() {
        return new InnerModifier(this);
    }

    public static class InnerModifier {
        private final Hr_ node;

        public InnerModifier(Hr_ node) {
            this.node = node;
        }

        public InnerModifier marginTop(double margin) {
            VBox.setMargin(node, new Insets(margin, 0, 0, 0));
            return this;
        }

        public InnerStyles styles() {
            return new InnerStyles(this);
        }

        public static class InnerStyles {
            private final InnerModifier mod;

            public InnerStyles(InnerModifier innerModifier) {
                this.mod = innerModifier;
            }

            public InnerStyles color(javafx.scene.paint.Color color) {
                mod.node.setLineColor(color);
                return this;
            }
        }
    }
}
