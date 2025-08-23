package my_app.components;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.scene.Node;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import my_app.data.ViewContract;

public class TextComponent extends Text implements ViewContract {
    ObjectProperty<Node> currentState = new SimpleObjectProperty<>();

    public TextComponent(String content) {
        super(content);
        currentState.set(this);
    }

    @Override
    public void appearance(Pane father) {
        father.getChildren().setAll(
                new FontWeightComponent(currentState),
                new FontColorPicker(currentState),
                new TextContentComponent(currentState),
                new FontSizeComponent(currentState)

        );
    }

    @Override
    public void settings(Pane father) {

        Text title = new Text("Text Settings");

        father.getChildren().setAll(title);
    }
}
