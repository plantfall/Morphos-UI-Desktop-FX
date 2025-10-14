package layout_generated;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import javafx.stage.Stage;

//here has the purpose of be an entrypoint of code genereated
public class App_base extends Application {

    Stage primaryStage;

    @Override
    public void start(Stage primaryStage) throws Exception {
        this.primaryStage = primaryStage;

        this.primaryStage.setTitle("Basic Desktop Builder");
        this.primaryStage.setScene(new Scene(new Screen()));

        this.primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}

class Screen extends Pane {
    Text text1 = new Text("Bem vindo ao app");
    Button button1 = new Button("Abrir chamado");
    ImageView imgV1 = new ImageView();
    TextField input1 = new TextField("");

    {
        getChildren().addAll(
                text1,
                button1,
                imgV1,
                input1);
        setup();
        styles();
    }

    void setup() {
        this.setPrefSize(800, 600);
        text1.setLayoutX(81.322266);
        text1.setLayoutY(40.019531);
        button1.setLayoutX(84.000000);
        button1.setLayoutY(59.000000);
        final var url = "file:/C:/dev/JAVA/JAVA-FX-PROJECTS/basic-desktop-builder/target/classes/assets/images/mago.jpg";
        imgV1.setFitWidth(400);
        imgV1.setFitHeight(100);
        imgV1.setImage(new Image(url));
        imgV1.setLayoutX(469.000000);
        imgV1.setLayoutY(39.000000);
        input1.setLayoutX(86.000000);
        input1.setLayoutY(126.000000);
        input1.setPromptText("digite seu nome");
    }

    void styles() {
        text1.setStyle("-fx-fill:black;-fx-font-size:16;-fx-font-weight:normal;");
        button1.setStyle(
                "-fx-background-color:#664db3;-fx-padding:10 10 10 10;-fx-font-weight:normal;-fx-background-radius:3;-fx-border-radius:3;-fx-text-fill:white;-fx-font-size: 16;-fx-border-width: 0;");
        imgV1.setStyle("");
        input1.setStyle("-fx-text-fill:#4d8080;-fx-font-size:13;-fx-font-weight:normal;");
    }

}
