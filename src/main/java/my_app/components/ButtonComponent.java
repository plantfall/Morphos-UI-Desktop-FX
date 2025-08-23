package my_app.components;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import my_app.data.ViewContract;

public class ButtonComponent extends Button implements ViewContract {

    ObjectProperty<Node> currentState = new SimpleObjectProperty<>();

    public ButtonComponent() {
        super();
        currentState.set(this); // 👈 sempre aponta para o próprio botão
    }

    public ButtonComponent(String content) {
        super(content);
        currentState.set(this);
    }

    @Override
    public void appearance(Pane father) {
        father.getChildren().setAll(
                new ButtonBgColorPicker(currentState),
                new ButtonPaddingComponent(currentState),
                new ButtonBorderRadius(currentState),
                new ButtonBorderWidth(currentState),
                new ButtonBorderColorPicker(currentState),
                new FontWeightComponent(currentState),
                new FontColorPicker(currentState),
                new TextContentComponent(currentState),
                new FontSizeComponent(currentState));
    }

    @Override
    public void settings(Pane father) {

        Text title = new Text("Button Settings");

        father.getChildren().setAll(title);

    }
}
