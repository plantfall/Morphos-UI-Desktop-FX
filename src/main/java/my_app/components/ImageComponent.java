package my_app.components;

import java.io.InputStream;

import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import my_app.data.ViewContract;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;

public class ImageComponent extends ImageView implements ViewContract {

    final int size = 100;

    // BooleanProperty appearenceIsSelected = new SimpleBooleanProperty(true);

    public ImageComponent() {
    }

    public ImageComponent(String sourcePath) {
        super(new Image(sourcePath));

        setFitWidth(size);
        setFitHeight(size);
        setPreserveRatio(true);
    }

    @Override
    public void appearance(Pane father) {

        Text title = new Text("Image Appearance");

        father.getChildren().setAll(title);
    }

    @Override
    public void settings(Pane father) {

        Text title = new Text("Image Settings");

        var widthRow = new ItemRowComponent("Width", String.valueOf(getFitWidth()), newVal -> {
            if (!newVal.isBlank()) {
                try {
                    double v = Double.parseDouble(newVal);
                    setFitWidth(v);
                } catch (NumberFormatException ignored) {
                }
            }
        });

        var heightRow = new ItemRowComponent("Height", String.valueOf(getFitWidth()), newVal -> {
            if (!newVal.isBlank()) {
                try {
                    double v = Double.parseDouble(newVal);
                    setFitHeight(v);
                } catch (NumberFormatException ignored) {
                }
            }
        });

        father.getChildren().setAll(title, widthRow, heightRow);
    }

}
