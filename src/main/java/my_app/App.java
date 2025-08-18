package my_app;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.stage.Stage;
import my_app.screens.Home.Home;
import my_app.screens.scenes.DataScene.DataScene;
import toolkit.theme.MaterialTheme;

public class App extends Application {

    Stage primaryStage;
    MaterialTheme theme = MaterialTheme.getInstance();

    @Override
    public void start(Stage primaryStage) throws Exception {
        this.primaryStage = primaryStage;

        // Botão para ir para outra scene
        var btnData = new Button("Data");

        // Cena principal
        HBox mainView = new HBox(btnData, new Home());
        HBox.setHgrow(mainView.getChildren().get(1), Priority.ALWAYS);
        mainView.setStyle("-fx-background-color:brown");

        Scene mainScene = new Scene(mainView, 1200, 650);

        // Cria a DataScene passando a referência da mainScene e do primaryStage
        DataScene dataScene = new DataScene(primaryStage, mainScene);

        // Botão muda para DataScene
        btnData.setOnAction(e -> primaryStage.setScene(dataScene));

        setup(mainScene); // seta ícone, título etc.
        this.primaryStage.show();
    }

    void setup(Scene scene) {
        // icon on window
        primaryStage.getIcons().add(new Image(
                getClass().getResourceAsStream("/assets/app_ico_window_32_32.png")));

        // load fonts
        theme.loadAllFonts();

        this.primaryStage.setTitle("Basic Desktop Builder");
        this.primaryStage.setScene(scene);
    }

    public static void main(String[] args) {
        launch(args);
    }
}