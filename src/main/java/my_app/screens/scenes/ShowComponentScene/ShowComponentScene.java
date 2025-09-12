package my_app.screens.scenes.ShowComponentScene;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.luaj.vm2.Globals;
import org.luaj.vm2.LuaTable;
import org.luaj.vm2.LuaValue;
import org.luaj.vm2.lib.jse.JsePlatform;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.image.Image;
import javafx.scene.layout.Background;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import my_app.components.CanvaComponent;
import my_app.components.ImageComponent;
import my_app.components.TextComponent;
import my_app.components.inputComponents.InputComponent;
import my_app.data.Commons;
import my_app.data.TextComponentData;
import my_app.screens.Home.Home;

record ConfigData(
        double width,
        double height) {
}

// Classe principal corrigida
class Components {
    public List<Map<String, Object>> components = new ArrayList<>();

    public Components() {
    }
}

record ComponentsWrapper(ConfigData config, List<Map<String, Serializable>> components) {
}

public class ShowComponentScene extends Scene {
    public Stage stage = new Stage();

    Home home = new Home(true);

    MenuBar mb = new MenuBar();

    static BorderPane root = new BorderPane();

    public ShowComponentScene() {
        super(root, 750, 300);
        stage.setScene(this);

        Menu menu = new Menu();
        MenuItem is = new MenuItem("Save");

        menu.setOnAction(ev -> {
            System.out.println("clicked");
            saveStateToFile(new File("components.json"),
                    home.canva);
        });

        menu.getItems().add(is);
        mb.getMenus().add(menu);

        root.setTop(mb);
        root.setCenter(home);

    }

    private void saveStateToFile(File file, CanvaComponent canva) {
        ObservableList<Node> children = canva.getChildren();

        ObjectMapper om = new ObjectMapper();
        om.enable(SerializationFeature.INDENT_OUTPUT);

        // Cria a config
        ConfigData configData = new ConfigData(canva.getWidth(), canva.getHeight());

        // Cria a lista de componentes
        List<Map<String, Serializable>> componentsList = new ArrayList<>();

        int textCount = 1;
        for (Node node : children) {

            if (node instanceof TextComponent component) {
                String key = "text_" + textCount++;

                var data = component.getData();

                Map<String, Serializable> map = new HashMap<>();
                map.put(key, data);

                componentsList.add(map);
                textCount++;
            }
        }

        // Cria o wrapper final
        ComponentsWrapper wrapper = new ComponentsWrapper(configData, componentsList);

        Commons.WriteJsonInDisc(file, wrapper);

    }

}
