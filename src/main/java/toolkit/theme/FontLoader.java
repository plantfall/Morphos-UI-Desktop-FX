package toolkit.theme;

import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

/**
 * Utilitário para carregar fontes personalizadas dos recursos
 */
public class FontLoader {

    private static final Map<String, Font> loadedFonts = new HashMap<>();
    private static boolean fontsLoaded = false;

    /**
     * Carrega todas as fontes disponíveis nos recursos
     */
    public static void loadFonts() {
        if (fontsLoaded)
            return;

        // Carrega fontes Montserrat
        loadFont("Montserrat-Regular", "/assets/fonts/Montserrat-Regular.ttf");
        loadFont("Montserrat-Bold", "/assets/fonts/Montserrat-Bold.ttf");
        loadFont("Montserrat-Medium", "/assets/fonts/Montserrat-Medium.ttf");
        loadFont("Montserrat-Light", "/assets/fonts/Montserrat-Light.ttf");
        loadFont("Montserrat-SemiBold", "/assets/fonts/Montserrat-SemiBold.ttf");
        loadFont("Montserrat-Black", "/assets/fonts/Montserrat-Black.ttf");

        // Carrega fontes Poppins
        loadFont("Poppins-Regular", "/assets/fonts/Poppins-Regular.ttf");
        loadFont("Poppins-Bold", "/assets/fonts/Poppins-Bold.ttf");
        loadFont("Poppins-Medium", "/assets/fonts/Poppins-Medium.ttf");
        loadFont("Poppins-Light", "/assets/fonts/Poppins-Light.ttf");
        loadFont("Poppins-SemiBold", "/assets/fonts/Poppins-SemiBold.ttf");
        loadFont("Poppins-Black", "/assets/fonts/Poppins-Black.ttf");

        fontsLoaded = true;
    }

    /**
     * Carrega uma fonte específica
     */
    private static void loadFont(String name, String resourcePath) {
        try {
            InputStream fontStream = FontLoader.class.getResourceAsStream(resourcePath);
            if (fontStream != null) {
                Font font = Font.loadFont(fontStream, 12);
                if (font != null) {
                    loadedFonts.put(name, font);
                    System.out.println("Fonte carregada: " + name);
                } else {
                    System.err.println("Falha ao carregar fonte: " + name);
                }
                fontStream.close();
            } else {
                System.err.println("Recurso de fonte não encontrado: " + resourcePath);
            }
        } catch (Exception e) {
            System.err.println("Erro ao carregar fonte " + name + ": " + e.getMessage());
        }
    }

    /**
     * Obtém uma fonte carregada pelo nome
     */
    public static Font getFont(String name) {
        if (!fontsLoaded) {
            loadFonts();
        }
        return loadedFonts.get(name);
    }

    /**
     * Obtém uma fonte com tamanho específico
     */
    public static Font getFontWithSize(String name, double size) {
        Font baseFont = getFont(name);
        if (baseFont != null) {
            return Font.font(baseFont.getFamily(), size);
        }
        return Font.font("System", size);
    }

    /**
     * Obtém uma fonte com peso e tamanho específicos
     */
    public static Font getFontWithWeight(String name, FontWeight weight, double size) {
        Font baseFont = getFont(name);
        if (baseFont != null) {
            return Font.font(baseFont.getFamily(), weight, size);
        }
        return Font.font("System", weight, size);
    }

    /**
     * Verifica se uma fonte foi carregada
     */
    public static boolean isFontLoaded(String name) {
        return loadedFonts.containsKey(name);
    }

    /**
     * Lista todas as fontes carregadas
     */
    public static void listLoadedFonts() {
        System.out.println("Fontes carregadas:");
        for (String fontName : loadedFonts.keySet()) {
            System.out.println("  - " + fontName);
        }
    }
}
