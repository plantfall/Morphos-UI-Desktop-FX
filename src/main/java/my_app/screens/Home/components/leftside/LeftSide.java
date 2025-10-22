package my_app.screens.Home.components.leftside;

import static my_app.themes.Typography.BodySecondary;

import java.util.ArrayList;
import java.util.List;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import my_app.contexts.ComponentsContext;
import my_app.data.Commons;
import my_app.screens.Home.Home;
import toolkit.theme.MaterialTheme;

public class LeftSide extends VBox {

        Text appName = new Text(Commons.AppName);
        ImageView iv = Commons.CreateImageView("/assets/images/m.png");

        HBox logo = new HBox(iv, appName);
        Label title = BodySecondary("Visual Elements");
        // new Text("Visual Elements");
        // List<String> optionsText = List.of("Text", "Button", "Input", "Image",
        // "Component", "Column items");
        List<String> optionsText = List.of("Text", "Button", "Input", "Image", "Component");

        List<Option> options = new ArrayList<>();

        MaterialTheme theme = MaterialTheme.getInstance();

        public LeftSide(Home home, ComponentsContext componentsContext) {

                config();
                styles();

                getChildren().addAll(logo, title);

                var spacer = new Region();
                spacer.setMaxHeight(10);
                spacer.setPrefHeight(10);

                getChildren().add(spacer);

                optionsText.forEach(title -> options.add(new Option(title, home, componentsContext)));

                getChildren().addAll(options);

        }

        void config() {
                iv.setFitHeight(50);
                iv.setFitWidth(50);

                logo.setAlignment(Pos.CENTER_LEFT);

                // Faz com que o LeftSide ocupe a altura toda
                setMaxHeight(Double.MAX_VALUE);

                setBackground(new Background(
                                new BackgroundFill(theme.getBackgroundColor(),
                                                CornerRadii.EMPTY, Insets.EMPTY)));

                // Espaçamento horizontal entre conteúdo e borda
                setPadding(new Insets(10, 10, 0, 10)); // top, right, bottom, left

        }

        void styles() {
                appName.setStyle("-fx-fill:#fff;-fx-font-size:17px;");
        }

}
