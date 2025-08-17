package toolkit.declarative_components;

import java.util.function.BiConsumer;
import java.util.function.Consumer;

import org.kordamp.ikonli.javafx.FontIcon;

import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderStroke;
import javafx.scene.layout.BorderStrokeStyle;
import javafx.scene.layout.BorderWidths;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;

public class Button_ extends Button {
    public Button_(String value) {
        super(value);
        FXNodeContext.add(this);
    }

    public Button_(String value, Runnable onClick) {
        super(value);
        setOnAction(ev -> {
            onClick.run();
        });
        FXNodeContext.add(this);
    }

    public Button_(String value, Consumer<InnerModifier> withModifier) {
        super(value);
        withModifier.accept(new InnerModifier(this));
        FXNodeContext.add(this);
    }

    public Button_(BiConsumer<Button_, InnerModifier> withModifier) {
        super();

        withModifier.accept(this, new InnerModifier(this));
        FXNodeContext.add(this);
    }

    public Button_(FontIcon icon, Runnable onClick) {
        setGraphic(icon);
        setOnAction(ev -> {
            onClick.run();
        });

        FXNodeContext.add(this);
    }

    public InnerModifier modifier() {
        return new InnerModifier(this);
    }

    public static class InnerModifier {
        private final Button node;

        public InnerModifier(Button node) {
            this.node = node;
        }

        public InnerModifier marginTop(double margin) {
            VBox.setMargin(node, new Insets(margin, 0, 0, 0));
            return this;
        }

        public InnerModifier fillMaxWidth(boolean b) {
            if (b) {
                node.setMaxWidth(Double.MAX_VALUE);
                VBox.setVgrow(node, Priority.ALWAYS);
            } else {
                node.setMaxWidth(Region.USE_COMPUTED_SIZE);
                VBox.setVgrow(node, Priority.NEVER);
            }
            return this;
        }

        public InnerModifier onClick(Runnable onClick) {
            node.setOnAction(ev -> {
                onClick.run();
            });
            return this;
        }

        public InnerModifier icon(FontIcon icon) {
            node.setGraphic(icon);
            return this;
        }

        public InnerModifier font(javafx.scene.text.Font font) {
            node.setFont(font);
            return this;
        }

        public InnerStyles styles() {
            return new InnerStyles(this);
        }

        public static class InnerStyles {
            private final InnerModifier modifier;
            private CornerRadii cornerRadii = CornerRadii.EMPTY;
            private Paint borderColor = null;

            public InnerStyles(InnerModifier modifier) {
                this.modifier = modifier;
            }

            public InnerStyles bgColor(Color color) {
                modifier.node.setBackground(new Background(
                        new BackgroundFill(color, cornerRadii, null)));
                return this;
            }

            public InnerStyles textColor(Color color) {
                modifier.node.setTextFill(color);
                return this;
            }

            public InnerStyles borderRadius(int radiusAll) {
                this.cornerRadii = new CornerRadii(radiusAll);

                // Reaplica o background com cantos arredondados
                BackgroundFill currentFill = modifier.node.getBackground() != null
                        ? modifier.node.getBackground().getFills().get(0)
                        : new BackgroundFill(Color.TRANSPARENT, cornerRadii, null);
                // recupera e aplica a cor do texto
                Color currentTextColor = (Color) modifier.node.getTextFill();
                modifier.node.setBackground(new Background(
                        new BackgroundFill(
                                currentFill.getFill(),
                                cornerRadii,
                                null)));
                modifier.node.setTextFill(currentTextColor);

                // Atualiza a borda usando a cor definida, se existir
                if (borderColor != null) {
                    modifier.node.setBorder(new Border(
                            new BorderStroke(
                                    borderColor,
                                    BorderStrokeStyle.SOLID,
                                    cornerRadii,
                                    new BorderWidths(1))));
                } else {
                    modifier.node.setBorder(Border.EMPTY);
                }

                return this;
            }

            public InnerStyles borderColor(Paint color) {
                this.borderColor = color;

                // Aplica a borda imediatamente caso já tenha um radius definido
                modifier.node.setBorder(new Border(
                        new BorderStroke(
                                borderColor,
                                BorderStrokeStyle.SOLID,
                                cornerRadii,
                                new BorderWidths(1))));
                return this;
            }

            public InnerStyles useCssClass(String cssClass) {
                if (!modifier.node.getStyleClass().contains(cssClass)) {
                    modifier.node.getStyleClass().add(cssClass);
                }
                return this;
            }

        }

    }
}
