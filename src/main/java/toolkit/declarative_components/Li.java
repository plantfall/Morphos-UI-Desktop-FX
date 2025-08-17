package toolkit.declarative_components;

import javafx.beans.binding.Bindings;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
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
public class Li extends Text_ {
    private final StringProperty content = new SimpleStringProperty();

    public Li(String content) {
        super(""); // inicia vazio

        setStyle("-fx-font-size: 14px; -fx-padding: 0 0 0 16px;");
        this.content.set(content);

        // Sempre que mudar o conteúdo, atualiza com a bullet
        textProperty().bind(Bindings.concat("• ", this.content));
    }

    public StringProperty contentProperty() {
        return content;
    }

    public void setContent(String content) {
        this.content.set(content);
    }

    public String getContent() {
        return content.get();
    }
}
