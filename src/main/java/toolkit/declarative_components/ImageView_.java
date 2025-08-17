package toolkit.declarative_components;

import java.util.function.Consumer;

import javafx.animation.ScaleTransition;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Circle;

public class ImageView_ extends ImageView {
    public ImageView_(Image image) {
        super(image);
        FXNodeContext.add(this);
    }

    public ImageView_(Image image, Runnable content) {
        super(image);
        FXNodeContext.add(this);
        content.run();
    }

    public ImageView_(Image image, Consumer<InnerModifier> withModifier) {
        super(image);
        FXNodeContext.add(this);
        withModifier.accept(new InnerModifier(this));
    }

    public InnerModifier modifier() {
        return new InnerModifier(this);
    }

    public enum Shape {
        Rectangle,
        Circle
    }

    public static class InnerModifier {
        private final ImageView_ node;

        public InnerModifier(ImageView_ node) {
            this.node = node;
        }

        public InnerModifier width(double width) {
            node.setFitWidth(width);
            return this;
        }

        public InnerModifier height(double height) {
            node.setFitHeight(height);
            return this;
        }

        public InnerModifier size(double size) {
            node.setFitWidth(size);
            node.setFitHeight(size);
            return this;
        }

        public InnerModifier shape(Shape shape, double arcOrRadius) {
            switch (shape) {
                case Rectangle:
                    Rectangle clipRect = new Rectangle(node.getFitWidth(), node.getFitHeight());
                    clipRect.setArcWidth(arcOrRadius);
                    clipRect.setArcHeight(arcOrRadius);
                    node.setClip(clipRect);
                    break;
                case Circle:
                    Circle clipCircle = new Circle(node.getFitWidth() / 2);
                    node.setClip(clipCircle);
                    break;
            }
            return this;
        }

        public InnerModifier animation(Consumer<ScaleTransition> config) {
            ScaleTransition transition = new ScaleTransition();
            transition.setNode(node);
            config.accept(transition);
            transition.play();
            return this;
        }
    }
}
