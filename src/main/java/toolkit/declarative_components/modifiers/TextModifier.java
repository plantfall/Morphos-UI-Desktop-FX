package toolkit.declarative_components.modifiers;

import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.TextAlignment;
import toolkit.theme.ThemeStyler;

/**
 * Classe abstrata base para modificadores de componentes de texto.
 * Promove reutilização de código entre Label_, Text_, ClickableText e outros
 * componentes de texto.
 */
public abstract class TextModifier<T extends Node> {
    protected final T node;

    protected TextModifier(T node) {
        this.node = node;
    }

    /**
     * Define margem superior do componente
     */
    public TextModifier<T> marginTop(double margin) {
        VBox.setMargin(node, new Insets(margin, 0, 0, 0));
        return this;
    }

    /**
     * Define o tamanho da fonte
     */
    public abstract TextModifier<T> fontSize(double fontSize);

    /**
     * Define a fonte diretamente
     */
    public abstract TextModifier<T> font(Font font);

    /**
     * Aplica estilo de cabeçalho H1
     */
    public TextModifier<T> h1() {
        ThemeStyler.h1(node);
        return this;
    }

    /**
     * Aplica estilo de cabeçalho H2
     */
    public TextModifier<T> h2() {
        ThemeStyler.h2(node);
        return this;
    }

    /**
     * Aplica estilo de cabeçalho H3
     */
    public TextModifier<T> h3() {
        ThemeStyler.h3(node);
        return this;
    }

    /**
     * Aplica estilo de corpo de texto
     */
    public TextModifier<T> body() {
        ThemeStyler.body(node);
        return this;
    }

    /**
     * Aplica estilo de legenda
     */
    public TextModifier<T> caption() {
        ThemeStyler.caption(node);
        return this;
    }

    /**
     * Retorna o modificador de estilos
     */
    public abstract TextStyles<T> styles();

    /**
     * Retorna o nó JavaFX subjacente
     */
    public T getNode() {
        return node;
    }
}
