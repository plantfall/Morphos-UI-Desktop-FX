package my_app.components.inputComponents;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.scene.Node;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import my_app.components.FontColorPicker;
import my_app.components.FontSizeComponent;
import my_app.components.FontWeightComponent;
import my_app.components.LayoutPositionComponent;
import my_app.components.TextContentComponent;
import my_app.data.Commons;
import my_app.data.ViewContract;

public class InputComponent extends TextField implements ViewContract {
    ObjectProperty<Node> currentState = new SimpleObjectProperty<>();

    public InputComponent(String content) {
        super(content);

        setStyle("-fx-text-fill:black;-fx-font-size:%s;-fx-font-weight:normal;"
                .formatted(
                        Commons.FontSizeDefault
                //
                ));

        currentState.set(this);
    }

    @Override
    public void appearance(Pane father) {
        father.getChildren().setAll(
                new FontWeightComponent(currentState),
                new FontColorPicker(currentState),
                new TextContentComponent(currentState),
                new FontSizeComponent(currentState),
                new PromptTextComponent(currentState));
    }

    @Override
    public void settings(Pane father) {
        father.getChildren().setAll(
                new LayoutPositionComponent(currentState));
    }
}
