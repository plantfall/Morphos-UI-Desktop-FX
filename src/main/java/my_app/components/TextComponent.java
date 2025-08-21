package my_app.components;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.ObjectProperty;
import javafx.scene.Node;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

public class TextComponent extends Text implements ViewContract {

    VBox appearenceContainer = new VBox();
    VBox settingsContainer = new VBox();

    private ObjectProperty<Node> selectedNode;

    public TextComponent(String content) {
        super(content);
    }

    public void activeNode(ObjectProperty<Node> selectedNode) {
        this.selectedNode = selectedNode;
    }

    public void renderRightSideContainer(Pane father, BooleanProperty appearenceIsSelected) {

        // render inicial baseado no valor atual
        if (appearenceIsSelected.get()) {
            appearance(father);
        } else {
            settings(father);
        }

        appearenceIsSelected.addListener((o, old, v) -> {
            if (v)
                appearance(father);
            else
                settings(father);
        });

    }

    @Override
    public void appearance(Pane father) {
        father.getChildren().clear(); // limpa o container

        appearenceContainer.getChildren().setAll(
                new FontWeightComponent(selectedNode),
                new FontColorPicker(selectedNode),
                new TextContentComponent(selectedNode),
                new FontSizeComponent(selectedNode)

        );

        father.getChildren().add(appearenceContainer);
    }

    @Override
    public void settings(Pane father) {
        father.getChildren().clear(); // limpa o container

        Text title = new Text("Text Settings");

        settingsContainer.getChildren().setAll(title);

        father.getChildren().add(settingsContainer);
    }
}
