package my_app.components.imageComponent;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import my_app.components.LayoutPositionComponent;
import my_app.components.shared.ButtonRemoverComponent;
import my_app.components.shared.HeightComponent;
import my_app.components.shared.WidthComponent;
import my_app.contexts.ComponentsContext;
import my_app.data.Commons;
import my_app.data.ImageComponentData;
import my_app.data.ViewContract;

public class ImageComponent extends ImageView implements ViewContract<ImageComponentData> {

    final int size = 100;
    ObjectProperty<Node> currentState = new SimpleObjectProperty<>();

    public ObjectProperty<FitMode> fitMode = new SimpleObjectProperty<>(FitMode.CONTAIN);

    public Stage stage;

    ComponentsContext componentsContext;

    public ImageComponent(ComponentsContext componentsContext) {
        config();
        this.componentsContext = componentsContext;
    }

    public ImageComponent(String sourcePath, ComponentsContext componentsContext) {
        super(new Image(sourcePath, true));
        // 'true' ativa carregamento assíncrono
        this.componentsContext = componentsContext;
        config();
    }

    void config() {

        setFitWidth(size);
        setFitHeight(size);
        setPreserveRatio(true);

        setId(String.valueOf(System.currentTimeMillis()));
        currentState.set(this);
    }

    @Override
    public void appearance(Pane father) {
        father.getChildren().setAll(
                new WidthComponent(this),
                new HeightComponent(this),
                new PreserveRatioComponent(this),
                new ImageBackgroundComponent(this),
                new ButtonRemoverComponent(this, componentsContext)
        // new FitComponent(this)
        );
    }

    @Override
    public void settings(Pane father) {

        father.getChildren().setAll(
                new LayoutPositionComponent(currentState));
    }

    @Override
    public ImageComponentData getData() {
        Image img = this.getImage();

        String url = (img != null && img.getUrl() != null) ? img.getUrl() : "";
        double width = this.getFitWidth();
        double height = this.getFitHeight();

        double x = this.getLayoutX();
        double y = this.getLayoutY();

        boolean preserveRatio = this.isPreserveRatio();
        var location = Commons.NodeInCanva(this);

        return new ImageComponentData(url, width, height, x, y, preserveRatio, this.getId(),
                location.inCanva(),
                location.fatherId());
    }

    @Override
    public void applyData(ImageComponentData data) {

        this.setId(data.identification());

        this.setImage(new Image(data.url()));

        this.setPreserveRatio(data.preserve_ratio());

        this.setLayoutX(data.x());
        this.setLayoutY(data.y());

        this.setFitHeight(data.height());
        this.setFitWidth(data.width());

    }

}
