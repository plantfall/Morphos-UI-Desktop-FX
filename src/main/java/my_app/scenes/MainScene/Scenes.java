package my_app.scenes.MainScene;

import javafx.scene.Scene;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import my_app.screens.Home.Home;

public class Scenes {
    public static Scene MainScene() {

        Home home = new Home(false);
        Stage stage = new Stage();

        Menu menu = new Menu("Options");
        MenuItem itemSalvar = new MenuItem("Save");
        MenuItem itemCarregar = new MenuItem("Load");
        MenuItem itemShowCode = new MenuItem("Show code");
        menu.getItems().addAll(itemSalvar, itemCarregar, itemShowCode);

        MenuBar menuBar = new MenuBar(menu);

        VBox mainView = new VBox(menuBar, home);

        HBox.setHgrow(mainView.getChildren().get(1), Priority.ALWAYS);
        VBox.setVgrow(mainView.getChildren().get(1), Priority.ALWAYS);

        // itemSalvar.setOnAction(_ -> saveSceneInJsonFile(new File(FileName),
        // home.canva));

        // itemCarregar.setOnAction(_ -> {
        // // generateJavaCode(home.canvaChildren());
        // loadSceneFromJsonFile(new File(FileName));
        // });

        // itemShowCode.setOnAction(_ -> handleShowJavaCode(home.canva));

        // stage.setOnCloseRequest(_ -> saveSceneInJsonFile(new File(FileName),
        // home.canva));

        var scene = new Scene(mainView, 1200, 650);
        return scene;

    }
}
