package my_app.scenes.MainScene;

import java.io.File;
import java.io.FileInputStream;

import com.fasterxml.jackson.databind.ObjectMapper;

import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import javafx.stage.Stage;
import my_app.contexts.ComponentsContext;
import my_app.data.Commons;
import my_app.screens.Home.Home;

public class MainSceneController {
    ComponentsContext componentsContext;

    public record PrefsData(String last_project_saved_path) {
    }

    public MainSceneController(ComponentsContext componentsContext) {
        this.componentsContext = componentsContext;
    }

    public void handleClickLoad(Home home, Stage stage) {
        var fc = new FileChooser();

        fc.setTitle("open selected project");
        fc.getExtensionFilters().add(
                new ExtensionFilter("ui extension", "*.json"));

        var uiFile = fc.showOpenDialog(stage);
        if (uiFile != null)
            componentsContext.loadJsonState(uiFile, home.canva, stage);
    }

    public void loadSceneFromJsonFile(Home home, Stage stage) {
        String appData = System.getenv("LOCALAPPDATA"); // C:\Users\<user>\AppData\Local
        if (appData == null) {
            appData = System.getProperty("user.home") + "\\AppData\\Local";
        }

        var appFolder = new File(appData, "BasicDesktopBuilder");
        var prefsJsonFile = new File(appFolder, "prefs.json");

        try {
            var stream = new FileInputStream(prefsJsonFile);

            var om = new ObjectMapper();
            final var path = om.readValue(stream, PrefsData.class).last_project_saved_path;

            var uiFile = new File(path);

            componentsContext.loadJsonState(uiFile, home.canva, stage);
        } catch (Exception e) {
            e.printStackTrace();
            componentsContext.loadJsonState(null, home.canva, stage);
        }

    }

    public void handleSave(Home home, Stage stage) {
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
            componentsContext.saveStateInJsonFile_v2(file, home.canva);
        }

    }

}
