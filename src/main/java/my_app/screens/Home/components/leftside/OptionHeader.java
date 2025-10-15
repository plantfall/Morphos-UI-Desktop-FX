package my_app.screens.Home.components.leftside;

import org.kordamp.ikonli.antdesignicons.AntDesignIconsOutlined;
import org.kordamp.ikonli.javafx.FontIcon;

import javafx.beans.property.BooleanProperty;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import my_app.App;
import my_app.contexts.ComponentsContext;
import my_app.screens.Home.Home;

public class OptionHeader extends HBox {
    Label label = new Label();
    Button btnAdd = new Button();

    public OptionHeader(
            String type,
            Home home, BooleanProperty expanded) {

        label.setText(type);
        label.setFont(Font.font(18));
        label.setStyle("-fx-text-fill: #F8FAFC;");
        label.setFont(App.FONT_BOLD);

        getChildren().add(label);
        getChildren().add(btnAdd);

        btnAdd.setOnAction(_ -> {
            setStyle("-fx-background-color:#3B38A0;");
            ComponentsContext.AddComponent(type, home);
            ComponentsContext.headerSelected.set(type);
            expanded.set(true);
        });

        var icon = FontIcon.of(
                AntDesignIconsOutlined.PLUS_CIRCLE,
                12,
                Color.BLACK);

        btnAdd.setGraphic(icon);

        ComponentsContext.nodeSelected.addListener((_, _, newSelected) -> {
            // Pega o tipo do item recém-selecionado (pode ser null)
            String newType = newSelected != null ? newSelected.type() : null;

            // Verifica se o tipo do novo item selecionado corresponde ao 'type' deste
            // OptionHeader
            if (newType != null && newType.equalsIgnoreCase(type)) {
                // Aplica a cor de seleção
                setStyle("-fx-background-color:#3B38A0;");
            } else if (newSelected == null) {
                // Se a seleção foi limpa (nodeSelected.set(null)), limpa o estilo
                if (!isHover()) {
                    setStyle("-fx-background-color: transparent;");
                }
            } else {
                // Se outro item de outro tipo foi selecionado, limpa o estilo deste
                if (!isHover()) {
                    setStyle("-fx-background-color: transparent;");
                }
            }
        });

        // Ajuste em setOnMouseExited para usar o novo estado
        setOnMouseExited(_ -> {
            String selectedType = ComponentsContext.nodeSelected.get() != null
                    ? ComponentsContext.nodeSelected.get().type()
                    : null;

            if (selectedType == null || !selectedType.equalsIgnoreCase(type)) {
                setStyle("-fx-background-color: transparent;");
            } else {
                // Se for o selecionado, volta para a cor de seleção quando o mouse sair.
                setStyle("-fx-background-color:#3B38A0;");
            }
        });

        // Lógica de clique do botão Add Component
        btnAdd.setOnAction(_ -> {
            setStyle("-fx-background-color:#3B38A0;");
            ComponentsContext.AddComponent(type, home);
            // REMOVEMOS: ComponentsContext.headerSelected.set(type); // Não é mais
            // necessário se o AddComponent chamar SelectNode
            expanded.set(true);
        });

        // Lógica de clique no cabeçalho
        setOnMouseClicked(_ -> {
            setStyle("-fx-background-color:#3B38A0;");
            // REMOVEMOS: ComponentsContext.headerSelected.set(type);
            expanded.set(!expanded.get());
        });

        setPadding(new Insets(5));
    }
}
