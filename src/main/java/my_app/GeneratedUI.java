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
ImageView item_0 = new ImageView();
item_0.setLayoutX(35.19999999999996);
item_0.setLayoutY(15.200000000000003);
item_0.setFitWidth(300.0);
item_0.setFitHeight(300.0);
item_0.setPreserveRatio(true);
item_0.setSmooth(true);
item_0.setImage(new Image("file:/C:/Users/Eliezer/Documents/DEV/JAVA/JAVA-FX-PROJECTS/basic-desktop-builder/target/classes/assets/images/mago.jpg"));
root.getChildren().add(item_0);
        Text item_1 = new Text("Bem vindo ao nosso sistema premium");
        item_1.setLayoutX(348.00000000000006);
        item_1.setLayoutY(28.799999999999997);
        item_1.setFont(Font.font("System", FontWeight.NORMAL, 17.0));
        item_1.setFill(Color.web("0x000000ff"));
        root.getChildren().add(item_1);
        Button item_2 = new Button("Acessar agora");
        item_2.setLayoutX(347.19999999999993);
        item_2.setLayoutY(51.19999999999999);
        item_2.setStyle("-fx-background-color: #1a4d4d; -fx-background-radius:  15.0; -fx-border-width: 0.0; -fx-padding: 5.0  12.0  5.0  12.0;");
        root.getChildren().add(item_2);
        stage.setScene(new Scene(root, 800, 600));
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
