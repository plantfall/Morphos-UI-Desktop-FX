package my_app;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import my_app.data.Commons;
import my_app.scenes.DataScene.DataScene;
import my_app.scenes.MainScene.MainScene;

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

        // Scene splashScene = new SplashScene(primaryStage);
        // primaryStage.setScene(splashScene);

        Scene mainScene = new MainScene();
        primaryStage.setScene(mainScene);

        mainScene.getStylesheets().addAll(
                getClass().getResource("/global_styles.css").toExternalForm(),
                getClass().getResource("/typography.css").toExternalForm());

        // getStylesheets().add(getClass().getResource("/global_styles.css").toExternalForm());
        primaryStage.addEventFilter(javafx.scene.input.MouseEvent.MOUSE_CLICKED, event -> {
            javafx.scene.Node node = event.getPickResult().getIntersectedNode();
            if (node != null) {
                System.out.println("Clique em: " + node.getClass().getSimpleName());
                // node.setStyle("-fx-effect: dropshadow(gaussian, red, 10, 0, 0, 0);");

                if (node.getId() != null)
                    System.out.println("ID: " + node.getId());
            }
        });

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
