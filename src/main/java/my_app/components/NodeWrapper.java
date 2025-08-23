package my_app.components;

import javafx.beans.property.BooleanProperty;
import javafx.scene.layout.Pane;
import my_app.data.ViewContract;

public class NodeWrapper {
    private ViewContract currentNode;

    public NodeWrapper(ViewContract node) {
        this.currentNode = node;
    }

    public void renderRightSideContainer(Pane father, BooleanProperty appearenceIsSelected) {

        if (appearenceIsSelected.get()) {
            this.currentNode.appearance(father);
        } else {
            this.currentNode.settings(father);
        }
    }

}
