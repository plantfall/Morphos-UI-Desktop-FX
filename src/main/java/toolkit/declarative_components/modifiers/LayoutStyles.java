package toolkit.declarative_components.modifiers;

import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderStroke;
import javafx.scene.layout.BorderStrokeStyle;
import javafx.scene.layout.BorderWidths;
import javafx.scene.layout.CornerRadii;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;

/**
 * Classe abstrata para estilos de componentes de layout (Row_ e Column)
 * Centraliza métodos comuns de estilos para promover reutilização de código
 */
public abstract class LayoutStyles<T> {

    protected final LayoutModifier<T> modifier;

    public LayoutStyles(LayoutModifier<T> modifier) {
        this.modifier = modifier;
    }

    /**
     * Define a cor de fundo
     */
    public abstract LayoutStyles<T> bgColor(Color color);

    /**
     * Define borda com raio específico
     */
    public abstract LayoutStyles<T> borderRadius(int radiusAll);

    /**
     * Define a cor da borda
     */
    public abstract LayoutStyles<T> borderColor(Paint color);

    /**
     * Retorna ao modificador para permitir encadeamento
     */
    public LayoutModifier<T> end() {
        return modifier;
    }

    /**
     * Retorna o modificador
     */
    public LayoutModifier<T> getModifier() {
        return modifier;
    }
}
