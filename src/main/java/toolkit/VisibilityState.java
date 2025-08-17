package toolkit;

import javafx.scene.Node;

/**
 * Utility class for managing the visibility and layout management state of
 * JavaFX {@link Node} objects.
 * <p>
 * This class provides static methods to enable or disable a node's visibility
 * and managed state.
 * When a node is enabled, it is both visible and managed by its parent layout.
 * When disabled, it is hidden and not managed by its parent layout.
 * </p>
 */
public class VisibilityState {

    /**
     * Enables the specified {@link Node} by setting its visibility and managed
     * state to {@code true}.
     *
     * @param node the JavaFX Node to enable; if {@code null}, this method does
     *             nothing
     */
    public static void Enable(Node node) {
        if (node == null)
            return;

        node.setManaged(true);
        node.setVisible(true);
    }

    /**
     * Disables the specified {@link Node} by setting its visibility and managed
     * state to {@code false}.
     *
     * @param node the JavaFX Node to disable; if {@code null}, this method does
     *             nothing
     */
    public static void Disable(Node node) {
        if (node == null)
            return;
        node.setManaged(false);
        node.setVisible(false);
    }
}