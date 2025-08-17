package my_app;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Background;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.util.Duration;
import my_app.screens.Home.Home;
import toolkit.Component;
import toolkit.declarative_components.Button_;
import toolkit.declarative_components.Column;
import toolkit.declarative_components.ImageView_;
import toolkit.declarative_components.ImageView_.Shape;
import toolkit.declarative_components.Image_;
import toolkit.declarative_components.Text_;
import toolkit.theme.MaterialTheme;
import java.awt.Desktop;
import java.net.URI;

public class App extends Application {

    Stage primaryStage;
    MaterialTheme theme = MaterialTheme.getInstance();

    @Override
    public void start(Stage primaryStage) throws Exception {
        this.primaryStage = primaryStage;

        StackPane root = new StackPane(new Home());

        Scene scene = new Scene(root, 1200, 700);
        setup(scene);

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