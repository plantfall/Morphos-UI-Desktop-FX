package my_app.screens.Home;

import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.BorderPane;
import my_app.components.canvaComponent.CanvaComponent;
import my_app.contexts.ComponentsContext;
import my_app.screens.Home.leftside.LeftSide;

public class Home extends BorderPane {
    SimpleObjectProperty<Node> visualNodeSelected = new SimpleObjectProperty<>();
    ComponentsContext componentsContext = ComponentsContext.getInstance();

    public CanvaComponent canva = new CanvaComponent();

    @FunctionalInterface
    public interface VisualNodeCallback {
        public void set(Node n);
    }

    public Home(boolean openComponentScene) {
        setLeft(new LeftSide());

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
        setRight(new RightSide(visualNodeSelected));

        // setStyle("-fx-background-color:red");

    }
}
