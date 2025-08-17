package toolkit.declarative_components;

import java.util.Stack;

import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.layout.Pane;
import toolkit.annotations.DeclarativeComponent;

public class FXNodeContext {
    private static final Stack<Parent> context = new Stack<>();

    public static void push(Parent parent) {
        context.push(parent);
    }

    public static void pop() {
        context.pop();
    }

    public static void add(Node node) {
        if (!context.isEmpty()) {
            Parent parent = context.peek();
            if (parent instanceof Pane pane) {
                pane.getChildren().add(node);
            }
        }
    }

    public static void tryAutoAdd(Node node) {
        if (node.getClass().isAnnotationPresent(DeclarativeComponent.class)) {
            add(node);
        }
    }

    public static Node getLastNode() {
        if (!context.isEmpty()) {
            Parent parent = context.peek();
            if (parent instanceof Pane pane && !pane.getChildren().isEmpty()) {
                return pane.getChildren().get(pane.getChildren().size() - 1);
            }
        }
        return null;
    }
}