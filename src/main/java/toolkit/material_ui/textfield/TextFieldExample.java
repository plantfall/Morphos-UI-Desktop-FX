package toolkit.material_ui.textfield;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

/**
 * Exemplo de uso do TextField_ seguindo o padrão Material-UI React
 * Demonstra as funcionalidades de label flutuante, variantes e multiline
 * 
 * NOVO COMPORTAMENTO:
 * - Sem foco e sem conteúdo: Mostra apenas a label dentro do input
 * - Com foco e sem conteúdo: Label sobe (flutuante) e placeholder aparece
 */
public class TextFieldExample extends Application {

        @Override
        public void start(Stage primaryStage) {
                primaryStage.setTitle("TextField_ - Exemplo Material-UI");

                VBox root = new VBox(30);
                root.setPadding(new Insets(30));
                root.setStyle("-fx-background-color: #f5f5f5;");

                // Título explicativo
                Label titleLabel = new Label("TextField_ com Label Flutuante e Placeholder");
                titleLabel.setStyle("-fx-font-size: 18px; -fx-font-weight: bold; -fx-text-fill: #333;");

                Label behaviorLabel = new Label("Comportamento: Sem foco mostra label, com foco mostra placeholder");
                behaviorLabel.setStyle("-fx-font-size: 12px; -fx-text-fill: #666; -fx-font-style: italic;");

                // TextField Outlined (padrão) - com label flutuante
                TextField_ outlinedField = new TextField_("", "outlined", "Nome completo");
                outlinedField.modifier()
                                .placeholder("Digite seu nome completo aqui...");

                // TextField Filled - com label flutuante
                TextField_ filledField = new TextField_("", "filled", "Email");
                filledField.modifier()
                                .placeholder("seu.email@exemplo.com");

                // TextField Standard - com label flutuante
                TextField_ standardField = new TextField_("", "standard", "Telefone");
                standardField.modifier()
                                .placeholder("(11) 99999-9999");

                // TextField Multiline (TextArea) - com label flutuante
                TextField_ multilineField = new TextField_("", "outlined", "Descrição");
                multilineField.modifier()
                                .placeholder("Digite uma descrição detalhada aqui...\nPode ter múltiplas linhas...");

                // TextField com valor inicial - demonstra label flutuante
                TextField_ withValueField = new TextField_("João Silva", "filled", "Nome do usuário");
                withValueField.modifier()
                                .placeholder("Digite o nome completo");

                // TextField sem label para comparação
                TextField_ noLabelField = new TextField_("", "outlined");
                noLabelField.modifier()
                                .placeholder("Campo sem label");

                // Adicionar todos os campos ao layout
                root.getChildren().addAll(
                                titleLabel,
                                behaviorLabel,
                                outlinedField,
                                filledField,
                                standardField,
                                multilineField,
                                withValueField,
                                noLabelField);

                Scene scene = new Scene(root, 500, 700);
                primaryStage.setScene(scene);
                primaryStage.show();
        }

        public static void main(String[] args) {
                launch(args);
        }
}
