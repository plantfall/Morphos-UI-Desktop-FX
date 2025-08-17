package my_app.screens.Home;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderStroke;
import javafx.scene.layout.BorderStrokeStyle;
import javafx.scene.layout.BorderWidths;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;

public class Center extends StackPane {
    public Center(SimpleStringProperty selectedOption, ObjectProperty<Node> selectedNode) {

        // espaçamento horizontal (20px à esquerda e à direita) e 40px no topo
        setPadding(new Insets(0, 20, 0, 20));

        Canva canva = new Canva(selectedOption, selectedNode);

        // margem no topo só do Canva
        StackPane.setMargin(canva, new Insets(-50, 0, 0, 0));

        getChildren().add(canva);
    }
}

class Canva extends Pane {

    private final ObjectProperty<Node> selectedNode;

    public Canva(SimpleStringProperty optionSelected, ObjectProperty<Node> selectedNode) {
        this.selectedNode = selectedNode;

        setBorder(new Border(
                new BorderStroke(
                        Color.BLACK,
                        BorderStrokeStyle.SOLID,
                        null,
                        new BorderWidths(1))));

        setHeight(600);
        setMaxHeight(600);

        optionSelected.addListener((obs, old, v) -> {
            if (v == null || v.isBlank())
                return;

            Node node = null;
            var content = "Im new here";

            if (v.equalsIgnoreCase("button")) {
                node = new Button(content);
            } else if (v.equalsIgnoreCase("input")) {
                node = new TextField(content);
            } else if (v.equalsIgnoreCase("text")) {
                node = new Text(content);
            }

            if (node != null) {
                addElementDragable(node);
                selectNode(node);
            }

            optionSelected.set("");
        });

        setOnMouseClicked(e -> {
            if (e.getTarget() == this) { // só dispara se clicou no fundo do Canva
                selectedNode.set(this);
                System.out.println("Canva selecionado");
            }
        });

    }

    private void addElementDragable(Node node) {
        // posição inicial centralizada
        double relX = 0.5;
        double relY = 0.5;

        node.setLayoutX(getWidth() * relX);
        node.setLayoutY(getHeight() * relY);

        // clique = seleciona
        node.setOnMouseClicked(e -> selectNode(node));

        enableDrag(node, relX, relY);
        getChildren().add(node);
    }

    private void enableDrag(Node node, double relX, double relY) {
        final double[] offsetX = new double[1];
        final double[] offsetY = new double[1];

        node.setOnMousePressed(e -> {
            offsetX[0] = e.getSceneX() - node.getLayoutX();
            offsetY[0] = e.getSceneY() - node.getLayoutY();
        });

        node.setOnMouseDragged(e -> {
            double x = e.getSceneX() - offsetX[0];
            double y = e.getSceneY() - offsetY[0];

            node.setLayoutX(x);
            node.setLayoutY(y);

            // salva posição relativa
            node.getProperties().put("relX", x / getWidth());
            node.getProperties().put("relY", y / getHeight());
        });

        // quando o canva for redimensionado, reposiciona proporcionalmente
        widthProperty().addListener((obs, oldW, newW) -> updateRelativePosition(node));
        heightProperty().addListener((obs, oldH, newH) -> updateRelativePosition(node));
    }

    private void updateRelativePosition(Node node) {
        Object relX = node.getProperties().get("relX");
        Object relY = node.getProperties().get("relY");

        if (relX instanceof Double && relY instanceof Double) {
            node.setLayoutX((Double) relX * getWidth());
            node.setLayoutY((Double) relY * getHeight());
        }
    }

    private void selectNode(Node node) {
        selectedNode.set(node);
        System.out.println("Selecionado: " + node);
    }

}
