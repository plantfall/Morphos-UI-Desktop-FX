package toolkit.declarative_components;

import java.util.function.Consumer;
import java.util.function.Predicate;
import java.util.function.Supplier;

import javafx.beans.value.ObservableValue;
import javafx.collections.ListChangeListener;
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
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import toolkit.declarative_components.DeclarativeContractsHandler.RenderIfHandler;
import toolkit.declarative_components.modifiers.LayoutModifier;
import toolkit.declarative_components.modifiers.LayoutStyles;

public class Row_ extends HBox implements DeclarativeContracts {

    private final DeclarativeContractsHandler<Row_> handler = new DeclarativeContractsHandler<>(this);

    private Row_() {
        super();
        // Não expandir por padrão
        HBox.setHgrow(this, Priority.NEVER);
        setMaxWidth(Region.USE_PREF_SIZE);
    }

    public Row_(Runnable content) {
        this();
        FXNodeContext.add(this);
        FXNodeContext.push(this);
        content.run();
        FXNodeContext.pop();
    }

    public Row_(Consumer<InnerModifier> withModifier) {
        this();
        FXNodeContext.add(this);
        FXNodeContext.push(this);
        withModifier.accept(new InnerModifier(this));
        FXNodeContext.pop();
    }

    // @Override
    public void render(Runnable content) {
        FXNodeContext.add(this);
        FXNodeContext.push(this);
        content.run();
        FXNodeContext.pop();
    }

    // @Override
    public void render(Consumer<InnerModifier> withModifier) {
        FXNodeContext.add(this);
        FXNodeContext.push(this);
        withModifier.accept(new InnerModifier(this));
        FXNodeContext.pop();
    }

    @Override
    public void mountEffect(Runnable effect, ObservableValue<?>... dependencies) {

    }

    public InnerModifier modifier() {
        return new InnerModifier(this);
    }

    public void setSpacing_(double spacing) {
        super.setSpacing(spacing);
        requestLayout();
    }

    public void setPadding_(Insets padding) {
        requestLayout();
    }

    public void setAlignment_(Pos alignment) {
        requestLayout();
    }

    public static class InnerModifier extends LayoutModifier<Row_> {

        public InnerModifier(Row_ container) {
            super(container);
        }

        @Override
        public InnerModifier alignment(Pos alignment) {
            node.setAlignment_(alignment);
            return this;
        }

        public InnerModifier spaceBetween() {
            node.setAlignment(Pos.CENTER_LEFT);

            // Adiciona um listener para quando elementos forem adicionados
            node.getChildren().addListener((ListChangeListener<Node>) change -> {
                while (change.next()) {
                    if (change.wasAdded() && node.getChildren().size() == 2) {
                        Region spacer = new Region();
                        HBox.setHgrow(spacer, Priority.ALWAYS);
                        node.getChildren().add(1, spacer);
                    }
                }
            });
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
        public InnerModifier marginTop(double margin) {
            HBox.setMargin(node, new Insets(margin, 0, 0, 0));
            return this;
        }

        @Override
        public InnerModifier padding(double top, double right, double bottom, double left) {
            node.setPadding(new Insets(top, right, bottom, left));
            return this;
        }

        @Override
        public InnerModifier fillMaxHeight(boolean b) {
            if (b) {
                node.setMaxHeight(Double.MAX_VALUE);
                VBox.setVgrow(node, Priority.ALWAYS);
            } else {
                node.setMaxHeight(Region.USE_COMPUTED_SIZE);
                VBox.setVgrow(node, Priority.NEVER);
            }
            return this;
        }

        @Override
        public InnerModifier fillMaxWidth(boolean b) {
            if (b) {
                node.setMaxWidth(Double.MAX_VALUE);
            } else {
                node.setMaxWidth(Region.USE_COMPUTED_SIZE);
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
        public InnerModifier width(double width) {
            node.setPrefWidth(width);
            node.setMinWidth(width);
            node.setMaxWidth(width);
            HBox.setHgrow(node, Priority.NEVER); // <-- não deixar expandir
            return this;
        }

        @Override
        public InnerModifier height(double height) {
            node.setPrefHeight(height);
            return this;
        }

        public InnerStyles styles() {
            return new InnerStyles(this);
        }

        public static class InnerStyles extends LayoutStyles<Row_> {

            public InnerStyles(InnerModifier modifier) {
                super(modifier);
            }

            @Override
            public InnerStyles bgColor(Color color) {
                modifier.getNode().setBackground(new Background(
                        new BackgroundFill(color, null, null)));
                return this;
            }

            @Override
            public InnerStyles borderRadius(int radiusAll) {
                modifier.getNode().setBorder(new Border(
                        new BorderStroke(
                                null, // Color - você pode definir uma cor aqui (ex: Color.BLACK)
                                BorderStrokeStyle.SOLID, // Estilo da borda
                                new CornerRadii(radiusAll), // Raio dos cantos
                                new BorderWidths(1) // Largura da borda (1 pixel por padrão)
                        )));
                return this;
            }

            @Override
            public InnerStyles borderColor(Paint color) {
                // Implementação específica para Row_ se necessário
                return this;
            }
        }

    }

    @Override
    public <T> RenderIfHandler<T> renderIf(ObservableValue<T> observable, Predicate<T> predicate,
            Supplier<Node> nodeSupplier) {
        return handler.renderIf(observable, predicate, nodeSupplier);
    }

    @Override
    public RenderIfHandler<Boolean> renderIf(ObservableValue<Boolean> observable, Supplier<Node> nodeSupplier) {
        return handler.renderIf(observable, nodeSupplier);
    }

}