package toolkit.theme;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import toolkit.declarative_components.Label_;
import toolkit.declarative_components.Text_;
import toolkit.material_ui.textfield.TextField_;
import toolkit.declarative_components.Button_;

/**
 * Exemplo demonstrando o uso elegante do método font()
 * nos componentes declarativos
 */
public class FontMethodExample extends Application {

        @Override
        public void start(Stage primaryStage) {
                // Inicializa o tema e carrega as fontes
                MaterialTheme theme = MaterialTheme.getInstance();
                theme.loadAllFonts();

                VBox root = new VBox(20);
                root.setAlignment(Pos.CENTER);
                root.setPadding(new Insets(40));

                // Exemplo 1: Label com fonte customizada usando font()
                Label_ elegantTitle = new Label_("Título Elegante",
                                modifier -> modifier.font(theme.getCustomFont("Montserrat-Bold", 28))
                                                .styles().primary());

                // Exemplo 2: Text com fonte customizada usando font()
                Text_ elegantSubtitle = new Text_("Subtítulo com fonte personalizada",
                                modifier -> modifier.font(theme.getCustomFont("Poppins-Medium", 18))
                                                .styles().onBackground());

                // Exemplo 3: Button com fonte customizada usando font()
                Button_ elegantButton = new Button_("Botão Elegante",
                                modifier -> modifier.font(theme.getCustomFont("Montserrat-SemiBold", 16))
                                                .styles().bgColor(theme.getPrimaryColor())
                                                .textColor(theme.getOnPrimaryColor())
                                                .borderRadius(8));

                // Exemplo 4: TextField com fonte customizada usando font()
                TextField_ elegantInput = new TextField_("Digite aqui...",
                                modifier -> modifier.font(theme.getCustomFont("Poppins-Regular", 14))
                                                .marginTop(20));

                // Exemplo 5: Combinação de métodos
                Label_ combinedExample = new Label_("Exemplo Combinado",
                                modifier -> modifier.font(theme.getCustomFont("Montserrat-Black", 22))
                                                .styles().secondary());

                root.getChildren().addAll(
                                elegantTitle,
                                elegantSubtitle,
                                elegantButton,
                                elegantInput,
                                combinedExample);

                Scene scene = new Scene(root, 600, 500);
                primaryStage.setTitle("Método font() Elegante");
                primaryStage.setScene(scene);
                primaryStage.show();
        }

        public static void main(String[] args) {
                launch(args);
        }
}
