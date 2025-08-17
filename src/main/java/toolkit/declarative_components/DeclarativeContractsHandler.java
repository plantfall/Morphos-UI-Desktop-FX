package toolkit.declarative_components;

import java.util.function.Predicate;
import java.util.function.Supplier;

import javafx.beans.value.ObservableValue;
import javafx.scene.Node;

public class DeclarativeContractsHandler<C extends javafx.scene.layout.Pane>
        implements DeclarativeContracts {

    protected final C container;

    public DeclarativeContractsHandler(C container) {
        this.container = container;
    }

    @Override
    public RenderIfHandler<Boolean> renderIf(
            ObservableValue<Boolean> observable,
            Supplier<Node> nodeSupplier) {
        return renderIf(observable, b -> b != null && b, nodeSupplier);
    }

    @Override
    public <T> RenderIfHandler<T> renderIf(
            ObservableValue<T> observable,
            Predicate<T> predicate,
            Supplier<Node> nodeSupplier) {

        RenderIfHandler<T> handler = new RenderIfHandler<>(container, observable, predicate, nodeSupplier);
        handler.init();
        return handler;
    }

    public static class RenderIfHandler<T> {
        private final javafx.scene.layout.Pane parent;
        private final ObservableValue<T> observable;
        private final Predicate<T> predicate;

        private Node onTrueNode;
        private Supplier<Node> onTrueSupplier;

        private Node onFalseNode;
        private Supplier<Node> onFalseSupplier;

        public RenderIfHandler(javafx.scene.layout.Pane parent,
                ObservableValue<T> observable,
                Predicate<T> predicate,
                Supplier<Node> onTrueSupplier) {
            this.parent = parent;
            this.observable = observable;
            this.predicate = predicate;
            this.onTrueSupplier = onTrueSupplier;
        }

        public void init() {
            onTrueNode = onTrueSupplier.get();
            // parent.getChildren().add(onTrueNode);
            onTrueNode.setVisible(false);
            onTrueNode.setManaged(false);

            if (onFalseSupplier != null) {
                onFalseNode = onFalseSupplier.get();
                // parent.getChildren().add(onFalseNode);
                onFalseNode.setVisible(false);
                onFalseNode.setManaged(false);
            }

            update(observable.getValue());
            observable.addListener((obs, oldVal, newVal) -> update(newVal));
        }

        private void update(T value) {
            boolean showTrue = predicate.test(value);
            if (onTrueNode != null) {
                onTrueNode.setVisible(showTrue);
                onTrueNode.setManaged(showTrue);
            }
            if (onFalseNode != null) {
                onFalseNode.setVisible(!showTrue);
                onFalseNode.setManaged(!showTrue);
            }
        }

        public RenderIfHandler<T> otherwise(Supplier<Node> onFalseSupplier) {
            this.onFalseSupplier = onFalseSupplier;
            if (onFalseNode == null) {
                onFalseNode = onFalseSupplier.get();
                // parent.getChildren().add(onFalseNode);
                onFalseNode.setVisible(false);
                onFalseNode.setManaged(false);
                update(observable.getValue());
            }
            return this;
        }
    }

    @Override
    public void mountEffect(Runnable effect, ObservableValue<?>... dependencies) {
        effect.run();

        if (dependencies != null)
            for (ObservableValue<?> dep : dependencies) {
                dep.addListener((obs, oldVal, newVal) -> effect.run());
            }
    }
}
