package my_app.scenes.MainScene;

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
import toolkit.Component;

public class MainScene extends Scene {

    ComponentsContext componentsContext = new ComponentsContext();
    Home home = new Home(componentsContext, false);
    Stage stage = new Stage();
    VBox mainView;

    MainSceneController controller = new MainSceneController(componentsContext);

    public MainScene() {
        super(new VBox(), 1200, 650);

        setup();

        controller.loadSceneFromJsonFile(home, stage);

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
        MenuItem itemSaveAs = new MenuItem("Save as");
        MenuItem itemLoad = new MenuItem("Load");
        MenuItem itemShowCode = new MenuItem("Show code");
        MenuItem itemContribute = new MenuItem("Become a contributor");
        menu.getItems().addAll(itemSalvar, itemSaveAs, itemLoad, itemShowCode, itemContribute);

        itemSalvar.setOnAction(_ -> controller.handleSave(home, stage));
        itemSaveAs.setOnAction(_ -> controller.handleSaveAs(home, stage));

        itemLoad.setOnAction(_ -> controller.handleClickLoad(home, stage));

        itemShowCode.setOnAction(_ -> handleShowJavaCode(home.canva));

        itemContribute.setOnAction(_ -> controller.handleBecomeContributor());

        return menu;
    }

    private void handleShowJavaCode(CanvaComponent canvaComponent) {
        new ShowCode(canvaComponent)
                .abrir();

    }

}
