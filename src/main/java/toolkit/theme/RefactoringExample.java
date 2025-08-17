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
import toolkit.material_ui.textfield.TextField_;
import toolkit.declarative_components.ClickableText;

/**
 * Exemplo demonstrando como a refatoração com classes abstratas
 * promove reutilização de código entre componentes de texto
 */
public class RefactoringExample extends Application {

        @Override
        public void start(Stage primaryStage) {
                MaterialTheme theme = MaterialTheme.getInstance();
                theme.loadAllFonts();

                VBox root = new VBox(20);
                root.setAlignment(Pos.CENTER);
                root.setPadding(new Insets(40));

                // Label_ usando métodos herdados da classe abstrata
                Label_ title = new Label_("Título Principal",
                                modifier -> modifier.font(theme.getCustomFont("Montserrat-Bold", 28))
                                                .h1() // Método herdado de TextModifier
                                                .styles().primary() // Método herdado de TextStyles
                );

                // Text_ usando métodos herdados da classe abstrata
                Text_ subtitle = new Text_("Subtítulo com fonte personalizada",
                                modifier -> modifier.font(theme.getCustomFont("Poppins-Medium", 18))
                                                .body() // Método herdado de TextModifier
                                                .styles().secondary() // Método herdado de TextStyles
                                                .fontWeightBold() // Método herdado de TextStyles
                );

                // ClickableText usando métodos herdados da classe abstrata
                ClickableText link = new ClickableText("Clique aqui para mais informações",
                                modifier -> modifier.font(theme.getCustomFont("Montserrat-SemiBold", 16))
                                                .caption() // Método herdado de TextModifier
                                                .styles().onBackground() // Método herdado de TextStyles
                );
                link.modifier().onClick(() -> System.out.println("Link clicado!"));

                // TextField_ usando métodos herdados da classe abstrata
                TextField_ input = new TextField_("Digite aqui...",
                                modifier -> modifier.font(theme.getCustomFont("Poppins-Regular", 14))
                                                .styles().color(Color.web("#1976D2")));
                input.modifier().applyTheme();

                // Demonstração de métodos específicos de cada componente
                Label_ customLabel = new Label_("Label com alinhamento personalizado",
                                modifier -> modifier.font(theme.getCustomFont("Montserrat-Regular", 16))
                                                .alignment(javafx.scene.text.TextAlignment.CENTER) // Específico do
                                                                                                   // Label_
                                                .styles().color(Color.web("#FF5722")));

                Text_ wrappedText = new Text_(
                                "Este é um texto longo que será quebrado em múltiplas linhas para demonstrar a funcionalidade de maxWidth",
                                modifier -> modifier.font(theme.getCustomFont("Poppins-Regular", 14))
                                                .maxWidth(300) // Específico do Text_
                                                .styles().onSurface());

                root.getChildren().addAll(
                                title,
                                subtitle,
                                link,
                                input,
                                customLabel,
                                wrappedText);

                Scene scene = new Scene(root, 700, 600);
                primaryStage.setTitle("Exemplo de Refatoração - Classes Abstratas");
                primaryStage.setScene(scene);
                primaryStage.show();
        }

        public static void main(String[] args) {
                launch(args);
        }
}
