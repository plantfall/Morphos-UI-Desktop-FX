package toolkit.declarative_components.modifiers;

import javafx.scene.Node;
import javafx.scene.paint.Color;
import toolkit.theme.ThemeStyler;

/**
 * Classe abstrata base para estilos de componentes de texto.
 * Promove reutilização de código entre Label_, Text_, ClickableText e outros
 * componentes de texto.
 */
public abstract class TextStyles<T extends Node> {
    protected final TextModifier<T> modifier;

    protected TextStyles(TextModifier<T> modifier) {
        this.modifier = modifier;
    }

    /**
     * Define a cor do texto
     */
    public abstract TextStyles<T> color(Color color);

    /**
     * Aplica cor primária do tema
     */
    public TextStyles<T> primary() {
        ThemeStyler.primary(modifier.getNode());
        return this;
    }

    /**
     * Aplica cor secundária do tema
     */
    public TextStyles<T> secondary() {
        ThemeStyler.secondary(modifier.getNode());
        return this;
    }

    /**
     * Aplica cor de erro do tema
     */
    public TextStyles<T> error() {
        ThemeStyler.error(modifier.getNode());
        return this;
    }

    /**
     * Aplica cor de texto sobre fundo
     */
    public TextStyles<T> onBackground() {
        ThemeStyler.onBackground(modifier.getNode());
        return this;
    }

    /**
     * Aplica cor de texto sobre superfície
     */
    public TextStyles<T> onSurface() {
        ThemeStyler.onSurface(modifier.getNode());
        return this;
    }

    /**
     * Aplica cor de texto desabilitado
     */
    public TextStyles<T> disabled() {
        ThemeStyler.disabled(modifier.getNode());
        return this;
    }

    /**
     * Define o peso da fonte
     */
    public abstract TextStyles<T> fontWeight(String weight);

    /**
     * Define o peso da fonte como negrito
     */
    public TextStyles<T> fontWeightBold() {
        return fontWeight("bold");
    }

    /**
     * Define o peso da fonte como normal
     */
    public TextStyles<T> fontWeightNormal() {
        return fontWeight("normal");
    }

    /**
     * Retorna ao modificador para continuar a cadeia
     */
    public TextModifier<T> end() {
        return modifier;
    }
}
