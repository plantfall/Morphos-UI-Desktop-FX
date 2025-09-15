package my_app.screens.Home.leftside;

import java.util.function.Consumer;

import org.kordamp.ikonli.antdesignicons.AntDesignIconsOutlined;
import org.kordamp.ikonli.javafx.FontIcon;

import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import my_app.App;

public class OptionHeader extends HBox {
    Label label = new Label();
    Button btnAdd = new Button();

    public OptionHeader(
            String type,
            Consumer<String> callbackClickOnBtnAdd,
            Runnable onClick) {

        label.setText(type);
        label.setFont(Font.font(18));
        label.setStyle("-fx-text-fill: #F8FAFC;");
        label.setFont(App.FONT_BOLD);

        getChildren().add(label);
        getChildren().add(btnAdd);

        btnAdd.setOnAction(ev -> {
            callbackClickOnBtnAdd.accept(type);
        });

        var icon = FontIcon.of(
                AntDesignIconsOutlined.PLUS_CIRCLE,
                12,
                Color.BLACK);

        btnAdd.setGraphic(icon);

        setOnMouseClicked(ev -> {
            setStyle("-fx-background-color:#3B38A0;");
            onClick.run();
        });

        setOnMouseEntered(e -> {
            setStyle("-fx-background-color: lightblue;");
        });

        setOnMouseExited(e -> {
            setStyle("-fx-background-color: transparent;");
        });

        setPadding(new Insets(5));
    }
}
