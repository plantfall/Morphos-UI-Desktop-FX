package my_app.scenes.ShowComponentScene;

import java.io.File;
import java.util.List;
import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import my_app.components.canvaComponent.CanvaComponent;
import my_app.contexts.ComponentsContext;
import my_app.data.Commons;
import my_app.data.ComponentsWrapper;
import my_app.screens.Home.Home;

public class ShowComponentScene extends Scene {
    public Stage stage = new Stage();

    static final String FileName = "components.json";
    Home home = new Home(true);

    MenuBar mb = new MenuBar();

    static BorderPane root = new BorderPane();

    ComponentsContext componentsContext = ComponentsContext.getInstance();

    public ShowComponentScene(CanvaComponent mainCanva) {
        super(root, 760, 300);
        stage.setScene(this);

        Menu menu = new Menu();
        MenuItem is = new MenuItem("Save");

        menu.setOnAction(ev -> {
            Node currentNode = home.canva;

            componentsContext.addCustomComponent(currentNode, mainCanva);

            saveStateToFile(new File(FileName),
                    home.canva);

            System.out.println("salvou");
        });

        menu.getItems().add(is);
        mb.getMenus().add(menu);

        root.setTop(mb);
        root.setCenter(home);

    }

    private void saveStateToFile(File file, CanvaComponent canva) {
        // carregar os components e dar um append
        var components = List.of(Commons.CreateCanvaComponent(file, canva));
        new ComponentsWrapper(components);

        //
        ObservableList<Node> children = canva.getChildren();
        var object = Commons.CreateCanvaComponent(file, canva);

        Commons.WriteJsonInDisc(file, components);

    }

}
