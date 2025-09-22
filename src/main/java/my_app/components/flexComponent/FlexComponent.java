package my_app.components.flexComponent;

import java.util.ArrayList;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.geometry.Orientation;
import javafx.scene.Node;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.Pane;
import my_app.components.LayoutPositionComponent;
import my_app.components.TextComponent;
import my_app.contexts.ComponentsContext;
import my_app.data.FlexComponentData;
import my_app.data.ViewContract;

public class FlexComponent extends FlowPane implements ViewContract<FlexComponentData> {
    ObjectProperty<Node> currentState = new SimpleObjectProperty<>();
    SimpleStringProperty currentChild = new SimpleStringProperty("Text");

    public FlexComponent() {
        var defaultComponents = new ArrayList<TextComponent>();

        for (int i = 0; i < 20; i++) {
            defaultComponents.add(new TextComponent("Im a " + i));
        }

        // TextComponent[] defaultComponents = { new TextComponent("Im a Text A"), new
        // TextComponent("Im a Text B"),
        // new TextComponent("Im a Text C") };

        getChildren().addAll(defaultComponents);

        // setPrefWrapLength(0); // evita quebra forçada de linha
        // setPrefWidth(Region.USE_COMPUTED_SIZE);
        // setPrefHeight(Region.USE_COMPUTED_SIZE);
        // setMinSize(Region.USE_PREF_SIZE, Region.USE_PREF_SIZE);
        // setMaxSize(Region.USE_COMPUTED_SIZE, Region.USE_COMPUTED_SIZE);

        setOrientation(Orientation.HORIZONTAL);
        setStyle("-fx-background-color:red;");
        setHgap(5);
        setVgap(5);

        setId(String.valueOf(System.currentTimeMillis()));
        currentState.set(this);

        ComponentsContext.idOfComponentSelected.addListener((_a, _b, newId) -> {
            System.out.println("newId: " + newId);

            if (newId.equals(this.getId())) {
                // String novoEstilo = Commons.UpdateEspecificStyle(this.getStyle(),
                // "-fx-background-color", "red");
                // this.setStyle(novoEstilo);
            }

        });
    }

    @Override
    public void appearance(Pane father) {
        father.getChildren().setAll(
                new OrientationComponent(this),
                new PrefLenghtComponent(this),
                new ChildComponent(this, currentChild));
    }

    @Override
    public void settings(Pane father) {
        father.getChildren().setAll(
                new LayoutPositionComponent(currentState));
    }

    @Override
    public FlexComponentData getData() {

        String childType = "Text";
        String orientation_ = getOrientation().equals(Orientation.VERTICAL) ? "column" : "row";

        if (currentChild.get().equals("Text")) {
            childType = "text_component";
        }

        return new FlexComponentData(
                childType,
                this.getId(),
                orientation_);
        // String style = getStyle();

        // String text = this.getText();
        // String fontWeight = Commons.getValueOfSpecificField(style,
        // "-fx-font-weight");
        // double x = this.getLayoutX();
        // double y = this.getLayoutY();

        // String fontSize = Commons.getValueOfSpecificField(style, "-fx-font-size");
        // String textFill = Commons.getValueOfSpecificField(style, "-fx-fill");

        // return new TextComponentData(text, x, y, fontSize, textFill, fontWeight,
        // this.getId());

    }

    @Override
    public void applyData(FlexComponentData data) {

        // this.setText(data.text());
        // this.setId(data.identification());

        // this.setStyle("-fx-fill:%s;-fx-font-size:%s;-fx-font-weight:%s;"
        // .formatted(data.color(), data.fontSize(), data.font_weight()));

        // this.setLayoutX(data.layout_x());
        // this.setLayoutY(data.layout_y());

    }

}
