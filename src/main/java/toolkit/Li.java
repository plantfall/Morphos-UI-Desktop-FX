package toolkit;

import javafx.scene.text.Text;

/**
 * The {@code Li} class simulates an HTML &lt;li&gt; (list item) element in
 * JavaFX.
 * <p>
 * It extends the {@link Text} class, automatically prepending a bullet ("•") to
 * the content
 * and applying a default font size and left padding for visual alignment.
 * </p>
 *
 * Example usage:
 * 
 * <pre>
 * Li item = new Li("This is a list item");
 * </pre>
 */
public class Li extends Text {
    /**
     * Constructs a new list item with the specified content.
     *
     * @param content the text to display after the bullet
     */
    public Li(String content) {
        super("• " + content);
        setStyle("-fx-font-size: 14px; -fx-padding: 0 0 0 16px;");
    }
}
