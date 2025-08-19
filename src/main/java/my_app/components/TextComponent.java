package my_app.components;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.ObjectProperty;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
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

        // atualizando a ui
        // selectedNode.addListener((obs, old, node) -> {
        // if (node instanceof ButtonComponent b) {
        // tf.setText(b.getText());
        // combo.setValue(getFontWeightName(FontWeight.findByName(b.getFont().getStyle())));
        // } else if (node instanceof TextField t) {
        // tf.setText(t.getText());
        // combo.setValue(getFontWeightName(FontWeight.findByName(t.getFont().getStyle())));
        // colorPicker.setValue(extractColorFromStyle(t.getStyle()));
        // } else if (node instanceof Text txt) {
        // tf.setText(txt.getText());
        // combo.setValue(getFontWeightName(FontWeight.findByName(txt.getFont().getStyle())));
        // colorPicker.setValue((Color) txt.getFill());
        // } else {
        // tf.setText("");
        // }
        // });

    }

    @Override
    public void appearance(Pane father) {
        father.getChildren().clear(); // limpa o container

        var nodes = AppearanceFactory.renderComponentes(null,
                selectedNode, "node-value-field", "font-color-size", "font-color-field", "font-weight-field");

        appearenceContainer.getChildren().setAll(nodes);

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
