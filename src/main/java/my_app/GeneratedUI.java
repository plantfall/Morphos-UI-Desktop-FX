package my_app;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class GeneratedUI extends Application {
    @Override
    public void start(Stage stage) {
        Pane root = new Pane();
        Text item_0 = new Text("Im new here");
        item_0.setLayoutX(27.19999999999999);
        item_0.setLayoutY(30.4);
        item_0.setFont(Font.font("System", FontWeight.NORMAL, 17.0));
        item_0.setFill(Color.web("0x334db3ff"));
        root.getChildren().add(item_0);
        ImageView item_1 = new ImageView();
        item_1.setLayoutX(142.39999999999998);
        item_1.setLayoutY(15.999999999999957);
        root.getChildren().add(item_1);
        Button item_2 = new Button("Im new here");
        item_2.setLayoutX(27.200000000000045);
        item_2.setLayoutY(48.79999999999997);
        root.getChildren().add(item_2);
        Text item_3 = new Text("ola mundo");
        item_3.setLayoutX(144.8);
        item_3.setLayoutY(165.6);
        item_3.setFont(Font.font("System", FontWeight.NORMAL, 40.0));
        item_3.setFill(Color.web("0x000000ff"));
        root.getChildren().add(item_3);
        stage.setScene(new Scene(root, 800, 600));
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
