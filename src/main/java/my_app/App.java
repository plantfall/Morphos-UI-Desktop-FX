package my_app;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import my_app.data.Commons;
import my_app.scenes.DataScene.DataScene;
import my_app.scenes.MainScene.MainScene;
import my_app.scenes.SplashScene.SplashScene;

public class App extends Application {

    public static Font FONT_REGULAR;
    public static Font FONT_MEDIUM;
    public static Font FONT_SEMIBOLD;
    public static Font FONT_BOLD;

    @Override
    public void init() {

        FONT_REGULAR = Font.loadFont(getClass().getResourceAsStream("/fonts/Nunito-Regular.ttf"), 14);
        FONT_MEDIUM = Font.loadFont(getClass().getResourceAsStream("/fonts/Nunito-Medium.ttf"), 14);
        FONT_SEMIBOLD = Font.loadFont(getClass().getResourceAsStream("/fonts/Nunito-SemiBold.ttf"), 16);
        FONT_BOLD = Font.loadFont(getClass().getResourceAsStream("/fonts/Nunito-Bold.ttf"), 14);

        // loadSubItemsOfCustomComponents();
        // ComponentsContext.getInstance();
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        primaryStage.setTitle(Commons.AppName + " " + Commons.AppVersion);

        Scene splashScene = new SplashScene(primaryStage);
        primaryStage.setScene(splashScene);

        Scene mainScene = new MainScene();

        // Cria a DataScene passando a referência da mainScene e do primaryStage
        DataScene dataScene = new DataScene(primaryStage, mainScene);

        // Botão muda para DataScene
        // componentData.setOnAction(e -> primaryStage.setScene(dataScene));

        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
