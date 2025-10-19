package my_app.scenes.ShowComponentScene;

import javafx.scene.Scene;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import my_app.components.CustomComponent;
import my_app.components.canvaComponent.CanvaComponent;
import my_app.contexts.ComponentsContext;
import my_app.screens.Home.Home;

public class ShowComponentScene extends Scene {
    public Stage stage = new Stage();

    ComponentsContext componentsContext = new ComponentsContext();

    Home home = new Home(componentsContext, true);

    MenuBar mb = new MenuBar();

    static BorderPane root = new BorderPane();

    public ShowComponentScene(CanvaComponent mainCanva) {
        super(root, 775, 300);
        stage.setScene(this);

        Menu menu = new Menu();
        MenuItem is = new MenuItem("Save");

        is.setOnAction(_ -> {
            // O 'home.canva' é o CanvaComponent com o conteúdo que o usuário desenhou (aqui
            // é o 'contentCanva').
            CanvaComponent contentCanva = home.canva;
            // style-> "-fx-background-color:#1a4d4d;"
            // 1. Cria o CustomComponent
            CustomComponent newCustomComponent = new CustomComponent(componentsContext);
            newCustomComponent.setStyle(contentCanva.getStyle());
            newCustomComponent.setPrefHeight(contentCanva.getPrefHeight());
            newCustomComponent.setPrefWidth(contentCanva.getPrefWidth());

            // 2. Transfere os filhos do canva temporário (home.canva) para o customComp.
            // **IMPORTANTE:** Isso move os Nodes, tirando-os do 'contentCanva'.
            // Se você precisar que os Nodes permaneçam no 'contentCanva', você precisa
            // CLONAR.
            newCustomComponent.getChildren().addAll(contentCanva.getChildren());

            // 3. Adiciona o nó à lista global e à sidebar.
            // O mainCanvaComponent aqui é usado apenas para a lógica interna (embora o
            // addCustomComponent não o use visualmente).
            componentsContext.addCustomComponent(newCustomComponent, mainCanva);

            System.out.println(
                    "Componente personalizado criado e adicionado ao sistema com ID: " + newCustomComponent.getId());

            // 4. Fecha a janela
            stage.close();
        });

        menu.getItems().add(is);
        mb.getMenus().add(menu);

        root.setTop(mb);
        root.setCenter(home);

    }

}
