package my_app.scenes.MainScene;

import java.io.File;

import javafx.scene.Scene;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import my_app.components.canvaComponent.CanvaComponent;
import my_app.contexts.ComponentsContext;
import my_app.screens.Home.Home;
import my_app.screens.ShowCode.ShowCode;

public class MainScene extends Scene {

    static ComponentsContext componentsContext = ComponentsContext.getInstance();
    static Home home = new Home(false);
    static Stage stage = new Stage();

    final static String FileName = "state copy.json";

    // Scene mainScene = new Scene(mainView, 1200, 650);
    public MainScene() {
        super(createRoot(), 1200, 650);

        loadSceneFromJsonFile(new File(FileName));
    }

    private static VBox createRoot() {
        Menu menu = new Menu("Options");
        MenuItem itemSalvar = new MenuItem("Save");
        MenuItem itemCarregar = new MenuItem("Load");
        MenuItem itemShowCode = new MenuItem("Show code");
        menu.getItems().addAll(itemSalvar, itemCarregar, itemShowCode);

        MenuBar menuBar = new MenuBar(menu);

        VBox mainView = new VBox(menuBar, home);

        HBox.setHgrow(mainView.getChildren().get(1), Priority.ALWAYS);
        VBox.setVgrow(mainView.getChildren().get(1), Priority.ALWAYS);

        itemSalvar.setOnAction(_ -> saveSceneInJsonFile(new File(FileName), home.canva));

        itemCarregar.setOnAction(_ -> {
            // generateJavaCode(home.canvaChildren());
            loadSceneFromJsonFile(new File(FileName));
        });

        itemShowCode.setOnAction(_ -> handleShowJavaCode(home.canva));

        stage.setOnCloseRequest(_ -> saveSceneInJsonFile(new File(FileName), home.canva));

        return mainView;
    }

    private static void saveSceneInJsonFile(File file, CanvaComponent mainCanva) {
        ComponentsContext.SaveStateInJsonFile_v2(file, mainCanva);
    }

    private static void loadSceneFromJsonFile(File file) {
        ComponentsContext.loadJsonState(file, home.canva, stage);
    }

    private static void handleShowJavaCode(CanvaComponent canvaComponent) {
        new ShowCode(canvaComponent)
                .abrir();

    }

}
