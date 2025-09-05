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
        item_0.setLayoutX(3.1999999999999886);
        item_0.setLayoutY(4.799999999999969);
        item_0.setStyle("-fx-background-color: #664db3; -fx-background-radius:  3.0; -fx-border-width: 0.0; -fx-padding: 10.0  10.0  10.0  10.0; -fx-font-family: 'System'; -fx-font-size: 12.0; -fx-font-style: Regular;");
        root.getChildren().add(item_0);
ImageView item_1 = new ImageView();
item_1.setLayoutX(3.9999999999999716);
item_1.setLayoutY(47.19999999999999);
item_1.setFitWidth(100.0);
item_1.setFitHeight(100.0);
item_1.setPreserveRatio(true);
item_1.setSmooth(true);
item_1.setImage(new Image("file:/C:/Users/Eliezer/Documents/DEV/JAVA/JAVA-FX-PROJECTS/basic-desktop-builder/target/classes/assets/images/mago.jpg"));
root.getChildren().add(item_1);
        Button item_2 = new Button("Im new here");
        item_2.setLayoutX(5.600000000000051);
        item_2.setLayoutY(155.19999999999996);
        item_2.setStyle("-fx-background-color: #664db3; -fx-background-radius:  3.0; -fx-border-width: 0.0; -fx-padding: 20.0  20.0  20.0  20.0; -fx-font-family: 'System'; -fx-font-size: 12.0; -fx-font-style: Regular;");
        root.getChildren().add(item_2);
        stage.setScene(new Scene(root, 800, 600));
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
