package layout_generated;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
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
    Text text1 = new Text("teste aqui");
    Button button1 = new Button("oi");
    Button button2 = new Button("Im new here");
    Button button3 = new Button("Im new here");
    Button button4 = new Button("Im new here");
    Button button5 = new Button("Im new here");
    ImageView imgV1 = new ImageView();
    TextField input1 = new TextField("picapau");

    {
        getChildren().addAll(
                text1,
                button1,
                button2,
                button3,
                button4,
                button5,
                input1);
        setup();
        styles();
    }

    void setup() {
        this.setPrefSize(800, 600);
        text1.setLayoutX(150.322266);
        text1.setLayoutY(154.500000);
        button1.setLayoutX(148.000000);
        button1.setLayoutY(17.000000);
        button2.setLayoutX(147.000000);
        button2.setLayoutY(79.000000);
        button3.setLayoutX(201.000000);
        button3.setLayoutY(16.000000);
        button4.setLayoutX(400.000000);
        button4.setLayoutY(300.000000);
        button5.setLayoutX(400.000000);
        button5.setLayoutY(300.000000);
        input1.setLayoutX(402.000000);
        input1.setLayoutY(13.000000);
    }

    void styles() {
        text1.setStyle("-fx-fill:black;-fx-font-size:16;-fx-font-weight:normal;");
        button1.setStyle(
                "-fx-background-color:#664db3;-fx-padding:10 10 10 10;-fx-font-weight:normal;-fx-background-radius:3;-fx-border-radius:3;-fx-text-fill:white;-fx-font-size: 16;-fx-border-width: 0;");
        button2.setStyle(
                "-fx-background-color:#664db3;-fx-padding:10 10 10 10;-fx-font-weight:normal;-fx-background-radius:3;-fx-border-radius:3;-fx-text-fill:white;-fx-font-size: 16;-fx-border-width: 0;");
        button3.setStyle(
                "-fx-background-color:#664db3;-fx-padding:10 10 10 10;-fx-font-weight:normal;-fx-background-radius:3;-fx-border-radius:3;-fx-text-fill:white;-fx-font-size: 16;-fx-border-width: 0;");
        button4.setStyle(
                "-fx-background-color:#664db3;-fx-padding:10 10 10 10;-fx-font-weight:normal;-fx-background-radius:3;-fx-border-radius:3;-fx-text-fill:white;-fx-font-size: 16;-fx-border-width: 0;");
        button5.setStyle(
                "-fx-background-color:#664db3;-fx-padding:10 10 10 10;-fx-font-weight:normal;-fx-background-radius:3;-fx-border-radius:3;-fx-text-fill:white;-fx-font-size: 16;-fx-border-width: 0;");
        imgV1.setStyle("");
        input1.setStyle("-fx-text-fill:black;-fx-font-size:16;-fx-font-weight:normal;");
    }
}