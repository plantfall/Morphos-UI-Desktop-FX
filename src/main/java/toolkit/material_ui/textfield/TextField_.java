package toolkit.material_ui.textfield;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Consumer;

import javafx.beans.property.SimpleStringProperty;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import toolkit.declarative_components.FXNodeContext;
import toolkit.declarative_components.modifiers.TextModifier;
import toolkit.declarative_components.modifiers.TextStyles;
import toolkit.theme.ThemeStyler;

public class TextField_ extends VBox {
    private final Map<String, String> styles = new HashMap<>();
    private TextField textField;
    private TextArea textArea;
    private Label label;
    private StackPane inputContainer;
    private String variant = "outlined"; // default variant
    private boolean isTextArea = false;
    private boolean hasFocus = false;
    private boolean hasContent = false;
    private String originalPlaceholder = ""; // Armazenar o placeholder original

    public TextField_() {
        this("");
    }

    public TextField_(String value) {
        this(value, "outlined");
    }

    public TextField_(String value, String variant) {
        this(value, variant, null);
    }

    public TextField_(String value, String variant, String labelText) {
        super(0); // no spacing between elements

        this.variant = variant;
        this.hasContent = value != null && !value.isEmpty();

        // Create input container
        this.inputContainer = new StackPane();
        this.inputContainer.setStyle("-fx-background-color: transparent;");

        // Create label if provided
        if (labelText != null && !labelText.isEmpty()) {
            this.label = new Label(labelText);
            this.label.setStyle(
                    "-fx-text-fill: #666; -fx-font-size: 14px; -fx-background-color: transparent; -fx-padding: 0;");
            this.label.setTranslateY(16);
            this.label.setTranslateX(12);
            StackPane.setAlignment(this.label, Pos.TOP_LEFT);
            this.inputContainer.getChildren().add(this.label);
        } else {
            this.label = null;
        }

        this.textField = new TextField(value);
        this.textArea = null;
        this.isTextArea = false;
        this.textField
                .setStyle("-fx-background-color: transparent; -fx-border-color: transparent; -fx-padding: 16 12;");
        this.inputContainer.getChildren().add(this.textField);

        // Bind text property for content detection
        this.textField.textProperty().addListener((obs, oldVal, newVal) -> {
            this.hasContent = newVal != null && !newVal.isEmpty();
            updateLabelPosition();
        });

        // Focus listeners
        this.textField.focusedProperty().addListener((obs, oldVal, newVal) -> {
            this.hasFocus = newVal;
            updateLabelPosition();
            updateBorderStyle();
        });

        // Add input container to main layout
        getChildren().add(this.inputContainer);

        // Apply variant styling
        applyVariant();

        // Atualizar posição inicial da label (sem verificar placeholder ainda)
        updateInitialLabelPosition();

        // Add to context
        FXNodeContext.add(this);
    }

    public TextField_(String value, Consumer<InnerModifier> withModifier) {
        this(value);
        withModifier.accept(new InnerModifier(this));
        // Add to context
        FXNodeContext.add(this);
    }

    public TextField_(String placeholder, SimpleStringProperty input) {
        this();
        if (isTextArea) {

            setPromptText(placeholder); // <-- usa o método customizado
            this.textArea.textProperty().bindBidirectional(input);
        } else {
            setPromptText(placeholder); // <-- usa o método customizado
            this.textField.textProperty().bindBidirectional(input);
        }
        // Add to context
        FXNodeContext.add(this);
    }

    private void updateInitialLabelPosition() {
        if (this.label == null)
            return;

        // Posição inicial da label (dentro do campo)
        this.label.setTranslateY(16);
        this.label.setTranslateX(12);
        this.label.setStyle(
                "-fx-text-fill: #666; -fx-font-size: 14px; -fx-background-color: transparent; -fx-padding: 0;");
    }

    private void updateLabelPosition() {
        if (this.label == null)
            return;

        // Verificar se há placeholder definido
        boolean hasPlaceholder = this.originalPlaceholder != null && !this.originalPlaceholder.isEmpty();

        if (this.hasFocus || this.hasContent) {
            // Label flutuante - acima da borda superior
            this.label.setTranslateY(-12);
            this.label.setTranslateX(8);
            this.label.setStyle(
                    "-fx-text-fill: #1976d2; -fx-font-size: 12px; -fx-background-color: white; -fx-padding: 0 4; -fx-font-weight: bold;");

            // Se há placeholder e não há conteúdo, mostrar o placeholder no campo
            if (hasPlaceholder && !this.hasContent) {
                if (isTextArea) {
                    this.textArea.setPromptText(this.originalPlaceholder);
                } else {
                    this.textField.setPromptText(this.originalPlaceholder);
                }
            }
        } else {
            // Label dentro do campo (como placeholder)
            this.label.setTranslateY(16);
            this.label.setTranslateX(12);
            this.label.setStyle(
                    "-fx-text-fill: #666; -fx-font-size: 14px; -fx-background-color: transparent; -fx-padding: 0;");

            // Se há placeholder, ocultá-lo quando a label está visível
            if (hasPlaceholder) {
                if (isTextArea) {
                    this.textArea.setPromptText("");
                } else {
                    this.textField.setPromptText("");
                }
            }
        }
    }

    private void updateBorderStyle() {
        String borderStyle = "";

        if (this.hasFocus) {
            borderStyle = "-fx-border-color: #1976d2; -fx-border-width: 2;";
        } else if (this.hasContent) {
            borderStyle = "-fx-border-color: #666; -fx-border-width: 1;";
        } else {
            borderStyle = "-fx-border-color: #ccc; -fx-border-width: 1;";
        }

        this.inputContainer.setStyle(this.inputContainer.getStyle() + ";" + borderStyle);
    }

    private void applyVariant() {
        String baseStyle = "";

        switch (variant) {
            case "outlined":
                baseStyle = "-fx-background-color: white; -fx-border-color: #ccc; -fx-border-width: 1; -fx-border-radius: 4; -fx-padding: 0;";
                break;
            case "filled":
                baseStyle = "-fx-background-color: #f5f5f5; -fx-border-color: transparent; -fx-border-width: 0; -fx-border-radius: 4; -fx-padding: 0;";
                break;
            case "standard":
                baseStyle = "-fx-background-color: transparent; -fx-border-color: #ccc; -fx-border-width: 0 0 1 0; -fx-padding: 0;";
                break;
        }

        this.inputContainer.setStyle(baseStyle);
        updateBorderStyle();
    }

    private void updateStyles() {
        StringBuilder sb = new StringBuilder();
        styles.forEach((k, v) -> sb.append(k).append(": ").append(v).append(";"));

        this.inputContainer.setStyle(this.inputContainer.getStyle() + ";" + sb.toString());
    }

    public void defaultFocusColor() {
        setFocusColor("#1976d2");
        setBorderWidth(1);
        setBorderRadius(4);
    }

    public void applyTheme() {
        ThemeStyler.textField(this);
    }

    public InnerModifier modifier() {
        return new InnerModifier(this);
    }

    public void setFocusColor(String colorHex) {
        styles.put("-fx-focus-color", colorHex);
        styles.put("-fx-faint-focus-color", "transparent");
        updateStyles();
    }

    public void setBorderWidth(int width) {
        styles.put("-fx-border-width", width + "");
        updateStyles();
    }

    public void setBorderRadius(int radius) {
        styles.put("-fx-border-radius", radius + "");
        updateStyles();
    }

    // Getters and setters for Material-UI compatibility
    public String getLabel() {
        return label != null ? label.getText() : "";
    }

    public void setLabel(String labelText) {
        if (label != null) {
            label.setText(labelText);
        } else if (labelText != null && !labelText.isEmpty()) {
            Label newLabel = new Label(labelText);
            newLabel.setStyle(
                    "-fx-text-fill: #666; -fx-font-size: 14px; -fx-background-color: transparent; -fx-padding: 0;");
            newLabel.setTranslateY(16);
            newLabel.setTranslateX(12);
            StackPane.setAlignment(newLabel, Pos.TOP_LEFT);
            this.inputContainer.getChildren().add(0, newLabel);
            this.label = newLabel;
            updateLabelPosition();
        }
    }

    public String getVariant() {
        return variant;
    }

    public void setVariant(String variant) {
        this.variant = variant;
        applyVariant();
    }

    public String getText() {
        if (isTextArea) {
            return this.textArea.getText();
        } else {
            return this.textField.getText();
        }
    }

    public void setText(String text) {
        if (isTextArea) {
            this.textArea.setText(text);
        } else {
            this.textField.setText(text);
        }
        this.hasContent = text != null && !text.isEmpty();
        updateLabelPosition();
    }

    public String getPromptText() {
        if (isTextArea) {
            return this.textArea.getPromptText();
        } else {
            return this.textField.getPromptText();
        }
    }

    public void setPromptText(String promptText) {

        // Armazenar o placeholder original
        this.originalPlaceholder = promptText != null ? promptText : "";

        if (isTextArea) {
            this.textArea.setPromptText(promptText);
        } else {
            this.textField.setPromptText(promptText);
        }
        // Atualizar a posição da label quando o placeholder for alterado
        updateLabelPosition();
    }

    public static class InnerModifier extends TextModifier<TextField_> {
        public InnerModifier(TextField_ node) {
            super(node);
        }

        @Override
        public InnerModifier fontSize(double fontSize) {
            node.styles.put("-fx-font-size", fontSize + "px");
            node.updateStyles();
            return this;
        }

        @Override
        public InnerModifier font(Font font) {
            if (node.isTextArea) {
                node.textArea.setFont(font);
            } else {
                node.textField.setFont(font);
            }
            return this;
        }

        public InnerModifier applyTheme() {
            node.applyTheme();
            return this;
        }

        public InnerModifier label(String labelText) {
            node.setLabel(labelText);
            return this;
        }

        public InnerModifier variant(String variant) {
            node.setVariant(variant);
            return this;
        }

        public InnerModifier placeholder(String placeholder) {
            node.setPromptText(placeholder);
            return this;
        }

        @Override
        public InnerStyles styles() {
            return new InnerStyles(this);
        }

        public static class InnerStyles extends TextStyles<TextField_> {
            public InnerStyles(InnerModifier modifier) {
                super(modifier);
            }

            @Override
            public InnerStyles color(Color color) {
                modifier.getNode().styles.put("-fx-text-fill", colorToHex(color));
                modifier.getNode().updateStyles();
                return this;
            }

            @Override
            public InnerStyles fontWeight(String weight) {
                modifier.getNode().styles.put("-fx-font-weight", weight);
                modifier.getNode().updateStyles();
                return this;
            }

            public InnerStyles focusColor(String colorHex) {
                modifier.getNode().setFocusColor(colorHex);
                return this;
            }

            public InnerStyles borderRadius(int radius) {
                modifier.getNode().setBorderRadius(radius);
                return this;
            }

            public InnerStyles borderWidth(int width) {
                modifier.getNode().setBorderWidth(width);
                return this;
            }

            private String colorToHex(Color color) {
                return String.format("#%02X%02X%02X",
                        (int) (color.getRed() * 255),
                        (int) (color.getGreen() * 255),
                        (int) (color.getBlue() * 255));
            }
        }
    }
}
