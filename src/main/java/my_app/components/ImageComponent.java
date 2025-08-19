package my_app.components;

import java.io.InputStream;

import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.scene.image.Image;

public class ImageComponent extends ImageView {

    final int size = 100;

    public ImageComponent() {
    }

    public ImageComponent(InputStream content) {
        super(new Image(content));

        setFitWidth(size);
        setFitHeight(size);
    }

    public void renderRightSideContainer(Pane father) {
        VBox btnControls = new VBox(10);
        btnControls.setUserData("bgControls");

        Text title = new Text("Image Settings");

        father.getChildren().add(btnControls);
    }

}
