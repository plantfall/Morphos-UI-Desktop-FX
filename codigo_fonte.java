import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;

public class GeneratedUI extends Application {
    @Override
    public void start(Stage stage) {
        Pane root = new Pane();
        Button btn_0 = new Button("Add Button");
        btn.setLayoutX(0.0);
        btn.setLayoutY(0.0);
        root.getChildren().add(btn);
        Button btn_1 = new Button("Salvar");
        btn.setLayoutX(90.0);
        btn.setLayoutY(0.0);
        root.getChildren().add(btn);
        Button btn_2 = new Button("Im new here");
        btn.setLayoutX(50.0);
        btn.setLayoutY(50.0);
        root.getChildren().add(btn);
        Button btn_3 = new Button("Im new here");
        btn.setLayoutX(145.2);
        btn.setLayoutY(52.4);
        root.getChildren().add(btn);
        stage.setScene(new Scene(root, 800, 600));
        stage.show();
    }
    public static void main(String[] args) {
    launch(args);
    }
}
