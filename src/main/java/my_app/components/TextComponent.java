package my_app.components;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.scene.Node;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import my_app.data.Commons;
import my_app.data.TextComponentData;
import my_app.data.ViewContract;

public class TextComponent extends Text implements ViewContract<TextComponentData> {
    ObjectProperty<Node> currentState = new SimpleObjectProperty<>();

    public TextComponent(String content) {
        super(content);

        setStyle("-fx-fill:black;-fx-font-size:%s;-fx-font-weight:normal;"
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
                new FontSizeComponent(currentState)

        );
    }

    @Override
    public void settings(Pane father) {
        father.getChildren().setAll(
                new LayoutPositionComponent(currentState));
    }

    @Override
    public TextComponentData getData() {
        String style = getStyle();

        String text = this.getText();
        String fontWeight = Commons.getValueOfSpecificField(style, "-fx-font-weight");
        double x = this.getLayoutX();
        double y = this.getLayoutY();

        String fontSize = Commons.getValueOfSpecificField(style, "-fx-font-size");
        String textFill = Commons.getValueOfSpecificField(style, "-fx-fill");

        return new TextComponentData(text, x, y, fontSize, textFill, fontWeight);
    }

    @Override
    public void applyData(TextComponentData data) {
        var node = (Text) this.currentState.get();

        node.setText(data.text());

        node.setStyle("-fx-fill:%s;-fx-font-size:%s;-fx-font-weight:%s;"
                .formatted(data.color(), data.fontSize(), data.font_weight()));

        node.setLayoutX(data.layout_x());
        node.setLayoutY(data.layout_y());

    }
}
