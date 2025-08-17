package toolkit.declarative_components;

import javafx.scene.image.Image;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.BackgroundSize;

public class Image_ extends Image {

    public Image_(String resourcesPath) {
        super(Image_.class.getResourceAsStream(resourcesPath));
    }

    public Background asBackground() {
        return new Background(new BackgroundImage(
                this,
                BackgroundRepeat.NO_REPEAT,
                BackgroundRepeat.NO_REPEAT,
                BackgroundPosition.CENTER,
                new BackgroundSize(100, 100, true, true, true, true)));
    }

    public Background asBackground(int sizeInPorcentage) {
        return new Background(new BackgroundImage(
                this,
                BackgroundRepeat.NO_REPEAT,
                BackgroundRepeat.NO_REPEAT,
                BackgroundPosition.CENTER,
                new BackgroundSize(sizeInPorcentage, sizeInPorcentage, true, true, true, true)));
    }

    public Background asBackground(int width, int height) {
        return new Background(new BackgroundImage(
                this,
                BackgroundRepeat.NO_REPEAT,
                BackgroundRepeat.NO_REPEAT,
                BackgroundPosition.CENTER,
                new BackgroundSize(width, height, true, true, true, true)));
    }

    public BackgroundImage asBackgroundImage() {
        return new BackgroundImage(
                this,
                BackgroundRepeat.NO_REPEAT,
                BackgroundRepeat.NO_REPEAT,
                BackgroundPosition.CENTER,
                new BackgroundSize(100, 100, true, true, true, true));
    }

    public BackgroundImage asBackgroundImage(int sizeInPorcentage) {
        return new BackgroundImage(
                this,
                BackgroundRepeat.NO_REPEAT,
                BackgroundRepeat.NO_REPEAT,
                BackgroundPosition.CENTER,
                new BackgroundSize(sizeInPorcentage, sizeInPorcentage, true, true, true, true));
    }

    public BackgroundImage asBackgroundImage(int width, int height) {
        return new BackgroundImage(
                this,
                BackgroundRepeat.NO_REPEAT,
                BackgroundRepeat.NO_REPEAT,
                BackgroundPosition.CENTER,
                new BackgroundSize(width, height, true, true, true, true));
    }
}
