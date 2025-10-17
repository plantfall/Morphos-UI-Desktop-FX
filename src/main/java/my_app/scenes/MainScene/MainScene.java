package my_app.scenes.MainScene;

import java.io.File;
import java.io.FileInputStream;

import com.fasterxml.jackson.databind.ObjectMapper;

import javafx.event.ActionEvent;
import javafx.scene.Scene;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import javafx.stage.Stage;
import my_app.components.canvaComponent.CanvaComponent;
import my_app.contexts.ComponentsContext;
import my_app.data.Commons;
import my_app.screens.Home.Home;
import my_app.screens.ShowCode.ShowCode;
import toolkit.Component;

public class MainScene extends Scene {

    ComponentsContext componentsContext = ComponentsContext.getInstance();
    Home home = new Home(false);
    Stage stage = new Stage();
    VBox mainView;

    public MainScene() {
        super(new VBox(), 1200, 650);

        setup();

        loadSceneFromJsonFile();

        getStylesheets().add(getClass().getResource("/global_styles.css").toExternalForm());
    }

    void setup() {
        MenuBar menuBar = new MenuBar(createMenuOptions());
        mainView = new VBox(menuBar, home);

        HBox.setHgrow(mainView.getChildren().get(1), Priority.ALWAYS);
        VBox.setVgrow(mainView.getChildren().get(1), Priority.ALWAYS);

        setRoot(mainView);
    }

    @Component
    Menu createMenuOptions() {
        Menu menu = new Menu("Options");
        MenuItem itemSalvar = new MenuItem("Save");
        MenuItem itemCarregar = new MenuItem("Load");
        MenuItem itemShowCode = new MenuItem("Show code");
        menu.getItems().addAll(itemSalvar, itemCarregar, itemShowCode);

        itemSalvar.setOnAction(this::handleSave);

        itemCarregar.setOnAction(_ -> {
            loadSceneFromJsonFile();
        });

        itemShowCode.setOnAction(_ -> handleShowJavaCode(home.canva));

        return menu;
    }

    public record PrefsData(String last_project_saved_path) {
    }

    private void handleSave(ActionEvent value) {
        var fc = new FileChooser();

        fc.setTitle("save project as");
        fc.getExtensionFilters().add(
                new ExtensionFilter("ui extension", ".json"));
        fc.setInitialFileName("ui.json");

        var file = fc.showSaveDialog(stage);
        if (file != null) {
            var filePath = file.getAbsolutePath();

            var pref = new PrefsData(filePath);

            String appData = System.getenv("LOCALAPPDATA"); // C:\Users\<user>\AppData\Local
            if (appData == null) {
                appData = System.getProperty("user.home") + "\\AppData\\Local";
            }

            var appFolder = new File(appData, "BasicDesktopBuilder");
            if (!appFolder.exists()) {
                appFolder.mkdirs();
            }

            var fileInCurrentDirectory = new File(appFolder, "prefs.json");
            Commons.WriteJsonInDisc(fileInCurrentDirectory, pref);

            // json bening saved on specfif directory
            ComponentsContext.SaveStateInJsonFile_v2(file, home.canva);
        }

    }

    private void loadSceneFromJsonFile() {
        String appData = System.getenv("LOCALAPPDATA"); // C:\Users\<user>\AppData\Local
        if (appData == null) {
            appData = System.getProperty("user.home") + "\\AppData\\Local";
        }

        // cuidar do caso quando a pasta nem sequer existe
        var appFolder = new File(appData, "BasicDesktopBuilder");

        var prefsJsonFile = new File(appFolder, "prefs.json");

        try {
            var stream = new FileInputStream(prefsJsonFile);

            var om = new ObjectMapper();
            final var path = om.readValue(stream, PrefsData.class).last_project_saved_path;

            var uiFile = new File(path);

            ComponentsContext.loadJsonState(uiFile, home.canva, stage);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private void handleShowJavaCode(CanvaComponent canvaComponent) {
        new ShowCode(canvaComponent)
                .abrir();

    }

}
