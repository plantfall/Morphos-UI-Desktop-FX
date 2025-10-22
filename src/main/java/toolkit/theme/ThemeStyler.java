package toolkit.theme;

import javafx.scene.Node;
import javafx.scene.control.Control;
import javafx.scene.control.Labeled;
import javafx.scene.text.Text;

/**
 * Utilitário para aplicar estilos do tema aos componentes
 */
@Deprecated
public class ThemeStyler {

    private static final MaterialTheme theme = MaterialTheme.getInstance();

    /**
     * Aplica estilo de cabeçalho H1 ao componente
     */
    public static <T extends Node> T h1(T node) {
        if (node instanceof Labeled) {
            ((Labeled) node).setFont(theme.getH1Font());
            ((Labeled) node).setTextFill(theme.getOnBackgroundColor());
        } else if (node instanceof Text) {
            ((Text) node).setFont(theme.getH1Font());
            ((Text) node).setFill(theme.getOnBackgroundColor());
        }
        return node;
    }

    /**
     * Aplica estilo de cabeçalho H2 ao componente
     */
    public static <T extends Node> T h2(T node) {
        if (node instanceof Labeled) {
            ((Labeled) node).setFont(theme.getH2Font());
            ((Labeled) node).setTextFill(theme.getOnBackgroundColor());
        } else if (node instanceof Text) {
            ((Text) node).setFont(theme.getH2Font());
            ((Text) node).setFill(theme.getOnBackgroundColor());
        }
        return node;
    }

    /**
     * Aplica estilo de cabeçalho H3 ao componente
     */
    public static <T extends Node> T h3(T node) {
        if (node instanceof Labeled) {
            ((Labeled) node).setFont(theme.getH3Font());
            ((Labeled) node).setTextFill(theme.getOnBackgroundColor());
        } else if (node instanceof Text) {
            ((Text) node).setFont(theme.getH3Font());
            ((Text) node).setFill(theme.getOnBackgroundColor());
        }
        return node;
    }

    /**
     * Aplica estilo de corpo ao componente
     */

    /**
     * Aplica estilo de legenda ao componente
     */
    public static <T extends Node> T caption(T node) {
        if (node instanceof Labeled) {
            ((Labeled) node).setFont(theme.getCaptionFont());
            ((Labeled) node).setTextFill(theme.getOnBackgroundColor());
        } else if (node instanceof Text) {
            ((Text) node).setFont(theme.getCaptionFont());
            ((Text) node).setFill(theme.getOnBackgroundColor());
        }
        return node;
    }

    /**
     * Aplica estilo de botão ao componente
     */
    public static <T extends Node> T button(T node) {
        if (node instanceof Labeled) {
            ((Labeled) node).setFont(theme.getButtonFont());
            ((Labeled) node).setTextFill(theme.getOnPrimaryColor());
        }
        return node;
    }

    /**
     * Aplica cor primária ao componente
     */
    public static <T extends Node> T primary(T node) {
        if (node instanceof Labeled) {
            ((Labeled) node).setTextFill(theme.getPrimaryColor());
        } else if (node instanceof Text) {
            ((Text) node).setFill(theme.getPrimaryColor());
        }
        return node;
    }

    /**
     * Aplica cor secundária ao componente
     */
    public static <T extends Node> T secondary(T node) {
        if (node instanceof Labeled) {
            ((Labeled) node).setTextFill(theme.getSecondaryColor());
        } else if (node instanceof Text) {
            ((Text) node).setFill(theme.getSecondaryColor());
        }
        return node;
    }

    /**
     * Aplica cor de erro ao componente
     */
    public static <T extends Node> T error(T node) {
        if (node instanceof Labeled) {
            ((Labeled) node).setTextFill(theme.getErrorColor());
        } else if (node instanceof Text) {
            ((Text) node).setFill(theme.getErrorColor());
        }
        return node;
    }

    /**
     * Aplica cor de texto sobre fundo ao componente
     */
    public static <T extends Node> T onBackground(T node) {
        if (node instanceof Labeled) {
            ((Labeled) node).setTextFill(theme.getOnBackgroundColor());
        } else if (node instanceof Text) {
            ((Text) node).setFill(theme.getOnBackgroundColor());
        }
        return node;
    }

    /**
     * Aplica cor de texto sobre superfície ao componente
     */
    public static <T extends Node> T onSurface(T node) {
        if (node instanceof Labeled) {
            ((Labeled) node).setTextFill(theme.getOnSurfaceColor());
        } else if (node instanceof Text) {
            ((Text) node).setFill(theme.getOnSurfaceColor());
        }
        return node;
    }

    /**
     * Aplica cor de texto sobre primária ao componente
     */
    public static <T extends Node> T onPrimary(T node) {
        if (node instanceof Labeled) {
            ((Labeled) node).setTextFill(theme.getOnPrimaryColor());
        } else if (node instanceof Text) {
            ((Text) node).setFill(theme.getOnPrimaryColor());
        }
        return node;
    }

    /**
     * Aplica cor de texto sobre secundária ao componente
     */
    public static <T extends Node> T onSecondary(T node) {
        if (node instanceof Labeled) {
            ((Labeled) node).setTextFill(theme.getOnSecondaryColor());
        } else if (node instanceof Text) {
            ((Text) node).setFill(theme.getOnSecondaryColor());
        }
        return node;
    }

    /**
     * Aplica cor de texto sobre erro ao componente
     */
    public static <T extends Node> T onError(T node) {
        if (node instanceof Labeled) {
            ((Labeled) node).setTextFill(theme.getOnErrorColor());
        } else if (node instanceof Text) {
            ((Text) node).setFill(theme.getOnErrorColor());
        }
        return node;
    }

    /**
     * Aplica cor de texto desabilitado ao componente
     */
    public static <T extends Node> T disabled(T node) {
        if (node instanceof Labeled) {
            ((Labeled) node).setTextFill(theme.getDisabledColor());
        } else if (node instanceof Text) {
            ((Text) node).setFill(theme.getDisabledColor());
        }
        return node;
    }

    /**
     * Aplica estilo de botão ao componente
     */
    public static <T extends Node> T buttonStyle(T node) {
        if (node instanceof Control) {
            Control control = (Control) node;
            control.setStyle(String.format(
                    "-fx-font-size: %fpx; " +
                            "-fx-font-weight: %s; " +
                            "-fx-background-color: %s; " +
                            "-fx-text-fill: %s; " +
                            "-fx-border-radius: %fpx; " +
                            "-fx-padding: %fpx %fpx;",
                    theme.getButtonFontSize(),
                    theme.getButtonFontWeight().toString().toLowerCase(),
                    theme.getPrimaryColorStyle(),
                    theme.getOnPrimaryColorStyle(),
                    theme.getBorderRadius(),
                    theme.spacing(1),
                    theme.spacing(2)));
        }
        return node;
    }

    /**
     * Aplica estilo de card ao componente
     */
    public static <T extends Node> T card(T node) {
        node.setStyle(String.format(
                "-fx-background-color: %s; " +
                        "-fx-border-radius: %fpx; " +
                        "-fx-effect: dropshadow(gaussian, rgba(0,0,0,0.1), 4, 0, 0, 2); " +
                        "-fx-padding: %fpx;",
                theme.getSurfaceColorStyle(),
                theme.getBorderRadius(),
                theme.spacing(2)));
        return node;
    }

    /**
     * Aplica margem ao componente
     */
    public static <T extends Node> T margin(T node, int units) {
        node.setStyle(String.format(
                "-fx-margin: %fpx;",
                theme.spacing(units)));
        return node;
    }

    /**
     * Aplica padding ao componente
     */
    public static <T extends Node> T padding(T node, int units) {
        node.setStyle(String.format(
                "-fx-padding: %fpx;",
                theme.spacing(units)));
        return node;
    }

    /**
     * Aplica estilo de container ao componente
     */
    public static <T extends Node> T container(T node) {
        node.setStyle(String.format(
                "-fx-background-color: %s; " +
                        "-fx-padding: %fpx;",
                theme.getBackgroundColorStyle(),
                theme.spacing(2)));
        return node;
    }
}