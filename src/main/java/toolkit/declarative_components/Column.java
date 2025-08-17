package toolkit.declarative_components;

import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;

import javafx.beans.value.ObservableValue;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
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
import toolkit.declarative_components.DeclarativeContractsHandler.RenderIfHandler;
import toolkit.declarative_components.modifiers.LayoutModifier;
import toolkit.declarative_components.modifiers.LayoutStyles;

public class Column extends VBox implements DeclarativeContracts {

    private final DeclarativeContractsHandler<Column> handler = new DeclarativeContractsHandler<>(this);

    private Column() {
    }

    public Column(Runnable content) {
        init(content);
    }

    public Column(Consumer<InnerModifier> content) {
        FXNodeContext.add(this); // <---- Adiciona esta Column ao contexto pai
        FXNodeContext.push(this); // Agora, ela é o contexto para seus próprios filhos
        content.accept(new InnerModifier(this));
        FXNodeContext.pop();
        setMinHeight(Region.USE_PREF_SIZE);
        setPrefHeight(Region.USE_COMPUTED_SIZE);

        // 🔑 Importante: impedir crescimento automático
        setMaxHeight(Region.USE_PREF_SIZE);
        VBox.setVgrow(this, Priority.NEVER);
    }

    public Column(BiConsumer<Column, InnerModifier> withModifier) {
        FXNodeContext.add(this); // <---- Adiciona esta Column ao contexto pai
        FXNodeContext.push(this); // Agora, ela é o contexto para seus próprios filhos
        withModifier.accept(this, new InnerModifier(this));
        FXNodeContext.pop();
        setMinHeight(Region.USE_PREF_SIZE);
        setPrefHeight(Region.USE_COMPUTED_SIZE);

        // 🔑 Importante: impedir crescimento automático
        setMaxHeight(Region.USE_PREF_SIZE);
        VBox.setVgrow(this, Priority.NEVER);
    }

    private void init(Runnable content) {
        FXNodeContext.add(this);
        FXNodeContext.push(this);
        content.run();
        FXNodeContext.pop();

        setMinHeight(Region.USE_PREF_SIZE);
        setPrefHeight(Region.USE_COMPUTED_SIZE);
        setMaxHeight(Region.USE_PREF_SIZE);
        VBox.setVgrow(this, Priority.NEVER);
    }

    @Override
    public <T> RenderIfHandler<T> renderIf(
            ObservableValue<T> observable,
            Predicate<T> predicate,
            Supplier<Node> nodeSupplier) {

        return handler.renderIf(observable, predicate, nodeSupplier);
    }

    @Override
    public RenderIfHandler<Boolean> renderIf(
            ObservableValue<Boolean> observable,
            Supplier<Node> nodeSupplier) {
        return handler.renderIf(observable, nodeSupplier);
    }

    @Override
    public void mountEffect(Runnable effect, ObservableValue<?>... dependencies) {
        handler.mountEffect(effect, dependencies);
    }

    public <T> void each(ObservableList<T> items, Function<T, Node> builder, Supplier<Node> renderIfEmpty) {
        VBox container = new VBox();
        container.setMinHeight(Region.USE_PREF_SIZE);
        container.setPrefHeight(Region.USE_COMPUTED_SIZE);

        VBox.setVgrow(container, Priority.NEVER);
        getChildren().add(container);

        Runnable renderList = () -> {
            container.getChildren().clear();

            if (items.isEmpty()) {
                container.getChildren().add(renderIfEmpty.get());
            } else {
                for (T item : items) {
                    container.getChildren().add(builder.apply(item));
                }
            }

        };

        renderList.run();
        items.addListener((ListChangeListener<T>) change -> renderList.run());
    }

    public void setSpacing_(double spacing) {
        super.setSpacing(spacing);
        requestLayout();
    }

    public void setPadding_(Insets padding) {
        super.setPadding(padding);
        requestLayout();
    }

    public void setAlignment_(Pos alignment) {
        super.setAlignment(alignment);
        requestLayout();
    }

    @Override
    protected double computePrefHeight(double width) {
        double totalHeight = getPadding().getTop() + getPadding().getBottom();
        for (Node child : getChildren()) {
            totalHeight += child.prefHeight(-1);
        }
        totalHeight += (getChildren().isEmpty() ? 0 : (getChildren().size() - 1) * getSpacing());
        return totalHeight;
    }

    public InnerModifier modifier() {
        return new InnerModifier(this);
    }

    public static class InnerModifier extends LayoutModifier<Column> {

        public Column self;

        public InnerModifier(Column vbox) {
            super(vbox);
            self = vbox;
        }

        @Override
        public InnerModifier alignment(Pos alignment) {
            node.setAlignment_(alignment);
            return this;
        }

        @Override
        public InnerModifier spacing(double spacing) {
            node.setSpacing_(spacing);
            return this;
        }

        @Override
        public InnerModifier padding(double all) {
            node.setPadding_(new Insets(all));
            return this;
        }

        @Override
        public InnerModifier padding(double top, double right, double bottom, double left) {
            node.setPadding(new Insets(top, right, bottom, left));
            return this;
        }

        @Override
        public InnerModifier marginTop(double margin) {
            // Implementação específica para Column se necessário
            return this;
        }

        @Override
        public InnerModifier fillMaxHeight(boolean enable) {
            if (enable) {
                node.setMaxHeight(Double.MAX_VALUE);
                VBox.setVgrow(node, Priority.ALWAYS);

                // Aplica aos filhos já existentes
                for (Node child : node.getChildren()) {
                    VBox.setVgrow(child, Priority.ALWAYS);
                    child.maxHeight(Double.MAX_VALUE);
                }

                // Observa novos filhos
                node.getChildren().addListener((ListChangeListener<Node>) change -> {
                    while (change.next()) {
                        if (change.wasAdded()) {
                            for (Node addedChild : change.getAddedSubList()) {
                                VBox.setVgrow(addedChild, Priority.ALWAYS);
                                addedChild.maxHeight(Double.MAX_VALUE);
                            }
                        }
                    }
                });

            } else {
                node.setMaxHeight(Region.USE_PREF_SIZE);
                VBox.setVgrow(node, Priority.NEVER);
            }

            return this;
        }

        @Override
        public InnerModifier fillMaxWidth(boolean enable) {
            node.setFillWidth(enable);

            if (enable) {
                // Aplica aos filhos já existentes
                for (Node child : node.getChildren()) {
                    VBox.setVgrow(child, Priority.ALWAYS);
                    child.maxWidth(Double.MAX_VALUE);
                }

                // Observa novos filhos
                node.getChildren().addListener((ListChangeListener<Node>) change -> {
                    while (change.next()) {
                        if (change.wasAdded()) {
                            for (Node addedChild : change.getAddedSubList()) {
                                VBox.setVgrow(addedChild, Priority.ALWAYS);
                                addedChild.maxWidth(Double.MAX_VALUE);
                            }
                        }
                    }
                });
            }

            return this;
        }

        @Override
        public InnerModifier maxHeight(double maxHeight) {
            node.setMaxHeight(maxHeight);
            return this;
        }

        @Override
        public InnerModifier maxWidth(double maxWidth) {
            node.setMaxWidth(maxWidth);
            return this;
        }

        @Override
        public InnerModifier height(double height) {
            node.setHeight(height);
            node.setPrefHeight(height);
            node.setMaxHeight(height);
            return this;
        }

        @Override
        public InnerModifier width(double width) {
            node.setWidth(width);
            node.setPrefWidth(width);
            node.setMaxWidth(width);
            return this;
        }

        public InnerStyles styles() {
            return new InnerStyles(this);
        }

        public static class InnerStyles extends LayoutStyles<Column> {
            private CornerRadii cornerRadii = CornerRadii.EMPTY; // Armazena o raio dos cantos
            private Paint borderColor = null;

            public InnerStyles(InnerModifier modifier) {
                super(modifier);
            }

            @Override
            public InnerStyles bgColor(Color color) {
                modifier.getNode().setBackground(new Background(
                        new BackgroundFill(color,
                                cornerRadii, null)));
                return this;
            }

            @Override
            public InnerStyles borderRadius(int radiusAll) {
                this.cornerRadii = new CornerRadii(radiusAll);

                // Reaplica o background com cantos arredondados
                BackgroundFill currentFill = modifier.getNode().getBackground() != null
                        ? modifier.getNode().getBackground().getFills().get(0)
                        : new BackgroundFill(Color.TRANSPARENT, cornerRadii, null);

                modifier.getNode().setBackground(new Background(
                        new BackgroundFill(
                                currentFill.getFill(),
                                cornerRadii,
                                null)));

                // Atualiza a borda usando a cor definida, se existir
                if (borderColor != null) {
                    modifier.getNode().setBorder(new Border(
                            new BorderStroke(
                                    borderColor,
                                    BorderStrokeStyle.SOLID,
                                    cornerRadii,
                                    new BorderWidths(1))));
                } else {
                    modifier.getNode().setBorder(Border.EMPTY);
                }

                return this;
            }

            @Override
            public InnerStyles borderColor(Paint color) {
                this.borderColor = color;

                // Aplica a borda imediatamente caso já tenha um radius definido
                modifier.getNode().setBorder(new Border(
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
