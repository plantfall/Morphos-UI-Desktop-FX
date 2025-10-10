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

        setOnMouseClicked(_ -> {
            setStyle("-fx-background-color:#3B38A0;");
            ComponentsContext.headerSelected.set(type);
            expanded.set(!expanded.get());
        });

        setOnMouseEntered(e -> {
            setStyle("-fx-background-color: lightblue;");
        });

        // ⚠️ CORREÇÃO AQUI
        setOnMouseExited(_ -> {
            // Verifica se o header atual NÃO é o selecionado antes de aplicar o fundo
            // transparente.
            if (!ComponentsContext.headerSelected.get().equalsIgnoreCase(type)) {
                setStyle("-fx-background-color: transparent;");
            } else {
                // Se for o selecionado, volta para a cor de seleção quando o mouse sair.
                setStyle("-fx-background-color:#3B38A0;");
            }
        });
        // Listener para garantir que o estilo de seleção seja aplicado quando o estado
        // mudar
        ComponentsContext.headerSelected.addListener((_, oldType, newType) -> {
            if (newType.equalsIgnoreCase(type)) {
                // Aplica a cor de seleção
                setStyle("-fx-background-color:#3B38A0;");
            } else if (oldType.equalsIgnoreCase(type)) {
                // Remove a cor de seleção do item anterior (se o mouse não estiver sobre ele)
                if (!isHover()) {
                    setStyle("-fx-background-color: transparent;");
                }
            }
        });

        setPadding(new Insets(5));
    }
}
