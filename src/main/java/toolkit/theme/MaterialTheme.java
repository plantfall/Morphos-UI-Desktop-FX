package toolkit.theme;

import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

/**
 * Sistema de tema global baseado em Material Design
 * Permite definir estilos programaticamente sem depender de arquivos CSS
 */
public class MaterialTheme {

    // Singleton instance
    private static MaterialTheme instance;

    // Cores do tema
    private Color primaryColor = Color.web("#1976D2");
    private Color primaryVariantColor = Color.web("#1565C0");
    private Color secondaryColor = Color.web("#FFC107");
    private Color secondaryVariantColor = Color.web("#FFA000");

    // Cores de fundo
    private Color backgroundColor = Color.web("#FFFFFF");
    private Color surfaceColor = Color.web("#FFFFFF");
    private Color errorColor = Color.web("#B00020");

    // Cores de texto
    private Color onPrimaryColor = Color.web("#FFFFFF");
    private Color onSecondaryColor = Color.web("#000000");
    private Color onBackgroundColor = Color.web("#000000");
    private Color onSurfaceColor = Color.web("#000000");
    private Color onErrorColor = Color.web("#FFFFFF");

    // Cores de estado
    private Color disabledColor = Color.web("#BDBDBD");
    private Color focusColor = Color.web("#1976D2");
    private Color hoverColor = Color.web("#E3F2FD");

    // Tipografia
    private FontWeight headingFontWeight = FontWeight.BOLD;
    private FontWeight bodyFontWeight = FontWeight.NORMAL;
    private FontWeight buttonFontWeight = FontWeight.MEDIUM;

    // Tamanhos de fonte
    private double h1FontSize = 32.0;
    private double h2FontSize = 24.0;
    private double h3FontSize = 20.0;
    private double h4FontSize = 18.0;
    private double h5FontSize = 16.0;
    private double h6FontSize = 14.0;
    private double bodyFontSize = 14.0;
    private double captionFontSize = 12.0;
    private double buttonFontSize = 14.0;

    // Espaçamentos
    private double spacingUnit = 8.0;
    private double borderRadius = 4.0;
    private double borderWidth = 1.0;

    // Família de fontes
    private String fontFamily = "Montserrat";

    private MaterialTheme() {
        // Construtor privado para singleton
    }

    public static MaterialTheme getInstance() {
        if (instance == null) {
            instance = new MaterialTheme();
        }
        return instance;
    }

    // Métodos para obter cores
    public Color getPrimaryColor() {
        return primaryColor;
    }

    public Color getPrimaryVariantColor() {
        return primaryVariantColor;
    }

    public Color getSecondaryColor() {
        return secondaryColor;
    }

    public Color getSecondaryVariantColor() {
        return secondaryVariantColor;
    }

    public Color getBackgroundColor() {
        return backgroundColor;
    }

    public Color getSurfaceColor() {
        return surfaceColor;
    }

    public Color getErrorColor() {
        return errorColor;
    }

    public Color getOnPrimaryColor() {
        return onPrimaryColor;
    }

    public Color getOnSecondaryColor() {
        return onSecondaryColor;
    }

    public Color getOnBackgroundColor() {
        return onBackgroundColor;
    }

    public Color getOnSurfaceColor() {
        return onSurfaceColor;
    }

    public Color getOnErrorColor() {
        return onErrorColor;
    }

    public Color getDisabledColor() {
        return disabledColor;
    }

    public Color getFocusColor() {
        return focusColor;
    }

    public Color getHoverColor() {
        return hoverColor;
    }

    // Métodos para obter tipografia
    public FontWeight getHeadingFontWeight() {
        return headingFontWeight;
    }

    public FontWeight getBodyFontWeight() {
        return bodyFontWeight;
    }

    public FontWeight getButtonFontWeight() {
        return buttonFontWeight;
    }

    // Métodos para obter tamanhos de fonte
    public double getH1FontSize() {
        return h1FontSize;
    }

    public double getH2FontSize() {
        return h2FontSize;
    }

    public double getH3FontSize() {
        return h3FontSize;
    }

    public double getH4FontSize() {
        return h4FontSize;
    }

    public double getH5FontSize() {
        return h5FontSize;
    }

    public double getH6FontSize() {
        return h6FontSize;
    }

    public double getBodyFontSize() {
        return bodyFontSize;
    }

    public double getCaptionFontSize() {
        return captionFontSize;
    }

    public double getButtonFontSize() {
        return buttonFontSize;
    }

    // Métodos para obter espaçamentos
    public double getSpacingUnit() {
        return spacingUnit;
    }

    public double getBorderRadius() {
        return borderRadius;
    }

    public double getBorderWidth() {
        return borderWidth;
    }

    public String getFontFamily() {
        return fontFamily;
    }

    // Métodos para calcular espaçamentos
    public double spacing(int units) {
        return spacingUnit * units;
    }

    public double spacing(double units) {
        return spacingUnit * units;
    }

    // Métodos para obter fontes tipográficas
    public Font getH1Font() {
        return Font.font(fontFamily, headingFontWeight, h1FontSize);
    }

    public Font getH2Font() {
        return Font.font(fontFamily, headingFontWeight, h2FontSize);
    }

    public Font getH3Font() {
        return Font.font(fontFamily, headingFontWeight, h3FontSize);
    }

    public Font getH4Font() {
        return Font.font(fontFamily, headingFontWeight, h4FontSize);
    }

    public Font getH5Font() {
        return Font.font(fontFamily, headingFontWeight, h5FontSize);
    }

    public Font getH6Font() {
        return Font.font(fontFamily, headingFontWeight, h6FontSize);
    }

    public Font getBodyFont() {
        return Font.font(fontFamily, bodyFontWeight, bodyFontSize);
    }

    public Font getCaptionFont() {
        return Font.font(fontFamily, bodyFontWeight, captionFontSize);
    }

    public Font getButtonFont() {
        return Font.font(fontFamily, buttonFontWeight, buttonFontSize);
    }

    // Métodos para definir cores (para personalização)
    public MaterialTheme setPrimaryColor(Color color) {
        this.primaryColor = color;
        return this;
    }

    public MaterialTheme setSecondaryColor(Color color) {
        this.secondaryColor = color;
        return this;
    }

    public MaterialTheme setBackgroundColor(Color color) {
        this.backgroundColor = color;
        return this;
    }

    public MaterialTheme setFontFamily(String fontFamily) {
        this.fontFamily = fontFamily;
        return this;
    }

    // Métodos para obter estilos CSS como String
    public String getPrimaryColorStyle() {
        return String.format("#%02X%02X%02X",
                (int) (primaryColor.getRed() * 255),
                (int) (primaryColor.getGreen() * 255),
                (int) (primaryColor.getBlue() * 255));
    }

    public String getSecondaryColorStyle() {
        return String.format("#%02X%02X%02X",
                (int) (secondaryColor.getRed() * 255),
                (int) (secondaryColor.getGreen() * 255),
                (int) (secondaryColor.getBlue() * 255));
    }

    public String getBackgroundColorStyle() {
        return String.format("#%02X%02X%02X",
                (int) (backgroundColor.getRed() * 255),
                (int) (backgroundColor.getGreen() * 255),
                (int) (backgroundColor.getBlue() * 255));
    }

    public String getOnPrimaryColorStyle() {
        return String.format("#%02X%02X%02X",
                (int) (onPrimaryColor.getRed() * 255),
                (int) (onPrimaryColor.getGreen() * 255),
                (int) (onPrimaryColor.getBlue() * 255));
    }

    public String getOnBackgroundColorStyle() {
        return String.format("#%02X%02X%02X",
                (int) (onBackgroundColor.getRed() * 255),
                (int) (onBackgroundColor.getGreen() * 255),
                (int) (onBackgroundColor.getBlue() * 255));
    }

    public String getFocusColorStyle() {
        return String.format("#%02X%02X%02X",
                (int) (focusColor.getRed() * 255),
                (int) (focusColor.getGreen() * 255),
                (int) (focusColor.getBlue() * 255));
    }

    public String getSurfaceColorStyle() {
        return String.format("#%02X%02X%02X",
                (int) (surfaceColor.getRed() * 255),
                (int) (surfaceColor.getGreen() * 255),
                (int) (surfaceColor.getBlue() * 255));
    }

    /**
     * Obtém uma fonte específica do FontLoader
     */
    public Font getCustomFont(String fontName) {
        return FontLoader.getFont(fontName);
    }

    /**
     * Obtém uma fonte específica com tamanho personalizado
     */
    public Font getCustomFont(String fontName, double size) {
        return FontLoader.getFontWithSize(fontName, size);
    }

    /**
     * Obtém uma fonte específica com peso e tamanho personalizados
     */
    public Font getCustomFont(String fontName, FontWeight weight, double size) {
        return FontLoader.getFontWithWeight(fontName, weight, size);
    }

    /**
     * Carrega todas as fontes disponíveis
     */
    public void loadAllFonts() {
        FontLoader.loadFonts();
    }
}
