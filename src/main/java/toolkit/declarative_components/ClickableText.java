package toolkit.declarative_components;

import java.util.function.Consumer;

import javafx.geometry.Insets;
import javafx.scene.Cursor;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.TextAlignment;
import toolkit.declarative_components.modifiers.TextModifier;
import toolkit.declarative_components.modifiers.TextStyles;

public class ClickableText extends Label {

    private static final Color DEFAULT_TEXT_COLOR = Color.web("#90caf9"); // Azul claro Material UI
    private static final Color HOVER_BG_COLOR = Color.web("#90caf9", 0.08); // Fundo translúcido no hover

    public ClickableText(String value) {
        super(value);
        FXNodeContext.add(this);
        setupDefaultStyles();
    }

    public ClickableText(String value, Runnable onClick) {
        super(value);
        FXNodeContext.add(this);
        setupDefaultStyles();
        onClick(onClick);
    }

    public ClickableText(String value, Consumer<InnerModifier> withModifier) {
        this(value);
        withModifier.accept(new InnerModifier(this));
    }

    private void onClick(Runnable runnable) {
        setOnMouseClicked(ev -> {
            runnable.run();
        });
    }

    private void setupDefaultStyles() {
        setTextFill(DEFAULT_TEXT_COLOR);
        setCursor(Cursor.HAND);
        setPadding(new Insets(4, 8, 4, 8));
        setBackground(null);
        setTextAlignment(TextAlignment.LEFT);

        // Eventos de hover
        addEventHandler(MouseEvent.MOUSE_ENTERED, e -> {
            setBackground(new Background(new BackgroundFill(HOVER_BG_COLOR, new CornerRadii(4), Insets.EMPTY)));
        });

        addEventHandler(MouseEvent.MOUSE_EXITED, e -> {
            setBackground(null);
        });
    }

    public InnerModifier modifier() {
        return new InnerModifier(this);
    }

    public static class InnerModifier extends TextModifier<ClickableText> {
        public InnerModifier(ClickableText node) {
            super(node);
        }

        public InnerModifier alignment(TextAlignment alignment) {
            node.setTextAlignment(alignment);
            return this;
        }

        @Override
        public InnerModifier fontSize(double fontSize) {
            node.setStyle(node.getStyle() + "-fx-font-size: " + fontSize + "px;");
            return this;
        }

        @Override
        public InnerModifier font(Font font) {
            node.setFont(font);
            return this;
        }

        public InnerModifier onClick(Runnable runnable) {
            node.setOnMouseClicked(ev -> runnable.run());
            return this;
        }

        public InnerModifier maxWidth(double maxWidth) {
            node.setMaxWidth(maxWidth);
            node.setWrapText(true);
            return this;
        }

        @Override
        public InnerStyles styles() {
            return new InnerStyles(this);
        }

        public static class InnerStyles extends TextStyles<ClickableText> {
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
