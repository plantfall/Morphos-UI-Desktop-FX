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
        item_0.setLayoutX(120.0);
        item_0.setLayoutY(63.199999999999974);
        item_0.setStyle("-fx-background-color: #664db3; -fx-background-radius:  3.0; -fx-border-width: 0.0; -fx-padding: 10.0  10.0  10.0  10.0;");
        root.getChildren().add(item_0);
        Button item_1 = new Button("Im new here");
        item_1.setLayoutX(339.2);
        item_1.setLayoutY(14.39999999999997);
        item_1.setStyle("-fx-background-color: #664db3; -fx-background-radius:  3.0; -fx-border-width: 0.0; -fx-padding: 10.0  10.0  10.0  10.0;");
        root.getChildren().add(item_1);
        Button item_2 = new Button("Im new here");
        item_2.setLayoutX(121.59999999999997);
        item_2.setLayoutY(12.799999999999997);
        item_2.setStyle("-fx-background-color: #664db3; -fx-background-radius:  3.0; -fx-border-width: 0.0; -fx-padding: 10.0  10.0  10.0  10.0;");
        root.getChildren().add(item_2);
ImageView item_3 = new ImageView();
item_3.setLayoutX(224.79999999999995);
item_3.setLayoutY(12.0);
item_3.setFitWidth(100.0);
item_3.setFitHeight(100.0);
item_3.setPreserveRatio(true);
item_3.setSmooth(true);
item_3.setImage(new Image("file:/C:/Users/Eliezer/Documents/DEV/JAVA/JAVA-FX-PROJECTS/basic-desktop-builder/target/classes/assets/images/mago.jpg"));
root.getChildren().add(item_3);
        stage.setScene(new Scene(root, 800, 600));
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
