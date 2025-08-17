package toolkit.declarative_components;

import java.util.function.Consumer;

import javafx.scene.layout.Region;

public class Spacer extends Region {
    public Spacer() {
        super();
        FXNodeContext.add(this);
        // Por padrão sem tamanho
        setMinSize(0, 10);
        setPrefSize(0, 10);
        setMaxHeight(10);
    }

    public Spacer(Consumer<InnerModifier> content) {
        this();
        content.accept(new InnerModifier(this));
    }

    public InnerModifier modifier() {
        return new InnerModifier(this);
    }

    public static class InnerModifier {
        private final Spacer node;

        public InnerModifier(Spacer node) {
            this.node = node;
        }

        /** Define altura fixa do Spacer */
        public InnerModifier height(double height) {
            node.setMinHeight(height);
            node.setPrefHeight(height);
            node.setMaxHeight(height);
            return this;
        }

        /** Define largura fixa do Spacer */
        public InnerModifier width(double width) {
            node.setMinWidth(width);
            node.setPrefWidth(width);
            node.setMaxWidth(width);
            return this;
        }
    }
}
