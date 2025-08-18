package my_app.screens.scenes.DataScene;

import javafx.collections.FXCollections;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;

public class DataScene extends Scene {

    private VBox dataListContainer; // Onde os dados criados serão exibidos

    public DataScene(Stage primaryStage, Scene mainScene) {
        super(new VBox(), 1200, 650);

        VBox root = (VBox) this.getRoot();
        root.setSpacing(15);
        root.setStyle("-fx-background-color: #f0f0f0; -fx-padding: 20;");

        // Botão voltar
        Button btnBack = new Button("Voltar");
        btnBack.setOnAction(e -> primaryStage.setScene(mainScene));
        root.getChildren().add(btnBack);

        // Botão para criar novo dado
        Button btnCreateData = new Button("Create Data");
        root.getChildren().add(btnCreateData);

        // Container para as Rows de criação de dados
        VBox createDataContainer = new VBox(10);
        root.getChildren().add(createDataContainer);

        // Container que lista todos os dados criados
        dataListContainer = new VBox(10);
        root.getChildren().add(dataListContainer);

        btnCreateData.setOnAction(e -> addNewDataRow(createDataContainer));
    }

    // Adiciona uma nova Row para criação de dado
    private void addNewDataRow(VBox container) {
        HBox row = new HBox(10);
        row.setStyle(
                "-fx-padding: 10; -fx-border-color: gray; -fx-border-radius: 5; -fx-background-radius: 5; -fx-background-color: #ffffff;");

        // Select tipo
        ComboBox<String> typeSelect = new ComboBox<>(
                FXCollections.observableArrayList("text", "number", "table", "boolean"));
        typeSelect.setPromptText("Select Type");

        // Input para nome do dado
        TextField nameInput = new TextField();
        nameInput.setPromptText("Data Name");
        nameInput.setDisable(true); // habilita somente após escolher tipo

        // Input para valor
        TextField valueInput = new TextField(); // default, será trocado se boolean
        valueInput.setDisable(true);

        // Botão salvar
        Button btnSave = new Button("Save");
        btnSave.setDisable(true);

        row.getChildren().addAll(typeSelect, nameInput, valueInput, btnSave);
        container.getChildren().add(row);

        // Lógica de interação
        typeSelect.setOnAction(ev -> {
            nameInput.setDisable(false);
            valueInput.setDisable(false);

            if (typeSelect.getValue().equals("boolean")) {
                ComboBox<String> booleanSelect = new ComboBox<>(FXCollections.observableArrayList("True", "False"));
                booleanSelect.setPromptText("Value");
                row.getChildren().set(2, booleanSelect); // substitui o input por select boolean
            } else {
                TextField valueField = new TextField();
                valueField.setPromptText("Value");
                row.getChildren().set(2, valueField);
            }
        });

        // Habilita botão salvar quando nome é digitado
        nameInput.textProperty().addListener((obs, oldText, newText) -> {
            btnSave.setDisable(newText.trim().isEmpty());
        });

        btnSave.setOnAction(ev -> {
            String type = typeSelect.getValue();
            String name = nameInput.getText();
            String value;

            if (type.equals("boolean")) {
                ComboBox<String> boolSelect = (ComboBox<String>) row.getChildren().get(2);
                value = boolSelect.getValue();
            } else {
                TextField valField = (TextField) row.getChildren().get(2);
                value = valField.getText();
            }

            addDataToList(type, name, value);

            // Remove a row de criação após salvar
            container.getChildren().remove(row);
        });
    }

    // Adiciona o dado criado no container principal
    private void addDataToList(String type, String name, String value) {
        HBox dataRow = new HBox(10);
        dataRow.setStyle(
                "-fx-padding: 8; -fx-border-color: black; -fx-border-radius: 5; -fx-background-radius: 5; -fx-background-color: #d9edf7;");

        Label lblType = new Label("Type: " + type);
        Label lblName = new Label("Name: " + name);
        Label lblValue = new Label("Value: " + value);

        dataRow.getChildren().addAll(lblType, lblName, lblValue);
        dataListContainer.getChildren().add(dataRow);
    }
}
