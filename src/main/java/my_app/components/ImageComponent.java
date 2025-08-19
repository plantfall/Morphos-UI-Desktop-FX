package my_app.components;

import java.io.InputStream;

import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;

public class ImageComponent extends ImageView {

    final int size = 100;

    // BooleanProperty appearenceIsSelected = new SimpleBooleanProperty(true);
    VBox appearenceContainer = new VBox();
    VBox settingsContainer = new VBox();

    public ImageComponent() {
    }

    public ImageComponent(InputStream content) {
        super(new Image(content));

        setFitWidth(size);
        setFitHeight(size);
        setPreserveRatio(true);
    }

    public void renderRightSideContainer(Pane father, BooleanProperty appearenceIsSelected) {

        // render inicial baseado no valor atual
        if (appearenceIsSelected.get()) {
            appearance(father);
        } else {
            settings(father);
        }

        appearenceIsSelected.addListener((o, old, v) -> {
            if (v)
                appearance(father);
            else
                settings(father);
        });

        VBox btnControls = new VBox(10);
        btnControls.setUserData("bgControls");

        father.getChildren().add(btnControls);
    }

    void appearance(Pane father) {
        father.getChildren().clear(); // limpa o container

        Text title = new Text("Image Appearance");

        // exemplo: controles de cor, borda, etc.
        // aqui poderia entrar um ColorPicker, por exemplo
        appearenceContainer.getChildren().setAll(title);

        father.getChildren().add(appearenceContainer);
    }

    void settings(Pane father) {
        father.getChildren().clear(); // limpa o container

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

        settingsContainer.getChildren().setAll(title, widthRow, heightRow);
        father.getChildren().add(settingsContainer);

    }

}
