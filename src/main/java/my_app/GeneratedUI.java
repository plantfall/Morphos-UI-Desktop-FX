package my_app;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.text.*;
import javafx.scene.image.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class GeneratedUI extends Application {
    @Override
    public void start(Stage stage) {
        Pane root = new Pane();
        Button item_0 = new Button("Im new here");
        item_0.setLayoutX(404.0);
        item_0.setLayoutY(318.4);
        item_0.setStyle("-fx-background-color: #664db3; -fx-background-radius:  3.0; -fx-border-width: 0; -fx-padding: 10.0  10.0  10.0  10.0; -fx-font-family: 'System'; -fx-font-size: 16; -fx-font-weight: normal; -fx-text-fill: white;");
        root.getChildren().add(item_0);
        stage.setScene(new Scene(root, 800, 600));
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
