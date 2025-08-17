package toolkit.declarative_components.modifiers;

import javafx.geometry.Pos;

/**
 * Classe abstrata para modificar componentes de layout (Row_ e Column)
 * Centraliza métodos comuns de layout para promover reutilização de código
 */
public abstract class LayoutModifier<T> {

    protected final T node;

    public LayoutModifier(T node) {
        this.node = node;
    }

    /**
     * Define o alinhamento do container
     */
    public abstract LayoutModifier<T> alignment(Pos alignment);

    /**
     * Define o espaçamento entre elementos filhos
     */
    public abstract LayoutModifier<T> spacing(double spacing);

    /**
     * Define padding uniforme em todos os lados
     */
    public abstract LayoutModifier<T> padding(double all);

    /**
     * Define padding específico para cada lado
     */
    public abstract LayoutModifier<T> padding(double top, double right, double bottom, double left);

    /**
     * Define margem superior
     */
    public abstract LayoutModifier<T> marginTop(double margin);

    /**
     * Define se o container deve preencher a altura máxima disponível
     */
    public abstract LayoutModifier<T> fillMaxHeight(boolean enable);

    /**
     * Define se o container deve preencher a largura máxima disponível
     */
    public abstract LayoutModifier<T> fillMaxWidth(boolean enable);

    /**
     * Define altura máxima
     */
    public abstract LayoutModifier<T> maxHeight(double maxHeight);

    /**
     * Define largura máxima
     */
    public abstract LayoutModifier<T> maxWidth(double maxWidth);

    /**
     * Define altura fixa
     */
    public abstract LayoutModifier<T> height(double height);

    /**
     * Define largura fixa
     */
    public abstract LayoutModifier<T> width(double width);

    /**
     * Retorna o container de estilos
     */
    public abstract LayoutStyles<T> styles();

    /**
     * Retorna o nó subjacente
     */
    public T getNode() {
        return node;
    }
}
