package toolkit.declarative_components;

import java.util.function.BiConsumer;
import java.util.function.Consumer;
import javafx.beans.value.ObservableValue;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.effect.DropShadow;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderStroke;
import javafx.scene.layout.BorderStrokeStyle;
import javafx.scene.layout.BorderWidths;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;

public class Card extends VBox {

    public Card() {
        super();
        setDefaults();
    }

    public Card(Runnable content) {
        setDefaults();
        FXNodeContext.add(this);
        FXNodeContext.push(this);
        content.run();
        FXNodeContext.pop();
    }

    public Card(Consumer<InnerModifier> content) {
        setDefaults();
        FXNodeContext.add(this);
        FXNodeContext.push(this);
        content.accept(new InnerModifier(this));
        FXNodeContext.pop();
    }

    public Card(BiConsumer<Card, InnerModifier> withModifier) {
        setDefaults();
        FXNodeContext.add(this);
        FXNodeContext.push(this);
        withModifier.accept(this, new InnerModifier(this));
        FXNodeContext.pop();
    }

    private void setDefaults() {
        // bg off-white
        setBackground(new Background(
                new BackgroundFill(Color.web("#FAFAFA"), new CornerRadii(8), Insets.EMPTY)));

        // shadow
        DropShadow shadow = new DropShadow();
        shadow.setRadius(6);
        shadow.setOffsetX(0);
        shadow.setOffsetY(2);
        shadow.setColor(Color.rgb(0, 0, 0, 0.15));
        setEffect(shadow);

        setPadding(new Insets(12));

        // default size
        setPrefWidth(300);
        setMinWidth(300);
        setMaxWidth(300);

        // center contend
        setAlignment(Pos.CENTER_LEFT);
        // setFillWidth(false);
    }

    public void mountEffect(Runnable effect, ObservableValue<?>... dependencies) {
        effect.run();

        if (dependencies != null)
            for (ObservableValue<?> dep : dependencies) {
                dep.addListener((obs, oldVal, newVal) -> effect.run());
            }
    }

    public InnerModifier modifier() {
        return new InnerModifier(this);
    }

    public static class InnerModifier {
        private final Card node;

        public InnerModifier(Card node) {
            this.node = node;
        }

        public InnerModifier padding(double all) {
            node.setPadding(new Insets(all));
            return this;
        }

        public InnerModifier padding(double top, double right, double bottom, double left) {
            node.setPadding(new Insets(top, right, bottom, left));
            return this;
        }

        public InnerModifier maxHeight(double maxHeight) {
            node.setMaxHeight(maxHeight);
            return this;
        }

        public InnerModifier maxWidth(double maxWidth) {
            node.setMaxWidth(maxWidth);
            return this;
        }

        public InnerModifier height(double height) {
            node.setHeight(height);
            node.setPrefHeight(height);
            node.setMaxHeight(height);
            return this;
        }

        public InnerModifier width(double width) {
            node.setWidth(width);
            node.setPrefWidth(width);
            node.setMaxWidth(width);
            return this;
        }

        public InnerStyles styles() {
            return new InnerStyles(this);
        }

        public static class InnerStyles {
            private final InnerModifier modifier;
            private CornerRadii cornerRadii = CornerRadii.EMPTY; // Armazena o raio dos cantos
            private Paint borderColor = null;

            public InnerStyles(InnerModifier modifier) {
                this.modifier = modifier;
            }

            public InnerStyles bgColor(Color color) {
                modifier.node.setBackground(new Background(
                        new BackgroundFill(color,
                                cornerRadii, null)));
                return this;
            }

            public InnerStyles borderRadius(int radiusAll) {
                this.cornerRadii = new CornerRadii(radiusAll);

                // Reaplica o background com cantos arredondados
                BackgroundFill currentFill = modifier.node.getBackground() != null
                        ? modifier.node.getBackground().getFills().get(0)
                        : new BackgroundFill(Color.TRANSPARENT, cornerRadii, null);

                modifier.node.setBackground(new Background(
                        new BackgroundFill(
                                currentFill.getFill(),
                                cornerRadii,
                                null)));

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
        }

    }

}
