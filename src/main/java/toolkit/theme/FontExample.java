package toolkit.theme;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import toolkit.declarative_components.Label_;
import toolkit.declarative_components.Text_;

/**
 * Exemplo de como usar fontes personalizadas carregadas dos recursos
 */
public class FontExample extends Application {

        @Override
        public void start(Stage primaryStage) {
                // Carrega todas as fontes disponíveis
                MaterialTheme theme = MaterialTheme.getInstance();
                theme.loadAllFonts();

                // Lista as fontes carregadas no console
                FontLoader.listLoadedFonts();

                VBox root = new VBox(20);
                root.setAlignment(Pos.CENTER);
                root.setPadding(new Insets(40));

                // Exemplo usando fonte Montserrat
                Label_ montserratTitle = new Label_("Título em Montserrat",
                                modifier -> modifier.font(theme.getCustomFont("Montserrat-Bold", 24))
                                                .styles().color(Color.web("#1976D2")));

                // Exemplo usando fonte Poppins
                Label_ poppinsTitle = new Label_("Título em Poppins",
                                modifier -> modifier.font(theme.getCustomFont("Poppins-SemiBold", 20))
                                                .styles().color(Color.web("#FF5722")));

                // Exemplo de texto em Montserrat
                Text_ montserratText = new Text_("Este texto está usando a fonte Montserrat Regular",
                                modifier -> modifier.font(theme.getCustomFont("Montserrat-Regular", 16))
                                                .styles().color(Color.web("#333333")));

                // Exemplo de texto em Poppins
                Text_ poppinsText = new Text_("Este texto está usando a fonte Poppins Light",
                                modifier -> modifier.font(theme.getCustomFont("Poppins-Light", 14))
                                                .styles().color(Color.web("#666666")));

                // Exemplo usando diferentes pesos da mesma fonte
                Label_ boldText = new Label_("Texto em Negrito",
                                modifier -> modifier.font(theme.getCustomFont("Montserrat-Black", 18))
                                                .styles().color(Color.web("#000000")));

                Label_ mediumText = new Label_("Texto em Médio",
                                modifier -> modifier.font(theme.getCustomFont("Montserrat-Medium", 16))
                                                .styles().color(Color.web("#333333")));

                Label_ lightText = new Label_("Texto em Leve",
                                modifier -> modifier.font(theme.getCustomFont("Montserrat-Light", 14))
                                                .styles().color(Color.web("#666666")));

                root.getChildren().addAll(
                                montserratTitle,
                                poppinsTitle,
                                montserratText,
                                poppinsText,
                                boldText,
                                mediumText,
                                lightText);

                Scene scene = new Scene(root, 600, 500);
                primaryStage.setTitle("Exemplo de Fontes Personalizadas");
                primaryStage.setScene(scene);
                primaryStage.show();
        }

        public static void main(String[] args) {
                launch(args);
        }
}
