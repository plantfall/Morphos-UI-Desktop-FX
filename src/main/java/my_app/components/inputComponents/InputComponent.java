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
import my_app.data.InputComponentData;
import my_app.data.ViewContract;

public class InputComponent extends TextField implements ViewContract<InputComponentData> {
    ObjectProperty<Node> currentState = new SimpleObjectProperty<>();

    public InputComponent(String content) {
        super(content);

        setStyle("-fx-text-fill:black;-fx-font-size:%s;-fx-font-weight:normal;"
                .formatted(
                        Commons.FontSizeDefault
                //
                ));

        setId(String.valueOf(System.currentTimeMillis()));
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

    @Override
    public InputComponentData getData() {

        String style = getStyle();

        String text = this.getText();
        String placeholder = this.getPromptText();

        String fontWeight = Commons.getValueOfSpecificField(style, "-fx-font-weight");

        String fontSize = Commons.getValueOfSpecificField(style, "-fx-font-size");
        String color = Commons.getValueOfSpecificField(style, "-fx-text-fill");

        double x = this.getLayoutX();
        double y = this.getLayoutY();

        return new InputComponentData(text, placeholder, fontWeight, fontSize, color, x, y, this.getId());
    }

    @Override
    public void applyData(InputComponentData data) {

        this.setId(data.identification());
        this.setText(data.text());

        this.setStyle("-fx-text-fill:%s;-fx-font-size:%s;-fx-font-weight:%s;"
                .formatted(data.color(), data.font_size(), data.font_weight()));

        this.setLayoutX(data.x());
        this.setLayoutY(data.y());
        this.setPromptText(data.placeholder());
    }
}
