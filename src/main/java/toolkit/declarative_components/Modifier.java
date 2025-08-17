package toolkit.declarative_components;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import java.util.function.Consumer;

public class Modifier {
    public static Consumer<VBox> vbox(Pos alignment, double spacing, Insets padding) {
        return v -> {
            v.setAlignment(alignment);
            v.setSpacing(spacing);
            v.setPadding(padding);
        };
    }

    public static Consumer<VBox> alignment(Pos alignment) {
        return v -> v.setAlignment(alignment);
    }

    public static Consumer<VBox> spacing(double spacing) {
        return v -> v.setSpacing(spacing);
    }

    public static Consumer<VBox> padding(Insets padding) {
        return v -> v.setPadding(padding);
    }

    // Composição de múltiplos
    public static <T extends Pane> Consumer<T> combine(Consumer<T>... modifiers) {
        return pane -> {
            for (Consumer<T> modifier : modifiers) {
                modifier.accept(pane);
            }
        };
    }
}
