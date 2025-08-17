package toolkit.theme;

import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import toolkit.declarative_components.Label_;
import toolkit.declarative_components.Text_;
import toolkit.material_ui.textfield.TextField_;
import toolkit.declarative_components.Button_;

/**
 * Exemplo de uso do sistema de tema global
 */
public class ThemeExample extends Application {

        @Override
        public void start(Stage primaryStage) {
                // Personalizando o tema (opcional)
                MaterialTheme theme = MaterialTheme.getInstance();
                theme.setPrimaryColor(javafx.scene.paint.Color.web("#FF5722"))
                                .setSecondaryColor(javafx.scene.paint.Color.web("#4CAF50"))
                                .setFontFamily("Segoe UI");

                // Criando a interface
                VBox root = new VBox(20);
                root.setAlignment(Pos.CENTER);
                root.setPadding(new javafx.geometry.Insets(40));

                // Aplicando tema ao container principal
                ThemeStyler.container(root);

                // Título principal usando H1
                Label_ title = new Label_("Sistema de Tema Global", modifier -> modifier.h1().styles().primary());

                // Subtítulo usando H2
                Label_ subtitle = new Label_("Demonstração dos estilos tipográficos",
                                modifier -> modifier.h2().styles().onBackground());

                // Texto do corpo
                Text_ bodyText = new Text_("Este é um exemplo de como usar o sistema de tema global " +
                                "sem depender de arquivos CSS. Todos os estilos são aplicados programaticamente.",
                                modifier -> modifier.body().styles().onSurface());

                // Campo de texto com tema
                TextField_ input = new TextField_("Digite algo aqui...",
                                modifier -> modifier.applyTheme().marginTop(20));

                // Botão com tema
                Button_ button = new Button_("Clique Aqui", modifier -> modifier.marginTop(20).styles()
                                .bgColor(theme.getPrimaryColor())
                                .textColor(theme.getOnPrimaryColor())
                                .borderRadius((int) theme.getBorderRadius()));

                // Adicionando componentes ao layout
                root.getChildren().addAll(title, subtitle, bodyText, input, button);

                // Configurando a cena
                Scene scene = new Scene(root, 600, 500);
                primaryStage.setTitle("Sistema de Tema Global - Exemplo");
                primaryStage.setScene(scene);
                primaryStage.show();
        }

        public static void main(String[] args) {
                launch(args);
        }
}