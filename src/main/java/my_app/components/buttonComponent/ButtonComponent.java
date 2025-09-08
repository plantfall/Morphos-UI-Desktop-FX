package my_app.components.buttonComponent;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.layout.Pane;
import my_app.components.FontColorPicker;
import my_app.components.FontSizeComponent;
import my_app.components.LayoutPositionComponent;
import my_app.components.TextContentComponent;
import my_app.data.Commons;
import my_app.data.ViewContract;

public class ButtonComponent extends Button implements ViewContract {

    ObjectProperty<Node> currentState = new SimpleObjectProperty<>();

    public ButtonComponent() {
        super();
        config();
    }

    public ButtonComponent(String content) {
        super(content);
        config();
    }

    void config() {

        setStyle(
                "-fx-background-color:%s;-fx-padding:%s;-fx-font-weight:%s;-fx-background-radius:%s;-fx-border-radius:%s;-fx-text-fill:%s;-fx-font-size: %s;-fx-border-width: %s;"
                        .formatted(
                                Commons.ButtonBgColorDefault,
                                Commons.ButtonPaddingDefault,
                                Commons.ButtonFontWeightDefault,
                                Commons.ButtonRadiusDefault,
                                Commons.ButtonRadiusDefault,
                                Commons.ButtonTextColorDefault,
                                Commons.ButtonFontSizeDefault,
                                Commons.ButtonRadiusWidth
                        //
                        ));

        currentState.set(this); // 👈 sempre aponta para o próprio botão
    }

    @Override
    public void appearance(Pane father) {
        father.getChildren().setAll(
                new ButtonBgColorPicker(currentState),
                new ButtonPaddingComponent(currentState),
                new ButtonBorderRadius(currentState),
                new ButtonBorderWidth(currentState),
                new ButtonBorderColorPicker(currentState),
                new my_app.components.FontWeightComponent(currentState),
                new FontColorPicker(currentState),
                new TextContentComponent(currentState),
                new FontSizeComponent(currentState));
    }

    @Override
    public void settings(Pane father) {

        father.getChildren().setAll(
                new LayoutPositionComponent(currentState));

    }
}
