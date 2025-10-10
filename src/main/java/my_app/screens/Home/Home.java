package my_app.screens.Home;

import javafx.scene.Node;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.BorderPane;
import my_app.components.canvaComponent.CanvaComponent;
import my_app.screens.Home.components.RightSide;
import my_app.screens.Home.components.leftside.LeftSide;

public class Home extends BorderPane {

    public CanvaComponent canva = new CanvaComponent();

    @FunctionalInterface
    public interface VisualNodeCallback {
        public void set(Node n);
    }

    public Home(boolean openComponentScene) {
        setLeft(new LeftSide(this));

        ScrollPane editor = new ScrollPane();

        if (openComponentScene) {
            canva.setPrefSize(370, 250);
        }
        // scrollPane mostra o canva com barras se for maior que a janela
        editor.setContent(canva);
        editor.setFitToWidth(false);
        editor.setFitToHeight(false);

        // setCenter(this.canva);
        setCenter(editor);
        setRight(new RightSide());

        // setStyle("-fx-background-color:red");

    }
}
