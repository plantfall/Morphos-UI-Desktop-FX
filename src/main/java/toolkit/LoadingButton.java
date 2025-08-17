package toolkit;

import javafx.animation.RotateTransition;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.util.Duration;

public class LoadingButton extends Button {

    private ImageView loadingIcon;
    private RotateTransition rotate;
    private String defaultText;
    private final BooleanProperty loading = new SimpleBooleanProperty(false);

    public LoadingButton(String text) {
        super(text);
        setup(text, "/assets/loading.png");
    }

    public LoadingButton(String text, String iconPath) {
        super(text);
        setup(text, iconPath);
    }

    void setup(String text, String iconPath) {

        this.defaultText = text;

        loadingIcon = new ImageView(new Image(getClass().getResourceAsStream(iconPath)));
        setIconSize(20);

        setupAnimation();

        // default style
        setStyle("-fx-background-color:#2ECC71; -fx-text-fill:white; -fx-font-size:14px;");

        // Listener of loading state
        loading.addListener((obs, wasLoading, isNowLoading) -> {
            if (isNowLoading) {
                setText("");
                setGraphic(loadingIcon);
                rotate.play();
            } else {
                rotate.stop();
                setGraphic(null);
                setText(defaultText);
            }
        });
    }

    private void setupAnimation() {
        rotate = new RotateTransition(Duration.millis(700));
        rotate.setNode(loadingIcon);
        rotate.setByAngle(360);
        rotate.setCycleCount(RotateTransition.INDEFINITE);
        rotate.setAutoReverse(false);
    }

    public BooleanProperty loadingProperty() {
        return loading;
    }

    public void setLoading(boolean isLoading) {
        this.loading.set(isLoading);
    }

    public void setIconSize(int size) {
        this.loadingIcon.setFitHeight(size);
        this.loadingIcon.setFitWidth(size);
    }

    public boolean isLoading() {
        return loading.get();
    }
}
