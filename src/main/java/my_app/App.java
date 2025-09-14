package my_app;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.databind.ObjectMapper;

import javafx.application.Application;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.text.Font;
import javafx.stage.Screen;
import javafx.stage.Stage;
import my_app.contexts.SubItemsContext;
import my_app.data.CanvaComponentJson;
import my_app.scenes.DataScene.DataScene;
import my_app.scenes.MainScene.MainScene;

public class App extends Application {

    Stage primaryStage;

    public static Font FONT_REGULAR;
    public static Font FONT_MEDIUM;
    public static Font FONT_SEMIBOLD;
    public static Font FONT_BOLD;

    public static List<CanvaComponentJson> ComponentsList = new ArrayList<>();

    @Override
    public void init() {
        FONT_REGULAR = Font.loadFont(getClass().getResourceAsStream("/fonts/Nunito-Regular.ttf"), 14);
        FONT_MEDIUM = Font.loadFont(getClass().getResourceAsStream("/fonts/Nunito-Medium.ttf"), 14);
        FONT_SEMIBOLD = Font.loadFont(getClass().getResourceAsStream("/fonts/Nunito-SemiBold.ttf"), 16);
        FONT_BOLD = Font.loadFont(getClass().getResourceAsStream("/fonts/Nunito-Bold.ttf"), 14);

        loadSubItemsOfCustomComponents();
    }

    File componentsFile = new File("components.json");

    void loadSubItemsOfCustomComponents() {
        SubItemsContext context = SubItemsContext.getInstance();

        try {
            ObjectMapper om = new ObjectMapper();

            // Lê o JSON como array primeiro
            CanvaComponentJson[] componentsArray = om.readValue(componentsFile, CanvaComponentJson[].class);

            ComponentsList = Arrays.asList(componentsArray);

            ComponentsList.forEach(it -> {
                context.addItem("component", it.self.identification);
            });

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        this.primaryStage = primaryStage;

        definirJanelaFromArquivo();

        Scene mainScene = new MainScene();

        // Cria a DataScene passando a referência da mainScene e do primaryStage
        DataScene dataScene = new DataScene(primaryStage, mainScene);

        // Botão muda para DataScene
        // componentData.setOnAction(e -> primaryStage.setScene(dataScene));

        setup(mainScene); // seta ícone, título etc.
        this.primaryStage.show();
    }

    private void definirJanelaFromArquivo() {
        // Lê configuração do arquivo
        Map<String, Double> config = lerConfiguracao(
                "C:\\Users\\Eliezer\\Documents\\DEV\\JAVA\\JAVA-FX-PROJECTS\\basic-desktop-builder\\gui_config.txt");

        int monitorIndex = config.get("monitor").intValue();
        double x = config.get("x");
        double y = config.get("y");
        double width = config.get("width");
        double height = config.get("height");

        // Seleciona monitor e aplica posição/tamanho
        if (monitorIndex >= 0 && monitorIndex < Screen.getScreens().size()) {
            Screen screen = Screen.getScreens().get(monitorIndex);
            Rectangle2D bounds = screen.getVisualBounds();

            primaryStage.setX(bounds.getMinX() + x);
            primaryStage.setY(bounds.getMinY() + y);
            primaryStage.setWidth(width);
            primaryStage.setHeight(height);
        }
    }

    private Map<String, Double> lerConfiguracao(String caminhoArquivo) {
        Map<String, Double> config = new HashMap<>();
        try (BufferedReader br = new BufferedReader(new FileReader(caminhoArquivo))) {
            String line;
            while ((line = br.readLine()) != null) {
                if (line.contains("=")) {
                    String[] parts = line.split("=");
                    String key = parts[0].trim();
                    double value = Double.parseDouble(parts[1].trim());
                    config.put(key, value);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return config;
    }

    void setup(Scene scene) {
        // icon on window
        primaryStage.getIcons().add(new Image(
                getClass().getResourceAsStream("/assets/app_ico_window_32_32.png")));

        // styles
        scene.getStylesheets().add(
                getClass().getResource("/global_styles.css")
                        .toExternalForm());

        this.primaryStage.setTitle("Basic Desktop Builder");
        this.primaryStage.setScene(scene);
    }

    public static void main(String[] args) {
        launch(args);
    }
}
