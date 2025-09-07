package my_app.components;

import javafx.beans.binding.Bindings;
import javafx.beans.property.ObjectProperty;
import javafx.scene.Node;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.util.converter.NumberStringConverter;

public class LayoutPositionComponent extends HBox {

    public LayoutPositionComponent(ObjectProperty<Node> selectedNode) {

        Node node = selectedNode.get();

        setSpacing(10);

        getChildren().addAll(item("X", node), item("Y", node));

    }

    HBox item(String title, Node node) {
        Text titleComp = new Text(title + ":");
        TextField tf = new TextField();

        config(titleComp);
        loadNodePositionOnCanva(node, tf, title);

        return new HBox(titleComp, tf);
    }

    private void loadNodePositionOnCanva(Node node, TextField tf, String title) {
        if ("X".equals(title)) {
            // vincula TextField <-> layoutX
            Bindings.bindBidirectional(
                    tf.textProperty(),
                    node.layoutXProperty(),
                    new NumberStringConverter());
        } else {
            // vincula TextField <-> layoutY
            Bindings.bindBidirectional(
                    tf.textProperty(),
                    node.layoutYProperty(),
                    new NumberStringConverter());
        }
    }

    void config(Text title) {
        title.setFont(Font.font(14));
        title.setFill(Color.WHITE);
        setSpacing(10);
    }
}
