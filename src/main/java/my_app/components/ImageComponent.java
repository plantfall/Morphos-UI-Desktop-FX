package my_app.components;

import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import my_app.data.ViewContract;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.scene.Node;
import javafx.scene.image.Image;

public class ImageComponent extends ImageView implements ViewContract {

    final int size = 100;
    ObjectProperty<Node> currentState = new SimpleObjectProperty<>();

    // BooleanProperty appearenceIsSelected = new SimpleBooleanProperty(true);

    public ImageComponent() {
        config();
    }

    public ImageComponent(String sourcePath) {
        super(new Image(sourcePath));
        config();
    }

    void config() {
        setFitWidth(size);
        setFitHeight(size);
        setPreserveRatio(true);

        currentState.set(this);
    }

    @Override
    public void appearance(Pane father) {

        Text title = new Text("Image Appearance");

        father.getChildren().setAll(title);
    }

    @Override
    public void settings(Pane father) {

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

        father.getChildren().setAll(
                new LayoutPositionComponent(currentState), widthRow, heightRow);
    }

}
